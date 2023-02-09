package ar.com.macro.validacion.domain.fido.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ProcesarRegistracionRequest {

	@Schema(description = "Fido Authentication Response de Identity-X", required = true)
	@NotNull(message = "{api.validacion.no.blank.message}")
	@JsonProperty("idxid")
	String idXId;

	@Schema(description = "Fido Registration Response de Identity-X", required = true)
	@NotNull(message = "{api.validacion.no.blank.message}")
	@JsonProperty("fidoregistrationresponse")
	String fidoRegistrationResponse;

	@Schema(description = "Fido registration Challenge Id de Identity-X", required = true)
	@NotNull(message = "{api.validacion.no.blank.message}")
	@JsonProperty("registrationchallengeid")
	String registrationChallengeId;

}
