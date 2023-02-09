package ar.com.macro.validacion.domain.identidad.rest.controller;

import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.exceptions.CrearHashException;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.CrearHashRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.CrearHashResponse;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IdentidadControllerTest extends ContextoTest {

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
            return new IdentidadControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }
	
	@Test
	public void consultaIdentidadAtributosExito() throws Exception {
    	
    	String jsonRequest = retrieveBody(Constantes.JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_REQUEST);
    	
		final String jsonResponse = retrieveBody(Constantes.JSON_CONSULTA_IDENTIDAD_RESPONSE);

		ConsultarIdentidadResponse consultarIdentidadResponse = mapper.readValue(jsonResponse, ConsultarIdentidadResponse.class);
		
		when(enrolamientoService.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenReturn(consultarIdentidadResponse);
		
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_IDENTIDAD, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarIdentidadResponse> response = obtenerRespuesta(result, ConsultarIdentidadResponse.class);
		
		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void consultaIdentidadNoEncontradoException() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_REQUEST);

		when(enrolamientoService.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenThrow(
				new InformacionNoEncontradaException(Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getCodigo(),Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getMensaje()));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_IDENTIDAD, jsonRequest, status().isNotFound(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarIdentidadResponse> response = obtenerRespuesta(result, ConsultarIdentidadResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getCodigo(), response.getError().getCodigo());
	}
	
	@Test
	public void consultaIdentidadComunicacionFallidaException() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_REQUEST);

		when(enrolamientoService.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenThrow(
				new ComunicacionFallidaException(Errores.DATAPOWER_NO_DISPONIBLE.getCodigo(), Errores.DATAPOWER_NO_DISPONIBLE.getMensaje()));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_IDENTIDAD, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarIdentidadResponse> response = obtenerRespuesta(result, ConsultarIdentidadResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(Errores.DATAPOWER_NO_DISPONIBLE.getCodigo(), response.getError().getCodigo());
	}

	@Test
	public void consultaIdentidadRequestMalformado() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTA_IDENTIDAD_ATRIBUTOS_FALTANTES_REQUEST);

		final String jsonResponse = retrieveBody(Constantes.JSON_CONSULTA_IDENTIDAD_RESPONSE);

		ConsultarIdentidadResponse consultarIdentidadResponse = mapper.readValue(jsonResponse, ConsultarIdentidadResponse.class);

		when(enrolamientoService.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenReturn(consultarIdentidadResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_IDENTIDAD, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarIdentidadResponse> response = obtenerRespuesta(result, ConsultarIdentidadResponse.class);
		
		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}

	@Test
	public void crearHash256RequestOk() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_HASH256_REQUEST);
		final String jsonResponse = retrieveBody(Constantes.JSON_CREAR_HASH256_RESPONSE);
		Optional<CrearHashResponse> optionalCrearHashResponse = Optional.of(mapper.readValue(jsonResponse, CrearHashResponse.class));
		when(enrolamientoService.crearHashCon256(any(CrearHashRequest.class))).thenReturn(optionalCrearHashResponse);
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CREAR_HASH, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<CrearHashResponse> response = obtenerRespuesta(result, CrearHashResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
	}

	@Test
	public void crearHash256RequestException() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_HASH256_REQUEST);
		when(enrolamientoService.crearHashCon256(any(CrearHashRequest.class))).thenThrow(CrearHashException.class);
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CREAR_HASH, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<CrearHashResponse> response = obtenerRespuesta(result, CrearHashResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
}
