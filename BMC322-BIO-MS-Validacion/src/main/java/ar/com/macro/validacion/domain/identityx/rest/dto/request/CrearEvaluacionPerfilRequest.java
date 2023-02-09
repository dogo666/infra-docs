package ar.com.macro.validacion.domain.identityx.rest.dto.request;

import ar.com.macro.validacion.domain.identityx.rest.dto.request.atributos.CrearEvaluacionPerfilRequestAtributos;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@CrearEvaluacionPerfilRequestAtributos
public class CrearEvaluacionPerfilRequest {

    @Schema(description = "Id Usuario de Identity-X", required = true)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.CrearEvaluacionPerfilRequestValidator.idusuario.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.CrearEvaluacionPerfilRequestValidator.idusuario.size.pattern.message}")
    private String idusuario;

    @Schema(description = "Id Check de Identity-X", required = true)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.CrearEvaluacionPerfilRequestValidator.idcheck.alfa.pattern.message}")
    private String idcheck;

}
