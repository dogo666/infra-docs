UPDATE mscompositeparamconfig
SET value = '{"autocaptura-dni": {"timeout-seconds": "90","detection-interval-milliseconds": "500","min-detection-milliseconds": "1500"}}'
WHERE application_id = 'EnrolamientoApp'
    AND microservice_id = 'macro-enrolamiento'
    AND name = 'configuracion-autocaptura-dni';