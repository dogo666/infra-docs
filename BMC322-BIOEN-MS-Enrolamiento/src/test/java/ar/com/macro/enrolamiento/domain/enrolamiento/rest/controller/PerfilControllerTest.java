package ar.com.macro.enrolamiento.domain.enrolamiento.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.Serializable;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.identityx.auth.support.RestException;
import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.AgregarDniPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnroladorRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.GuardarEnroladorListaRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ValidarRolUsuarioRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.AgregarDniPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnroladorEstadoResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.GuardarEnroladorResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ValidarRolUsuarioResponse;
import ar.com.macro.exceptions.ComunicacionFeingException;
import ar.com.macro.exceptions.GeneralException;
import ar.com.macro.exceptions.IdentityXException;

public class PerfilControllerTest extends ContextoTest {

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
			return new PerfilControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
		}
	}

	@Test
	public void debeCrearPerfilUsuarioExito() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_REQUEST);

		final String jsonResponse = retrieveBody(Constantes.JSON_CREAR_PERFIL_RESPONSE);
		CrearPerfilResponse crearPerfilUsuarioResponse = mapper.readValue(jsonResponse, CrearPerfilResponse.class);

		when(perfilService.crearPerfilUsuario(any(CrearPerfilRequest.class))).thenReturn(crearPerfilUsuarioResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CREAR_PERFIL_USUARIO, jsonRequest, status().isOk(),
				Constantes.X_OPERATION_ID_VALUE);
		Respuesta<CrearPerfilResponse> response = obtenerRespuesta(result, CrearPerfilResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void debeCrearPerfilUsuarioErrorSinIdUsuario() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_SIN_ID_REQUEST);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CREAR_PERFIL_USUARIO, jsonRequest,
				status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<CrearPerfilResponse> response = obtenerRespuesta(result, CrearPerfilResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}

	@Test
	public void debeCrearPerfilUsuarioErrorIdentityX() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_REQUEST);

		when(perfilService.crearPerfilUsuario(any(CrearPerfilRequest.class))).thenThrow(
				new IdentityXException(Constantes.ERROR_CREAR_PERFIL_USUARIO_EXISTENTE_CODIGO,
						Constantes.ERROR_CREAR_PERFIL_USUARIO_EXISTENTE_MSG));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CREAR_PERFIL_USUARIO, jsonRequest,
				status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<CrearPerfilResponse> response = obtenerRespuesta(result, CrearPerfilResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.ERROR_CREAR_PERFIL_USUARIO_EXISTENTE_CODIGO, response.getError().getCodigo());
	}

	@Test
	public void debeCrearPerfilUsuarioRestException() throws Exception, JsonProcessingException {

		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_REQUEST);

		doThrow(new RestException(""))
				.when(perfilService).crearPerfilUsuario(any(CrearPerfilRequest.class));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CREAR_PERFIL_USUARIO, jsonRequest,
				status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.ERROR_CREAR_PERFIL_USUARIO_REST_IDENTITY_X_EXCEPTION_CODIGO,
				response.getError().getCodigo());

	}

	@Test
	public void debeAgregarPerfilUsuarioExito() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_REQUEST);

		final String jsonResponse = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_RESPONSE);
		AgregarDniPerfilResponse agregarDniPerfilResponse = mapper.readValue(jsonResponse,
				AgregarDniPerfilResponse.class);

		when(perfilService.agregarDniPerfilUsuario(any(AgregarDniPerfilRequest.class)))
				.thenReturn(agregarDniPerfilResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO, jsonRequest, status().isOk(),
				Constantes.X_OPERATION_ID_VALUE);
		Respuesta<AgregarDniPerfilResponse> response = obtenerRespuesta(result, AgregarDniPerfilResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void debeAgregarPerfilUsuarioErrorSinIdUsuario() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_SIN_ID_REQUEST);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO, jsonRequest,
				status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<AgregarDniPerfilResponse> response = obtenerRespuesta(result, AgregarDniPerfilResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}

	@Test
	public void debeAgregarPerfilUsuarioErrorIdentityX() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_REQUEST);

		when(perfilService.agregarDniPerfilUsuario(any(AgregarDniPerfilRequest.class))).thenThrow(
				new IdentityXException(Constantes.ERROR_AGREGAR_PERFIL_USUARIO_CODIGO,
						Constantes.ERROR_AGREGAR_PERFIL_USUARIO_MSG));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO, jsonRequest,
				status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<AgregarDniPerfilResponse> response = obtenerRespuesta(result, AgregarDniPerfilResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.ERROR_AGREGAR_PERFIL_USUARIO_CODIGO, response.getError().getCodigo());
	}

	@Test
	public void debeAgregarPerfilUsuarioRestException() throws Exception, JsonProcessingException {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_REQUEST);

		doThrow(new RestException(""))
				.when(perfilService).agregarDniPerfilUsuario(any(AgregarDniPerfilRequest.class));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO, jsonRequest,
				status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.ERROR_CREAR_PERFIL_USUARIO_REST_IDENTITY_X_EXCEPTION_CODIGO,
				response.getError().getCodigo());

	}

	@Test
	public void guardarDatosEnroladorOk() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_GUARDAR_DATOS_ENROLADOR_REQUEST);
		String jsonResponse = retrieveBody(Constantes.JSON_GUARDAR_DATOS_ENROLADOR_RESPONSE);
		GuardarEnroladorResponse guardarEnroladorResponse = mapper.readValue(jsonResponse,
				GuardarEnroladorResponse.class);
		when(datosGeneralesService.guardarDatosEnrolador(any(GuardarEnroladorListaRequest.class), anyString()))
				.thenReturn(Optional.of(guardarEnroladorResponse));
		MvcResult result = ejecutarPostConHeaders(Constantes.URL_GUARDAR_DATOS_ENROLADOR, jsonRequest, status().isOk(),
				Constantes.X_OPERATION_ID_VALUE, Constantes.X_APPLICATION_ID_VALUE);
		Respuesta<GuardarEnroladorResponse> response = obtenerRespuesta(result, GuardarEnroladorResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void guardarDatosEnroladorException() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_GUARDAR_DATOS_ENROLADOR_REQUEST);
		when(datosGeneralesService.guardarDatosEnrolador(any(GuardarEnroladorListaRequest.class), anyString()))
				.thenThrow(ComunicacionFeingException.class);
		MvcResult result = ejecutarPostConHeaders(Constantes.URL_GUARDAR_DATOS_ENROLADOR, jsonRequest,
				status().isConflict(), Constantes.X_OPERATION_ID_VALUE, Constantes.X_APPLICATION_ID_VALUE);
		Respuesta<GuardarEnroladorResponse> response = obtenerRespuesta(result, GuardarEnroladorResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}

	@Test
	public void consultarDatosEnroladorOk() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_ENROLADOR_REQUEST);
		String jsonResponse = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_ENROLADOR_RESPONSE);
		var consultarEnroladorResponse = mapper.readValue(jsonResponse, ConsultarEnroladorEstadoResponse.class);
		when(datosGeneralesService.getEstadoEnrolador(any(ConsultarEnroladorRequest.class), anyString()))
				.thenReturn(consultarEnroladorResponse);
		MvcResult result = ejecutarPostConHeaders(Constantes.URL_CONSULTAR_DATOS_ENROLADOR, jsonRequest,
				status().isOk(), Constantes.X_OPERATION_ID_VALUE, Constantes.X_APPLICATION_ID_VALUE);
		Respuesta<ConsultarEnroladorEstadoResponse> response = obtenerRespuesta(result,
				ConsultarEnroladorEstadoResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void consularDatosEnroladorException() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_ENROLADOR_REQUEST);
		when(datosGeneralesService.getEstadoEnrolador(any(ConsultarEnroladorRequest.class), anyString()))
				.thenThrow(ComunicacionFeingException.class);
		MvcResult result = ejecutarPostConHeaders(Constantes.URL_CONSULTAR_DATOS_ENROLADOR, jsonRequest,
				status().isConflict(), Constantes.X_OPERATION_ID_VALUE, Constantes.X_APPLICATION_ID_VALUE);
		Respuesta<ConsultarEnroladorEstadoResponse> response = obtenerRespuesta(result,
				ConsultarEnroladorEstadoResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}

    @Test
    public void validarRolUsuarioOk() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_REQUEST);
      var jsonResponse = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_RESPONSE);
  
      var consultarEnroladorResponse =
          mapper.readValue(jsonResponse, ValidarRolUsuarioResponse.class);
  
      when(perfilService.validarRolUsuario(any(ValidarRolUsuarioRequest.class), anyString()))
          .thenReturn(consultarEnroladorResponse);
  
      var result =
          ejecutarPostConHeaders(
              Constantes.URL_VALIDAR_ROL_USUARIO,
              jsonRequest,
              status().isOk(),
              Constantes.X_OPERATION_ID_VALUE,
              Constantes.X_APPLICATION_ID_VALUE);
  
      Respuesta<ValidarRolUsuarioResponse> response =
          obtenerRespuesta(result, ValidarRolUsuarioResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getDatos());
      assertNull(response.getError());
      assertEquals(1, response.getDatos().getRoles().size());
    }
    
    @Test
    public void validarRolUsuarioException() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_REQUEST);
  
      when(perfilService.validarRolUsuario(any(ValidarRolUsuarioRequest.class), anyString()))
          .thenThrow(new GeneralException(Errores.ERROR_VALIDANDO_USUARIO_GRUPO_AD));
  
      var result =
          ejecutarPostConHeaders(
              Constantes.URL_VALIDAR_ROL_USUARIO,
              jsonRequest,
              status().isConflict(),
              Constantes.X_OPERATION_ID_VALUE,
              Constantes.X_APPLICATION_ID_VALUE);
  
      Respuesta<ValidarRolUsuarioResponse> response =
          obtenerRespuesta(result, ValidarRolUsuarioResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getError());
      assertNull(response.getDatos());
      assertEquals(Integer.valueOf(75135), response.getError().getCodigo());
    }
    
    @Test
    public void validarRolUsuarioRequestMalFormadoException() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_BAD_REQUEST);
      
      var result =
          ejecutarPostConHeaders(
              Constantes.URL_VALIDAR_ROL_USUARIO,
              jsonRequest,
              status().isBadRequest(),
              Constantes.X_OPERATION_ID_VALUE,
              Constantes.X_APPLICATION_ID_VALUE);
  
      Respuesta<ValidarRolUsuarioResponse> response =
          obtenerRespuesta(result, ValidarRolUsuarioResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getError());
      assertNull(response.getDatos());
      assertEquals(Integer.valueOf(99000), response.getError().getCodigo());
    }
}
