package ar.com.macro.validacion.domain.fido.rest.dto.response;

import static com.daon.identityx.rest.model.def.AuthenticationRequestStatusEnum.COMPLETED_SUCCESSFUL;
import java.io.Serializable;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class GetAutenticationResponse implements Serializable {
  private static final long serialVersionUID = 5836826260011973176L;

  @JsonProperty("completedsuccessful")
  Boolean completedSuccessful;
  
  public GetAutenticationResponse(){
    this.completedSuccessful = null;
  }

  public GetAutenticationResponse(final AuthenticationRequest authRequest) {
    this.completedSuccessful = (authRequest.getStatus() == COMPLETED_SUCCESSFUL);
  }
}
