SELECT nome_paciente, numero_dose, data_diagnostico, nome_doenca
FROM paciente 
JOIN vacinacao ON paciente.id_paciente = vacinacao.id_paciente
JOIN diagnostico ON paciente.id_paciente = diagnostico.id_paciente
JOIN doenca ON diagnostico.id_doenca = doenca.id_doenca;