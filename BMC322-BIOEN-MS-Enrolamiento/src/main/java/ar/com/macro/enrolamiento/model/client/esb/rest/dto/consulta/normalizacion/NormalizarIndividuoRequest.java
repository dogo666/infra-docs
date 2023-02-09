package ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.normalizacion;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto request del servicio de normalizar individuo v2
 * @author juan.hoyos
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NormalizarIndividuoRequest {

    @Schema(description = "DNI", required = true)
	@NotNull(message = "{api.validacion.NormalizarIndividuoRequest.dni.notnull.message}")
	@Pattern(regexp = "\\d{1,9}", message = "{api.validacion.NormalizarIndividuoRequest.dni.pattern.message}")
	private String dni;

    @Schema(description = "Apellido")
	@Pattern(regexp = "['\\w\\s\\ñ\\Ñ]{1,20}", flags=Pattern.Flag.UNICODE_CASE, message = "{api.validacion.NormalizarIndividuoRequest.apellido.pattern.message}")
	private String apellido;

    @Schema(description = "Sexo")
	@Pattern(regexp = "[A-Z]{1}", message = "{api.validacion.NormalizarIndividuoRequest.sexo.pattern.message}")
	private String sexo;

}
