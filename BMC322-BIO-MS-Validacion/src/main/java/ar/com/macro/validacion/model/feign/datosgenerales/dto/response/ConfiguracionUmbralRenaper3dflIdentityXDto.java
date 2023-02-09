package ar.com.macro.validacion.model.feign.datosgenerales.dto.response;

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
public class ConfiguracionUmbralRenaper3dflIdentityXDto {

    @JsonProperty("umbral-renaper-3dfl-identity-x")
    private ConfiguracionAmbienteIdentityX configuracionPoliticaIdentityX;
}
