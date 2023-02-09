package ar.com.macro.validacion.model.client.esb.rest.dto.utils;

import ar.com.macro.commons.values.constants.format.DatePatternsConstants;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientesConstantes {

	public static final int CERO = 0;
	public static final int UNO = 1;
	public static final int DOS = 2;
	public static final int TRES = 3;
	public static final int CUATRO = 4;
	public static final int CINCO = 5;
    public static final int OCHENTA = 80;
    
    public static final String NOMBRE = "nombre";
    public static final String APELLIDO = "apellido";
    public static final String DOCUMENTO = "documento";
    public static final String SEXO = "sexo";
    
    public static final String NOMBRE_PARAMETRO_CONSULTA_GENERAL_CLIENTE_DOCUMENTO = "i_ntributa";
    public static final String NOMBRE_PARAMETRO_NORMALIZACION_INDIVIDUO_NUMERODOCUMENTO = "numeroDocumento";
    
    public static final String MENSAJE_INFORMACION_NO_ENCONTRADA_SERVICIO = "No se encontro informacion al consultar el servicio";
    
    public static final String TIPO_CUENTA_CORRIENTE = "CUENTACORRIENTE";
    public static final String CODIGO_TIPO_CUENTA_CORRIENTE = "CC";
    public static final String TIPO_CAJA_AHORRO = "CUENTA DE AHORROS";
    public static final String CODIGO_TIPO_CAJA_AHORRO = "CA";
    public static final String TIPO_ROL_TITULAR = "T";
    public static final String TIPO_ROL_COTITULAR = "A";
    
    public static final int TIPO_MONEDA_PESO = 80;
    
    public static final String TIPO_TELEFONO_CELULAR = "CELULAR";
    public static final String TIPO_TELEFONO_PARTICULAR = "PARTICULAR";

    public static final int LONGITUD_MINIMA_DNI_NORMALIZABLE_DIRECTAMENTE = 8;
    public static final int VALIDACION_NRO_CUENTA_MAX_SIZE = 16;
    public static final int LONGITUD_DIGITOS_TERMINACION = 4;
    public static final int LONGITUD_MINIMA_EMAIL = 7;
    public static final int LONGITUD_MAXIMA_EMAIL = 64;
    
    public static final int CANTIDAD_PRODUCTOS_POR_PAGINA_ESB = 20;
    
    public static final int LONGITUD_MINIMA_TELEFONO = 4;
    public static final int LONGITUD_MAXIMA_TELEFONO = 12;

    public static final DateTimeFormatter FORMATTER_FECHA_RESPUESTA_ULTIMA_VISITA_CLIENTE
            = DateTimeFormatter.ofPattern(DatePatternsConstants.FECHA_FORMATO_DD_MM_YYYY_CON_BARRAS);
    
    public static final String ESTADO_COMPLETO = "COMPLETO";
    public static final String ESTADO_INCOMPLETO = "INCOMPLETO";
    
    public static final String INICIATIVA_ONB = "onb";
    
    public static final String TIPO_DOCUMENTO_ORIGEN = "96";
    public static final String TIPO_TRIBUTARIO_ORIGEN = "86";
    
    public static final String DDI = "0054";
    
    public static final String TIPO_TELEFONO_ALTA_CLIENTE = "E";
    
    public static final Integer TRANSFORMACIONDATO_ESTADO_ACTIVO = 1;
    
    public  static final String PAIS_OTROS_CODE = "198";
    
    public static final List<String> MOTIVOS_NO_NORMALIZACION_DOMICILIO = new ArrayList<>(Arrays.asList("LI", "CI", "AI"));
    
    public static final String PROCESO_CREACION_CAJA_AHORRO_Y_TARJETA_DEBITO = "Caja Ahorro y Tarjeta Debito";
    
    public static final int STATUS_NO_EXISTE_MAIL_PARA_CLIENTE = 9;
    public static final int STATUS_MAIL_VIGENTE = 1;
    public static final int STATUS_MAIL_NO_VIGENTE = 5;

    public static final String VIGENTE = "Vigente";

    private ClientesConstantes() {
        // para ocultar el constructor default, esta clase no se instancia
    }

}
