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
public class ValidarRostroVideoPersonaRequest {

	@Schema(description = "Id Usuario de Identity-X", required = true)
    @NotNull(message = "{api.validacion.ValidarRostroVideoPersonaRequest.idusuario.notNull.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ValidarRostroVideoPersonaRequest.idusuario.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ValidarRostroVideoPersonaRequest.idusuario.size.pattern.message}")
    private String idusuario;

	@Schema(description = "Video de Usuario en Base64", required = true)
    @NotNull(message = "{api.validacion.ValidarRostroVideoPersonaRequest.video.notNull.message}")
    private String video;
}
