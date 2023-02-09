package ar.com.macro.validacion.model.client.daonengine.mapper;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodigosDeRespuestaVerificarIdentificacionDaonEngine {

    CODIGO_DESCONOCIDO(-1, "Código recibido desde DAON no identificado"),
	IDENTIFICADOR_IDENTIDAD_INVALIDO(221,"Identificador de identidad invalido"),
	IDENTIDAD_INEXISTENTE(222,"Identidad inexistente"),
	INDICADOR_TIPO_NO_SOPORTADO_DOMINIO(233,"Type qualifier not supported by domain"),
	POLITICA_INVALIDA(240,"Politica invalida"),
	POLITICA_INEXISTENTE(242,"Politica inexistente"),
	INDICADOR_USO_NO_SOPORTADO(243,"Type usage not supported by policy"),
	IDENTIFICADOR_POLITICA_INVALIDO(244,"Identificador de politica invalido"),
	POLITICA_INCOMPATIBLE(245,"Policy incompatible with domain"),
	DATOS_BIOMETRICOS_INVALIDOS(260,"Datos biometricos invalidos"),
	DATOS_BIOMETRICOS_INEXISTENTES(262,"Datos biometricos inexistentes"),
	SET_DATOS_BIOMETRICOS_INCOMPLETOS(263,"Set de datos biometricos incompletos"),
	INDICADOR_USO_INEXISTENTE(266,"Non-existent usage qualifier"),
	INDICADOR_TIPO_INEXISTENTE(268,"Non-existent type qualifier"),
	INDICADOR_TIPO_NO_SOPORTADO_OPERACION(269,"Type qualifier not supported for operation"),
	ERROR_TRANSFORMACION_DATOS_BIOMETRICOS(272,"Error de transformacion de datos biometricos"),
	SET_DATOS_BIOMETRICOS_INCOMPATIBLE(273,"Set de datos biometricos incompatible"),
	INDICADORES_INCOMPATIBLES(274,"Incompatible type usage qualifiers"),
	INDICADOR_USO_NO_SOPORTADO_OPERACION(275,"Usage qualifier not supported for operation"),
	CLAVE_INVALIDA(290,"Clave identificadora invalida"),
	CLAVE_INEXISTENTE(291,"Clave inexistente"),
	INFORMACION_PRIVADA_INVALIDA(292,"Informacion privada invalida")
	;
	
	public final int code;
    public final String message;
    
    public static CodigosDeRespuestaVerificarIdentificacionDaonEngine buscarPorCodigo(Integer codigo){
        var codigoOpt = Arrays.stream(CodigosDeRespuestaVerificarIdentificacionDaonEngine.values()).filter(en -> en.code == codigo).findFirst();
        if (codigoOpt.isPresent()) {
            return codigoOpt.get();
        }

        return CODIGO_DESCONOCIDO;
    }
}
