UPDATE mscompositeparamconfig
    SET
        value = '{ "reintentos-rostro" : { "enrolador" : "2" , "cliente" : "2" }}',
        modificationdate = now()
    WHERE
        application_id = 'EnrolamientoApp'
            AND microservice_id = 'macro-enrolamiento'
            AND name = 'configuracion-reintentos-rostro';
