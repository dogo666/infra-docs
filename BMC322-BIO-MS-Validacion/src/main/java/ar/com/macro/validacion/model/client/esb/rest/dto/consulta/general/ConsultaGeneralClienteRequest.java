package ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Objeto request del servicio Consulta General Cliente
 * @author juan.hoyos
 *
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaGeneralClienteRequest {
	
    @Schema(description = "Documento", required = true)
	@NotBlank(message = "{api.validacion.ConsultaGeneralClienteRequest.documento.notblank.message}")
	private String documento;
}
