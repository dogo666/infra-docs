package ar.com.macro.validacion.model.client.daonengine.mapper;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine {

    IDENTIDAD_INEXISTENTE(222),
    DATOS_BIOMETRICOS_INEXISTENTES(262);

    public final int code;

    public static boolean buscarPorCodigo(Integer codigo){
        return Arrays.stream(CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine.values()).filter(en -> en.code == codigo).findFirst().isPresent();
    }
}
