package ar.com.macro.validacion.domain.enrolamiento.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultarEnrolamientoIdentityXResponse {

    @JsonProperty("registrado")
    int registrado;

    @JsonProperty("enrolado")
    int enrolado;

    @JsonProperty("fecha")
    String fecha;

    @JsonProperty("enrolador")
    String enrolador;

    @JsonProperty("confidence")
    Long confidence;

}