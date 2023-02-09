package ar.com.macro.validacion.model.client.renaper;

import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientRequest;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarRostroRenaperClientRequest;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;

import java.util.Optional;

public interface RenaperClient {

    Optional<ValidarDNIRenaperClientResponse> validarDni(ValidarDNIRenaperClientRequest validarDNIRenaperClientRequest);

    Optional<ValidarRostroRenaperClientResponse> validarRostro(ValidarRostroRenaperClientRequest validarRostroRenaperClientRequest);
}
