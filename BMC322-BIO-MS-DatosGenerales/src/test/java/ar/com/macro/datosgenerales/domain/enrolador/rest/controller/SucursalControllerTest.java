package ar.com.macro.datosgenerales.domain.enrolador.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.commons.values.constants.config.TraceConstants.APPLICATION;
import static ar.com.macro.commons.values.constants.config.TraceConstants.HEADER_APPLICATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.config.SecurityConstants;
import ar.com.macro.constants.Constantes;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarSucursalesRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.SucursalesEnroladorResponse;
import ar.com.macro.datosgenerales.model.service.SucursalService;
import ar.com.macro.exceptions.ConsultarSucursalesException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RunWith(SpringRunner.class)
public class SucursalControllerTest extends ContextoTest {

  @MockBean
  protected SucursalService sucursalService;
  
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
            return new SucursalControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

    private HttpHeaders prepararHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderConstants.HEADER_X_APPLICATION_ID, Constantes.ENROLAMIENTO_X_APPLICATION_ID);
        return headers;
    }

    @Test
    public void getSucursalesConEnroladorPendiente_Exito() throws Exception {
      var jsonResponse = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_SUCURSALES_RESPONSE);
  
      var respuestaEsperada = mapper.readValue(jsonResponse, SucursalesEnroladorResponse.class);
  
      when(sucursalService.getSucursalesConEnroladorPendiente())
          .thenReturn(respuestaEsperada);
  
      var result =
          ejecutarRequestConCustomHeaders(
              HttpMethod.POST,
              Constantes.URL_BIOMETRIA_CONSULTAR_SUCURSALES,
              status().isOk(),
              prepararHeaders());
  
      Respuesta<SucursalesEnroladorResponse> response = obtenerRespuesta(result, SucursalesEnroladorResponse.class);
      Assert.assertNotNull(response.getDatos());
      assertEquals(3, response.getDatos().getSucursales().size());
    }
  
    @Test
    public void getSucursalesConEnroladorPendiente_Error() throws Exception {
      
      when(sucursalService.getSucursalesConEnroladorPendiente())
          .thenThrow(ConsultarSucursalesException.class);
     
      var result =
          ejecutarRequestConCustomHeaders(
              HttpMethod.POST,
              Constantes.URL_BIOMETRIA_CONSULTAR_SUCURSALES,
              status().isConflict(),
              prepararHeaders());
      
      var respuesta = obtenerRespuesta(result, SucursalesEnroladorResponse.class);
      Assert.assertNotNull(respuesta);
    }

    @Test
    public void getSucursalesConEnroladorEstados_Exito() throws Exception {
        var jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_SUCURSALES_ESTADOS_REQUEST);
        var req = mapper.readValue(jsonRequest, ConsultarSucursalesRequest.class);

        var jsonResponse = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_SUCURSALES_ESTADOS_RESPONSE);
        var respuestaEsperada = mapper.readValue(jsonResponse, SucursalesEnroladorResponse.class);

        when(sucursalService.getSucursalesConEnroladorEnEstados(req))
                .thenReturn(respuestaEsperada);

        var result = ejecutarRequestConCustomHeaders(
                HttpMethod.POST,
                Constantes.URL_BIOMETRIA_CONSULTAR_SUCURSALES_ESTADOS,
                jsonRequest,
                status().isOk(),
                prepararHeaders());

        Respuesta<SucursalesEnroladorResponse> response = obtenerRespuesta(result, SucursalesEnroladorResponse.class);
        Assert.assertNotNull(response.getDatos());
        assertEquals(3, response.getDatos().getSucursales().size());
    }

    @Test
    public void getSucursalesConEnroladorEstados_Error() throws Exception {
        var jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_SUCURSALES_ESTADOS_REQUEST);
        var req = mapper.readValue(jsonRequest, ConsultarSucursalesRequest.class);

        when(sucursalService.getSucursalesConEnroladorEnEstados(req))
                .thenThrow(ConsultarSucursalesException.class);

        var result = ejecutarRequestConCustomHeaders(
                HttpMethod.POST,
                Constantes.URL_BIOMETRIA_CONSULTAR_SUCURSALES_ESTADOS,
                jsonRequest,
                status().isConflict(),
                prepararHeaders());

        var respuesta = obtenerRespuesta(result, SucursalesEnroladorResponse.class);
        Assert.assertNotNull(respuesta);
    }

}
