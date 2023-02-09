package ar.com.macro.datosgenerales.domain.operacion.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.commons.values.constants.config.TraceConstants.APPLICATION;
import static ar.com.macro.commons.values.constants.config.TraceConstants.HEADER_APPLICATION;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;
import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.config.SecurityConstants;
import ar.com.macro.constants.Constantes;
import ar.com.macro.datosgenerales.domain.operacion.rest.dto.response.CrearOperationIdResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class OperacionControllerTest extends ContextoTest {
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
            return new OperacionControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

    @Test
    public void debeCrearOperationIdExito() throws Exception {
        final String jsonResponse = retrieveBody(Constantes.JSON_CREAR_OPERATION_ID_RESPONSE);
        CrearOperationIdResponse respuestaEsperada = mapper.readValue(jsonResponse, CrearOperationIdResponse.class);

        when(operacionService.crearOperationId()).thenReturn(respuestaEsperada);

        MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_CREAR_OPERATION_ID,
                "", status().isOk(), prepararHeaders());

        Respuesta<CrearOperationIdResponse> respuesta = obtenerRespuesta(result, CrearOperationIdResponse.class);
        Assert.assertNotNull(respuesta);
        Assert.assertNotNull(respuesta.getDatos());
        Assert.assertEquals(respuestaEsperada.getIdoperacion(),respuesta.getDatos().getIdoperacion());

    }
    

    private HttpHeaders prepararHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderConstants.HEADER_X_APPLICATION_ID, Constantes.ENROLAMIENTO_X_APPLICATION_ID);
        return headers;
    }
}
