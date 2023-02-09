
CREATE TABLE tracemsvalidacion (
   id INT AUTO_INCREMENT,
   operationid varchar(100) NOT NULL,
   applicationid varchar(50) NULL,
   endpoint varchar(100) NULL,
   request TEXT  NULL,
   response TEXT  NULL,
   httpcode int NULL,
   httpcodevalue varchar(300) NULL,
   exceptionmessage TEXT  NULL,
   stacktrace TEXT  NULL,
   sessiontrackinid varchar(50) NULL,
   creationdate datetime NOT NULL,
   lastmodificationdate datetime NULL,
   CONSTRAINT PK_tracemsvalidacion PRIMARY KEY (id)
);

CREATE INDEX tracemsvalidacion_creationdate_IDX USING BTREE ON tracemsvalidacion (creationdate);
CREATE INDEX tracemsvalidacion_sessiontrackinid_IDX USING BTREE ON tracemsvalidacion (sessiontrackinid);
CREATE INDEX tracemsvalidacion_operationid_IDX USING BTREE ON tracemsvalidacion (operationid);
CREATE INDEX tracemsvalidacion_operationid_sessiontrackinid_IDX USING BTREE ON tracemsvalidacion (operationid,sessiontrackinid);
