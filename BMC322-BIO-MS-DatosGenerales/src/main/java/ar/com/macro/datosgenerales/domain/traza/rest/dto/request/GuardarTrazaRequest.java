package ar.com.macro.datosgenerales.domain.traza.rest.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuardarTrazaRequest {

    Integer indicador;
    String endpoint ;
    String request;
    String response;
    Integer codigohttp;
    String valorcodigohttp;
    String mensajeexcepcion;
    String stacktrace;
    String idrastreosesion;
    String dni;
    String microservicio;
}
