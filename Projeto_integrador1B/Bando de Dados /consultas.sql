-- Vacinado x Doente

SELECT nome_paciente, nome_doenca, SUM(numero_dose) AS total_doses
FROM paciente  
JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente
JOIN diagnostico ON paciente.id_paciente = diagnostico.id_paciente
JOIN doenca ON diagnostico.id_doenca = doenca.id_doenca
GROUP BY nome_paciente, nome_doenca;



-- Vacinado x Escolaridade

SELECT nivel_escolaridade, COUNT(DISTINCT vacinacao.id_paciente)  AS total_vacinados
FROM escolaridade
JOIN paciente ON escolaridade.id_escolaridade = paciente.id_escolaridade
JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente
GROUP BY nivel_escolaridade;


-- Vacinado x Região

SELECT regiao, COUNT(DISTINCT vacinacao.id_paciente)  AS total_vacinados
FROM localizacao
JOIN paciente ON localizacao.id_localizacao = paciente.id_localizacao
JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente
GROUP BY regiao; 

