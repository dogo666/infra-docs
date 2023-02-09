package ar.com.macro.constants;

public class Constantes {
	
	public static final String X_OPERATION_ID_VALUE = "35DCD7B5-AE5A-4106-871B-001314DA1462";
    
	public static final String JSON_CREAR_PERFIL_USUARIO_REQUEST = "crear_perfil_usuario_REQUEST.json";
	public static final String JSON_CREAR_PERFIL_USUARIO_SIN_ID_REQUEST = "crear_perfil_usuario_sin_id_REQUEST.json";
	public static final String JSON_CREAR_PERFIL_USUARIO_RESPONSE = "crear_perfil_usuario_RESPONSE.json";
	public static final String JSON_CREAR_PERFIL_RESPONSE = "crear_perfil_RESPONSE.json";

	public static final String JSON_AGREGAR_PERFIL_USUARIO_REQUEST = "agregar_perfil_usuario_REQUEST.json";
	public static final String JSON_AGREGAR_PERFIL_USUARIO_SIN_ID_REQUEST = "agregar_perfil_usuario_sin_id_REQUEST.json";
	public static final String JSON_AGREGAR_PERFIL_RESPONSE = "agregar_perfil_RESPONSE.json";

	public static final String JSON_ELIMINAR_PERFIL_USUARIO_REQUEST = "eliminar_perfil_usuario_REQUEST.json";

	public static final String JSON_ACTUALIZAR_HUELLA_DAONENGINE_REQUEST = "actualizar_huella_daonengine_REQUEST.json";
	public static final String JSON_ACTUALIZAR_HUELLA_DAONENGINE_RESPONSE = "actualizar_huella_daonengine_RESPONSE.json";
	
	public static final String JSON_ACTUALIZAR_TRAZAS_DAONENGINE_REQUEST = "actualizar_trazas_daonengine_REQUEST.json";
    public static final String JSON_ACTUALIZAR_TRAZAS_DAONENGINE_RESPONSE = "actualizar_trazas_daonengine_RESPONSE.json";

	public static final String JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE = "identityx_crear_perfil_usuario_RESPONSE.json";

	public static final Integer ERROR_CREAR_PERFIL_USUARIO_REST_IDENTITY_X_EXCEPTION_CODIGO =75057;
	
	public static final Integer CODIGO_GENERAL_VALIDACION = 99000;

	public static final String JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_REQUEST = "consulta_identidad_atributos_REQUEST.json";
	public static final String JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_FALTANTES_REQUEST = "consulta_identidad_atributos_faltantes_REQUEST.json";
	public static final String JSON_CONSULTA_IDENTIDAD_CUIL_REQUEST = "consulta_identidad_cuil_REQUEST.json";
	public static final String JSON_CONSULTA_IDENTIDAD_RESPONSE = "consulta_identidad_RESPONSE.json";
	
	public static final String JSON_VALIDAR_HUELLA_RENAPER_REQUEST = "validar_huella_renaper_REQUEST.json";
	public static final String JSON_VALIDAR_HUELLA_RENAPER_SEXO_INVALIDO_REQUEST = "validar_huella_renaper_sexo_invalido_REQUEST.json";
	public static final String JSON_VALIDAR_HUELLA_RENAPER_SIN_HUELLAS_REQUEST = "validar_huella_renaper_sin_huellas_REQUEST.json";
	public static final String JSON_VALIDAR_HUELLA_RENAPER_CANTIDAD_HUELLAS_REQUEST = "validar_huella_renaper_cantidad_huellas_REQUEST.json";


	public static final String JSON_ENROLAR_HUELLA_DAONENGINE_REQUEST = "enrolar_huella_daonengine_REQUEST.json";
	public static final String JSON_ENROLAR_HUELLA_DAONENGINE_RESPONSE = "enrolar_huella_daonengine_RESPONSE.json";

	public static final String RENAPER_HUELLA = "huella1.txt";
	
	public static final String RENAPER_PULGAR_DERECHO = "1";
	public static final String RENAPER_PULGAR_IZQUIERDO = "6";
	
	public static final String RENAPER_CONSULTA_TCN_HIT = "HIT";
	public static final String RENAPER_CONSULTA_TCN_NOHIT = "NOHIT";
	public static final String RENAPER_CONSULTA_TCN_MENSAJE_OK = "OK";

	public static final String SEXO_MASCULINO = "M";

	public static final Integer ERROR_CREAR_PERFIL_USUARIO_EXISTENTE_CODIGO = 520;
	public static final String ERROR_CREAR_PERFIL_USUARIO_EXISTENTE_MSG = "Could not create user - The unique identifier already exists";

