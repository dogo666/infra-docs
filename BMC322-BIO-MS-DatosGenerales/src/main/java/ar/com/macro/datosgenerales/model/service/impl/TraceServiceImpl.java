package ar.com.macro.datosgenerales.model.service.impl;

import java.sql.Timestamp;
import java.util.Objects;

import ar.com.macro.datosgenerales.model.repository.entity.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.macro.commons.values.constants.config.TraceConstants;
import ar.com.macro.datosgenerales.domain.traza.rest.dto.request.GuardarTrazaRequest;
import ar.com.macro.datosgenerales.model.service.TraceFactory;
import ar.com.macro.datosgenerales.model.service.TraceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TraceServiceImpl implements TraceService {

    private final TraceFactory traceFactory;

    @Override
    @Transactional
    public void guardar(final GuardarTrazaRequest trazaRequest) {

        TraceInbound trace = (TraceInbound) traceFactory.create(trazaRequest.getIndicador());
        trace.setOperationid(MDC.get(TraceConstants.LOG_X_OPERATION_ID));
        trace.setApplicationid(MDC.get(TraceConstants.APPLICATION));
        trace.setEndpoint(trazaRequest.getEndpoint());
        trace.setRequest(trazaRequest.getRequest());
        trace.setResponse(trazaRequest.getResponse());
        trace.setHttpcode(trazaRequest.getCodigohttp());
        trace.setHttpcodevalue(trazaRequest.getValorcodigohttp());
        trace.setExceptionmessage(trazaRequest.getMensajeexcepcion());
        trace.setStacktrace(trazaRequest.getStacktrace());
        trace.setSessiontrackinid(trazaRequest.getIdrastreosesion());
        trace.setDni(trazaRequest.getDni());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        trace.setCreationdate(Objects.nonNull(trace.getRequest())?timestamp : null);
        trace.setLastmodificationdate(!Objects.nonNull(trace.getRequest())?timestamp : null);
        
        traceFactory.save(trazaRequest.getIndicador(), trace);
    }

    @Override
    @Transactional
    public void guardarOutboundIdentityX(GuardarTrazaRequest trazaRequest) {

        TraceIdentityX trace  = (TraceIdentityX) traceFactory.create(trazaRequest.getIndicador());
        String applicationId = MDC.get(TraceConstants.APPLICATION);
        String operationId = MDC.get(TraceConstants.LOG_X_OPERATION_ID);
        trace.setOperationid(operationId);
        trace.setApplicationid(applicationId);
        trace.setEndpoint(trazaRequest.getEndpoint());
        trace.setRequest(trazaRequest.getRequest());
        trace.setResponse(trazaRequest.getResponse());
        trace.setHttpcode(trazaRequest.getCodigohttp());
        trace.setHttpcodevalue(trazaRequest.getValorcodigohttp());
        trace.setExceptionmessage(trazaRequest.getMensajeexcepcion());
        trace.setStacktrace(trazaRequest.getStacktrace());
        trace.setSessiontrackinid(trazaRequest.getIdrastreosesion());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        trace.setCreationdate(Objects.nonNull(trace.getRequest())?timestamp : null);
        trace.setLastmodificationdate(!Objects.nonNull(trace.getRequest())?timestamp : null);
        trace.setMicroservicio(trazaRequest.getMicroservicio());
        traceFactory.saveTraceIdentityX(trazaRequest.getIndicador(),trace);

    }

}
