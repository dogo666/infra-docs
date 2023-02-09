package ar.com.macro.validacion.domain.fido.rest.dto.response;

import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class ValidarAutenticacionResponse implements Serializable {

	@JsonProperty("fidoauthenticationresponse")
	String fidoAuthenticationResponse;

	@JsonProperty("fidoresponsecode")
	Long fidoResponseCode;

	@JsonProperty("fidoresponsemsg")
	String fidoResponseMsg;

	public ValidarAutenticacionResponse() {
		this.fidoAuthenticationResponse = null;
		this.fidoResponseCode = null;
		this.fidoResponseMsg = null;
	}

	public ValidarAutenticacionResponse(AuthenticationRequest authRequest) {
		this.fidoAuthenticationResponse = authRequest.getFidoAuthenticationResponse();
		this.fidoResponseCode = authRequest.getFidoResponseCode();
		this.fidoResponseMsg = authRequest.getFidoResponseMsg();
	}
}
