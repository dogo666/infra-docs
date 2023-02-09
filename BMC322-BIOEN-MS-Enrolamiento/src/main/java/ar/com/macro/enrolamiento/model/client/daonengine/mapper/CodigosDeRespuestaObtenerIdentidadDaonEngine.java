package ar.com.macro.enrolamiento.model.client.daonengine.mapper;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodigosDeRespuestaObtenerIdentidadDaonEngine {

    CODIGO_DESCONOCIDO(-1, "El código recibido no se encuentra en el catálogo"),
    IDENTIFICADOR_INVALIDO(221,"Identificador de identidad no válido en Daon Engine"),
    IDENTIDAD_INCOMPATIBLE(225,"Identidad incompatible con el dominio en Daon Engine"),
    CALIFICADOR_INEXISTENTE(266,"Calificador de uso inexistente en Daon Engine"),
    TIPO_INEXISTENTE(268,"Calificador de tipo inexistente en Daon Engine"),
    DATOS_BIOGRAFICOS_INEXISTENTES(282,"Datos biográficos inexistentes en Daon Engine"),
    ELEMENTOS_IDEFINIDOS(283,"Elemento de datos biográficos indefinidos en Daon Engine");

    private final int code;
    private final String message;

    public static CodigosDeRespuestaObtenerIdentidadDaonEngine buscarPorCodigo(Integer codigo){
        var codigoOpt =
                Arrays.stream(CodigosDeRespuestaObtenerIdentidadDaonEngine.values())
                        .filter(en -> en.code == codigo)
                        .findFirst();

        if (codigoOpt.isPresent()) {
            return codigoOpt.get();
        }

        return CODIGO_DESCONOCIDO;
    }

}
