package ar.com.macro.validacion.domain.enrolamiento.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultarEnrolamientoResponse implements Serializable {

    @JsonProperty("rostro")
    ConsultarEnrolamientoIdentityXResponse consultarEnrolamientoIdentityXResponse;

    @JsonProperty("huella")
    ConsultarEnrolamientoDaonEngineResponse consultarEnrolamientoDaonEngineResponse;
}


