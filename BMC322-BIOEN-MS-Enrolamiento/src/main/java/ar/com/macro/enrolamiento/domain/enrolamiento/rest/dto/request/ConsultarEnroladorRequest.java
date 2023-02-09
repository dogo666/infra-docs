package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsultarEnroladorRequest {

    @JsonProperty("email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{api.enrolamiento.GuardarEnroladorRequest.email.alfa.pattern.message}")
    private String email;

}
