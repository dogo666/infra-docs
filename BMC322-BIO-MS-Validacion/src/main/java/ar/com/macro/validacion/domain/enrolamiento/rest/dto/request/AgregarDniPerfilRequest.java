package ar.com.macro.validacion.domain.enrolamiento.rest.dto.request;

import javax.validation.constraints.NotNull;
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
public class AgregarDniPerfilRequest {

    @Schema(description = "Id Usuario de Identity-X", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    private String idusuario;

    @Schema(description = "Frente del documento", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    private String frente;

    @Schema(description = "Dorso del documento", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    private String dorso;
}


