package ar.com.macro.validacion.model.service;

import ar.com.macro.validacion.domain.renaper.rest.dto.request.ValidarHuellaEnRenaperRequest;
import ar.com.macro.validacion.domain.renaper.rest.dto.response.ValidarHuellaEnRenaperResponse;
import ar.com.macro.validacion.domain.renaper.rest.dto.request.ValidarDniRenaperRequest;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.request.ValidarRostroRenaperRequest;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;

import java.util.Optional;

public interface RenaperService {


    Optional<ValidarDNIRenaperClientResponse> validarIdentidadPersonaPorDNI(ValidarDniRenaperRequest validarDniRenaperRequest);

    Optional<ValidarRostroRenaperClientResponse> validarIdentidadPersonaPorRostro(ValidarRostroRenaperRequest calidarRostroRenaperRequest);

    ValidarHuellaEnRenaperResponse validarHuellaEnRenaper(ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest);
}
