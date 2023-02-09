UPDATE mscompositeparamconfig
    SET value = '{"politica-identity-x": {"local": "Liveness_WebCheck","dev1": "Liveness_WebCheck","stg1": "Liveness_WebCheck","lab1": "Liveness_WebCheck","test1": "Liveness_WebCheck","pre-prd1": "Liveness_WebCheck","pre-prd2": "Liveness_WebCheck","prd1": "Liveness_WebCheck","prd2": "Liveness_WebCheck"}}',
    modificationdate = now()
WHERE 1=1
AND microservice_id = 'macro-validacion'
AND name = 'configuracion-politica-3dfl-identity-x';
