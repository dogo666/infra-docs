package ar.com.macro.validacion.model.feign.enrolamiento.proxy;

import ar.com.macro.commons.components.feign.FeignLogConfiguration;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.request.ValidarRostroRenaperRequest;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.response.ValidarRostroRenaperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        url = "https://${macro.services.enrolamiento.id}",
        value = "https://${macro.services.enrolamiento.id}",
        configuration = {FeignLogConfiguration.class}
)
public interface EnrolamientoProxyService {

    @PostMapping(path = "${endpoint.macro.enrolamiento.rnp.persona.rostro.validar.path}")
    ResponseEntity<Respuesta<ValidarRostroRenaperResponse>> validarRostro(@RequestBody ValidarRostroRenaperRequest validarRostroRenaperRequest, @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) ;
}