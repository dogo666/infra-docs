package ar.com.macro.validacion.domain.renaper.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidarDniRenaperRequest {

    @Schema(description = "Numero DNI de la persona a identificar", required = true)
    @NotNull(message = "{api.validacion.ValidarDniRenaperRequest.dni.notNull.message}")
    @Size(min = 1, max = 15, message = "{api.validacion.ValidarDniRenaperRequest.dni.size.message}")
    @Pattern(regexp = "\\d{1,9}", message = "{api.validacion.ValidarDniRenaperRequest.dni.pattern.message}")
    private String numero;

    @Schema(description = "Genero de la persona a identificar", required = true)
    @NotNull(message = "{api.validacion.ValidarDniRenaperRequest.genero.notNull.message}")
    @Pattern(regexp = "[A-Z]{1}", message = "{api.validacion.ValidarDniRenaperRequest.genero.pattern.message}")
    private String genero;

    @Schema(description = "Numero de tramite", required = true)
    @NotBlank(message = "{api.validacion.ValidarDniRenaperRequest.orden.notNull.message}")
    @Size(min = 1, max = 25, message = "{api.validacion.ValidarDniRenaperRequest.orden.size.message}")
    private String orden;
}
