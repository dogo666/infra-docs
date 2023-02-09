package ar.com.macro.validacion.domain.daonengine.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.constant.Errores.ERROR_COMPARAR_HUELLAS_UNO_A_POCOS;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_LIST_SUMMARY_AUDITS;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_LIST_SUMMARY_AUDITS_NOT_MATCH;
import static ar.com.macro.constant.Errores.ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE;
import static ar.com.macro.constants.Constantes.X_OPERATION_ID_VALUE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
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
import ar.com.macro.exceptions.CompararHuellasUnoPocosException;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.CompararHuellasDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ConsultarValidacionHuellaPersonaRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ValidarHuellaDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.CompararHuellasDaonEngineResponse;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ConsultarValidacionHuellaPersonaResponse;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ValidarHuellaDaonEngineResponse;


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
	public void debeConsultarTrackingUIDExito() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_VALIDACION_HUELLA_REQUEST);

		final String jsonResponse = retrieveBody(Constantes.JSON_CONSULTAR_VALIDACION_HUELLA_RESPONSE);
		ConsultarValidacionHuellaPersonaResponse consultarValidacionHuellaPersonaResponse = mapper.readValue(jsonResponse, ConsultarValidacionHuellaPersonaResponse.class);

		when(huellaDaonEngineService.consultarTrackingUID(any(ConsultarValidacionHuellaPersonaRequest.class), anyString()))
				.thenReturn(consultarValidacionHuellaPersonaResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_VALIDACION_HUELLA, jsonRequest, status().isOk(), X_OPERATION_ID_VALUE);
		Respuesta<ConsultarValidacionHuellaPersonaResponse> response = obtenerRespuesta(result, ConsultarValidacionHuellaPersonaResponse.class);

	}

	@Test
	public void validarHuellaDaonExito() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_DAON_REQUEST);

		final String jsonResponse = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_DAON_RESPONSE);
		ValidarHuellaDaonEngineResponse validarHuellaDaonEngineResponse = mapper.readValue(jsonResponse, ValidarHuellaDaonEngineResponse.class);

		when(enrolamientoService.validarHuellaDaon(any(ValidarHuellaDaonEngineRequest.class), anyString())).thenReturn(validarHuellaDaonEngineResponse);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_DAON, jsonRequest, status().isOk(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaDaonEngineResponse> response = obtenerRespuesta(result, ValidarHuellaDaonEngineResponse.class);

		assertNotNull(response);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}


    @Test
    public void debeConsultarTrackingUIDSinIdRequestTracking() throws Exception {

        String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_VALIDACION_HUELLA_SIN_IDREQUESTTRACKING_REQUEST);

        MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_VALIDACION_HUELLA, jsonRequest, status().isBadRequest(), X_OPERATION_ID_VALUE);
        Respuesta<ConsultarValidacionHuellaPersonaResponse> response = obtenerRespuesta(result, ConsultarValidacionHuellaPersonaResponse.class);

        assertNotNull(response);
        assertNull(response.getDatos());
        assertNotNull(response.getError());
    }

    @Test
    public void debeConsultarTrackingUIDError() throws Exception {

        String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_VALIDACION_HUELLA_REQUEST);

        final String jsonResponse = retrieveBody(Constantes.JSON_CONSULTAR_VALIDACION_HUELLA_RESPONSE);

        when(huellaDaonEngineService.consultarTrackingUID(any(ConsultarValidacionHuellaPersonaRequest.class),anyString()))
                .thenThrow(new DaonEngineException(ERROR_OBTENER_LIST_SUMMARY_AUDITS));

        MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_VALIDACION_HUELLA, jsonRequest, status().isConflict(), X_OPERATION_ID_VALUE);
        Respuesta<ConsultarValidacionHuellaPersonaResponse> response = obtenerRespuesta(result, ConsultarValidacionHuellaPersonaResponse.class);

        assertNotNull(response);
        assertNotNull(response.getError());
        assertEquals(ERROR_OBTENER_LIST_SUMMARY_AUDITS.getCodigo(),response.getError().getCodigo());
    }

    @Test
    public void debeConsultarTrackingUIDNoEncontrado() throws Exception {

        String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_VALIDACION_HUELLA_REQUEST);

        final String jsonResponse = retrieveBody(Constantes.JSON_CONSULTAR_VALIDACION_HUELLA_RESPONSE);

        when(huellaDaonEngineService.consultarTrackingUID(any(ConsultarValidacionHuellaPersonaRequest.class), anyString()))
                .thenThrow(new DaonEngineException(ERROR_OBTENER_LIST_SUMMARY_AUDITS_NOT_MATCH));

        MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_VALIDACION_HUELLA, jsonRequest, status().isConflict(), X_OPERATION_ID_VALUE);
        Respuesta<ConsultarValidacionHuellaPersonaResponse> response = obtenerRespuesta(result, ConsultarValidacionHuellaPersonaResponse.class);

        assertNotNull(response);
        assertNotNull(response.getError());
        assertEquals(ERROR_OBTENER_LIST_SUMMARY_AUDITS_NOT_MATCH.getCodigo(),response.getError().getCodigo());
    }

	
	@Test
	public void validarHuellaDaonSinUsuarioError() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_DAON_SIN_USUARIO_REQUEST);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_DAON, jsonRequest, status().isBadRequest(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaDaonEngineResponse> response = obtenerRespuesta(result, ValidarHuellaDaonEngineResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void validarHuellaDaonSinIdentificadorError() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_DAON_SIN_IDENTIFICADOR_REQUEST);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_DAON, jsonRequest, status().isBadRequest(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaDaonEngineResponse> response = obtenerRespuesta(result, ValidarHuellaDaonEngineResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void validarHuellaDaonSinHuellaError() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_DAON_SIN_HUELLA_REQUEST);

		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_DAON, jsonRequest, status().isBadRequest(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaDaonEngineResponse> response = obtenerRespuesta(result, ValidarHuellaDaonEngineResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void validarHuellaDaonErrorValidacion() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_DAON_REQUEST);

		when(enrolamientoService.validarHuellaDaon(any(ValidarHuellaDaonEngineRequest.class), anyString())).thenThrow(
				new DaonEngineException(ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getCodigo(), ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getMensaje()));

		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_DAON, jsonRequest, status().isConflict(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarHuellaDaonEngineResponse> response = obtenerRespuesta(result, ValidarHuellaDaonEngineResponse.class);

		assertNotNull(response);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getCodigo(), response.getError().getCodigo());
	}

    @Test
    public void validarHuellasUnoaPocosMatch() throws Exception {
  
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_REQUEST);
      var jsonResponse =
          retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_RESPONSE_MATCH);
  
      var compararHuellasDaonEngineResponse =
          mapper.readValue(jsonResponse, CompararHuellasDaonEngineResponse.class);
  
      when(enrolamientoService.validarHuellasUnoaPocosDaon(
              any(CompararHuellasDaonEngineRequest.class), anyString()))
          .thenReturn(compararHuellasDaonEngineResponse);
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_VALIDAR_HUELLAS_UNO_A_POCOS_DAON,
              jsonRequest,
              status().isOk(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<CompararHuellasDaonEngineResponse> response =
          obtenerRespuesta(result, CompararHuellasDaonEngineResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getDatos());
      assertNull(response.getError());
      assertEquals(1, response.getDatos().getStatus());
    }
  
    @Test
    public void validarHuellasUnoaPocosNoMatch() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_REQUEST);
      var jsonResponse =
          retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_RESPONSE_NOMATCH);
  
      var compararHuellasDaonEngineResponse =
          mapper.readValue(jsonResponse, CompararHuellasDaonEngineResponse.class);
  
      when(enrolamientoService.validarHuellasUnoaPocosDaon(
              any(CompararHuellasDaonEngineRequest.class), anyString()))
          .thenReturn(compararHuellasDaonEngineResponse);
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_VALIDAR_HUELLAS_UNO_A_POCOS_DAON,
              jsonRequest,
              status().isOk(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<CompararHuellasDaonEngineResponse> response =
          obtenerRespuesta(result, CompararHuellasDaonEngineResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getDatos());
      assertNull(response.getError());
      assertEquals(0, response.getDatos().getStatus());
    }

    @Test
    public void validarHuellasUnoaPocosSinIdUsuarioError() throws Exception {
      var jsonRequest =
          retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_SIN_ID_USUARIO_REQUEST);
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_VALIDAR_HUELLAS_UNO_A_POCOS_DAON,
              jsonRequest,
              status().isBadRequest(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<CompararHuellasDaonEngineResponse> response =
          obtenerRespuesta(result, CompararHuellasDaonEngineResponse.class);
  
      assertNotNull(response);
      assertNull(response.getDatos());
      assertNotNull(response.getError());
    }
    
    @Test
    public void validarHuellasUnoaPocosSinHuellasError() throws Exception {
      var jsonRequest =
          retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_SIN_ID_USUARIO_REQUEST);
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_VALIDAR_HUELLAS_UNO_A_POCOS_DAON,
              jsonRequest,
              status().isBadRequest(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<CompararHuellasDaonEngineResponse> response =
          obtenerRespuesta(result, CompararHuellasDaonEngineResponse.class);
  
      assertNotNull(response);
      assertNull(response.getDatos());
      assertNotNull(response.getError());
    }

    @Test
    public void validarHuellaUnoaPocosErrorDaonEngine() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_REQUEST);
  
      when(enrolamientoService.validarHuellasUnoaPocosDaon(
              any(CompararHuellasDaonEngineRequest.class), anyString()))
          .thenThrow(
              new DaonEngineException(
                  ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getCodigo(),
                  ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getMensaje()));
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_VALIDAR_HUELLAS_UNO_A_POCOS_DAON,
              jsonRequest,
              status().isConflict(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<CompararHuellasDaonEngineResponse> response =
          obtenerRespuesta(result, CompararHuellasDaonEngineResponse.class);
  
      assertNotNull(response);
      assertNull(response.getDatos());
      assertNotNull(response.getError());
      assertEquals(ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getCodigo(), response.getError().getCodigo());
    }

  @Test
    public void validarHuellaUnoaPocosErrorValidacion() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLAS_UNO_A_POCOS_REQUEST);
  
      when(enrolamientoService.validarHuellasUnoaPocosDaon(
              any(CompararHuellasDaonEngineRequest.class), anyString()))
          .thenThrow(
              new CompararHuellasUnoPocosException(
                  ERROR_COMPARAR_HUELLAS_UNO_A_POCOS.getCodigo(),
                  ERROR_COMPARAR_HUELLAS_UNO_A_POCOS.getMensaje()));
  
      var result =
          ejecutarPostConHeader(
              Constantes.URL_VALIDAR_HUELLAS_UNO_A_POCOS_DAON,
              jsonRequest,
              status().isConflict(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<CompararHuellasDaonEngineResponse> response =
          obtenerRespuesta(result, CompararHuellasDaonEngineResponse.class);
  
      assertNotNull(response);
      assertNull(response.getDatos());
      assertNotNull(response.getError());
      assertEquals(ERROR_COMPARAR_HUELLAS_UNO_A_POCOS.getCodigo(), response.getError().getCodigo());
    }

	@Test
	public void test(){

		String sFalseAcceptRateTest1 = "1.0E-4";
		String sFalseAcceptRateTest2 ="1.2647113631760175E-7" ;
		String sScore = "1.2647113631760176E-7";

		BigDecimal dScore = new BigDecimal(sScore);
		BigDecimal dFalseAcceptRate = new BigDecimal(sFalseAcceptRateTest1);

		if(dScore.compareTo(dFalseAcceptRate) < 0){
			System.out.println(dScore.doubleValue() + "MATCH");
		}else{
			System.out.println("NO_MATCH");
		}

	}

}
