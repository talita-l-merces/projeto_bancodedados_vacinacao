import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Scanner;

public class InterfaceJava {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/vacinacao";
		String usuario = "root";
		String senha ="minha_senha";
		try {
			Connection conexao = DriverManager.getConnection(url,usuario,senha);
		System.out.println("Conexão estabelecida com sucesso!");
		Scanner leia = new Scanner(System.in);
		System.out.println("Bem-Vindo ao Banco de Dados de Vacinação");
	    System.out.println("Escolha sua opção de análise e visualize");
	    System.out.println("Opção:1 - Análise Vacinado X Doente");
	    System.out.println("Opção:2 - Análise Vacinado X Escolaridade");
	    System.out.println("Opção:3 - Análise Vacinado X Regiao");
	    
	    
	    int opcao = leia.nextInt();
	    
	    switch(opcao) {
	    case 1:  {
	    	String sql = "SELECT nome_paciente, nome_doenca, SUM(numero_dose) AS total_doses\n"
	    			+ "FROM paciente  \n"
	    			+ "JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente\n"
	    			+ "JOIN diagnostico ON paciente.id_paciente = diagnostico.id_paciente\n"
	    			+ "JOIN doenca ON diagnostico.id_doenca = doenca.id_doenca\n"
	    			+ "GROUP BY nome_paciente, nome_doenca;";
	    	Statement stmt = conexao.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	while (rs.next()) {
	    		System.out.println("Nome: " + rs.getString("nome_paciente") + "/ Doença:  " + rs.getString("nome_doenca") + " Total de Doses: " + rs.getInt("total_doses"));	
	    	}
	    	break;
	    }
	    	
	    case 2:{
	    	String sql = "SELECT nivel_escolaridade, COUNT(DISTINCT vacinacao.id_paciente)  AS total_vacinados\n"
	    			+ "FROM escolaridade\n"
	    			+ "JOIN paciente ON escolaridade.id_escolaridade = paciente.id_escolaridade\n"
	    			+ "JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente\n"
	    			+ "GROUP BY nivel_escolaridade;";
	    	Statement stm = conexao.createStatement();
	    	ResultSet rs = stm.executeQuery(sql);
	    	while (rs.next()) {
	    		System.out.println( "Nível Escolar: "+ rs.getString("nivel_escolaridade")+ "/ Total Vacinados: " + rs.getInt("total_vacinados"));
	    	}
	    	break;
	    }
	    	
	    case 3:{
	    	String sql = "SELECT regiao, COUNT(DISTINCT vacinacao.id_paciente)  AS total_vacinados\n"
	    			+ "FROM localizacao\n"
	    			+ "JOIN paciente ON localizacao.id_localizacao = paciente.id_localizacao\n"
	    			+ "JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente\n"
	    			+ "GROUP BY regiao; ";
	    	Statement stm = conexao.createStatement();
	    	ResultSet rs = stm.executeQuery(sql);
	    	while (rs.next()) {
	    		System.out.println( "Região: "+ rs.getString("regiao")+ "/ Total Vacinado  " + rs.getInt("total_vacinados"));
	    	}
	    	break;
	    	
	    }
	    default:
    		System.out.println("Opção invalida. Por favor escolha: 1, 2 ou 3");
    		break;
	    } System.out.println("\n Obrigada por usar o Sistema de Vacinação");
		
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
}