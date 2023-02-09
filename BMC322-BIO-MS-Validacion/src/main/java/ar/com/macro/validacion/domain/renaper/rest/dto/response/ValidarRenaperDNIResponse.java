package ar.com.macro.validacion.domain.renaper.rest.dto.response;

import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static ar.com.macro.validacion.model.client.esb.rest.dto.utils.ClientesConstantes.VIGENTE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidarRenaperDNIResponse implements Serializable {

    private String codigo;
    private String mensaje;
    private String persona;
    private String vigente;

    public ValidarRenaperDNIResponse(ValidarDNIRenaperClientResponse validarDNIRenaperClientResponse) {
        this.codigo = validarDNIRenaperClientResponse.getCode();
        this.mensaje = validarDNIRenaperClientResponse.getMessage();
        this.persona = validarDNIRenaperClientResponse.getPerson();
        this.vigente = validarDNIRenaperClientResponse.getValid();
    }

    public boolean isVigente() {
        return VIGENTE.equalsIgnoreCase(vigente);
    }
}
