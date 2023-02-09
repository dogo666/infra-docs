package ar.com.macro.datosgenerales.domain.parametros.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.commons.values.constants.config.TraceConstants.APPLICATION;
import static ar.com.macro.commons.values.constants.config.TraceConstants.HEADER_APPLICATION;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.config.SecurityConstants;
import ar.com.macro.constants.Constantes;
import ar.com.macro.datosgenerales.domain.parametros.rest.dto.response.ParametrosConfigResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;

@RunWith(SpringRunner.class)
public class ParametrosConfigControllerTest extends ContextoTest {
	
	private static final String MICROSERVICIO = "macro-enrolamiento";
	private static final String NOMBRE = "configuracion-huellas-daon-engine";

	@TestConfiguration
    static class ApiInterceptorConfiguration {
        static class MockApiInterceptor extends ApiInterceptor {
            MockApiInterceptor() {
                super();
            }

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
				String applicationId = ((Claims) Jwts.parser()
						.setSigningKey(SecurityConstants.ENCODED)
						.parseClaimsJws(request.getHeader(HEADER_APPLICATION))
						.getBody()).get(APPLICATION).toString();
				this.enhanceLogging(request, applicationId);
                return true;
            }
        }

        @Bean
        public ApiInterceptor apiInterceptor() {
            return new ParametrosConfigControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }
	
