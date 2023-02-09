package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrearEvaluacionPerfilResponse implements Serializable {
    private Integer resultado;
}
