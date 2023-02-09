package ar.com.macro.validacion.model.feign.enrolamiento.dto.response;

import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidarRostroRenaperResponse implements Serializable {

    private Integer codigo;
    private String mensaje;
    private Integer confidence;
    private Boolean match;

    public ValidarRostroRenaperResponse(ValidarRostroRenaperClientResponse validarRostroRenaperClientResponse) {
        this.codigo = validarRostroRenaperClientResponse.getCode();
        this.mensaje = validarRostroRenaperClientResponse.getMessage();
        this.confidence = validarRostroRenaperClientResponse.getConfidence();
        this.match = validarRostroRenaperClientResponse.getMatchThreshold();
    }
}
