package ar.com.macro.validacion.domain.enrolamiento.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultarEnrolamientoDaonEngineResponse {

    @JsonProperty("registrado")
    int registrado;

    @JsonProperty("calificadores")
    List<Integer> calificadores;

    @JsonProperty("fecha")
    String fecha;

    @JsonProperty("enrolador")
    String enrolador;

}