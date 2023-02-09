package ar.com.macro.validacion.model.client.esb.rest.dto.consulta.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaClienteAtributosRequest {

    @Schema(description = "DNI", required = true)
	@NotNull(message = "{api.validacion.ConsultaClienteAtributosRequest.dni.notnull.message}")
	@Pattern(regexp = "\\d{1,9}", message = "{api.validacion.ConsultaClienteAtributosRequest.dni.pattern.message}")
	private String dni;

    @Schema(description = "Apellido")
	@Pattern(regexp = "(?U)['\\w\\s\\ñ\\Ñ]{1,30}", message = "{api.validacion.ConsultaClienteAtributosRequest.apellido.pattern.message}")
	private String apellido;

    @Schema(description = "Sexo")
	@Pattern(regexp = "[A-Z]{1}", message = "{api.validacion.ConsultaClienteAtributosRequest.sexo.pattern.message}")
	private String sexo;

}
