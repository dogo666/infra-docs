package ar.com.macro.biometria.commons.trace.feing.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuardarTrazaRequest {

    private Integer indicador;
    private String endpoint;
    private String request;
    private String response;
    private Integer codigohttp;
    private String valorcodigohttp;
    private String mensajeexcepcion;
    private String stacktrace;
    private String idrastreosesion;
}
