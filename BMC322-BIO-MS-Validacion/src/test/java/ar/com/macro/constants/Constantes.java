package ar.com.macro.constants;

public class Constantes {

    public static final String X_OPERATION_ID_VALUE = "35DCD7B5-AE5A-4106-871B-001314DA1462";

    public static final String JSON_CREAR_PERFIL_USUARIO_REQUEST = "crear_perfil_usuario_REQUEST.json";

    public static final String JSON_AGREGAR_PERFIL_USUARIO_REQUEST = "agregar_perfil_usuario_REQUEST.json";
    public static final String JSON_AGREGAR_PERFIL_USUARIO_SIN_ID_REQUEST = "agregar_perfil_usuario_sin_id_REQUEST.json";
    public static final String JSON_AGREGAR_PERFIL_RESPONSE = "agregar_perfil_RESPONSE.json";

    public static final String JSON_ELIMINAR_PERFIL_USUARIO_REQUEST = "eliminar_perfil_usuario_REQUEST.json";

    public static final String JSON_ACTUALIZAR_HUELLA_DAONENGINE_REQUEST = "actualizar_huella_daonengine_REQUEST.json";

    public static final String JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE = "identityx_crear_perfil_usuario_RESPONSE.json";
    
    public static final String JSON_VALIDAR_HUELLA_DAON_REQUEST = "validar_huella_daon_REQUEST.json";
    public static final String JSON_VALIDAR_HUELLA_DAON_SIN_USUARIO_REQUEST = "validar_huella_daon_sin_usuario_REQUEST.json";
    public static final String JSON_VALIDAR_HUELLA_DAON_SIN_IDENTIFICADOR_REQUEST = "validar_huella_daon_sin_identificador_REQUEST.json";
    public static final String JSON_VALIDAR_HUELLA_DAON_SIN_HUELLA_REQUEST = "validar_huella_daon_sin_huella_REQUEST.json";
    public static final String JSON_VALIDAR_HUELLA_DAON_RESPONSE = "validar_huella_daon_RESPONSE.json";

    public static final String JSON_VALIDAR_HUELLAS_UNO_A_POCOS_REQUEST = "validar_huellas_uno_a_pocos_daon_REQUEST.json";
    public static final String JSON_VALIDAR_HUELLAS_UNO_A_POCOS_RESPONSE_MATCH = "validar_huellas_uno_a_pocos_daon_match_RESPONSE.json";
    public static final String JSON_VALIDAR_HUELLAS_UNO_A_POCOS_RESPONSE_NOMATCH = "validar_huellas_uno_a_pocos_daon_nomatch_RESPONSE.json";
    public static final String JSON_VALIDAR_HUELLAS_UNO_A_POCOS_SIN_ID_USUARIO_REQUEST = "validar_huellas_uno_a_pocos_daon_sin_id_usuario_REQUEST.json";
    public static final String JSON_VALIDAR_HUELLAS_UNO_A_POCOS_SIN_HUELLAS_REQUEST = "validar_huellas_uno_a_pocos_daon_sin_huellas_REQUEST.json";
    
    public static final Integer ERROR_CREAR_PERFIL_USUARIO_REST_IDENTITY_X_EXCEPTION_CODIGO = 75057;

    public static final Integer CODIGO_GENERAL_VALIDACION = 99000;

    public static final String JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_REQUEST = "consulta_identidad_atributos_REQUEST.json";
    public static final String JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_FALTANTES_REQUEST = "consulta_identidad_atributos_faltantes_REQUEST.json";
    public static final String JSON_CONSULTA_IDENTIDAD_RESPONSE = "consulta_identidad_RESPONSE.json";

    public static final String JSON_VALIDAR_HUELLA_RENAPER_REQUEST = "validar_huella_renaper_REQUEST.json";

    public static final String JSON_ENROLAR_HUELLA_DAONENGINE_REQUEST = "enrolar_huella_daonengine_REQUEST.json";

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

    public static final String JSON_CONSULTAR_DATOS_DNI_REQUEST = "consultar_datos_dni_REQUEST.json";
    public static final String JSON_CONSULTAR_DATOS_DNI_SIN_ID_REQUEST = "consultar_datos_dni_sin_id_REQUEST.json";
    public static final String JSON_OBTENER_REGISTRO_USUARIO_RESPONSE = "obtener_registro_usuario_RESPONSE.json";

    public static final String JSON_LEER_DNI_PERSONA_REQUEST_VALIDO = "leer_dni_persona_ok_REQUEST.json";
    public static final String JSON_LEER_DNI_PERSONA_SIN_FRENTE_REQUEST = "leer_dni_persona_sin_frente_REQUEST.json";
    public static final String JSON_LEER_DNI_PERSONA_SIN_DORSO_REQUEST = "leer_dni_persona_sin_dorso_REQUEST.json";

