SELECT 'localizacao' AS tabela, COUNT(*) AS registros FROM localizacao
UNION ALL SELECT 'escolaridade', COUNT(*) FROM escolaridade
UNION ALL SELECT 'vacina', COUNT(*) FROM vacina
UNION ALL SELECT 'doenca', COUNT(*) FROM doenca
UNION ALL SELECT 'paciente', COUNT(*) FROM paciente
UNION ALL SELECT 'vacinacao', COUNT(*) FROM vacinacao
UNION ALL SELECT 'diagnostico', COUNT(*) FROM diagnostico;
