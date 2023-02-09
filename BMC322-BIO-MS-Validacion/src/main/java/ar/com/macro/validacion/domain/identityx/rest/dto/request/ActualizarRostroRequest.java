package ar.com.macro.validacion.domain.identityx.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarRostroRequest {

    @Schema(description = "Numero de identificacion cliente", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    private String idcliente;

    @Schema(description = "Representacion en base 64 de la selfie de la persona", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    private String archivo;
}