package ar.com.macro.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errores {

	ERROR_OBTENER_USUARIO_IDENTITY_X(75052, "Ocurrió un error en Identity-X al tratar de obtener el registro"),
	ERROR_GENERAR_TRANSACCION(75053, "Ocurrió un error al tratar de generar la transacción para la validación de huella en RENAPER"),
	ERROR_CONSULTAR_TCN(75054, "Ocurrió un error al realizar la validación de huella en RENAPER"),
	ERROR_RENAPER_ERROR_VALIDACION(75055, "Ocurrió un error en Renaper al realizar la validacion"),
	ERROR_RENAPER_GENERAL(75068, "Ocurrió un error de conexión con RENAPER"),
	ERROR_CREAR_USUARIO_IDENTITY_X(75056, "Ocurrió un error en Identity-X al tratar de crear el perfil del usuario"),
	ERROR_CONEXION_IDENTITY_X(75057, "Ocurrió un error de conexión con Identity-X"),
	ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X(75067, "Ocurrió un error en Identity-X al realizar la validación la  nueva imagen de perfil."),
	ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X(75058, "Ocurrió un error en Identity-X al agregar la imagen de perfil."),
	ERROR_ELIMINAR_USUARIO_IDENTITY_X(75059, "Ocurrió un error en Identity-X al tratar de eliminar el perfil del usuario"),
	ERROR_OBTENER_IDENTIDAD_DAON_ENGINE(75060,"Ocurrió un error en Daon Engine al realizar la consulta de identidad"),
	ERROR_CREAR_IDENTIDAD_DAON_ENGINE(75061,"Ocurrió un error en Daon Engine al crear el perfil del usuario"),
	ERROR_CREAR_IDCHECK_IDENTITY_X(75062, "Ocurrió un error en Identity-X al tratar de crear el identificador de control."),
	ERROR_ENVIAR_DOCUMENTO_IDENTITY_X(75063, "Ocurrió un error en Identity-X al tratar de enviar el documento de identidad."),
	ERROR_OBTENER_DATOS_OCR_IDENTITY_X(75064, "Ocurrió un error en Identity-X al tratar de obtener la información del documento"),
	ERROR_ACTUALIZAR_IDENTIDAD_DAON_ENGINE(75065, "Ocurrió un error en Daon Engine al actualizar el perfil del usuario"),
	ERROR_CONVERTIR_JSON_A_OBJETO(75066, "Ocurrió un error al convertir json a objeto"),
	ERROR_APLICACION_NO_ENCONTRADA(75069, "No se encontró aplicación configurada para el enrolamiento de rostro."),
	ERROR_POLITICA_NO_ENCONTRADA(75070, "No se encontró politica configurada para el enrolamiento de rostro."),
	ERROR_OBTENER_APLICACION_IDENTITY_X(75071, "Ocurrió un error en Identity-X en la búsqueda de la aplicación."),
	ERROR_OBTENER_POLITICA_IDENTITY_X(75072, "Ocurrió un error en Identity-X en la búsqueda de la política de la aplicación."),
	ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X(75073, "Ocurrió un error en Identity-X al registrar al usuario en la aplicación."),
	ERROR_BUSQUEDA_PERFIL_IDENTITY_X(75074, "Ocurrió un error en Identity-X en la búsqueda del perfil solicitado."),
	ERROR_AGREGAR_DNI_PROFILE_USUARIO_IDENTITY_X(75075, "Ocurrió un error en Identity-X al tratar de crear el perfil del usuario."),
	ERROR_AGREGAR_DNI_PROFILE_IDCHECK_IDENTITY_X(75076, "Ocurrió un error en Identity-X al tratar de crear el identificador de control."),
	ERROR_AGREGAR_DNI_PROFILE_SUBMIT_DOCUMENT(75077, "Ocurrió un error en Identity-X al tratar de enviar el documento de identidad."),
	ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X(75111, "Ocurrió un error en Identity-X al tratar de crear video challenge."),
	ERROR_3DFL_SUBIR_VIDEO_IDENTITY_X(75112, "Ocurrió un error en Identity-X al tratar de subir video."),
	ERROR_CREAR_EVALUACION_IDENTITY_X(75113, "Ocurrió un error en Identity-X al tratar de crear evaluacion del video."),
	ERROR_3DFL_CREAR_VIDEO_IDENTITY_X(75114, "Ocurrió un error en Identity-X al tratar de crear video."),
	ERROR_BIOMETRIC_DATA_VACIO(75078, "La lista de datos biometricos obtenida en Daon Engine esta vacia"),
	ERROR_FILTRO_BIOMETRIC_DATA(75079, "Ocurrio un error al filtrar los datos biometricos provenientes de Daon Engine"),
	ERROR_AL_TRADUCIR_DATOS_DAON_A_RENAPER_CONSULTA(75080, "Ocurrió un error en traducción de Daon a Renaper en consulta de identidad"),
	ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON(75107, "Ocurrió un error en traducción de Renaper a DAON"),
	ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ENROLAR(75081, "Ocurrió un error en traducción de Renaper a Daon en enrolamiento"),
	ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ACTUALIZAR(75082,"Ocurrió un error en traducción de Renaper a Daon en actualizacion"),
	ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ENROLAR(75083, "Ocurrio un error al generar la lista de datos biometricos en enrolamiento"),
	ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ACTUALIZAR(75084, "Ocurrio un error al generar la lista de datos biometricos en actualizacion"),
	ERROR_GENERANDO_LA_LISTA_BIOGRAFIC_DATA(75085, "Ocurrio un error al generar la lista de datos biograficos"),
	ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ENROLAR(75086, "Ocurrio un error agregando items de huellas en enrolamiento"),
	ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ACTUALIZACION(75087, "Ocurrio un error agregando items de huellas en actualizacion"),
	ERROR_OPCION_BLOQUEADO_EN_ACTUALIZACION(75088, "Ocurrio un error en el formato de la opción bloqueado en actualizacion"),
	ERROR_DATOS_GENERALES(75090, "Ocurrió un error en la consulta de datos generales"),
	ERROR_AGREGAR_DNI_PERFIL(75091, "Ocurrió un error al agregar dni perfil"),
    ERROR_CONSULTAR_DATOS_DNI(75092,"Ocurrio un error al consultar los datos del dni "),
	ERROR_OBTENER_REGISTRACION_IDENTITY_X(75093, "Ocurrió un error en Identity-X obteniendo registración."),
	ERROR_AUTENTICACION_REQUEST_IDENTITY_X(75094, "Ocurrió un error en Identity-X con una Autenticacion."),
	ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X(75095, "Ocurrió un error en Identity-X al tratar de crear una Autenticacion."),
	ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X(75096, "Ocurrió un error en Identity-X al tratar de actualizar una Autenticacion."),
	ERROR_OBTENER_CREAR_EVALUACION_IDENTITY_X(75097, "Ocurrió un error en Identity-X al tratar de crear la evaluación"),
	ERROR_POLITICA_EVALUACION_NO_ENCONTRADA(75098, "No se encontró política de evaluación configurada para la creación de la política de evaluación."),
	ERROR_CREACION_EVALUACION_VACIO(75099, "La respuesta de evaluación esta vacia."),
	ERROR_CREACION_EVALUACION_LISTA_ITEMS(75100, "La lista de items de respuesta en evaluación no contiene la lista esperada"),
	ERROR_ESPERA_RESULTADO_VALIDACION_HUELLA (75101, "Ocurrió un error en la espera del resultado de la validación de huella en ReNaPer"),
	DATAPOWER_NO_DISPONIBLE(75102, "DataPower no esta disponible"),
	DATAPOWER_INFORMACION_NO_ENCONTRADA(75103, "Información no encontrada en DataPower"),
	ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X(75104, "Ocurrió un error en Identity-X al consultar verificación de una Autenticacion."),
	ERROR_OBTENER_FACE_ENROLED_IDENTITYX(75105, "Ocurrió un error al obtener el face enrolled de identityX"),
    ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE(75106,"Ocurrió un error en Daon Engine al realizar la verificacion de Identificación"),
	ERROR_OBTENER_LIST_SUMMARY_AUDITS(75108, "Ocurrió un error al obtener validacion de huella previamente realizada en DAON Engine"),
	ERROR_OBTENER_LIST_SUMMARY_AUDITS_NOT_MATCH(75109, "Validacion de huella previamente realizada en DAON Engine No encontrada"),
	ERROR_UMBRAL_POLITICA_DAON_ENGINE_NO_ENCONTRADA(75110, "No se encontró la configuracion del umbral para la politica."),
	ERROR_UMBRAL_RENAPER_3DFL_NO_ENCONTRADA(75115, "No se encontró la configuracion del umbral renaper."),
	ERROR_GET_FACES_IDENTITY_X(75116, "Ocurrió un error en Identity-X al obtener la lista de registros de rostros."),
	ERROR_VALIDAR_ROSTRO_PERSONA_IDENTITY_X(75117, "Ocurrió un error inesperado en el proceso de validación de rostro con video."),
	ERROR_VALIDACION_RENAPER_3DFL_NO_ENCONTRADA(75118, "No se encontró validacion renaper 3dfl configurada."), // NO VA A EXISTIR
	ERROR_CREAR_SHA256(75119, "Ocurrió un error al crear el hash"),
	ERROR_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO(75120, "Ocurrió un error al crear la registracion."),
	ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY(75121, "Debe existir una definición de política fido en el objeto de política especificado."),
	ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_SPONSORSHIPTOKEN(75122, "El código de patrocinio proporcionado venció o ya se completó."),
	ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE(75123, "Ocurrió un error al crear la registracion."),
	ERROR_FIDO_PROCESAR_REGISTRATION_CHALLENGE(75124, "Ocurrió un error al procesar la registracion."),
	ERROR_FIDO_GUARDANDO_REGISTRATION_CHALLENGE(75125, "Ocurrió un error al guardar la registracion."),
	ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE(75126, "No se puede encontrar el procesar la registracion."),
	ERROR_FIDO_USER_REGISTRATION_CHALLENGE(75127, "La respuesta de registro no pertenece al usuario de IdentityX."),
	ERROR_FIDO_CREAR_AUTENTICACION(75128, "Ocurrió un error al crear la Autenticacion."),
	ERROR_FIDO_USER_BLOQUEADO(75129, "Usuario bloqueado o suspendido."),
	ERROR_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES(75130, "No se pudo crear una solicitud de autenticación para el usuario porque no tiene autenticadores registrados."),
	ERROR_FIDO_VALIDAR_AUTENTICACION(75131, "Error validando autenticacion."),
	ERROR_FIDO_NO_VALIDAR_AUTENTICACION(75132, "No se pudo validar la autenticacion."),
	ERROR_FIDO_NO_AUTENTICACION_REQUEST(75133, "No se pudo encontrar la autenticacion."),
	ERROR_FIDO_GUARDANDO_AUTENTICACION_REQUEST(75134, "Ocurrió un error al guardar la autenticacion."),
	ERROR_GET_SENSITIVE_DATA_IDENTITY_X(75135, "Ocurrió un error en Identity-X al obtener la imagen de la selfie."),
    ERROR_GET_AUTHENTICATION_REQUEST(75136, "Ocurrió un error obteniendo el Request de Autenticación."),
    ERROR_GET_AUTHENTICATION_REQUEST_USERID_NO_CORRESPONDE(75137, "El Request de Autenticación encontrado no corresponde al usuario."),
    ERROR_COMPARAR_HUELLAS_UNO_A_POCOS(75138, "Ocurrió un error inesperado comparando las huellas del usuario."),
    ERROR_COMPARAR_HUELLAS_UNO_A_POCOS_ERROR_CODE_DAON(75139, "Se han obtenido diferentes códigos de error desde DAON en el proceso de Uno a pocos."),
	ERROR_FIDO_OBTENIENDO_AUTENTICADORES(75140, "Ocurrió un error al intentar obtener los autenticadores."),
	ERROR_FIDO_ARCHIVANDO_AUTENTICADOR(75141, "Ocurrió un error al intentar archivar un autenticador."),
	ERROR_FIDO_CONSULTAR_REGISTRATION_CHALLENGE(75142, "Ocurrió un error al consultar la registracion."),
	ERROR_GET_ENROLLMENTS_IDENTITY_X(75143, "Ocurrió un error consultando los enrollments del usuario en IdentityX."),
	ERROR_GET_SAMPLES_IDENTITY_X(75144, "Ocurrió un error consultando los samples del usuario en IdentityX."),
	ERROR_GET_IDCHECKS_IDENTITY_X(75145, "Ocurrió un error consultando los idchecks del usuario en IdentityX."),
	ERROR_GET_DOCUMENTS_IDENTITY_X(75146, "Ocurrió un error consultando los documentos del usuario en IdentityX."),
	ERROR_GET_CLIENTS_CAPTURE_IDENTITY_X(75147, "Ocurrió un error consultando los document capture del usuario en IdentityX."),
    ERROR_CONSULTANDO_IMAGENES_DNI(75148, "Ocurrió un error obteniendo las imagenes del DNI para el usuario en IdentityX.");
	
    private Integer codigo;
	private String mensaje;

}