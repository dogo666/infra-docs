package ar.com.macro.datosgenerales.model.service;

import ar.com.macro.datosgenerales.model.repository.*;
import ar.com.macro.datosgenerales.model.repository.entity.Trace;
import ar.com.macro.datosgenerales.model.repository.entity.TraceIdentityX;
import ar.com.macro.datosgenerales.model.repository.entity.TraceOutbound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TraceFactory {

    private final TraceMsEnrolamientoRepositorio traceEnrolamientoRepositorio;

    private final TraceMsValidacionRepositorio tracevalidacionRepositorio;

    private final TraceIdentityXRepositorio tracevalidacionOutboundRepositorio;

    private static Map<TrazaEnum , TraceRepositorio> traceEnumMap = new HashMap<>();

    @PostConstruct
    private void create () {
        traceEnumMap.put(TrazaEnum.MS_ENROLAMIENTO,traceEnrolamientoRepositorio);
        traceEnumMap.put(TrazaEnum.MS_VALIDACION,tracevalidacionRepositorio);
        traceEnumMap.put(TrazaEnum.OUTBOUND_IDENTITYX,tracevalidacionOutboundRepositorio);
    }

    public  Trace create(Integer indicador){
        return TrazaEnum.getTraceByInteger(indicador).getInstance();
    }

    public TraceRepositorio getRepository(Integer indicador){
        return traceEnumMap.get(TrazaEnum.getTraceByInteger(indicador));
    }

    public void save(Integer indicador,Trace trace ){
        if(nonNull(trace.getRequest())){
            getRepository(indicador).save(trace);
        }else{
            getRepository(indicador).update(trace);
        }
    }
    public void saveTraceIdentityX(Integer indicador, TraceIdentityX trace ){
        if (nonNull(trace.getRequest())) {
            getRepository(indicador).save(trace);
        } else {
            getRepository(indicador).update(trace);
        }
    }
}
