**Nome: Talita Lima das Mercês**


Sobre o Projeto

O objetivo deste projeto é compreender e aplicar os conceitos de banco
de dados relacional e estrutura de dados. O contexto escolhido é o da
vacinação, um avanço da humanidade que vem sendo tratado de forma
negligente. A vacinação vem sendo evitada por falta de conhecimento e
por informações errôneas que geram comoções desnecessárias e o
reaparecimento de doenças há muito erradicadas.

Para entender a situação a fundo e permitir uma análise correta de
políticas futuras, é muito importante que essas informações sejam
armazenadas de forma adequada. Neste trabalho, será utilizado o formato
de armazenamento SGBD --- Sistema Gerenciador de Banco de Dados, mais
especificamente um Banco de Dados Relacional.

O Banco de Dados Relacional organiza dados em tabelas que se conectam
por meio de chaves (relacionamentos). Ele é ideal para o armazenamento
de informações de forma organizada, pois não apenas guarda os dados, mas
também garante o estabelecimento de regras para relacionamento e
modelagem, o que permite uma estrutura detalhada e precisa.

O que preciso resolver

Criar um banco de dados com as informações relevantes a serem estudadas,
com um sistema de interação que permita visualizá-las para análise.

Documentação de Resolução

O primeiro passo é elaborar um MER (Modelo de Entidade e Relacionamento)
e um DER (Diagrama de Entidade e Relacionamento). Antes disso, é
necessário responder às seguintes perguntas:

Entidades

- Paciente

- Vacina

- Vacinação

- Localização

- Doença

- Diagnóstico

- Escolaridade

Atributos

**Paciente:**

id_paciente, nome_paciente, idade_paciente, telefone_paciente,
endereco_paciente, id_localizacao, id_escolaridade

**Vacina:**

id_vacina, tipo_vacina, fabricante_vacina

**Vacinação:**

id_vacinacao, id_paciente, id_vacina, data_aplicacao, numero_dose

**Localização:**

id_localizacao, regiao, cidade, estado

**Doença:**

id_doenca, nome_doenca, descricao_doenca

**Diagnóstico:**

id_diagnostico, id_paciente, id_doenca, data_diagnostico

**Escolaridade:**

id_escolaridade, nivel_escolaridade

Relacionamentos

- Paciente --- Vacinação

- Paciente --- Localização

- Paciente --- Diagnóstico

- Vacina --- Doença

- Paciente --- Escolaridade

- Vacinação --- Vacina

Cardinalidade dos Relacionamentos

- Paciente --- Vacinação: 1:N

- Vacina --- Vacinação: 1:N

- Paciente --- Localização: N:1

- Paciente --- Diagnóstico: 1:N

- Vacina --- Doença: 1:N

- Paciente --- Escolaridade: N:1

Criação do Banco de Dados

Ao popular o banco de dados com registros aleatórios, foi necessário
respeitar o conceito de tabelas base de prioridade: as tabelas que não
dependem de outras devem ser criadas primeiro. A ordem seguida foi:

doença → vacina → localização → escolaridade → paciente → vacinação →
diagnóstico

População dos Dados

Para este projeto, optou-se pela criação de dados fictícios gerados por
uma plataforma de inteligência artificial, em vez da importação de dados
reais. Os parâmetros definidos para a geração foram:

- Vacinas e fabricantes: as 3 mais comuns

- Doenças: Hepatite B, Hepatite D, Difteria e tétano, Febre amarela,
  Sarampo, Caxumba, Rubéola, Síndrome da rubéola congênita e Varicela

- Regiões/cidades/estados: todas as regiões e estados do Brasil

- Níveis de escolaridade: Fundamental, Médio e Superior

- Pacientes fictícios: 100

Queries de Análise

Após a população dos dados, foram criadas as queries para as análises
solicitadas no enunciado. O arquivo consultas.sql reúne todas as
consultas principais.

**Vacinado x Doente:**

SELECT nome_paciente, nome_doenca, SUM(numero_dose) AS total_doses

FROM paciente

JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente

JOIN diagnostico ON paciente.id_paciente = diagnostico.id_paciente

JOIN doenca ON diagnostico.id_doenca = doenca.id_doenca

GROUP BY nome_paciente, nome_doenca;

**Vacinado x Escolaridade:**

