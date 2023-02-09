package ar.com.macro.validacion.domain.fido.rest.dto.response;

import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.Authenticator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@AllArgsConstructor
public class GetAutenticadoresResponse implements Serializable {

  private static final long serialVersionUID = 5836826260011973176L;

  @JsonProperty("autenticadores")
  List<Autenticador> autenticadores;

  public GetAutenticadoresResponse(){
    this.autenticadores = null;
  }
  public GetAutenticadoresResponse(final Authenticator[] autenticadores) {
    this.autenticadores = Arrays.stream(autenticadores)
            .parallel()
            .map(a -> new Autenticador(a))
            .collect(toList());
  }
}
