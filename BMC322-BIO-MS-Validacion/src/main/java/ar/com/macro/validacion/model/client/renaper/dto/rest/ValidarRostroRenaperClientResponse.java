package ar.com.macro.validacion.model.client.renaper.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidarRostroRenaperClientResponse {
    Integer code;
    String message;
    Integer confidence;
    Boolean matchThreshold;
    Integer faceAuthenticationId;

}
