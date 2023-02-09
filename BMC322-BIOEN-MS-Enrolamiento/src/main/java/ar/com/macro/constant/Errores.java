package ar.com.macro.constant;

import java.util.Arrays;
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
	ERROR_BIOMETRIC_DATA_VACIO(75078, "La lista de datos biometricos obtenida en Daon Engine esta vacia"),
	ERROR_FILTRO_BIOMETRIC_DATA(75079, "Ocurrio un error al filtrar los datos biometricos provenientes de Daon Engine"),
	ERROR_AL_TRADUCIR_DATOS_DAON_A_RENAPER_CONSULTA(75080, "Ocurrió un error en traducción de Daon a Renaper en consulta de identidad"),
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
	ERROR_OBTENER_FACE_ENROLED_IDENTITYX(75105, "Ocurrió un error al obtener el face enrolled de identityX"),
	ERROR_ESPERA_RESULTADO_CONSULTAR_DATOS_DNI(75106, "Ocurrió un error en la espera del resultado de la consulta de datos DNI"),
	ERROR_GUARDAR_DATOS_ENROLADOR_FEING(75109,"Ocurrio un error al guardar los datos del enrolador en el MS de Validacion"),
	ERROR_GUARDAR_DATOS_ENROLADOR_OBJETO_VACIO(75112,"Objeto guardar datos enrolador vacio"),
	ERROR_CONSULTAR_ENROLADORES_FEIGN(75113,"Ocurrio un error al consultar los enroladores en el MS Datos Generales"),
	ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE_FEIGN(75114,"Ocurrio un error al consultar las sucursales con enroladores en estado pendiente en el MS Datos Generales"),
    ERROR_FACE_NOT_FOUND(75115, "No se pudo encontrar una cara en la imagen"),
    ERROR_TOO_MANY_FACES(75116, "Se encontro más de una cara en la foto."),
    ERROR_FACE_TO_CLOSE(75117, "La cara esta muy cerca de la camara."),
    ERROR_FACE_CLOSE_TO_BORDER(75118, "La cara esta muy cercana a uno de los bordes de la camara."),
    ERROR_FACE_CROPPED(75119, "La cara no se ve completa."),
    ERROR_FACE_TOO_SMALL(75120, "La cara se ve muy pequeña, podría ser que el cliente este muy lejos de la camara."),
    ERROR_FACE_ANGLE_TO_LARGE(75121, "No se pudo encontrar una cara en la imagen."),
    ERROR_FACE_IS_OCCLUDED(75122, "No se ve correctamente la cara en la imagen."),
    ERROR_OTHER_ERROR(75123, "Hubo un error en la captura por favor intentalo nuevamente."),
    ERROR_COULD_NOT_ASSESS_PASIVE_FACE_LIVENESS(75124, "Hubo un error en la captura por favor intentalo nuevamente."),
    ERROR_PASIVE_FACE_LIVENESS_CHECK_FAILED(75125, "Hubo un error en la captura por favor intentalo nuevamente."),
    ERROR_FACE_LIVENESS_IMPROVE_LIGHTING(75126, "No se pudo encontrar una cara en la imagen."),
    ERROR_FACE_LIVENESS_COULD_NOT_FIND_EYES(75127, "No se ven correctamente los ojos en la imagen."),
    ERROR_CONSULTAR_ENROLADOR_POR_DNI_FEIGN(75128, "No se encontraron datos para el enrolador."),
	ERROR_GUARDANDO_TRAZAS(75129, "Ocurrio un error guardando los datos de auditoria en Daon"),
	ERROR_VALIDANDO_USUARIO_GRUPO_AD(75135, "Ocurrio un error validando si el usuario hace parte del Rol.");
  
	private Integer codigo;
	private String mensaje;
	
	public static Errores getValueOf(final Integer codigo) {
	    return Arrays.asList(values())
	        .stream()
	        .filter(error -> error.getCodigo().equals(codigo))
	        .findFirst()
	        .orElse(null);
	  }
}
