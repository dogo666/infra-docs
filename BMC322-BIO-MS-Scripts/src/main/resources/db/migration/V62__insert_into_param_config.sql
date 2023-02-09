
DELETE FROM mscompositeparamconfig  WHERE name = 'configuracion-fido-politica-identity-x';

INSERT INTO mscompositeparamconfig (application_id,microservice_id,name,value,creationdate,modificationdate)
VALUES ('EnrolamientoApp','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('BlanqueoPinWeb','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('MandatoExtraccionAppId','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('SupervivenciaWeb','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('FeDeVidaWeb','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('GuruClavesApplicationId','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('AppMiMacro','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('AppFull','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('DatosContactoApp','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('AppTag','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('TrazabilidadTD','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('ReposicionTD','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('eResumen','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now()),
       ('GuruWeb','macro-validacion','configuracion-fido-politica-reg-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_reg", "dev1" : "fido_auth_reg", "stg1" : "fido_auth_reg", "lab1" : "fido_auth_reg", "test1" : "fido_auth_reg", "pre-prd1" : "BioFace_APP_Full-Registration", "pre-prd2" : "BioFace_APP_Full-Registration", "prd1" : "BioFace_APP_Full-Registration", "prd2" : "BioFace_APP_Full-Registration" }}', now(), now());


DELETE FROM mscompositeparamconfig  WHERE name = 'configuracion-fido-politica-auth-identity-x';

INSERT INTO mscompositeparamconfig (application_id,microservice_id,name,value,creationdate,modificationdate)
VALUES ('EnrolamientoApp','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('BlanqueoPinWeb','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('MandatoExtraccionAppId','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('SupervivenciaWeb','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('FeDeVidaWeb','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('GuruClavesApplicationId','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('AppMiMacro','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('AppFull','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('DatosContactoApp','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('AppTag','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('TrazabilidadTD','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('ReposicionTD','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('eResumen','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now()),
       ('GuruWeb','macro-validacion','configuracion-fido-politica-auth-identity-x','{ "politica-identity-x" : { "local" : "fido_auth_auth", "dev1" : "fido_auth_auth", "stg1" : "fido_auth_auth", "lab1" : "fido_auth_auth", "test1" : "fido_auth_auth", "pre-prd1" : "BioFace_APP_Full", "pre-prd2" : "BioFace_APP_Full", "prd1" : "BioFace_APP_Full", "prd2" : "BioFace_APP_Full" }}', now(), now());
