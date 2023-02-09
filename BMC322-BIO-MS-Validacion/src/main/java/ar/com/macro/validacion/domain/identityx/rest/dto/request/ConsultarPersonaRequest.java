package ar.com.macro.validacion.domain.identityx.rest.dto.request;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultarPersonaRequest {

  @Schema(description = "Indicador de búsqueda")
  @Pattern(regexp = "[1|2|3]{1}", message = "{api.validacion.ConsultarPersonaRequest.indicador.alfa.pattern.message}")
  @JsonProperty("indicador")
  String indicador = "1";

  @Schema(description = "Id Usuario de Identity-X", required = true)
  @NotNull(message = "{api.validacion.ConsultarPersonaRequest.idusuario.notNull.message}")
  @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ConsultarPersonaRequest.idusuario.alfa.pattern.message}")
  @Size(min = 8, max = 15, message = "{api.validacion.ConsultarPersonaRequest.idusuario.size.pattern.message}")
  @JsonProperty("idusuario")
  String idusuario;

  public IndicadorEnum getIndicadorValue() {
    IndicadorEnum value;

    if (Objects.isNull(getIndicador()) || getIndicador().equals("1")) {
      value = IndicadorEnum.TODOS;
      
    } else if (getIndicador().equals("2")) {
      value = IndicadorEnum.SELFIE;
      
    } else {
      value = IndicadorEnum.DNI;
    }

    return value;
  }

  public enum IndicadorEnum {
    SELFIE,
    DNI,
    TODOS;
  }
}
