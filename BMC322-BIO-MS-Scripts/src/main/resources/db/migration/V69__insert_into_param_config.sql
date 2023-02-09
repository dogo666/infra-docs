DELETE FROM mscompositeparamconfig 
	WHERE application_id = 'EnrolamientoApp' AND microservice_id = 'macro-enrolamiento' AND name = 'configuracion-toma-huella-rol-renaper';

INSERT INTO mscompositeparamconfig(application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
	('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-toma-huella-rol-renaper', '{ "huella-renaper" : { "enrolador" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ], "cliente" : [ 1, 6, 2, 7 ], "alternativas-cliente" : [ 3, 8, 4, 9, 5, 10 ]}}', now(), now())
	;