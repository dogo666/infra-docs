package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarHuellaDaonEngineResponse implements Serializable {
    private String idtracking;
}
