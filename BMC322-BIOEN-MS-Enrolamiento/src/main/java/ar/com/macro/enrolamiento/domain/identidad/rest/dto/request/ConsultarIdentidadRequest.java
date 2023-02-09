package ar.com.macro.enrolamiento.domain.identidad.rest.dto.request;

import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@ConsultarIdentidadAtributos
public class ConsultarIdentidadRequest {

  @Schema(description = "DNI del cliente")
  @Pattern(
      regexp = "\\d{1,9}",
      message = "{api.validacion.ConsultarIdentidadRequest.dni.pattern.message}")
  private String dni;

  @Schema(description = "Sexo del cliente")
  @Pattern(
      regexp = "[A-Z]{1}",
      message = "{api.validacion.ConsultarIdentidadRequest.sexo.pattern.message}")
  private String sexo;
}
