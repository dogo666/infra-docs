package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos.GuardarEnroladorRequestAtributos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@GuardarEnroladorRequestAtributos
public class GuardarEnroladorRequest {

    @JsonProperty("email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{api.enrolamiento.GuardarEnroladorRequest.email.alfa.pattern.message}")
    String email;

    @JsonProperty("tipodocumento")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.enrolamiento.GuardarEnroladorRequest.tipodocumento.alfa.pattern.message}")
    String tipodocumento;

    @JsonProperty("numerodocumento")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.enrolamiento.GuardarEnroladorRequest.numerodocumento.alfa.pattern.message}")
    String numerodocumento;

    @JsonProperty("genero")
    @Pattern(regexp = "[A-Z]{1}", message = "{api.enrolamiento.GuardarEnroladorRequest.genero.alfa.pattern.message}")
    String genero;

    @JsonProperty("sucursal")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{api.enrolamiento.GuardarEnroladorRequest.sucursal.alfa.pattern.message}")
    String sucursal;

    @JsonProperty("idcobis")
    @Pattern(regexp = "^(?=\\s*\\S).*$", message = "{api.enrolamiento.GuardarEnroladorRequest.idcobis.alfa.pattern.message}")
    String idCobis;

    @JsonProperty("estado")
    String estado;

    @JsonProperty("cantidaddehuellas")
    String cantidadDeHuellas;

    @JsonProperty("razon")
    String razon;

    @JsonProperty("supervisor")
    String supervisor;

}
