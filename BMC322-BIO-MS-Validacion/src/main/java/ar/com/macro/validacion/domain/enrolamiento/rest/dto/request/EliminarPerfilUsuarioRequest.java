package ar.com.macro.validacion.domain.enrolamiento.rest.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EliminarPerfilUsuarioRequest {


    @NotEmpty(message = "{api.validacion.no.blank.message}")
    @JsonProperty("userId")
    private String userId;
}
