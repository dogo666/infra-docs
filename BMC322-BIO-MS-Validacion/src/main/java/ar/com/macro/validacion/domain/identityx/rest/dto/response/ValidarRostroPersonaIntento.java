package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import com.daon.identityx.rest.model.pojo.AuthenticationAttempt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ar.com.macro.constant.Constantes.YES;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidarRostroPersonaIntento {

    private Integer resultado;
    private String motivo;
    private String estado;
    private List<ValidarRostroPersonaIntentoDetalle> detalle;

    public ValidarRostroPersonaIntento(final AuthenticationAttempt authenticationAttempt) {

        this.resultado = YES.equals(authenticationAttempt.getDecision()) ? 1:0;
        this.motivo = authenticationAttempt.getReason();
        this.estado = authenticationAttempt.getState();
        this.detalle = new ArrayList<>();

        if(Objects.nonNull(authenticationAttempt.getItems())) {
            Arrays.stream(authenticationAttempt.getItems())
                    .forEach((d) -> this.detalle.add(new ValidarRostroPersonaIntentoDetalle(d)));
        }
    }

}
