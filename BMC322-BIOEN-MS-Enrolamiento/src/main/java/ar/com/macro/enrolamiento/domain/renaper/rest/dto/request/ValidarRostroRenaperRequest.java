package ar.com.macro.enrolamiento.domain.renaper.rest.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidarRostroRenaperRequest {

  @Schema(
      description = "Numero DNI",
      required = true)
  @NotNull(message = "{api.validacion.ValidarRostroRenaperRequest.dni.notNull.message}")
  @Size(
      min = 1,
      max = 15,
      message = "{api.validacion.ValidarRostroRenaperRequest.dni.size.message}")
  @Pattern(
      regexp = "\\d{1,9}",
      message = "{api.validacion.ValidarRostroRenaperRequest.dni.pattern.message}")
  private String numero;

  @Schema(
      description = "Genero",
      required = true)
  @NotNull(message = "{api.validacion.ValidarRostroRenaperRequest.genero.notNull.message}")
  @Pattern(
      regexp = "[A-Z]{1}",
      message = "{api.validacion.ValidarRostroRenaperRequest.genero.pattern.message}")
  private String genero;

  @Schema(
      description = "Selfies",
      required = true)
  @NotEmpty(
      message = "{api.validacion.ValidarRostroRenaperRequest.rostro.selfieList.notNull.message}")
  private List<RenaperSelfieRequest> selfies;
}
