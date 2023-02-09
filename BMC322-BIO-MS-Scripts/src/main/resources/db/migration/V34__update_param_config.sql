UPDATE mscompositeparamconfig 
SET value = '{ "cantidad-minima-huellas" : { "enrolador" : "10" , "cliente" : "4" }}' 
WHERE application_id = 'EnrolamientoApp' AND microservice_id = 'macro-enrolamiento' AND name = 'configuracion-cantidad-minima-huellas';