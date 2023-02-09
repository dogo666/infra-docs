package ar.com.macro.datosgenerales.model.repository;

import ar.com.macro.datosgenerales.model.repository.entity.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;


@NoRepositoryBean
public interface TraceRepositorio<T extends Trace> extends JpaRepository<T, Long> {

    @Query(
            "UPDATE #{#entityName} t SET t.httpcode = :httpcode,"
                    + "t.httpcodevalue = :httpcodevalue,"
                    + "t.response = :response,"
                    + "t.exceptionmessage = :exceptionmessage,"
                    + "t.lastmodificationdate = :lastmodificationdate,"
                    + "t.stacktrace = :stacktrace "
                    + "WHERE t.endpoint = :endpoint "
                    + "AND t.operationid = :operationid "
                    + "AND t.applicationid = :applicationid")
    @Modifying
    void updateTrace(@Param("httpcode") Integer httpcode,
                         @Param("httpcodevalue") String httpcodevalue,
                         @Param("response") String response,
                         @Param("exceptionmessage") String exceptionmessage,
                         @Param("lastmodificationdate") Timestamp lastmodificationdate,
                         @Param("stacktrace") String stacktrace,
                         @Param("endpoint") String endpoint,
                         @Param("operationid") String operationid,
                         @Param("applicationid") String applicationid);


    default void update(Trace trace){
        updateTrace(
                trace.getHttpcode(),
                trace.getHttpcodevalue(),
                trace.getResponse(),
                trace.getExceptionmessage(),
                trace.getLastmodificationdate(),
                trace.getStacktrace(),
                trace.getEndpoint(),
                trace.getOperationid(),
                trace.getApplicationid());
    }
}