    public static final String JSON_CREAR_ID_CHECK_IDENTITY_X_RESPONSE = "identityx_crear_idcheck_RESPONSE.json";
    public static final String JSON_ENVIAR_FRENTE_Y_DORSO_IDENTITY_X_RESPONSE = "identityx_enviar_frente_dorso_RESPONSE.json";
    public static final String JSON_OBTENER_DATOS_OCR_IDENTITY_X_RESPONSE = "identityx_obtener_datos_ocr_RESPONSE.json";

    public static final String IDENTITYX_OCR_NUMERO_DOCUMENTO = "2";
    public static final String IDENTITYX_OCR_NOMBRE_Y_APELLIDOS = "25";

    public static final String PATRON_FORMATO_FECHA = "yyyyMMddhhmmssSSS";

    public static final String JSON_VALIDACION_RENAPER_DNI_REQUEST = "renaper_validar_persona_dni_request_valido.json";
    public static final String JSON_VALIDACION_RENAPER_DNI_REQUEST_INVALIDO = "renaper_validar_persona_dni_request_invalido.json";

    public static final String JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST = "enrolar_rostro_identityx_REQUEST.json";
    public static final String JSON_CREAR_EVALUACION_IDENTITYX_REQUEST = "crear_evaluacion_identityx_REQUEST.json";

    public static final String JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST = "validar_rostro_identityx_sin_REQUEST.json";

    public static final String JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_REQUEST = "consultar_verificacion_rostro_identityx_request.json";

    public static final String JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_SIN_IDVERIFICACION_REQUEST = "consultar_verificacion_rostro_identityx_sin_idverificacion_request.json";

    public static final String JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_RESPONSE = "consultar_verificacion_rostro_identityx_response.json";

    public static final String JSON_CONSULTAR_ENROLAMIENTO_REQUEST = "consultar_enrolamiento_REQUEST.json";

    public static final Integer ERROR_CONSULTAR_ENROLAMIENTO_CODIGO = 75055;

    public static final String JSON_CONSULTAR_ENROLAMIENTO_RESPONSE = "consultar_enrolamiento_RESPONSE.json";

    public static final String NOMBRE_MICROSERVICIO_DATOS_GENERALES = "macro-validacion";

    public static final String NOMBRE_PARAMETROS_DATOS_GENERALES = "configuracion-huellas-daon-engine";
    
    public static final String JSON_CONSULTAR_IDENTIFICACION_CLIENTE_RESPONSE = "consultar_identificacion_cliente_RESPONSE.json";
    
    public static final String JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST = "validar_rostro_video_persona_identityx_REQUEST.json";
    public static final String JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_SIN_IDUSUARIO_REQUEST = "validar_rostro_viseo_persona_identityx_sin_idusuario_REQUEST.json";
    public static final String JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_SIN_VIDEO_REQUEST = "validar_rostro_video_persona_identityx_sin_video_REQUEST.json";
    public static final String JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_SIN_IDCHECK_REQUEST = "validar_rostro_video_persona_identityx_sin_idcheck_REQUEST.json";
    
    public static final String JSON_CREAR_VIDEO_CHALLENGE_3DFL_IDENTITYX_RESPONSE = "crear_video_challenge_3dfl_identityx_response.json";
    public static final String JSON_CREAR_EVALUACION_3DFL_IDENTITYX_RESPONSE = "crear_evaluacion_3dfl_identityx_response.json";
    public static final String JSON_CREAR_REGISTRACION_FIDO_IDENTITYX_REQUEST = "crear_registracion_fido_identityx_REQUEST.json";
    public static final String JSON_PROCESAR_REGISTRACION_FIDO_IDENTITYX_REQUEST = "procesar_registracion_fido_identityx_REQUEST.json";
    public static final String JSON_CREAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST = "crear_autenticacion_fido_identityx_REQUEST.json";
    public static final String JSON_VALIDAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST = "validar_autenticacion_fido_identityx_REQUEST.json";
    public static final String JSON_GET_AUTENTICACION_REQUEST_FIDO_IDENTITYX_REQUEST = "get_autenticacion_request_fido_identityx_REQUEST.json";
    public static final String JSON_GET_AUTENTICADORES_REQUEST_FIDO_IDENTITYX_REQUEST = "get_autenticadores_request_fido_identityx_REQUEST.json";
    public static final String JSON_ARCHIVAR_AUTENTICADOR_REQUEST_FIDO_IDENTITYX_REQUEST = "archivar_autenticador_request_fido_identityx_REQUEST.json";
    public static final String JSON_CONSULTAR_REGISTRACION_REQUEST_FIDO_IDENTITYX_REQUEST = "get_registracion_request_fido_identityx_REQUEST.json";
    public static final String URL_CONSULTAR_ENROLAMIENTO = "/validacion/enrolamiento/consultar";
    public static final String URL_CONSULTAR_IDENTIDAD = "/validacion/identidad/consultar";
    public static final String URL_RENAPER_VALIDACION_DNI = "/validacion/rnp/persona/dni/validar";
    public static final String URL_AGREGAR_PERFIL_USUARIO = "/validacion/ix/perfil/dni/agregar";

