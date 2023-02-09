package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request;

import java.util.List;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.atributos.ActualizarHuellaDaonEngineRequestAtributos;
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
@ActualizarHuellaDaonEngineRequestAtributos
public class ActualizarHuellaDaonEngineRequest {

    @Schema(description = "Numero de identificacion cliente", required = true)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.idusuario.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.idusuario.size.pattern.message}")
    private String idusuario;

    @Schema(description = "Tipo de documento cliente", required = true)
    @Size(min = 2, max = 3, message = "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.tipodocumento.size.pattern.message}")
    private String tipodocumento;

    @Schema(description = "Numero de documento cliente", required = true)
    @Size(min = 5, max = 10, message = "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.numerodocumento.size.pattern.message}")
    private String numerodocumento;

    @Schema(description = "Genero cliente", required = true)
    @Pattern(regexp = "[A-Z]{1}", message = "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.sexo.pattern.message}")
    private String genero;

    @Schema(description = "Nombre cliente", required = true)
    private String nombre;

    @Schema(description = "Bloqueo cliente", required = true)
    private Integer bloqueado;

    @Schema(description = "Cadena de trazas cliente", required = true)
    private String trazas;
    
    @Schema(description = "Lista de huellas cliente", required = true)
    private List<HuellasActualizarDaonEngineRequest> huellas;

    private String enrolledDate;

}
