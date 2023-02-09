package ar.com.macro.validacion.domain.daonengine.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsultarValidacionHuellaPersonaRequest {

    @Schema(description = "Id request tracking UID")
    @NotBlank(message = "{api.validacion.no.blank.message}")
    private String idrequesttracking;
}
