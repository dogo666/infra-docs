package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request;

import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ar.com.macro.constant.Constantes;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.atributos.GuardarEnroladorRequestAtributos;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@GuardarEnroladorRequestAtributos
public class GuardarEnroladorRequest {

    @JsonProperty("email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = Constantes.RESPUESTA_PATTERN_GUARDAR_ENROLADOR_EMAIL)
    String email;

    @JsonProperty("tipodocumento")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = Constantes.RESPUESTA_PATTERN_GUARDAR_ENROLADOR_TIPO_DOCUMENTO)
    String tipoDocumento;

    @JsonProperty("numerodocumento")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = Constantes.RESPUESTA_PATTERN_GUARDAR_ENROLADOR_NUMERO_DOCUMENTO)
    String numeroDocumento;

    @JsonProperty("genero")
    @Pattern(regexp = "[M|F]{1}", message = Constantes.RESPUESTA_PATTERN_GUARDAR_ENROLADOR_GENERO)
    String genero;

    @JsonProperty("sucursal")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = Constantes.RESPUESTA_PATTERN_GUARDAR_ENROLADOR_SUCURSAL)
    String sucursal;

    @JsonProperty("idcobis")
    String idCobis;

    @JsonProperty("estado")
    String estado;

    @JsonProperty("cantidaddehuellas")
    String cantidadDeHuellas;

    @JsonProperty("razon")
    String razon;

    @JsonProperty("supervisor")
    String supervisor;

}
