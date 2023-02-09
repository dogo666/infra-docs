DELETE FROM mscompositeparamconfig 
	WHERE application_id = 'EnrolamientoApp' AND microservice_id = 'macro-enrolamiento' AND name = 'configuracion-timeout-sesion-milisegundos';

INSERT INTO mscompositeparamconfig(application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
	('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-timeout-sesion-milisegundos', '{"timeout-sesion-milisegundos": {"local": "300000","dev1": "300000","stg1": "300000","lab1": "300000","test1": "300000","pre-prd1": "300000","pre-prd2": "300000","prd1": "300000","prd2": "300000"}}', now(), now())
	;