INSERT INTO mscompositeparamconfig (application_id,microservice_id,name,value,creationdate,modificationdate) 
VALUES
	 ('eResumen','macro-validacion','configuracion-politica-identity-x','{ "politica-identity-x" : { "local" : "authentication", "dev1" : "authentication", "stg1" : "authentication", "lab1" : "authentication", "test1" : "authentication", "pre-prd1" : "BioFace_OperacionesSimples", "pre-prd2" : "BioFace_OperacionesSimples", "prd1" : "BioFace_OperacionesSimples", "prd2" : "BioFace_OperacionesSimples" }}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-aplicacion-identity-x','{ "aplicacion-identity-x" : { "local" : "banco_macro_app", "dev1" : "banco_macro_app", "stg1" : "banco_macro_app", "lab1" : "banco_macro_app", "test1" : "banco_macro_app", "pre-prd1" : "Validacion_de_FACE", "pre-prd2" : "Validacion_de_FACE", "prd1" : "Validacion_de_FACE", "prd2" : "Validacion_de_FACE" }}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-huellas-daon-engine','{ "manoDerecha" : { "pulgar" : 21, "indice" : 22, "mayor" : 23, "anular" : 24, "menique" : 25}, "manoIzquierda" : { "pulgar" : 15, "indice" : 14, "mayor" : 13, "anular" : 12, "menique" : 11 }}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-huellas-renaper','{ "manoDerecha" : { "pulgar" : 1, "indice" : 2, "mayor" : 3, "anular" : 4, "menique" : 5}, "manoIzquierda" : { "pulgar" : 6, "indice" : 7, "mayor" : 8, "anular" : 9, "menique" : 10 }}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-umbral-politica-daon-engine','{"umbral-politica-daon-engine": {"local": "1.0E-4","dev1": "1.0E-4","stg1": "1.0E-4","lab1": "1.0E-4","test1": "1.0E-4","pre-prd1": "1.0E-4","pre-prd2": "1.0E-4","prd1": "1.0E-4","prd2": "1.0E-4"}}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-politica-3dfl-identity-x','{"politica-identity-x": {"local": "Liveness_WebCheck","dev1": "Liveness_WebCheck","stg1": "Liveness_WebCheck","lab1": "Liveness_WebCheck","test1": "Liveness_WebCheck","pre-prd1": "Liveness_WebCheck","pre-prd2": "Liveness_WebCheck","prd1": "Liveness_WebCheck","prd2": "Liveness_WebCheck"}}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-politica-evaluacion-identity-x','{"aplicacion-identity-x": {"local": "globantEvalutationtest","dev1": "globantEvalutationtest","stg1": "globantEvalutationtest","lab1": "globantEvalutationtest","test1": "globantEvalutationtest","pre-prd1": "globantEvalutationtest","pre-prd2": "globantEvalutationtest","prd1": "globantEvalutationtest","prd2": "globantEvalutationtest"}}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-validacion-renaper-3dfl-identity-x','{"validacion-renaper-3dfl-identity-x": {"local": "true","dev1": "true","stg1": "true","lab1": "true","test1": "true","pre-prd1": "true","pre-prd2": "true","prd1": "true","prd2": "true"}}', now(), now()),
	 ('eResumen','macro-validacion','configuracion-umbral-renaper-3dfl-identity-x','{"umbral-renaper-3dfl-identity-x": {"local": "60","dev1": "60","stg1": "60","lab1": "60","test1": "60","pre-prd1": "60","pre-prd2": "60","prd1": "60","prd2": "60"}}', now(), now())
	 ;
