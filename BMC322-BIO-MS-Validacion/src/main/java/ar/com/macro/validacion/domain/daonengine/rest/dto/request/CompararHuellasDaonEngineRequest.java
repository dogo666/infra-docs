package ar.com.macro.validacion.domain.daonengine.rest.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CompararHuellasDaonEngineRequest
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-26
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CompararHuellasDaonEngineRequest {

    @Schema(description = "Id Usuario", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ValidarHuellaDaonEngineRequest.idusuario.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ValidarHuellaDaonEngineRequest.idusuario.size.pattern.message}")
    private String idusuario;

    @Schema(description = "Lista de huellas cliente", required = true)
    private List<HuellaDaonEngineRequest> huellas;

}
