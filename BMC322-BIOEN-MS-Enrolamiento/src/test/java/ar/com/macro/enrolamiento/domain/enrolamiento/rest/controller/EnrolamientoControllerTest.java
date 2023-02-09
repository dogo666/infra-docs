package ar.com.macro.enrolamiento.domain.enrolamiento.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;

import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ValidarHuellaEnRenaperResponse;

public class EnrolamientoControllerTest extends ContextoTest {

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
            return new EnrolamientoControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

	@Test
	public void debeValidarHuellaRenaperErrorSexoInvalido() throws Exception {
    	
    	String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_SEXO_INVALIDO_REQUEST);
		
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_RENAPER, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaEnRenaperResponse> response = obtenerRespuesta(result, ValidarHuellaEnRenaperResponse.class);
		
		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void debeValidarHuellaRenaperErrorSinHuellas() throws Exception {
    	
    	String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_SIN_HUELLAS_REQUEST);
		
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_RENAPER, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaEnRenaperResponse> response = obtenerRespuesta(result, ValidarHuellaEnRenaperResponse.class);
		
		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void debeValidarHuellaRenaperErrorCantidadHuellas() throws Exception {
    	
    	String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_CANTIDAD_HUELLAS_REQUEST);
		
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_RENAPER, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaEnRenaperResponse> response = obtenerRespuesta(result, ValidarHuellaEnRenaperResponse.class);
		
		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}


}
