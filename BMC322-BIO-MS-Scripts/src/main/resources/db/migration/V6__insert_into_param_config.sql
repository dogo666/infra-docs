INSERT INTO mscompositeparamconfig(application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
	('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-confidence-rostro-renaper', '{ "rostro-renaper" : { "enrolador" : "60" , "cliente" : "60" }}', now(), now());