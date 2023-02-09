package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ar.com.macro.constant.Constantes.YES;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidarRostroPersonaResponse implements Serializable {

    private String idverificacion;
    private Integer completado;
    private Integer resultado;
    private List<ValidarRostroPersonaIntento> intentos;

    public ValidarRostroPersonaResponse(final AuthenticationRequest verificationResponse) {
        this.idverificacion = verificationResponse.getId();
        this.completado = verificationResponse.getComplete() == true ? 1 : 0;
        this.resultado = YES.equals(verificationResponse.getVerificationResult()) ? 1 : 0;
        this.intentos = new ArrayList<>();

        if(Objects.nonNull(verificationResponse.getAuthenticationAttempts())){
            Arrays.stream(verificationResponse.getAuthenticationAttempts())
                    .forEach((i) -> this.intentos.add(new ValidarRostroPersonaIntento(i)));
        }

    }
}