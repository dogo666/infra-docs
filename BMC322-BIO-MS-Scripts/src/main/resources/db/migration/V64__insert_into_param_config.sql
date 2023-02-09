DELETE FROM mscompositeparamconfig WHERE name = 'configuracion-reintentos-uno-pocos';

INSERT INTO mscompositeparamconfig (application_id, microservice_id, name, value, creationdate, modificationdate)
        VALUES
        	('EnrolamientoApp', 'macro-validacion', 'configuracion-reintentos-uno-pocos', '{"reintentos-uno-pocos":{"enrolador":{"local":"1","dev1":"1","stg1":"1","lab1":"1","test1":"1","pre-prd1":"1","pre-prd2":"1","prd1":"1","prd2":"1"},"cliente":{"local":"1","dev1":"1","stg1":"1","lab1":"1","test1":"1","pre-prd1":"1","pre-prd2":"1","prd1":"1","prd2":"1"}}', now(), now());
