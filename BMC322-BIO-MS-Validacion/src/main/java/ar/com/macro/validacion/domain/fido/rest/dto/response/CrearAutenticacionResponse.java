package ar.com.macro.validacion.domain.fido.rest.dto.response;

import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
@Value
@AllArgsConstructor
public class CrearAutenticacionResponse implements Serializable {

	@JsonProperty("fidoauthenticationrequest")
	private String fidoAuthenticationRequest;

	@JsonProperty("authenticationrequestid")
	private String authenticationRequestId;

	public CrearAutenticacionResponse() {
		this.fidoAuthenticationRequest = null;
		this.authenticationRequestId = null;
	}

	public CrearAutenticacionResponse(AuthenticationRequest authenticationRequest) {
		this.fidoAuthenticationRequest = authenticationRequest.getFidoAuthenticationRequest();
		this.authenticationRequestId = authenticationRequest.getAuthenticationRequestId();
	}
}
