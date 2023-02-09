package ar.com.macro.biometria.commons.trace.feing.proxy;

import ar.com.macro.biometria.commons.trace.feing.dto.GuardarTrazaRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.GuardarTrazaResponse;
import ar.com.macro.commons.components.feign.FeignLogConfiguration;

import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        url = "https://${macro.services.datosgenerales.id}",
        value = "https://${macro.services.datosgenerales.id}",
        configuration = FeignLogConfiguration.class)
public interface DatosGeneralesTraceProxyService {


    @PostMapping(value = "${endpoint.macro.datosgenerales.traza.guardar.path}")
    void guardarTraza(@RequestBody GuardarTrazaRequest guardarTrazaRequest, @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationID);
}