    public static final String URL_VALIDAR_ROSTRO_IDENTITYX = "/validacion/ix/persona/rostro/validar";
    public static final String URL_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX = "/validacion/ix/persona/rostro/verificacion/consultar";
    public static final String URL_CONSULTAR_VALIDACION_HUELLA= "/validacion/de/persona/huella/validacion/consultar";
    public static final String URL_VALIDAR_ROSTRO_VIDEO_IDENTITYX = "/validacion/ix/persona/video/rostro/validar";

    public static final String JSON_CONSULTAR_VALIDACION_HUELLA_REQUEST = "consultar_validacion_huella_REQUEST.json";
    public static final String JSON_CONSULTAR_VALIDACION_HUELLA_SIN_IDREQUESTTRACKING_REQUEST = "consultar_validacion_huella_sin_idrequesttracking_REQUEST.json";
    public static final String JSON_CONSULTAR_VALIDACION_HUELLA_RESPONSE = "consultar_validacion_huella_RESPONSE.json";

    public static final String JSON_LIST_SUMMARY_AUDITS_RESPONSE = "consultar_validacion_huella_listSummaryAudits_RESPONSE.json";

    public static final String URL_VALIDAR_HUELLA_DAON = "/validacion/de/persona/huella/validar";
    
    public static final String URL_VALIDAR_HUELLAS_UNO_A_POCOS_DAON = "/validacion/de/persona/huellas/unoapocos/validar";

    public static final String JSON_CREAR_HASH256_REQUEST = "crear_hash256_REQUEST.json";
    public static final String JSON_CREAR_HASH256_RESPONSE = "crear_hash256_RESPONSE.json";
    public static final String URL_CREAR_HASH = "/validacion/crear/hash";

    public static final String URL_CREAR_REGISTRACION_FIDO_IDENTITYX = "/validacion/fido/registracion/crear";
    public static final String URL_PROCESAR_REGISTRACION_FIDO_IDENTITYX = "/validacion/fido/registracion/procesar";
    public static final String URL_CREAR_AUTENTICACION_FIDO_IDENTITYX = "/validacion/fido/autenticacion/crear";
    public static final String URL_VALIDAR_AUTENTICACION_FIDO_IDENTITYX = "/validacion/fido/autenticacion/validar";
    public static final String URL_AUTENTICACION_CONSULTAR_FIDO_IDENTITYX = "/validacion/fido/autenticacion/consultar";
    public static final String URL_AUTENTICADORES_CONSULTAR_FIDO_IDENTITYX = "/validacion/fido/autenticadores/consultar";
    public static final String URL_AUTENTICADOR_ARCHIVAR_FIDO_IDENTITYX = "/validacion/fido/autenticador/archivar";
    public static final String JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST = "consultar_persona_identityx_REQUEST.json";
    public static final String URL_CONSULTAR_PERSONA_IDENTITYX = "/validacion/ix/persona/consultar";
    public static final String URL_CONSULTAR_REGISTRACION_FIDO_IDENTITYX = "/validacion/fido/registracion/consultar";
    
    public static final String JSON_DNI_IMAGES_ENROLLMENTS = "get_dni_images_enrollments_RESPONSE.json";
    public static final String JSON_DNI_IMAGES_CLIENT_CAPTURE = "get_dni_images_clientcapture_RESPONSE.json";
    public static final String JSON_DNI_IMAGES_CLIENT_DOCUMENTS = "get_dni_images_documents_RESPONSE.json";
    public static final String JSON_DNI_IMAGES_ID_CHECK = "get_dni_images_idcheck_RESPONSE.json";
    public static final String JSON_DNI_IMAGES_SAMPLES = "get_dni_images_samples_RESPONSE.json";
    public static final String JSON_DNI_IMAGES_SENSITIVE_DATA = "get_dni_images_sensitivedata_RESPONSE.json";
    public static final String JSON_DNI_IMAGES_USER_ID_CHECKS = "get_dni_images_user_idchecks_RESPONSE.json";
}

