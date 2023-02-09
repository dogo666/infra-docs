UPDATE mscompositeparamconfig
    SET
        value = '{ "aplicacion-identity-x" : { "local" : "banco_macro_app", "dev1" : "banco_macro_app", "stg1" : "banco_macro_app", "lab1" : "banco_macro_app", "test1" : "banco_macro_app", "pre-prd1" : "banco_macro_app", "pre-prd2" : "banco_macro_app", "prd1" : "banco_macro_app", "prd2" : "banco_macro_app" }}',
        modificationdate = now()
    WHERE
        application_id = 'EnrolamientoApp'
            AND microservice_id = 'macro-enrolamiento'
            AND name = 'configuracion-aplicacion-identity-x';