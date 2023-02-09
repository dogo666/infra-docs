INSERT INTO application(application_id, path_regex)
SELECT * FROM (SELECT 'ImportacionHuellasAnsesApp', '[a-zA-Z0-9\/]*\/[a-zA-Z0-9\/]*') AS tmp
WHERE NOT EXISTS (
    SELECT application_id FROM application WHERE application_id = 'ImportacionHuellasAnsesApp'
    )
LIMIT 1;
