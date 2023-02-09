package ar.com.macro.validacion.domain.fido.rest.dto.response;

import ar.com.macro.validacion.model.service.dto.FIDORegChallenge;
import ar.com.macro.validacion.model.service.dto.FIDORegChallengeAndId;
import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class CrearRegistracionResponse implements Serializable {

    @JsonProperty("idxid")
    String idXId;

    @JsonProperty("idxusername")
    String idXUsername;

    @JsonProperty("registrationchallenge")
    FIDORegChallenge registrationChallenge;

    public CrearRegistracionResponse() {
        this.idXId = null;
        this.idXUsername = null;
        this.registrationChallenge = null;
    }

    public CrearRegistracionResponse(FIDORegChallengeAndId fidoRegChallengeAndId) {
        this.idXId = fidoRegChallengeAndId.getIdXId();
        this.idXUsername = fidoRegChallengeAndId.getIdXUsername();
        this.registrationChallenge = new FIDORegChallenge(fidoRegChallengeAndId.getRegistrationChallenge());
    }
}
