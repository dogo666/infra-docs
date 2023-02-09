package ar.com.macro.enrolamiento.model.client.renaper;

import java.util.Optional;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarDNIRenaperClientRequest;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarRostroRenaperClientRequest;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;

public interface RenaperClient {

    Optional<ValidarDNIRenaperClientResponse> validarDni(ValidarDNIRenaperClientRequest validarDNIRenaperClientRequest);

    @Retryable( value = Exception.class, maxAttemptsExpression  = "${api.macro.identificacion.renaper.service.rostro.reintentos.maximo:1}", backoff = @Backoff(delay = 100))
    Optional<ValidarRostroRenaperClientResponse> validarRostro(ValidarRostroRenaperClientRequest validarRostroRenaperClientRequest);

}