	private HttpHeaders prepararHeaders() {
		HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderConstants.HEADER_X_APPLICATION_ID, Constantes.ENROLAMIENTO_X_APPLICATION_ID);
        return headers;
	}
	
	@Test
	public void buscarConfiguracionSimpleIniciativa_Exito() throws Exception {
		final String jsonResponse = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_RESPONSE);
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_REQUEST);
		ParametrosConfigResponse respuestaEsperada = mapper.readValue(jsonResponse, ParametrosConfigResponse.class);
		when(parametrosConfigService.buscarConfiguracionSimpleIniciativaServicio(Constantes.INICIATIVA_ENROLAMIENTO, null)).thenReturn(respuestaEsperada);
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_SIMPLE_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
        Assert.assertNotNull(respuesta.getDatos().getParametros());
        Assert.assertEquals(4, respuesta.getDatos().getParametros().size());
	}
	
	@Test
	public void buscarConfiguracionSimpleIniciativa_NoSeEncuentraIniciativa() throws Exception {
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_REQUEST);
		when(parametrosConfigService.buscarConfiguracionSimpleIniciativaServicio(Constantes.INICIATIVA_ENROLAMIENTO, null)).thenReturn(new ParametrosConfigResponse(Collections.emptyList()));
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_SIMPLE_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
	}
	
	@Test
	public void buscarConfiguracionSimpleIniciativaMicroservicio_Exito() throws Exception {
		final String jsonResponse = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_MICROSERVICIO_RESPONSE);
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_MICROSERVICIO_REQUEST);
		ParametrosConfigResponse respuestaEsperada = mapper.readValue(jsonResponse, ParametrosConfigResponse.class);
		when(parametrosConfigService.buscarConfiguracionSimpleIniciativaServicio(Constantes.INICIATIVA_ENROLAMIENTO, MICROSERVICIO)).thenReturn(respuestaEsperada);
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_SIMPLE_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
        Assert.assertNotNull(respuesta.getDatos().getParametros());
        Assert.assertEquals(3, respuesta.getDatos().getParametros().size());
	}
	
	@Test
	public void buscarConfiguracionSimpleIniciativaMicroservicio_NoSeEncuentraIniciativa() throws Exception {
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_MICROSERVICIO_REQUEST);
		when(parametrosConfigService.buscarConfiguracionSimpleIniciativaServicio(Constantes.INICIATIVA_ENROLAMIENTO, MICROSERVICIO)).thenReturn(new ParametrosConfigResponse(Collections.emptyList()));
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_SIMPLE_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
	}
	
	@Test
	public void buscarConfiguracionCompuestaIniciativa_Exito() throws Exception {
		final String jsonResponse = retrieveBody(Constantes.JSON_CONFIGURACION_COMPUESTA_INICIATIVA_RESPONSE);
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_REQUEST);
		ParametrosConfigResponse respuestaEsperada = mapper.readValue(jsonResponse, ParametrosConfigResponse.class);
		when(parametrosConfigService.buscarConfiguracionCompuestaIniciativaServicioNombre(Constantes.INICIATIVA_ENROLAMIENTO, null, null)).thenReturn(respuestaEsperada);
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_COMPUESTA_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
        Assert.assertNotNull(respuesta.getDatos().getParametros());
        Assert.assertEquals(3, respuesta.getDatos().getParametros().size());
	}
	
	@Test
	public void buscarConfiguracionCompuestaIniciativa_NoSeEncuentraIniciativa() throws Exception {
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_REQUEST);
		when(parametrosConfigService.buscarConfiguracionCompuestaIniciativaServicioNombre(Constantes.INICIATIVA_ENROLAMIENTO, null, null)).thenReturn(new ParametrosConfigResponse(Collections.emptyList()));
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_COMPUESTA_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
	}
	
	@Test
	public void buscarConfiguracionCompuestaIniciativaMicroservicio_Exito() throws Exception {
		final String jsonResponse = retrieveBody(Constantes.JSON_CONFIGURACION_COMPUESTA_INICIATIVA_MICROSERVICIO_RESPONSE);
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_MICROSERVICIO_REQUEST);
		ParametrosConfigResponse respuestaEsperada = mapper.readValue(jsonResponse, ParametrosConfigResponse.class);
		when(parametrosConfigService.buscarConfiguracionCompuestaIniciativaServicioNombre(Constantes.INICIATIVA_ENROLAMIENTO, MICROSERVICIO, null)).thenReturn(respuestaEsperada);
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_COMPUESTA_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
        Assert.assertNotNull(respuesta.getDatos().getParametros());
        Assert.assertEquals(2, respuesta.getDatos().getParametros().size());
	}
	
	@Test
	public void buscarConfiguracionCompuestaIniciativaMicrosrevicio_NoSeEncuentraIniciativa() throws Exception {
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_MICROSERVICIO_REQUEST);
		when(parametrosConfigService.buscarConfiguracionCompuestaIniciativaServicioNombre(Constantes.INICIATIVA_ENROLAMIENTO, MICROSERVICIO, null)).thenReturn(new ParametrosConfigResponse(Collections.emptyList()));
		
		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_COMPUESTA_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());
		
		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);
		
		Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
	}

	@Test
	public void buscarConfiguracionCompuestaIniciativaMicrosrevicioNombre_Exito() throws Exception {
		final String jsonResponse = retrieveBody(Constantes.JSON_CONFIGURACION_COMPUESTA_INICIATIVA_MICROSERVICIO_RESPONSE_NOMBRE);
		final String jsonRequest = retrieveBody(Constantes.JSON_CONFIGURACION_INICIATIVA_MICROSERVICIO_REQUEST_NOMBRE);
		ParametrosConfigResponse respuestaEsperada = mapper.readValue(jsonResponse, ParametrosConfigResponse.class);
		when(parametrosConfigService.buscarConfiguracionCompuestaIniciativaServicioNombre(Constantes.INICIATIVA_ENROLAMIENTO, MICROSERVICIO, NOMBRE)).thenReturn(respuestaEsperada);

		MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CONSULTAR_CONFIGURACION_COMPUESTA_INICIATIVA,
				jsonRequest, status().isOk(), prepararHeaders());

		Respuesta<ParametrosConfigResponse> respuesta = obtenerRespuesta(result, ParametrosConfigResponse.class);

		Assert.assertNotNull(respuesta);
		Assert.assertNotNull(respuesta.getDatos());
		Assert.assertNotNull(respuesta.getDatos().getParametros());
		Assert.assertEquals(1, respuesta.getDatos().getParametros().size());
	}
}
