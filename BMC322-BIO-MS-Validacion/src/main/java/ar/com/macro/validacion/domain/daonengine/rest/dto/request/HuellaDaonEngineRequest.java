package ar.com.macro.validacion.domain.daonengine.rest.dto.request;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HuellaDaonEngineRequest {

  @Schema(description = "Id de huella ReNaPer", required = true)
  @NotBlank(message = "{api.validacion.no.blank.message}")
  private Integer identificador;

  @Schema(description = "Huella base 64", required = true)
  @NotBlank(message = "{api.validacion.no.blank.message}")
  private String huella;
}
