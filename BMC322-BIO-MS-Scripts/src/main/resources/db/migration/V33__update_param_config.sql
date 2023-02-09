UPDATE mscompositeparamconfig 
SET value = '{"validar-huella-renaper": {"local": "false","dev1": "false","stg1": "false","lab1": "false","test1": "false","pre-prd1": "false","pre-prd2": "false","prd1": "false","prd2": "false"}}' 
WHERE application_id = 'EnrolamientoApp' AND microservice_id = 'macro-enrolamiento' AND name = 'configuracion-validar-huella-renaper';
