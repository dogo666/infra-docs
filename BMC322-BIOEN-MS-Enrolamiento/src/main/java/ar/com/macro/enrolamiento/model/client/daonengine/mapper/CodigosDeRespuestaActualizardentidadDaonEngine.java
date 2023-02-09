package ar.com.macro.enrolamiento.model.client.daonengine.mapper;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodigosDeRespuestaActualizardentidadDaonEngine {
  
  CODIGO_DESCONOCIDO(-1, "El código recibido no se encuentra en el catálogo"),
  IDENTIFICADOR_INVALIDO(221, "Identificador de identidad no válido en Daon Engine"),
  IDENTIDAD_INEXISTENTE(222, "Identidad inexistente en Daon Engine"),
  CALIFICADOR_INCOMPATIBLE(
      233, "El calificador de tipo no es compatible con el dominio en Daon Engine"),
  POLIZA_NO_EXISTENTE(242, "La poliza no existe"),
  GRUPO_INVALIDO(250, "Identificador de grupo no válido en Daon Engine"),
  ALIAS_INEXISTENTE(254, "Alias de usuario existente en Daon Engine"),
  GRUPO_BLOQUEADO(258, "Grupo bloqueado en Daon Engine"),
  DATOS_INVALIDOS(260, "Datos biométricos no válidos en Daon Engine"),
  CALIFICADOR_INEXISTENTE(266, "Calificador de uso inexistente en Daon Engine"),
  CALIFICADOR_TIPO_INEXISTENTE(268, "Calificador de tipo inexistente en Daon Engine"),
  TRANSFRMACION_DATOS(272, "Error de transformación de datos biométricos en Daon Engine"),
  CALIFICADOR_INCOMPATIBLE_CON_OPERACION(
      275, "El calificador de uso no es compatible con la operación en Daon Engine"),
  DATOS_BIOGRAFICOS_INVALIDOS(280, "Datos biográficos no válidos en Daon Engine"),
  DATOS_BIOGRAFICOS_INDEFINIDOS(283, "Elemento de datos biográficos indefinidos en Daon Engine"),
  CLAVE_NO_VALIDO(290, "Identificador de clave no válido en Daon Engine"),
  CLAVE_INEXISTENTE(291, "Clave inexistente en Daon Engine"),
  INFORMACION_INVALIDA(292, "Nonce de información privada no válida en Daon Engine");

  private final int code;
  private final String message;

  public static CodigosDeRespuestaActualizardentidadDaonEngine buscarPorCodigo(Integer codigo) {
    var codigoOpt =
        Arrays.stream(CodigosDeRespuestaActualizardentidadDaonEngine.values())
            .filter(en -> en.code == codigo)
            .findFirst();

    if (codigoOpt.isPresent()) {
      return codigoOpt.get();
    }

    return CODIGO_DESCONOCIDO;
  }
}