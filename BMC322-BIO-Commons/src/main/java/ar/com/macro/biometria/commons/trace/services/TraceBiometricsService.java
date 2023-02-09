package ar.com.macro.biometria.commons.trace.services;

import ar.com.macro.biometria.commons.trace.feing.dto.GuardarTrazaRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.GuardarTrazaResponse;
import ar.com.macro.biometria.commons.trace.feing.proxy.DatosGeneralesTraceProxyService;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.config.TraceConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraceBiometricsService {

    private final DatosGeneralesTraceProxyService datosGeneralesTraceProxyService;

    @Value("${api.macro.biometrics.trace.identificador}")
    private Integer indicador;

    public void guardar(GuardarTrazaRequest traza) {
        log.debug("crearTraza {}", traza);
        traza.setIndicador(indicador);
        datosGeneralesTraceProxyService.guardarTraza(traza, (String) MDC.get(TraceConstants.LOG_X_OPERATION_ID));

    }
}
