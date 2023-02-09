UPDATE mscompositeparamconfig
    SET
        value = '{ "documentos-cobis" : [{ "id" : "01", "nombre" : "DNI"}, { "id" : "08", "nombre" : "CUIL" }, { "id" : "11", "nombre" : "CUIT" }, { "id" : "02", "nombre" : "LE" }, { "id" : "03", "nombre" : "LC" }, { "id" : "101", "nombre" : "CI" }, { "id" : "125", "nombre":"PA" }]}',
        modificationdate = now()
    WHERE
        application_id = 'EnrolamientoApp'
            AND microservice_id = 'macro-enrolamiento'
            AND name = 'configuracion-lista-documentos-cobis';