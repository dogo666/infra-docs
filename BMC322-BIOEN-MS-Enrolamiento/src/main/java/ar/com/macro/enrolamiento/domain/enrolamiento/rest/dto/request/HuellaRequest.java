package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HuellaRequest {

	@NotBlank(message = "{api.validacion.no.blank.message}")
	private String identificador;
	
	@NotBlank(message = "{api.validacion.no.blank.message}")
	private String huella;
}
