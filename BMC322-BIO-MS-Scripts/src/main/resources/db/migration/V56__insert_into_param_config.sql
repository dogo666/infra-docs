INSERT INTO mscompositeparamconfig (application_id, microservice_id, name, value, creationdate, modificationdate)
VALUES
    ('EnrolamientoApp', 'macro-enrolamiento', 'configuracion-autocaptura-dni', '{"autocaptura-dni": { "actived": "true", "timeout-seconds": "90", "detection-interval-milliseconds": "500", "min-detection-milliseconds": "1500", "image-margin": "50" }}', now(), now());
 