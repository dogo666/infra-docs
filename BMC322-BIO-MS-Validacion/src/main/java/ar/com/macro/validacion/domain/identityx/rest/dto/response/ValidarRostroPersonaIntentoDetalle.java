package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import com.daon.identityx.rest.model.pojo.AuthenticationAttemptItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidarRostroPersonaIntentoDetalle {

    private String tipo;
    private Double score;
    private Double fmr;
    private String resultado;
    private String motivo;

    public ValidarRostroPersonaIntentoDetalle(final AuthenticationAttemptItem d) {
        this.tipo = d.getType();
        this.score = d.getScore();
        this.fmr = d.getFmr();
        this.resultado = d.getResult();
        this.motivo = d.getReason();
    }
}
