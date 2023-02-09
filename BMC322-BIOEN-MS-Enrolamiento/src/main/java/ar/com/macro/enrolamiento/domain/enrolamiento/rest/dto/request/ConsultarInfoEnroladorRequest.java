package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos.ConsultarInfoEnroladorRequestAtributos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * ConsultarInfoEnroladorRequest.
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-12
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConsultarInfoEnroladorRequestAtributos
public class ConsultarInfoEnroladorRequest {

  @Schema(description = "Email de usuario", required = false)
  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{api.enrolamiento.GuardarEnroladorRequest.email.alfa.pattern.message}")
  @JsonProperty("email")
  String email;
  
  @Schema(description = "Tipo de documento", required = false)
  @Size(min = 2, max = 3, message = "{api.validacion.EnrolarHuellaDaonEngineRequestValidator.tipodocumento.size.pattern.message}")
  @JsonProperty("tipodocumento")
  String tipodocumento;

  @Schema(description = "Numero de documento", required = false)
  @Size(min = 5, max = 10, message = "{api.validacion.EnrolarHuellaDaonEngineRequestValidator.numerodocumento.size.pattern.message}")
  @JsonProperty("numerodocumento")
  String numerodocumento;

  @Schema(description = "Genero", required = false)
  @Pattern(regexp = "[A-Z]{1}", message = "{api.validacion.EnrolarHuellaDaonEngineRequestValidator.sexo.pattern.message}")
  @JsonProperty("genero")
  String genero;
}
