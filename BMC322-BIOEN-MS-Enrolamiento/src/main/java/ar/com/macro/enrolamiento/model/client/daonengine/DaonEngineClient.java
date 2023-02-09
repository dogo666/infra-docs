package ar.com.macro.enrolamiento.model.client.daonengine;

import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GetIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GetIdentityResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityResponse;

import java.util.Optional;

public interface DaonEngineClient {

	Optional<GetIdentityResponse> obtenerIdentidad(GetIdentityRequest getIdentityRequest);

	CreateIdentityResponse crearIdentidad(CreateIdentityRequest createIdentityRequest);

	UpdateIdentityResponse actualizarIdentidad(UpdateIdentityRequest updateIdentityRequest);

}
