package ar.com.macro.validacion.domain.fido.rest.dto.response;

import ar.com.macro.validacion.model.service.dto.FIDORegChallenge;
import ar.com.macro.validacion.model.service.dto.FIDORegChallengeAndId;
import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class ConsultarRegistracionResponse implements Serializable {

    @JsonProperty("estado")
    String estado;

    public ConsultarRegistracionResponse() {
        this.estado = null;
    }

    public ConsultarRegistracionResponse(FIDORegChallengeAndId fidoRegChallengeAndId) {
        this.estado = fidoRegChallengeAndId.getRegistrationChallenge().getStatus().name();
    }
}
