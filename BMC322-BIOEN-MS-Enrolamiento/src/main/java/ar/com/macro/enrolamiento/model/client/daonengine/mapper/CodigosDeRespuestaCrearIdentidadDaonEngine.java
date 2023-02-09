package ar.com.macro.enrolamiento.model.client.daonengine.mapper;

import lombok.AllArgsConstructor;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum CodigosDeRespuestaCrearIdentidadDaonEngine {

    IDENTIDAD_EXISTENTE(223,"Identidad existente en Daon Engine"),
    CALIFICADOR_INCOMPATIBLE(233,"El calificador de tipo no es compatible con el dominio en Daon Engine"),
    POLITICA_INVALIDA(240,"Política inválida en Daon Engine"),
    GRUPO_INEXISTENTE(252,"Grupo inexistente en Daon Engine"),
    IDENTIFICADOR_INVALIDO(250,"Identificador de grupo no válido en Daon Engine"),
    ALIAS_EXISTENTE(254,"Alias de usuario existente en Daon Engine"),
    GRUPO_BLOQUEADO(258,"Grupo bloqueado en Daon Engine"),
    DATOS_BIOMETRICOS_INVALIDOS(260,"Datos biométricos no válidos en Daon Engine"),
    DATOS_BIOMETRICOS_EXISTENTES(261,"Datos biométricos existentes en Daon Engine"),
    CALIFICADOR_INEXISTENTE(266,"Calificador de uso inexistente en Daon Engine"),
    TIPO_INEXISTENTE(268,"Calificador de tipo inexistente en Daon Engine"),
    TRANSFRMACION_DATOS(272,"Error de transformación de datos biométricos en Daon Engine"),
    CALIFICADOR_INCOMPATIBLE_CON_OPERACION(275,"El calificador de uso no es compatible con la operación en Daon Engine"),
    DATOS_BIOGRAFICOS_INVALIDOS(280,"Datos biográficos no válidos en Daon Engine"),
    DATOS_BIOGRAFICOS_INDEFINIDOS(283,"Elemento de datos biográficos indefinidos en Daon Engine"),
    CLAVE_NO_VALIDO(290,"Identificador de clave no válido en Daon Engine"),
    CLAVE_INEXISTENTE(291,"Clave inexistente en Daon Engine"),
    INFORMACION_INVALIDA(292,"Nonce de información privada no válida en Daon Engine");

    public final int code;
    public final String message;

    public static Optional<CodigosDeRespuestaCrearIdentidadDaonEngine> buscarPorCodigo(Integer codigo){
        return Arrays.stream(CodigosDeRespuestaCrearIdentidadDaonEngine.values()).filter(en -> en.code == codigo).findFirst();
    }

}
