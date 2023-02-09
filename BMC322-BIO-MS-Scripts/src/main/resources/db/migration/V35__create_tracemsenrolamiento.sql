
CREATE TABLE tracemsenrolamiento (
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
   CONSTRAINT PK_tracemsenrolamiento PRIMARY KEY (id)
);

CREATE INDEX tracemsenrolamiento_creationdate_IDX USING BTREE ON tracemsenrolamiento (creationdate);
CREATE INDEX tracemsenrolamiento_sessiontrackinid_IDX USING BTREE ON tracemsenrolamiento (sessiontrackinid);
CREATE INDEX tracemsenrolamiento_operationid_IDX USING BTREE ON tracemsenrolamiento (operationid);
CREATE INDEX tracemsenrolamiento_operationid_sessiontrackinid_IDX USING BTREE ON tracemsenrolamiento (operationid,sessiontrackinid);
