package ar.com.macro.datosgenerales.domain.enrolador.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.commons.values.constants.config.TraceConstants.APPLICATION;
import static ar.com.macro.commons.values.constants.config.TraceConstants.HEADER_APPLICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.exceptions.FuncionalidadNoSoportadaException;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.config.SecurityConstants;
import ar.com.macro.constants.Constantes;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladorRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladoresRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladorResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladoresResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.GuardarEnroladorResponse;
import ar.com.macro.exceptions.ConsultarEnroladoresException;
import ar.com.macro.exceptions.GuardarDatosEnroladorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RunWith(SpringRunner.class)
public class EnroladorControllerTest extends ContextoTest {

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
            return new EnroladorControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

    private HttpHeaders prepararHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderConstants.HEADER_X_APPLICATION_ID, Constantes.ENROLAMIENTO_X_APPLICATION_ID);
        return headers;
    }

    @Test
    public void consultarDatosEnrolador_Exito() throws Exception {
        final String jsonResponse = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_ENROLADOR_RESPONSE);
        final String jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_ENROLADOR_REQUEST);
        ConsultarEnroladorResponse respuestaEsperada = mapper.readValue(jsonResponse, ConsultarEnroladorResponse.class);
        when(enroladorService.consultarDatosEnrolador(any(ConsultarEnroladorRequest.class))).thenReturn(respuestaEsperada);
        MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_BIOMETRIA_DATOS_ENROLADOR,
                jsonRequest, status().isOk(), prepararHeaders());
        Respuesta<ConsultarEnroladorResponse> respuesta = obtenerRespuesta(result, ConsultarEnroladorResponse.class);
        Assert.assertNotNull(respuesta);
    }

    @Test
    public void consultarDatosEnrolador_Error() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_ENROLADOR_REQUEST);
        when(enroladorService.consultarDatosEnrolador(any(ConsultarEnroladorRequest.class))).thenThrow(FuncionalidadNoSoportadaException.class);
        MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_BIOMETRIA_DATOS_ENROLADOR,
                jsonRequest, status().isConflict(), prepararHeaders());
        Respuesta<ConsultarEnroladorResponse> respuesta = obtenerRespuesta(result, ConsultarEnroladorResponse.class);
        Assert.assertNotNull(respuesta);
    }

    @Test
    public void guardarDatosEnrolador_Exito() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_CONSULTA_ENROLADOR_REQUEST);
        final String jsonResponse = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_CONSULTA_ENROLADOR_RESPONSE);
        GuardarEnroladorResponse res = mapper.readValue(jsonResponse, GuardarEnroladorResponse.class);
        when(enroladorService.guardarDatosEnrolador(any())).thenReturn(res);
        MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_BIOMETRIA_GUARDAR_ENROLADOR,
                jsonRequest, status().isOk(), prepararHeaders());
        Respuesta<GuardarEnroladorResponse> respuesta = obtenerRespuesta(result, GuardarEnroladorResponse.class);
        Assert.assertNotNull(respuesta);
    }

    @Test
    public void guardarDatosEnrolador_Error() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_CONSULTA_ENROLADOR_REQUEST);
        when(enroladorService.guardarDatosEnrolador(any())).thenThrow(GuardarDatosEnroladorException.class);
        MvcResult result = ejecutarRequestConCustomHeaders(HttpMethod.POST, Constantes.URL_BIOMETRIA_GUARDAR_ENROLADOR,
                jsonRequest, status().isConflict(), prepararHeaders());
        Respuesta<GuardarEnroladorResponse> respuesta = obtenerRespuesta(result, GuardarEnroladorResponse.class);
        Assert.assertNotNull(respuesta);
    }

    @Test
    public void getUsuariosEnrolador_Exito() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_ENROLADORES_REQUEST);
      var jsonResponse = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_ENROLADORES_RESPONSE);
  
      var respuestaEsperada = mapper.readValue(jsonResponse, ConsultarEnroladoresResponse.class);
  
      when(enroladorService.getUsuariosEnrolador(any(ConsultarEnroladoresRequest.class)))
          .thenReturn(respuestaEsperada);
  
      var result =
          ejecutarRequestConCustomHeaders(
              HttpMethod.POST,
              Constantes.URL_BIOMETRIA_CONSULTAR_ENROLADORES,
              jsonRequest,
              status().isOk(),
              prepararHeaders());
  
      Respuesta<ConsultarEnroladoresResponse> respuesta = obtenerRespuesta(result, ConsultarEnroladoresResponse.class);
      Assert.assertNotNull(respuesta.getDatos());
      Assert.assertNull(respuesta.getError());
    }
  
    @Test
    public void getUsuariosEnrolador_Error() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_ENROLADORES_REQUEST);
      
      when(enroladorService.getUsuariosEnrolador(any()))
          .thenThrow(ConsultarEnroladoresException.class);
     
      var result =
          ejecutarRequestConCustomHeaders(
              HttpMethod.POST,
              Constantes.URL_BIOMETRIA_CONSULTAR_ENROLADORES,
              jsonRequest,
              status().isConflict(),
              prepararHeaders());
      
      Respuesta<ConsultarEnroladoresResponse> respuesta = obtenerRespuesta(result, ConsultarEnroladoresResponse.class);
      Assert.assertNull(respuesta.getDatos());
      Assert.assertNotNull(respuesta.getError());
    }
}
