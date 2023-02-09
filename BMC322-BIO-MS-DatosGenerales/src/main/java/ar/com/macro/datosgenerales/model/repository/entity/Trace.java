package ar.com.macro.datosgenerales.model.repository.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Trace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String operationid;

    String applicationid;

    String endpoint;

    String request;

    String response;

    Integer httpcode;

    String httpcodevalue;

    String exceptionmessage;

    String stacktrace;

    String sessiontrackinid;

    Timestamp creationdate;

    Timestamp lastmodificationdate;

}
