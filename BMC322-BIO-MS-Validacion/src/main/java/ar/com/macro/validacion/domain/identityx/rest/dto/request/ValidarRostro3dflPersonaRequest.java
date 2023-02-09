package ar.com.macro.validacion.domain.identityx.rest.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class ValidarRostro3dflPersonaRequest {

    @Schema(description = "Id Usuario de Identity-X", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ValidarRostro3dflPersonaRequest.idusuario.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ValidarRostro3dflPersonaRequest.idusuario.size.pattern.message}")
    private String idusuario;

    @Schema(description = "Id Check de Identity-X", required = false)
    private String idcheck;

    @Schema(description = "Video de Usuario en base 64", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    private String video;
}

