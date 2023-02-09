DELETE FROM mscompositeparamconfig
WHERE name = 'configuracion-autocaptura-dni';

INSERT INTO mscompositeparamconfig (application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
    ('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-autocaptura-dni', '{"autocaptura-dni": {"timeout-seconds": "30","detection-interval-milliseconds": "500","min-detection-milliseconds": "1500"}}', now(), now());
 