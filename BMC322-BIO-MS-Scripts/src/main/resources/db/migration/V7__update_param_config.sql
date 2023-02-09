UPDATE mscompositeparamconfig
    SET
        value = '{ "huella-renaper" : { "enrolador" : { 1 , 6 , 2 , 7 , 3 , 8 , 4 , 9 , 5 , 10 }, "cliente" : {1 , 6 , 2 , 7}, "alternativas-cliente" : { 3 , 8 , 4 , 9 , 5 , 10}}'
    WHERE
        application_id = 'EnrolamientoApp'
            AND microservice_id = 'macro-enrolamiento'
            AND name = 'configuracion-toma-huella-rol-renaper';