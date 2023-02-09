package ar.com.macro.enrolamiento.domain.daonengine.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.GuardarTrazasDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.ActualizarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.GuardarTrazasDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.EnrolarHuellaDaonEngineResponse;

public class DaonEngineControllerTest extends ContextoTest {

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
            return new DaonEngineControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

    @Test
    public void enrolarHuellaDaonEngineExito() throws Exception {
        String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_HUELLA_DAONENGINE_REQUEST);
        final String jsonResponse = retrieveBody(Constantes.JSON_ENROLAR_HUELLA_DAONENGINE_RESPONSE);
        EnrolarHuellaDaonEngineResponse consultarEnrolamientoResponse = mapper.readValue(jsonResponse, EnrolarHuellaDaonEngineResponse.class);
        when(huellaDaonEngineService.enrolarHuellaDaonEngine(any(EnrolarHuellaDaonEngineRequest.class),anyString())).thenReturn(consultarEnrolamientoResponse);
        MvcResult result = ejecutarPostConHeader(Constantes.URL_ENROLAR_HUELLA_DAONENGINE, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<EnrolarHuellaDaonEngineResponse> response = obtenerRespuesta(result, EnrolarHuellaDaonEngineResponse.class);
        assertNotNull(response);
        assertNotNull(response.getDatos());
        assertNull(response.getError());
    }

    @Test(expected = Exception.class)
    public void enrolarHuellaDaonEngineError() throws Exception {
        String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_HUELLA_DAONENGINE_REQUEST);
        when(huellaDaonEngineService.enrolarHuellaDaonEngine(any(EnrolarHuellaDaonEngineRequest.class),anyString())).thenThrow(Exception.class);
        MvcResult result = ejecutarPostConHeader(Constantes.URL_ENROLAR_HUELLA_DAONENGINE, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        obtenerRespuesta(result, EnrolarHuellaDaonEngineResponse.class);
    }

    @Test
    public void actualizarHuellaDaonEngineExito() throws Exception {
        String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_HUELLA_DAONENGINE_REQUEST);
        final String jsonResponse = retrieveBody(Constantes.JSON_ACTUALIZAR_HUELLA_DAONENGINE_RESPONSE);
        ActualizarHuellaDaonEngineResponse actualizarHuellaDaonEngineResponse = mapper.readValue(jsonResponse, ActualizarHuellaDaonEngineResponse.class);
        when(huellaDaonEngineService.actualizarHuellaDaonEngine(any(ActualizarHuellaDaonEngineRequest.class),anyString())).thenReturn(actualizarHuellaDaonEngineResponse);
        MvcResult result = ejecutarPostConHeader(Constantes.URL_ACTUALIZAR_HUELLA_DAONENGINE, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ActualizarHuellaDaonEngineResponse> response = obtenerRespuesta(result, ActualizarHuellaDaonEngineResponse.class);
        assertNotNull(response);
        assertNotNull(response.getDatos());
        assertNull(response.getError());
    }

    @Test(expected = Exception.class)
    public void actualizarHuellaDaonEngineError() throws Exception {
        String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_HUELLA_DAONENGINE_REQUEST);
        when(huellaDaonEngineService.actualizarHuellaDaonEngine(any(ActualizarHuellaDaonEngineRequest.class),anyString())).thenThrow(Exception.class);
        MvcResult result = ejecutarPostConHeader(Constantes.URL_ACTUALIZAR_HUELLA_DAONENGINE, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        obtenerRespuesta(result, ActualizarHuellaDaonEngineResponse.class);
    }

    @Test
    public void actualizarTrazasDaonEngineExito() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_TRAZAS_DAONENGINE_REQUEST);
      var jsonResponse = retrieveBody(Constantes.JSON_ACTUALIZAR_TRAZAS_DAONENGINE_RESPONSE);
  
      var actualizarTrazasDaonEngineResponse =
          mapper.readValue(jsonResponse, GuardarTrazasDaonEngineResponse.class);
  
      when(huellaDaonEngineService.guardarTrazasDaonEngine(
              any(GuardarTrazasDaonEngineRequest.class), anyString()))
          .thenReturn(actualizarTrazasDaonEngineResponse);
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_ACTUALIZAR_TRAZAS_DAONENGINE,
              jsonRequest,
              status().isOk(),
              Constantes.X_OPERATION_ID_VALUE);
  
      Respuesta<GuardarTrazasDaonEngineResponse> response =
          obtenerRespuesta(result, GuardarTrazasDaonEngineResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getDatos());
      assertNotNull(response.getDatos().getStatus());
      assertEquals(1, response.getDatos().getStatus().intValue());
      assertNull(response.getError());
    }
  
    @Test(expected = Exception.class)
    public void actualizarTrazasDaonEngineError() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_TRAZAS_DAONENGINE_REQUEST);
  
      when(huellaDaonEngineService.guardarTrazasDaonEngine(
              any(GuardarTrazasDaonEngineRequest.class), anyString()))
          .thenThrow(Exception.class);
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_ACTUALIZAR_TRAZAS_DAONENGINE,
              jsonRequest,
              status().isOk(),
              Constantes.X_OPERATION_ID_VALUE);
  
      obtenerRespuesta(result, GuardarTrazasDaonEngineResponse.class);
    }
    
}
