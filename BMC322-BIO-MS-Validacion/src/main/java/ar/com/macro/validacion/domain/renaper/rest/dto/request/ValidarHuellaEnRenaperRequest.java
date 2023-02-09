package ar.com.macro.validacion.domain.renaper.rest.dto.request;

import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.HuellaRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidarHuellaEnRenaperRequest {

    @Schema(description = "DNI del cliente", required = true)
	@NotBlank(message = "{api.validacion.no.blank.message}")
	private String dni;
	
    @Schema(description = "Sexo del cliente", required = true)
	@NotBlank(message = "{api.validacion.no.blank.message}")
	@Pattern(regexp = "[A-Z]{1}", message = "{api.validacion.validarHuella.sexo.pattern.message}")
	private String sexo;
	
    @Schema(description = "Huellas de pulgares", required = true)
	@NotEmpty(message = "{api.validacion.no.blank.message}")
	@Size(min = 2, max = 2, message = "{api.validacion.validarHuella.huellas.size.message}")
	private List<@Valid HuellaRequest> huellas;
}