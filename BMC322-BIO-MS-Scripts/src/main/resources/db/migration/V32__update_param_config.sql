UPDATE mscompositeparamconfig t
SET t.value = '{"aplicacion-identity-x": {"local": "globantEvalutationtest","dev1": "globantEvalutationtest","stg1": "globantEvalutationtest","lab1": "globantEvalutationtest","test1": "globantEvalutationtest","pre-prd1": "Evaluation_WebEnrolamiento","pre-prd2": "Evaluation_WebEnrolamiento","prd1": "Evaluation_WebEnrolamiento","prd2": "Evaluation_WebEnrolamiento"}}'
WHERE t.name = 'configuracion-politica-evaluacion-identity-x'
  AND t.application_id = 'EnrolamientoApp'
  AND t.microservice_id = 'macro-enrolamiento';

UPDATE mscompositeparamconfig t
SET t.value = '{ "politica-identity-x" : { "local" : "authentication", "dev1" : "authentication", "stg1" : "authentication", "lab1" : "authentication", "test1" : "authentication", "pre-prd1" : "BioFace_OperacionesSimples", "pre-prd2" : "BioFace_OperacionesSimples", "prd1" : "BioFace_OperacionesSimples", "prd2" : "BioFace_OperacionesSimples" }}'
WHERE t.name = 'configuracion-politica-identity-x'
  AND t.application_id IN ('SupervivenciaWeb','EnrolamientoApp')
  AND t.microservice_id = 'macro-validacion';

UPDATE mscompositeparamconfig t
SET t.value = '{ "politica-identity-x" : { "local" : "authentication", "dev1" : "authentication", "stg1" : "authentication", "lab1" : "authentication", "test1" : "authentication", "pre-prd1" : "BioFace_GuruSucursal", "pre-prd2" : "BioFace_GuruSucursal", "prd1" : "BioFace_GuruSucursal", "prd2" : "BioFace_GuruSucursal" }}'
WHERE t.name = 'configuracion-politica-identity-x'
  AND t.application_id = 'GuruClavesApplicationId'
  AND t.microservice_id = 'macro-validacion';

UPDATE mscompositeparamconfig t
SET t.value = '{ "aplicacion-identity-x" : { "local" : "banco_macro_app", "dev1" : "banco_macro_app", "stg1" : "banco_macro_app", "lab1" : "banco_macro_app", "test1" : "banco_macro_app", "pre-prd1" : "Validacion_de_FACE", "pre-prd2" : "Validacion_de_FACE", "prd1" : "Validacion_de_FACE", "prd2" : "Validacion_de_FACE" }}'
WHERE t.name = 'configuracion-aplicacion-identity-x'
  AND t.microservice_id = 'macro-validacion';