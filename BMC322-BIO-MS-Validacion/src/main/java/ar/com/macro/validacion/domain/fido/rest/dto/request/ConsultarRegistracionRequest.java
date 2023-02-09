package ar.com.macro.validacion.domain.fido.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarRegistracionRequest {

    @Schema(description = "Id Registration Challenge de Identity-X", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    @JsonProperty("registrationchallengeid")
    String registrationChallengeId;

}
