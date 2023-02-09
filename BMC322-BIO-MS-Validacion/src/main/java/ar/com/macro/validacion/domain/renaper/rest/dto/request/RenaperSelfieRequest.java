package ar.com.macro.validacion.domain.renaper.rest.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RenaperSelfieRequest {

    @Schema(description = "Representacion en base 64 de la selfie de la persona",  required = true )
    @NotBlank(message = "{api.validacion.ValidarRostroRenaperRequest.rostro.archivo.notNull.message}"  )
    private String archivo;

    @Schema(description = "Tipo de imagen de la selfie",  required = true )
    @NotBlank( message = "{api.validacion.ValidarRostroRenaperRequest.rostro.archivo.tipo.notNull.message}" )
    private String tipo;

}
