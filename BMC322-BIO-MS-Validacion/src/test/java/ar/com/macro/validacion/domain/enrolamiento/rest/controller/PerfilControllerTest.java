package ar.com.macro.validacion.domain.enrolamiento.rest.controller;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.macro.constant.Errores;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.AgregarDniPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.AgregarDniPerfilResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoResponse;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;

import com.identityx.auth.support.RestException;

import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constants.Constantes;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
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
	public void debeAgregarPerfilUsuarioExito() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_REQUEST);

		final String jsonResponse = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_RESPONSE);
		AgregarDniPerfilResponse agregarDniPerfilResponse = mapper.readValue(jsonResponse, AgregarDniPerfilResponse.class);

		when(perfilService.agregarDniPerfilUsuario(any(AgregarDniPerfilRequest.class))).thenReturn(agregarDniPerfilResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<AgregarDniPerfilResponse> response = obtenerRespuesta(result, AgregarDniPerfilResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void debeAgregarPerfilUsuarioErrorSinIdUsuario() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_SIN_ID_REQUEST);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<AgregarDniPerfilResponse> response = obtenerRespuesta(result, AgregarDniPerfilResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}

	@Test
	public void debeAgregarPerfilUsuarioErrorIdentityX() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_REQUEST);

		when(perfilService.agregarDniPerfilUsuario(any(AgregarDniPerfilRequest.class))).thenThrow(
				new IdentityXException(Constantes.ERROR_AGREGAR_PERFIL_USUARIO_CODIGO, Constantes.ERROR_AGREGAR_PERFIL_USUARIO_MSG));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<AgregarDniPerfilResponse> response = obtenerRespuesta(result, AgregarDniPerfilResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.ERROR_AGREGAR_PERFIL_USUARIO_CODIGO, response.getError().getCodigo());
	}

	@Test
	public void debeAgregarPerfilUsuarioRestException() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_AGREGAR_PERFIL_USUARIO_REQUEST);

		doThrow( new RestException(""))
				.when(perfilService).agregarDniPerfilUsuario(any(AgregarDniPerfilRequest.class));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_AGREGAR_PERFIL_USUARIO , jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<Serializable> response = obtenerRespuesta(result, Respuesta.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.ERROR_CREAR_PERFIL_USUARIO_REST_IDENTITY_X_EXCEPTION_CODIGO, response.getError().getCodigo());

	}

	@Test
	public void debeConsultarErrorEnrolamiento() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_REQUEST);

		when(enrolamientoService.consultarEnrolamiento(any(ConsultarEnrolamientoRequest.class),anyString())).thenThrow(new DaonEngineException(Errores.ERROR_RENAPER_ERROR_VALIDACION.getCodigo(), Errores.ERROR_RENAPER_ERROR_VALIDACION.getMensaje()));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_ENROLAMIENTO, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarEnrolamientoResponse> response = obtenerRespuesta(result, ConsultarEnrolamientoResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Constantes.ERROR_CONSULTAR_ENROLAMIENTO_CODIGO, response.getError().getCodigo());
	}

	@Test
	public void debeConsultarEnrolamientoExito() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_REQUEST);

		final String jsonResponse = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_RESPONSE);
		ConsultarEnrolamientoResponse consultarEnrolamientoResponse = mapper.readValue(jsonResponse, ConsultarEnrolamientoResponse.class);

		when(enrolamientoService.consultarEnrolamiento(any(ConsultarEnrolamientoRequest.class),anyString())).thenReturn(consultarEnrolamientoResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_ENROLAMIENTO, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarEnrolamientoResponse> response = obtenerRespuesta(result, ConsultarEnrolamientoResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

}
