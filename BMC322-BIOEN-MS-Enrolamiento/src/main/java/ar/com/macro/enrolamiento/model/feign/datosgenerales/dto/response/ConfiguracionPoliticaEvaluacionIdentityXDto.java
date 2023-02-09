package ar.com.macro.enrolamiento.model.feign.datosgenerales.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguracionPoliticaEvaluacionIdentityXDto {

    @JsonProperty("aplicacion-identity-x")
    private ConfiguracionAmbienteIdentityX configuracionPoliticaIdentityX;
}
