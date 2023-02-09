package ar.com.macro.enrolamiento.model.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfiguracionHuellasDto {
    @JsonProperty("manoDerecha")
    private Mano manoDerecha;
    @JsonProperty("manoIzquierda")
    private Mano manoIzquierda;
}
