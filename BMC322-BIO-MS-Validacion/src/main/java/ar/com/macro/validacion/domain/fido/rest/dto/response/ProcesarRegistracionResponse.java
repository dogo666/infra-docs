package ar.com.macro.validacion.domain.fido.rest.dto.response;

import ar.com.macro.validacion.model.service.dto.FIDORegChallenge;
import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class ProcesarRegistracionResponse implements Serializable {

	@JsonProperty("fidoregistrationconfirmation")
	String fidoRegistrationConfirmation;

	@JsonProperty("fidoresponsecode")
	Long fidoResponseCode;

	@JsonProperty("fidoresponsemsg")
	String fidoResponseMsg;

	public ProcesarRegistracionResponse() {
		this.fidoRegistrationConfirmation = null;
		this.fidoResponseCode = null;
		this.fidoResponseMsg = null;
	}

	public ProcesarRegistracionResponse(FIDORegChallenge challenge) {
		fidoRegistrationConfirmation = challenge.getFidoRegistrationResponse();
		fidoResponseCode = challenge.getFidoResponseCode();
		fidoResponseMsg = challenge.getFidoResponseMsg();
	}

}
