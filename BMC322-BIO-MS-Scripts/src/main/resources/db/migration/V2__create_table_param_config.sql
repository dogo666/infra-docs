CREATE TABLE IF NOT EXISTS mssingleparamconfig (
	id INT AUTO_INCREMENT,
	application_id VARCHAR(100) NOT NULL,
	microservice_id VARCHAR(100) NOT NULL,
	name VARCHAR(50) NOT NULL,
	value TEXT NOT NULL,
	creationdate DATETIME NOT NULL,
	modificationdate DATETIME NOT NULL,
	PRIMARY KEY(id)
)ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS mscompositeparamconfig (
	id INT AUTO_INCREMENT,
	application_id VARCHAR(100) NOT NULL,
	microservice_id VARCHAR(100) NOT NULL,
	name VARCHAR(50) NOT NULL,
	value TEXT NOT NULL,
	creationdate DATETIME NOT NULL,
	modificationdate DATETIME NOT NULL,
	PRIMARY KEY(id)
)ENGINE=INNODB;