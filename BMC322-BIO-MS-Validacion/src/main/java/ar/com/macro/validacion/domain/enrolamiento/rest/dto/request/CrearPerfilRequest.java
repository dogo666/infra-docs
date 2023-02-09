package ar.com.macro.validacion.domain.enrolamiento.rest.dto.request;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearPerfilRequest {

    @Schema(description = "Id Usuario de Identity-X", required = true)
	@NotNull(message = "{api.validacion.no.blank.message}")
	private String idusuario;
}
