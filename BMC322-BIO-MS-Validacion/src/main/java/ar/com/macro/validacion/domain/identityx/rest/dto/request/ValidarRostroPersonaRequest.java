package ar.com.macro.validacion.domain.identityx.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidarRostroPersonaRequest {
  
    @Schema(description = "Id Usuario de Identity-X", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ValidarRostroPersonaRequest.idusuario.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ValidarRostroPersonaRequest.idusuario.size.pattern.message}")
    private String idusuario;

    @Schema(description = "Foto selfie en base 64", required = true)
    @NotBlank(message = "{api.validacion.no.blank.message}")
    private String selfie;
}
