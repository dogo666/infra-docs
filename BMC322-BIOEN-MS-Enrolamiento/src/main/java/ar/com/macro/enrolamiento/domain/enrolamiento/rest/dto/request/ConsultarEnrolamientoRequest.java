package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos.ConsultarEnrolamientoRequestAtributos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@ConsultarEnrolamientoRequestAtributos
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultarEnrolamientoRequest {

    @Schema(description = "Indicador de busqueda")
    @Pattern(regexp = "[1|2|3]{1}", message = "{api.validacion.ConsultarEnrolamientoRequestValidator.indicador.alfa.pattern.message}")
    @JsonProperty("indicador")
    String indicador;

    @Schema(description = "Identificación cliente")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.validacion.ConsultarEnrolamientoRequestValidator.idcliente.alfa.pattern.message}")
    @Size(min = 8, max = 15, message = "{api.validacion.ConsultarEnrolamientoRequestValidator.idusuario.size.pattern.message}")
    @JsonProperty("idusuario")
    String idCliente;

}
