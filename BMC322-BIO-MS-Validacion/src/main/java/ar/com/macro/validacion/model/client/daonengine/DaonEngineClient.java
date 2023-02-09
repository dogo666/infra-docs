package ar.com.macro.validacion.model.client.daonengine;

import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityRequest;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityResponse;

import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyRequest;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResponse;

import java.util.Optional;

public interface DaonEngineClient {

	Optional<GetIdentityResponse> obtenerIdentidad(GetIdentityRequest getIdentityRequest);
	
	Optional<VerifyResponse> verificarIdentificacion(VerifyRequest verifyRequest);

}
