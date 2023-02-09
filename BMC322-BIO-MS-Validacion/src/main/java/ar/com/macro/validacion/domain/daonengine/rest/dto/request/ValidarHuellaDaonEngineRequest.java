package ar.com.macro.validacion.domain.daonengine.rest.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ValidarHuellaDaonEngineRequest {

	@Schema(description = "Id Usuario", required = true)
	@NotBlank(message = "{api.validacion.no.blank.message}")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ValidarHuellaDaonEngineRequest.idusuario.alfa.pattern.message}")
	@Size(min = 8, max = 15, message = "{api.validacion.ValidarHuellaDaonEngineRequest.idusuario.size.pattern.message}")
	private String idusuario;
	
	@Schema(description = "Id de huella ReNaPer", required = true)
	@NotBlank(message = "{api.validacion.no.blank.message}")
	private String identificador;
	
	@Schema(description = "Huella base 64", required = true)
	@NotBlank(message = "{api.validacion.no.blank.message}")
	private String huella;
}
