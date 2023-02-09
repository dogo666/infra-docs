package ar.com.macro.enrolamiento.domain.identityx.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.constant.Errores.ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_APLICACION_NO_ENCONTRADA;
import static ar.com.macro.constant.Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_POLITICA_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_POLITICA_EVALUACION_NO_ENCONTRADA;
import static ar.com.macro.constant.Errores.ERROR_POLITICA_NO_ENCONTRADA;
import static ar.com.macro.constant.Errores.ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;

import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ActualizarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ConsultarDatosDniRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.EnrolarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ConsultarDatosDniResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.CrearEvaluacionPerfilResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ValidarRostroPersonaResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CodigoBarrasResponse;
import ar.com.macro.exceptions.ConsultarDatosDniIdentityXException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.RegistracionNoEncontradoIdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;

public class IdentityXControllerTest extends ContextoTest {

	@TestConfiguration
    static class ApiInterceptorConfiguration {
        static class MockApiInterceptor extends ApiInterceptor {
            MockApiInterceptor() {
                super();
            }

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                return true;
            }
        }

        @Bean
        public ApiInterceptor apiInterceptor() {
            return new IdentityXControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }
	
	@Test
	public void actualizarRostroIdentidadExito() throws Exception {
    	String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_ROSTRO_IDENTITYX_REQUEST);
		ejecutarPostConHeaderResponseEmpty(Constantes.URL_ACTUALIZAR_ROSTRO_IDENTITYX, jsonRequest, status().is2xxSuccessful(), Constantes.X_OPERATION_ID_VALUE);
	}

	@Test
	public void actualizarRostroIdentidadErrorObteniendoUsuario() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X))
				.when(enrolamientoService).actualizarRostro(any(ActualizarRostroRequest.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ACTUALIZAR_ROSTRO_IDENTITYX, jsonRequest, status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(), response.getError().getCodigo());

	}

	@Test
	public void actualizarRostroIdentidadErrorActualizando() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X))
				.when(enrolamientoService).actualizarRostro(any(ActualizarRostroRequest.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ACTUALIZAR_ROSTRO_IDENTITYX, jsonRequest, status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getCodigo(), response.getError().getCodigo());

	}
	
	@Test
	public void actualizarRostroIdentidadErrorValidandoImagen() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X))
				.when(enrolamientoService).actualizarRostro(any(ActualizarRostroRequest.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ACTUALIZAR_ROSTRO_IDENTITYX, jsonRequest, status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getCodigo(), response.getError().getCodigo());

	}
	
	@Test
	public void enrolarRostroExito() throws Exception {
    	String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
	}
	
	@Test
	public void enrolarRostroExitoErrorSinIdUsuario() throws Exception {
    	
    	String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_SIN_IDUSUARIO_REQUEST);
    	
		MvcResult result = ejecutarPostConHeader(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);
		
		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void enrolarRostroExitoErrorSinSelfie() throws Exception {
    	
    	String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_SIN_SELFIE_REQUEST);
    	
		MvcResult result = ejecutarPostConHeader(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);
		
		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorObteniendoUsuario() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_BUSQUEDA_PERFIL_IDENTITY_X))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorAgregandoImagenRostro() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorValidandoImagenRostro() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorBuscandoNombreAplicacion() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_APLICACION_NO_ENCONTRADA))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_APLICACION_NO_ENCONTRADA.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorObteniendoAplicacionIdentityX() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_OBTENER_APLICACION_IDENTITY_X))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_APLICACION_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorBuscandoPolitica() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_POLITICA_NO_ENCONTRADA))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_POLITICA_NO_ENCONTRADA.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorObteniendoPoliticaIdentityX() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_POLITICA_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void enrolarRostroIdentidadErrorRegistrandoUsuarioIdentityX() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);

		doThrow( new IdentityXException(ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X))
				.when(enrolamientoService).enrolarRostro(any(EnrolarRostroRequest.class), any(String.class));

		MvcResult result = ejecutarPostConHeaderResponseEmpty(Constantes.URL_ENROLAR_ROSTRO_IDENTITYX, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}

	@Test
	public void validarRostroExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenReturn(new ValidarRostroPersonaResponse());
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is2xxSuccessful(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void validarRostroErrorObteniendoUsuarioIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoConfiguracionAplicacionID() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(Errores.ERROR_APLICACION_NO_ENCONTRADA));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_APLICACION_NO_ENCONTRADA.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoRegistracion() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new RegistracionNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoAplicacionIndentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_OBTENER_APLICACION_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_APLICACION_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoPoliticaIndentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_POLITICA_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorCrearAutenticacionIndentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorActualizarAutenticacionIndentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

  @Test
	public void debeConsultarDatosDniExito() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);

		ConsultarDatosDniResponse consultarDatosDniResponse = new ConsultarDatosDniResponse();

		CodigoBarrasResponse codigoBarras = new CodigoBarrasResponse();
		codigoBarras.setNumero("19016612");
		codigoBarras.setApellidos("CHUANG");
		consultarDatosDniResponse.setCodigobarras(codigoBarras);
		when(enrolamientoService.consultarDatosDni(any(ConsultarDatosDniRequest.class))).thenReturn(consultarDatosDniResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_DNI_PERSONA_IDENTITY_X, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarDatosDniResponse> response = obtenerRespuesta(result, ConsultarDatosDniResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
		assertEquals("19016612", response.getDatos().getCodigobarras().getNumero());
		assertEquals("CHUANG", response.getDatos().getCodigobarras().getApellidos());
	}

	@Test
	public void cuandoConsultarDatosDniSinIdException() throws  Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_SIN_ID_REQUEST);
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_DNI_PERSONA_IDENTITY_X, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarDatosDniResponse> response = obtenerRespuesta(result, ConsultarDatosDniResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.CODIGO_GENERAL_VALIDACION, response.getError().getCodigo());
	}

	@Test
	public void cuandoConsultarDatosDniObtenerDatosOcrIdentityXExcepcion() throws  Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);

		when(enrolamientoService.consultarDatosDni(any(ConsultarDatosDniRequest.class))).thenThrow(
				new IdentityXException(Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getCodigo(), Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getMensaje()));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_DNI_PERSONA_IDENTITY_X, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarDatosDniResponse> response = obtenerRespuesta(result, ConsultarDatosDniResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}

	@Test
	public void cuandoConsultarDatosDniExcepcion() throws  Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);

		when(enrolamientoService.consultarDatosDni(any(ConsultarDatosDniRequest.class))).thenThrow(
				new ConsultarDatosDniIdentityXException(Errores.ERROR_CONSULTAR_DATOS_DNI.getCodigo(), Errores.ERROR_CONSULTAR_DATOS_DNI.getMensaje()));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_DNI_PERSONA_IDENTITY_X, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarDatosDniResponse> response = obtenerRespuesta(result, ConsultarDatosDniResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Errores.ERROR_CONSULTAR_DATOS_DNI.getCodigo(), response.getError().getCodigo());
	}

	@Test
	public void cuandoConsultarDatosDniObtenerUsuarioExcepcion() throws  Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);

		when(enrolamientoService.consultarDatosDni(any(ConsultarDatosDniRequest.class))).thenThrow(
				new UsuarioNoEncontradoIdentityXException(Errores.ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(), Errores.ERROR_OBTENER_USUARIO_IDENTITY_X.getMensaje()));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_DNI_PERSONA_IDENTITY_X, jsonRequest, status().isNotFound(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarDatosDniResponse> response = obtenerRespuesta(result, ConsultarDatosDniResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Errores.ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(), response.getError().getCodigo());
	}



	@Test
	public void ejecutarCrearEvaluacionExito() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		final String jsonResponse = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_RESPONSE);
		CrearEvaluacionPerfilResponse crearEvaluacionPerfilResponse = mapper.readValue(jsonResponse, CrearEvaluacionPerfilResponse.class);
		when(perfilService.crearEvaluacion(any(CrearEvaluacionPerfilRequest.class), anyString())).thenReturn(crearEvaluacionPerfilResponse);
		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_CREAR_EVALUACION_IDENTITY_X, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<CrearEvaluacionPerfilResponse> response = obtenerRespuesta(result, CrearEvaluacionPerfilResponse.class);
		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void ejecutarCrearEvaluacionError() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		doThrow( new IdentityXException(ERROR_POLITICA_EVALUACION_NO_ENCONTRADA))
				.when(perfilService).crearEvaluacion(any(CrearEvaluacionPerfilRequest.class), anyString());
		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_CREAR_EVALUACION_IDENTITY_X, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<CrearEvaluacionPerfilResponse> response = obtenerRespuesta(result, CrearEvaluacionPerfilResponse.class);
		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}


}
