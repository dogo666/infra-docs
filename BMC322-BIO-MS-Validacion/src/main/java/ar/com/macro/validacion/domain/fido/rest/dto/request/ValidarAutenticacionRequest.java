package ar.com.macro.validacion.domain.fido.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ValidarAutenticacionRequest {

	@Schema(description = "Fido Authentication Response de Identity-X", required = true)
	@NotNull(message = "{api.validacion.no.blank.message}")
	@JsonProperty("fidoauthenticationresponse")
	String fidoAuthenticationResponse;

	@Schema(description = "Fido Authentication Request Id de Identity-X", required = true)
	@NotNull(message = "{api.validacion.no.blank.message}")
	@JsonProperty("authenticationrequestid")
	String authenticationRequestId;

	@Schema(description = "Fido Authentication Request de Identity-X", required = false)
	@JsonProperty("fidoauthenticationrequest")
	String fidoAuthenticationRequest;

	@Schema(description = "Email de Identity-X", required = false)
	@JsonProperty("email")
	String email;
	

}
