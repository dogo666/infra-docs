package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
  @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.CrearPerfilRequest.idcliente.alfa.pattern.message}")
  @Size(min = 8, max = 15, message = "{api.validacion.CrearPerfilRequest.idusuario.size.pattern.message}")
  private String idusuario;
}
