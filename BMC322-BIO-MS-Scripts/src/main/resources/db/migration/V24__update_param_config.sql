UPDATE mscompositeparamconfig t
SET t.value = '{ "cantidad-minima-huellas" : { "enrolador" : "2" , "cliente" : "2" }}'
WHERE t.application_id = 'EnrolamientoApp'
AND t.microservice_id = 'macro-enrolamiento'
AND t.name = 'configuracion-cantidad-minima-huellas';