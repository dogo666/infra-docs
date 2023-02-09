package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ar.com.macro.enrolamiento.model.service.dto.TrazasDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * GuardarTrazasDaonEngineRequest.
 *
 * @author globant
 * @version 1.0
 * @since 2022-06-09
 */
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuardarTrazasDaonEngineRequest {

    @Schema(description = "Numero de identificacion cliente", required = true)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.idusuario.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.idusuario.size.pattern.message}")
    String idusuario;

    @Schema(description = "Cadena de trazas cliente", required = true)
    TrazasDto trazas;
}
