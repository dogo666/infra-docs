INSERT INTO mssingleparamconfig(application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
	('EnrolamientoApp', 'macro-enrolamiento', 'mesesvigencia', '6', now(), now()),
	('EnrolamientoApp', 'macro-enrolamiento', 'dias-entrega-domicilio', '3', now(), now()),
	('EnrolamientoApp', 'macro-enrolamiento', 'dias-entrega-sucursal', '1', now(), now()),
	('EnrolamientoApp', 'macro-validacion', 'dias-entrega-sucursal', '8', now(), now())
	;

INSERT INTO mscompositeparamconfig(application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
	('EnrolamientoApp', 'macro-enrolamiento', 'url-hash', '{ "landing-page" : { "base-url" : {"dev1":"https://dev-bmc322.globant.com/tramiteonline/contacto/","stg1":"https://stg-bmc322.globant.com/tramiteonline/contacto/","test1":"https://tdmob-test.macro.com.ar/tramiteonline/contacto/","pre-prd1":"https://prep.macro.com.ar/tramiteonline/contacto/","pre-prd2":"https://prep.macro.com.ar/tramiteonline/contacto/","prd1":"https://www.macro.com.ar/tramiteonline/contacto/","prd2":"https://www.macro.com.ar/tramiteonline/contacto/"}}}', now(), now()),
	('EnrolamientoApp', 'macro-enrolamiento', 'toc', '{ "toc" : { "mode" : 1, "liveness" : { "intentos-permitidos": 6, "horas-bloqueo" : 48 }}}', now(), now())
	;