package ar.com.macro.validacion.domain.fido.rest.dto.response;

import com.daon.identityx.rest.model.pojo.Authenticator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

import static java.util.Objects.nonNull;

@Value
@AllArgsConstructor
public class ArchivarAutenticadorResponse implements Serializable {

  @JsonProperty("id")
  String id;

  @JsonProperty("status")
  String status;

  @JsonProperty("fidoderegistrationrequest")
  String fidoDeregistrationRequest;

  public ArchivarAutenticadorResponse() {
    this.id = null;
    this.status = null;
    this.fidoDeregistrationRequest = null;
  }
  public ArchivarAutenticadorResponse(Authenticator authenticator) {
    this.id = authenticator.getId();
    this.status = nonNull(authenticator.getStatus())?authenticator.getStatus().name():null;
    this.fidoDeregistrationRequest = authenticator.getFidoDeregistrationRequest();
  }
}