	public static final Integer ERROR_AGREGAR_PERFIL_USUARIO_CODIGO = 75067;
	public static final String ERROR_AGREGAR_PERFIL_USUARIO_MSG = "Ocurrió un error al agregar dni perfil";

	public static final Integer ERROR_GENERAR_TRANSACCION_CODIGO = 75053;
	public static final String ERROR_GENERAR_TRANSACCION_MENSAJE = "Ocurrió un error al tratar de generar la transacción para la validación de huella en RENAPER";


	public static final Integer ERROR_ELIMINAR_PERFIL_USUARIO_CODIGO = 75059;
	public static final String ERROR_ELIMINAR_PERFIL_USUARIO_MENSAJE = "Ocurrió un error en Identity-X al tratar de eliminar el perfil del usuario";

	public static final String URL_CREAR_PERFIL_USUARIO = "/enrolamiento/ix/perfil/crear";
	public static final String URL_CONSULTAR_IDENTIDAD = "/enrolamiento/identidad/consultar";
	public static final String URL_AGREGAR_PERFIL_USUARIO = "/enrolamiento/ix/perfil/dni/agregar";

	public static final String URL_AGREGAR_CREAR_EVALUACION_IDENTITY_X = "/enrolamiento/ix/perfil/evaluacion/crear";

	public static final String URL_RENAPER_VALIDACION_DNI = "/enrolamiento/rnp/persona/dni/validar";
	public static final String URL_RENAPER_VALIDACION_ROSTRO = "/enrolamiento/rnp/persona/rostro/validar";
	
	public static final String URL_VALIDAR_HUELLA_RENAPER = "/enrolamiento/rnp/persona/huella/validar";

	public static final String URL_CONSULTAR_DNI_PERSONA_IDENTITY_X = "/enrolamiento/ix/datos/dni/consultar";
	public static final String JSON_CONSULTAR_DATOS_DNI_REQUEST = "consultar_datos_dni_REQUEST.json";
	public static final String JSON_CONSULTAR_DATOS_DNI_SIN_ID_REQUEST = "consultar_datos_dni_sin_id_REQUEST.json";
	public static final String JSON_OBTENER_REGISTRO_USUARIO_RESPONSE = "obtener_registro_usuario_RESPONSE.json";


	public static final String JSON_LEER_DNI_PERSONA_REQUEST_VALIDO = "leer_dni_persona_ok_REQUEST.json";
	public static final String JSON_LEER_DNI_PERSONA_SIN_FRENTE_REQUEST = "leer_dni_persona_sin_frente_REQUEST.json";
	public static final String JSON_LEER_DNI_PERSONA_SIN_DORSO_REQUEST = "leer_dni_persona_sin_dorso_REQUEST.json";
	
	public static final String JSON_CREAR_ID_CHECK_IDENTITY_X_RESPONSE = "identityx_crear_idcheck_RESPONSE.json";
	public static final String JSON_ENVIAR_FRENTE_Y_DORSO_IDENTITY_X_RESPONSE = "identityx_enviar_frente_dorso_RESPONSE.json";
	public static final String JSON_OBTENER_DATOS_OCR_IDENTITY_X_RESPONSE = "identityx_obtener_datos_ocr_RESPONSE.json";
	public static final String JSON_OBTENER_DATOS_OCR_MRZ_IDENTITY_X_RESPONSE = "identityx_obtener_datos_ocr_mrz_RESPONSE.json";
	
	public static final String IDENTITYX_OCR_SEXO = "12";
	public static final String IDENTITYX_OCR_NUMERO_DOCUMENTO = "2";
	public static final String IDENTITYX_OCR_NOMBRE_Y_APELLIDOS = "25";
	public static final String IDENTITYX_OCR_APELLIDOS = "8";
	public static final String IDENTITYX_OCR_NOMBRES = "9";
	
	public static final String PATRON_FORMATO_FECHA = "yyyyMMddhhmmssSSS";

	public static final String JSON_VALIDACION_RENAPER_DNI_REQUEST = "renaper_validar_persona_dni_request_valido.json";
	public static final String JSON_VALIDACION_RENAPER_DNI_REQUEST_INVALIDO = "renaper_validar_persona_dni_request_invalido.json";
	public static final String JSON_VALIDACION_RENAPER_ROSTRO_REQUEST = "renaper_validar_persona_rostro_request_valido.json";
	public static final String JSON_VALIDACION_RENAPER_ROSTRO_REQUEST_INVALIDO = "renaper_validar_persona_rostro_request_invalido.json";

