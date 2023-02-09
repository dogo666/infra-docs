package ar.com.macro.enrolamiento.model.client.daonengine.mapper;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine {

    IDENTIDAD_INEXISTENTE(222),
    DATOS_BIOMETRICOS_INEXISTENTES(262);

    public final int code;

    public static boolean buscarPorCodigo(Integer codigo){
        return Arrays.stream(CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine.values()).filter(en -> en.code == codigo).findFirst().isPresent();
    }
}
