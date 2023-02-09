package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HuellasEnrolarDaonEngineRequest {
    private int identificador;
    private String imagen;
}
