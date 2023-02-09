CREATE TABLE IF NOT EXISTS traceidentityx (
  id int NOT NULL AUTO_INCREMENT,
  operationid varchar(100) NOT NULL,
  applicationid varchar(100) DEFAULT NULL,
  microservicio varchar(100) DEFAULT NULL,
  endpoint varchar(500) DEFAULT NULL,
  sessiontrackinid varchar(50) DEFAULT NULL,
  request text,
  response text,
  httpcode int DEFAULT NULL,
  httpcodevalue varchar(300) DEFAULT NULL,
  exceptionmessage text,
  stacktrace text,
  creationdate datetime NOT NULL,
  lastmodificationdate datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY traceidentityx_creationdate_IDX (creationdate) USING BTREE,
  KEY traceidentityx_sessiontrackinid_IDX (sessiontrackinid) USING BTREE,
  KEY traceidentityx_operationid_IDX (operationid) USING BTREE,
  KEY traceidentityx_operationid_sessiontrackinid_IDX (operationid,sessiontrackinid) USING BTREE
) ENGINE=InnoDB;

