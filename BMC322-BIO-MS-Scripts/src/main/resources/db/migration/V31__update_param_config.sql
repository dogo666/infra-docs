
UPDATE application
    SET path_regex = '[a-zA-Z0-9/]*/[a-zA-Z0-9/]*'
    WHERE application_id = 'SupervivenciaWeb';

UPDATE mscompositeparamconfig
    SET value = '{ "politica-identity-x" : { "local" : "authentication", "dev1" : "authentication", "stg1" : "authentication", "lab1" : "authentication", "test1" : "authentication", "pre-prd1" : "authentication", "pre-prd2" : "authentication", "prd1" : "authentication", "prd2" : "authentication" }}'
    WHERE application_id = 'SupervivenciaWeb'
        AND microservice_id = 'macro-validacion'
        AND name = 'configuracion-politica-identity-x';
