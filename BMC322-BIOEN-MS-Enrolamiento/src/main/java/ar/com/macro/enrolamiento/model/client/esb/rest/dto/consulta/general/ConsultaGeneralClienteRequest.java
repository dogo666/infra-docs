package ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
