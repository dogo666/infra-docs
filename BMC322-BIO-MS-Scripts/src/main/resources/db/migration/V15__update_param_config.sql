UPDATE mscompositeparamconfig
    SET
        value = '{ "politica-identity-x" : { "local" : "authentication", "dev1" : "authentication", "stg1" : "authentication", "lab1" : "authentication", "test1" : "authentication", "pre-prd1" : "authentication", "pre-prd2" : "authentication", "prd1" : "authentication", "prd2" : "authentication" }}',
        modificationdate = now()
    WHERE
        application_id = 'EnrolamientoApp'
            AND microservice_id = 'macro-enrolamiento'
            AND name = 'configuracion-politica-identity-x';