package ar.com.macro.validacion.domain;

import java.io.Serializable;
import java.util.List;


import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.constant.Errores;
import ar.com.macro.exceptions.*;
import com.identityx.auth.support.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.utils.rest.dto.Error;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.utils.rest.dto.ValidationError;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import ar.com.macro.exceptions.RenaperException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class BaseController {
	
	private static final Integer CODIGO_GENERAL_VALIDACION = 99000;
	
	/**
	 * Caso de recurso no encontrado
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarRutaNoEncontradaException(
            NoHandlerFoundException e) {
        return crearRespuestaExcepcion(CODIGO_GENERAL_VALIDACION, String.format("No se encontro metodo %s para URL %s", e.getHttpMethod(), e.getRequestURL()), HttpStatus.NOT_FOUND);
    }
	
	/**
	 * Caso de error en validacion
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarArgumentoNoValidoException(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		return crearRespuestaExcepcion(CODIGO_GENERAL_VALIDACION, crearMensajeErrorForObjectErrorsCustom(allErrors), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Caso de header faltante
	 * @param e
	 * @return
	 */
	@ExceptionHandler(org.springframework.web.bind.MissingRequestHeaderException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarHeaderFaltante(org.springframework.web.bind.MissingRequestHeaderException e) {
		return crearRespuestaExcepcion(CODIGO_GENERAL_VALIDACION, String.format("Invocacion fallida: Header faltante %s", e.getHeaderName()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IdentityXException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarErrorIdentityX(IdentityXException e) {
		return crearRespuestaExcepcion(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MacroException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarErrorMacro(MacroException e) {
		return crearRespuestaExcepcion(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RestException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarErrorRestException(
			RestException e) {
		log.error(Errores.ERROR_CONEXION_IDENTITY_X.getMensaje(), e);
		return crearRespuestaExcepcion(Errores.ERROR_CONEXION_IDENTITY_X.getCodigo(), Errores.ERROR_CONEXION_IDENTITY_X.getMensaje(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InformacionNoEncontradaException.class)
	public ResponseEntity<Respuesta<Serializable>> handleErrorComunicacionExcepcion(InformacionNoEncontradaException e) {
		return crearRespuestaExcepcion(e, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UsuarioNoEncontradoIdentityXException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarErrorIdentityX(UsuarioNoEncontradoIdentityXException e) {
		
		return crearRespuestaExcepcion(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConsultarDatosDniIdentityXException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarErrorConsultarDatosDniIdentityX(ConsultarDatosDniIdentityXException e) {
		log.error(Errores.ERROR_CONSULTAR_DATOS_DNI.getMensaje(), e);
		return crearRespuestaExcepcion(Errores.ERROR_CONSULTAR_DATOS_DNI.getCodigo(), Errores.ERROR_CONSULTAR_DATOS_DNI.getMensaje(), HttpStatus.CONFLICT);
	}


	@ExceptionHandler(ComunicacionFallidaException.class)
	public ResponseEntity<Respuesta<Serializable>> handlerException(ComunicacionFallidaException e) {
		return crearRespuestaExcepcion(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RenaperException.class)
	public ResponseEntity<Respuesta<Serializable>> handlerException(RenaperException e) {
		log.error(e.getMessage(), e);
		return crearRespuestaExcepcion(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DaonEngineException.class)
	public ResponseEntity<Respuesta<Serializable>> handlerException(DaonEngineException e) {
		log.error(e.getMessage(), e);
		return crearRespuestaExcepcion(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ObtenerDatosGeneralesCompuestosException.class)
	public ResponseEntity<Respuesta<Serializable>> handlerException(ObtenerDatosGeneralesCompuestosException e) {
		log.error(e.getMessage(), e);
		return crearRespuestaExcepcion(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CrearHashException.class)
	public ResponseEntity<Respuesta<Serializable>> manejarErrorCrearHashException(CrearHashException e) {
		return crearRespuestaExcepcion(e, HttpStatus.CONFLICT);
	}

	/**
	 * Genera una respuesta http con un codigo general a partir de errores de validacion
	 * @param validationErrors lista de errores de validacion
	 * @return respuesta con con estado 409
	 */
	private ResponseEntity<Respuesta<Serializable>> crearRespuestaErrorConflict(List<ValidationError> validationErrors) {
		StringBuilder builder = new StringBuilder();
		validationErrors.stream().findFirst().ifPresent(validationError -> {
			builder.append(validationError.getObject());
			builder.append(": ");
			builder.append(validationError.getReason());
		});
		return crearRespuestaExcepcion(CODIGO_GENERAL_VALIDACION, crearMensajeError(validationErrors), HttpStatus.CONFLICT);
	}
	
	/**
	 * Genera una cadena a partir de los errores de validacion
	 * @param validationErrors lista de errores de validacion
	 * @return cadena con el error presente
	 */
	private String crearMensajeError(List<ValidationError> validationErrors) {
		StringBuilder builder = new StringBuilder();
		validationErrors.stream().findFirst().ifPresent(validationError -> {
			builder.append(validationError.getObject());
			builder.append(": ");
			builder.append(validationError.getReason());
		});
		return builder.toString();
	}

	/**
	 * Genera una cadena a partir de los errores de validacion
	 * @param objectErrors lista de errores custom de validacion
	 * @return cadena con el error presente
	 */
	private String crearMensajeErrorForObjectErrors( List<ObjectError> objectErrors) {
		StringBuilder builder = new StringBuilder();
		objectErrors.stream().findFirst().ifPresent(objectError -> {
			builder.append(objectError.getCode());
			builder.append(": ");
			builder.append(objectError.getDefaultMessage());
		});
		return builder.toString();
	}

	/**
	 * Genera una cadena a partir de los errores de validacion
	 * @param objectErrors lista de errores custom de validacion
	 * @return cadena con el error presente
	 */
	private String crearMensajeErrorForObjectErrorsCustom( List<ObjectError> objectErrors) {
		StringBuilder builder = new StringBuilder();
		objectErrors.stream().findFirst().ifPresent(objectError -> {
			builder.append(objectError.getDefaultMessage());
		});
		return builder.toString();
	}

	/**
	 * Genera una respuesta http a partir de una excepcion y un codigo de estado
	 * @param excepcion Excepcion a partir de la cual obtener codigo y mensaje
	 * @param h codigo de estado
	 * @return
	 */
	private ResponseEntity<Respuesta<Serializable>> crearRespuestaExcepcion(MacroException excepcion, HttpStatus h) {
		log.error(excepcion.getMessage(), excepcion);
		return ResponseEntity.status(h).body(generarRespuestaError(excepcion.getCodigo(), excepcion.getMessage()));
	}
	
	/**
	 * Genera una respuesta http a partir de un codigo interno, un mensaje y un codigo de estado
	 * @param codigo codigo interno
	 * @param mensaje
	 * @param h codigo de estado
	 * @return
	 */
	private ResponseEntity<Respuesta<Serializable>> crearRespuestaExcepcion(Integer codigo, String mensaje, HttpStatus h) {
		return ResponseEntity.status(h).body(generarRespuestaError(codigo, mensaje));
	}
	
	/**
	 * Genera un objeto de respuesta con los datos de error
	 * @param codigo codigo interno
	 * @param mensaje
	 * @return
	 */
	private Respuesta<Serializable> generarRespuestaError(Integer codigo, String mensaje) {
    	return Respuesta.builder().error(Error.builder().codigo(codigo).mensaje(mensaje).build()).build();
	}
}
