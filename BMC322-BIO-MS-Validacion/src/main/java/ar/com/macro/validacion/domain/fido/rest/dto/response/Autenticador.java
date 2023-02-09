package ar.com.macro.validacion.domain.fido.rest.dto.response;

import com.daon.identityx.rest.model.pojo.Authenticator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

import static java.util.Objects.nonNull;
@Value
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Autenticador implements Serializable {

    @JsonProperty("id")
    String id;
    @JsonProperty("authenticatorid")
    String authenticatorId;
    @JsonProperty("status")
    String status;
    @JsonProperty("logicalname")
    String logicalName;
    @JsonProperty("make")
    String make;
    @JsonProperty("model")
    String model;
    @JsonProperty("devicecorrelationid")
    String deviceCorrelationId;
    @JsonProperty("authenticatorattestationid")
    String authenticatorAttestationId;

    public Autenticador() {
        this.id = null;
        this.authenticatorId = null;
        this.status = null;
        this.logicalName = null;
        this.make = null;
        this.model = null;
        this.deviceCorrelationId = null;
        this.authenticatorAttestationId = null;
    }

    public Autenticador(Authenticator authenticator) {
        this.id = authenticator.getId();
        this.authenticatorId = authenticator.getAuthenticatorId();
        this.status = nonNull(authenticator.getStatus()) ? authenticator.getStatus().name() : null;
        this.logicalName = authenticator.getLogicalName();
        this.make = authenticator.getMake();
        this.model = authenticator.getModel();
        this.deviceCorrelationId = authenticator.getDeviceCorrelationId();
        this.authenticatorAttestationId = authenticator.getAuthenticatorAttestationId();
    }
}
