package ar.com.macro.enrolamiento.domain.identityx.rest.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarDatosDniRequest {

    @Schema(description = "Id Usuario de Identity-X", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ConsultarDatosDniRequest.idcliente.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ConsultarDatosDniRequest.idusuario.size.pattern.message}")
    private String idusuario;

    @Schema(description = "Id Check de Identity-X", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    private String idcheck;

    @Schema(description = "Id Documento de Identity-X", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    private String iddocument;
}