SELECT nivel_escolaridade, COUNT(DISTINCT vacinacao.id_paciente) AS
total_vacinados

FROM escolaridade

JOIN paciente ON escolaridade.id_escolaridade = paciente.id_escolaridade

JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente

GROUP BY nivel_escolaridade;

**Vacinado x Região:**

SELECT regiao, COUNT(DISTINCT vacinacao.id_paciente) AS total_vacinados

FROM localizacao

JOIN paciente ON localizacao.id_localizacao = paciente.id_localizacao

JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente

GROUP BY regiao;

Interface em Java

Com as queries definidas, foi desenvolvida a interface em Java. A
conexão com o banco de dados foi feita por meio do Connector/J. Após as
importações necessárias, a interface foi construída utilizando a
estrutura de switch-case, com ajustes finais para melhor experiência do
usuário.

*Nota: a senha está definida diretamente no código para facilitar a
avaliação. Em ambiente de produção, o ideal é lê-la de uma variável de
ambiente ou arquivo de configuração externo, evitando expor credenciais
no código-fonte.*

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;

import java.util.Scanner;

public class InterfaceJava {

public static void main(String\[\] args) {

String url = \"jdbc:mysql://localhost:3306/vacinacao\";

String usuario = \"root\";

String senha = \"senhasql\"; // Substituir pela senha local

try {

Connection conexao = DriverManager.getConnection(url, usuario, senha);

System.out.println(\"Conexão estabelecida com sucesso!\");

Scanner leia = new Scanner(System.in);

System.out.println(\"Bem-Vindo ao Banco de Dados de Vacinação\");

System.out.println(\"Escolha sua opção de análise e visualize\");

System.out.println(\"Opção: 1 - Análise Vacinado X Doente\");

System.out.println(\"Opção: 2 - Análise Vacinado X Escolaridade\");

System.out.println(\"Opção: 3 - Análise Vacinado X Região\");

int opcao = leia.nextInt();

switch (opcao) {

case 1: {

String sql = \"SELECT nome_paciente, nome_doenca, SUM(numero_dose) AS
total_doses \" +

\"FROM paciente \" +

\"JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente \" +

\"JOIN diagnostico ON paciente.id_paciente = diagnostico.id_paciente \"
+

\"JOIN doenca ON diagnostico.id_doenca = doenca.id_doenca \" +

\"GROUP BY nome_paciente, nome_doenca;\";

Statement stmt = conexao.createStatement();

ResultSet rs = stmt.executeQuery(sql);

while (rs.next()) {

System.out.println(\"Nome: \" + rs.getString(\"nome_paciente\") +

\" / Doença: \" + rs.getString(\"nome_doenca\") +

\" / Total de Doses: \" + rs.getInt(\"total_doses\"));

}

break;

}

case 2: {

String sql = \"SELECT nivel_escolaridade, COUNT(DISTINCT
vacinacao.id_paciente) AS total_vacinados \" +

\"FROM escolaridade \" +

\"JOIN paciente ON escolaridade.id_escolaridade =
paciente.id_escolaridade \" +

\"JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente \" +

\"GROUP BY nivel_escolaridade;\";

Statement stm = conexao.createStatement();

ResultSet rs = stm.executeQuery(sql);

while (rs.next()) {

System.out.println(\"Nível Escolar: \" +
rs.getString(\"nivel_escolaridade\") +

\" / Total Vacinados: \" + rs.getInt(\"total_vacinados\"));

}

break;

}

case 3: {

String sql = \"SELECT regiao, COUNT(DISTINCT vacinacao.id_paciente) AS
total_vacinados \" +

\"FROM localizacao \" +

\"JOIN paciente ON localizacao.id_localizacao = paciente.id_localizacao
\" +

\"JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente \" +

\"GROUP BY regiao;\";

Statement stm = conexao.createStatement();

ResultSet rs = stm.executeQuery(sql);

while (rs.next()) {

System.out.println(\"Região: \" + rs.getString(\"regiao\") +

\" / Total Vacinados: \" + rs.getInt(\"total_vacinados\"));

}

break;

}

default:

System.out.println(\"Opção inválida. Por favor escolha: 1, 2 ou 3\");

break;

}

System.out.println(\"\\nObrigada por usar o Sistema de Vacinação\");

} catch (SQLException e) {

e.printStackTrace();

}

}

}
