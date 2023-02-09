package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ConsultarEnroladorRequest {

    @JsonProperty(value = "idusuario", required = false)
    String idUsuario;
    
    @JsonProperty(value = "tipodocumento", required = false)
    String tipoDocumento;

    @JsonProperty(value = "numerodocumento", required = false)
    String numeroDocumento;

    @JsonProperty(value = "genero", required = false)
    String genero;
}
