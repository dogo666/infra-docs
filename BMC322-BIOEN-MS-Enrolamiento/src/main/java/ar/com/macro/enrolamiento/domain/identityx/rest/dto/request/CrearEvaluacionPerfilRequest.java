package ar.com.macro.enrolamiento.domain.identityx.rest.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.atributos.CrearEvaluacionPerfilRequestAtributos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@CrearEvaluacionPerfilRequestAtributos
public class CrearEvaluacionPerfilRequest {

    @Schema(description =  "Id Usuario de Identity-X", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.CrearEvaluacionPerfilRequest.idcliente.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.CrearEvaluacionPerfilRequest.idusuario.size.pattern.message}")
    private String idusuario;

    @Schema(description = "Id Check de Identity-X", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    private String idcheck;

}
