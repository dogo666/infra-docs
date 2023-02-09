package ar.com.macro.validacion.model.feign.enrolamiento.dto.request;

import ar.com.macro.validacion.domain.renaper.rest.dto.request.RenaperSelfieRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidarRostroRenaperRequest {

    @Schema(description = "Numero DNI", required = true)
    @NotNull(message = "{api.validacion.ValidarRostroRenaperRequest.dni.notNull.message}")
    @Size(min = 1, max = 15, message = "{api.validacion.ValidarRostroRenaperRequest.dni.size.message}")
    @Pattern(regexp = "\\d{1,9}", message = "{api.validacion.ValidarRostroRenaperRequest.dni.pattern.message}")
    private String numero;

    @Schema(description = "Genero", required = true)
    @NotNull(message = "{api.validacion.ValidarRostroRenaperRequest.genero.notNull.message}")
    @Pattern(regexp = "[A-Z]{1}", message = "{api.validacion.ValidarRostroRenaperRequest.genero.pattern.message}")
    private String genero;

    @Schema(description = "Selfies", required = true)
    @NotEmpty(message = "{api.validacion.ValidarRostroRenaperRequest.rostro.selfieList.notNull.message}")
    private List<RenaperSelfieRequest> selfies;

}