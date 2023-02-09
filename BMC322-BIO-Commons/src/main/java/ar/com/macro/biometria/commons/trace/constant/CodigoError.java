package ar.com.macro.biometria.commons.trace.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CodigoError {
    ERROR_DE_SISTEMA(HttpStatus.CONFLICT, "80000", "Error interno en biometria commons");
    private HttpStatus httpStatus;
    private String codigo;
    private String mensaje;
}
