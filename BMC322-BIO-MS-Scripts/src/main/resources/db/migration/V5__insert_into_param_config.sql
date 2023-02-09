INSERT INTO mscompositeparamconfig(application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
	('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-match-score-huella-renaper', '{ "huella-renaper" : { "enrolador" : "2000" , "cliente" : "2000" }}', now(), now()),
	('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-reintentos-huella-renaper', '{ "huella-renaper" : { "enrolador" : "3" , "cliente" : "3" }}', now(), now()),
	('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-toma-huella-rol-renaper', '{ "huella-renaper" : { "enrolador" : {1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10}, "cliente" : {1 , 2 , 6 , 7}}', now(), now())
	;