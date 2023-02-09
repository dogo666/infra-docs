UPDATE mscompositeparamconfig
    SET
        value = '{ "reintentos-captura-huellas-lector" : { "reintentos" : "2" }}',
        modificationdate = now()
    WHERE
        application_id = 'EnrolamientoApp'
            AND microservice_id = 'macro-enrolamiento'
            AND name = 'configuracion-reintentos-captura-huellas-lector';