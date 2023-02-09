package ar.com.macro.validacion.domain.identityx.rest.dto.request;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultarVerificacionRostroPersonaRequest {

    @Schema(description = "Id Verificacion de Identity-X", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    private String idverificacion;
}