	public static final String JSON_ACTUALIZAR_ROSTRO_IDENTITYX_REQUEST = "actualizar_rostro_identityx_REQUEST.json";
	public static final String JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST = "enrolar_rostro_identityx_REQUEST.json";
	public static final String JSON_ENROLAR_ROSTRO_IDENTITYX_SIN_IDUSUARIO_REQUEST = "enrolar_rostro_identityx_sin_idusuario_REQUEST.json";
	public static final String JSON_ENROLAR_ROSTRO_IDENTITYX_SIN_SELFIE_REQUEST = "enrolar_rostro_identityx_sin_selfie_REQUEST.json";
	public static final String JSON_CREAR_EVALUACION_IDENTITYX_REQUEST = "crear_evaluacion_identityx_REQUEST.json";
	public static final String JSON_CREAR_EVALUACION_IDENTITYX_RESPONSE = "crear_evaluacion_identityx_RESPONSE.json";

	public static final String JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST = "validar_rostro_identityx_sin_REQUEST.json";

	public static final String URL_ACTUALIZAR_ROSTRO_IDENTITYX = "/enrolamiento/ix/persona/rostro/actualizar";
	public static final String URL_ENROLAR_ROSTRO_IDENTITYX = "/enrolamiento/ix/persona/rostro/enrolar";
	public static final String URL_VALIDAR_ROSTRO_IDENTITYX = "/enrolamiento/ix/persona/rostro/validar";

	public static final String JSON_CONSULTAR_ENROLAMIENTO_REQUEST = "consultar_enrolamiento_REQUEST.json";

	public static final String JSON_CONSULTAR_DATOS_HUELLAS_RESPONSE = "consultar_enrolamiento_huellas_RESPONSE.json";

	public static final Integer ERROR_CONSULTAR_ENROLAMIENTO_CODIGO = 75055;

	public static final String ERROR_CONSULTAR_ENROLAMIENTO_MSG = "Error consultando en Daon Engine";

	public static final String URL_CONSULTAR_ENROLAMIENTO = "/enrolamiento/consultar";

	public static final String URL_ENROLAR_HUELLA_DAONENGINE = "/enrolamiento/de/persona/huella/enrolar";

	public static final String URL_ACTUALIZAR_HUELLA_DAONENGINE = "/enrolamiento/de/persona/huella/actualizar";
	
	public static final String URL_ACTUALIZAR_TRAZAS_DAONENGINE = "/enrolamiento/de/persona/trazas/guardar";

	public static final String JSON_CONSULTAR_ENROLAMIENTO_RESPONSE = "consultar_enrolamiento_RESPONSE.json";

	public static final String NOMBRE_MICROSERVICIO_DATOS_GENERALES = "macro-enrolamiento";

	public static final String NOMBRE_PARAMETROS_DATOS_GENERALES = "configuracion-huellas-daon-engine";

	public static final String JSON_GUARDAR_DATOS_ENROLADOR_REQUEST = "guardar_datos_enrolador_REQUEST.json";

	public static final String JSON_GUARDAR_DATOS_ENROLADOR_RESPONSE = "guardar_datos_enrolador_RESPONSE.json";

	public static final String URL_GUARDAR_DATOS_ENROLADOR = "/enrolamiento/enrolador/guardar";

	public static final String JSON_CONSULTAR_DATOS_ENROLADOR_REQUEST = "consultar_datos_enrolador_REQUEST.json";
	public static final String JSON_CONSULTAR_DATOS_ENROLADOR_RESPONSE = "consultar_datos_enrolador_RESPONSE.json";

	public static final String URL_CONSULTAR_DATOS_ENROLADOR = "/enrolamiento/enrolador/consultar";

	public static final String X_APPLICATION_ID_VALUE = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBsaWNhdGlvbklkIjoiRW5yb2xhbWllbnRvQXBwIn0.qlnvig59ZmGYx79SIpTrqqPKDqdRCoYEO-VcpUUL3YI";
	
	public static final String JSON_VALIDAR_ROL_USUARIO_REQUEST = "validar_rol_usuario_REQUEST.json";
	public static final String JSON_VALIDAR_ROL_USUARIO_BAD_REQUEST = "validar_rol_usuario_bad_REQUEST.json";
    public static final String JSON_VALIDAR_ROL_USUARIO_RESPONSE = "validar_rol_usuario_RESPONSE.json";
    public static final String URL_VALIDAR_ROL_USUARIO = "/enrolamiento/usuario/rol/validar";

}
