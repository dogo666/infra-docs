package ar.com.macro.enrolamiento.domain.renaper.rest.controller;

import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.request.ValidarHuellaEnRenaperRequest;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ValidarHuellaEnRenaperResponse;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ValidarRenaperDNIResponse;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ValidarRostroRenaperResponse;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;
import ar.com.macro.exceptions.RenaperException;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RenaperControllerTest extends ContextoTest {

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
            return new RenaperControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

    @Test
    public void cuandoValidarPorDNIYRequestEsValidoYServicioRenaperRetornaValoresEntoncesStatusDeberiaSerOkYRespuestaDeberiaSerRetornada() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_VALIDACION_RENAPER_DNI_REQUEST);

        when(renaperService.validarIdentidadPersonaPorDNI(any())).thenReturn(crearRespuestaIdentificacionPorDNI());

        MvcResult result = ejecutarPostConHeader(Constantes.URL_RENAPER_VALIDACION_DNI, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarRenaperDNIResponse> respuesta = obtenerRespuesta(result, ValidarRenaperDNIResponse.class);

        assertNotNull(respuesta);
        assertNotNull(respuesta.getDatos());
        assertNotNull(respuesta.getDatos().getCodigo());
    }

    @Test
    public void cuandoValidarPorDNIYRequestEsInvalidoEntoncesStatusDeberiaSerBadRequestYRespuestaNoDeberiaSerRetornada() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_VALIDACION_RENAPER_DNI_REQUEST_INVALIDO);

        MvcResult result = ejecutarPostConHeader(Constantes.URL_RENAPER_VALIDACION_DNI, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarDNIRenaperClientResponse> respuesta = obtenerRespuesta(result, ValidarRenaperDNIResponse.class);

        assertNotNull(respuesta);
        assertNull(respuesta.getDatos());
        assertNotNull(respuesta.getError());
    }

    @Test
    public void cuandoValidarPorDNIYRequestEsValidoYServicioRenaperGeneraExcepcionEntoncesStatusDeberiaSerConflictYRespuestaNoDeberiaSerRetornada() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_VALIDACION_RENAPER_DNI_REQUEST);

        when(renaperService.validarIdentidadPersonaPorDNI(any())).thenThrow(new ComunicacionFallidaException(1, "ESB no disponible"));

        MvcResult result = ejecutarPostConHeader(Constantes.URL_RENAPER_VALIDACION_DNI, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarDNIRenaperClientResponse> respuesta = obtenerRespuesta(result, ValidarRenaperDNIResponse.class);

        assertNotNull(respuesta);
        assertNull(respuesta.getDatos());
        assertNotNull(respuesta.getError());
        assertNotNull(respuesta.getError().getCodigo());
    }

    @Test
    public void cuandoValidarPorRostroYRequestEsValidoYServicioRenaperRetornaValoresEntoncesStatusDeberiaSerOkYRespuestaDeberiaSerRetornada() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_VALIDACION_RENAPER_ROSTRO_REQUEST);

        when(renaperService.validarIdentidadPersonaPorRostro(any())).thenReturn(crearRespuestaIdentificacionPorRostro());

        MvcResult result = ejecutarPostConHeader(Constantes.URL_RENAPER_VALIDACION_ROSTRO, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarRostroRenaperResponse> respuesta = obtenerRespuesta(result, ValidarRostroRenaperResponse.class);

        assertNotNull(respuesta);
        assertNotNull(respuesta.getDatos());
        assertNotNull(respuesta.getDatos().getCodigo());
    }

    @Test
    public void cuandoValidarPorRostroYRequestEsInvalidoEntoncesStatusDeberiaSerBadRequestYRespuestaNoDeberiaSerRetornada() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_VALIDACION_RENAPER_ROSTRO_REQUEST_INVALIDO);

        MvcResult result = ejecutarPostConHeader(Constantes.URL_RENAPER_VALIDACION_ROSTRO, jsonRequest, status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarRostroRenaperResponse> respuesta = obtenerRespuesta(result, ValidarRostroRenaperResponse.class);

        assertNotNull(respuesta);
        assertNull(respuesta.getDatos());
        assertNotNull(respuesta.getError());
    }

    @Test
    public void cuandoValidarPorRostroYRequestEsValidoYServicioRenaperGeneraExcepcionEntoncesStatusDeberiaSerConflictYRespuestaNoDeberiaSerRetornada() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_VALIDACION_RENAPER_ROSTRO_REQUEST);

        when(renaperService.validarIdentidadPersonaPorRostro(any())).thenThrow(new ComunicacionFallidaException(1, "ESB no disponible"));

        MvcResult result = ejecutarPostConHeader(Constantes.URL_RENAPER_VALIDACION_ROSTRO, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarRostroRenaperResponse> respuesta = obtenerRespuesta(result, ValidarRostroRenaperResponse.class);

        assertNotNull(respuesta);
        assertNull(respuesta.getDatos());
        assertNotNull(respuesta.getError());
        assertNotNull(respuesta.getError().getCodigo());
    }

    @Test
    public void cuandoValidarPorRostroYRequestEsValidoYServicioRenaperGeneraExcepcionEntoncesStatusDeberiaSerConflict() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_VALIDACION_RENAPER_ROSTRO_REQUEST);

        when(renaperService.validarIdentidadPersonaPorRostro(any())).thenThrow(new RenaperException(Errores.ERROR_RENAPER_ERROR_VALIDACION.getCodigo(), Errores.ERROR_RENAPER_ERROR_VALIDACION.getMensaje()));

        MvcResult result = ejecutarPostConHeader(Constantes.URL_RENAPER_VALIDACION_ROSTRO, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarRostroRenaperResponse> respuesta = obtenerRespuesta(result, ValidarRostroRenaperResponse.class);

        assertNotNull(respuesta);
        assertNull(respuesta.getDatos());
        assertNotNull(respuesta.getError());
        assertNotNull(respuesta.getError().getCodigo());
        assertEquals(Errores.ERROR_RENAPER_ERROR_VALIDACION.getCodigo(), respuesta.getError().getCodigo());
    }

    @Test
    public void debeValidarHuellaRenaperErrorRenaperException() throws Exception {

        String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_REQUEST);

        when(renaperService.validarHuellaEnRenaper(any(ValidarHuellaEnRenaperRequest.class))).thenThrow(
                new RenaperException(Constantes.ERROR_GENERAR_TRANSACCION_CODIGO, Constantes.ERROR_GENERAR_TRANSACCION_MENSAJE));

        MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_RENAPER, jsonRequest, status().isConflict(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarHuellaEnRenaperResponse> response = obtenerRespuesta(result, ValidarHuellaEnRenaperResponse.class);

        assertNotNull(response);
        assertNull(response.getDatos());
        assertNotNull(response.getError());
        assertEquals(Constantes.ERROR_GENERAR_TRANSACCION_CODIGO, response.getError().getCodigo());
    }

    @Test
    public void debeValidarHuellaRenaperExito() throws Exception {

        String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_REQUEST);

        ValidarHuellaEnRenaperResponse validarHuellaEnRenaperResponse = new ValidarHuellaEnRenaperResponse();
        validarHuellaEnRenaperResponse.setPuntaje("9402");
        validarHuellaEnRenaperResponse.setResultado(Constantes.RENAPER_CONSULTA_TCN_HIT);
        when(renaperService.validarHuellaEnRenaper(any(ValidarHuellaEnRenaperRequest.class))).thenReturn(validarHuellaEnRenaperResponse);

        MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_HUELLA_RENAPER, jsonRequest, status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarHuellaEnRenaperResponse> response = obtenerRespuesta(result, ValidarHuellaEnRenaperResponse.class);

        assertNotNull(response);
        assertNotNull(response.getDatos());
        assertNull(response.getError());
        assertEquals(Constantes.RENAPER_CONSULTA_TCN_HIT, response.getDatos().getResultado());
        assertEquals("9402", response.getDatos().getPuntaje());
    }

    private Optional<ValidarRostroRenaperClientResponse> crearRespuestaIdentificacionPorRostro() {
        ValidarRostroRenaperClientResponse validacionRenaperRostroResponse =  new ValidarRostroRenaperClientResponse(2003, "Face compare success", 8, true,null);
        return Optional.of(validacionRenaperRostroResponse);
    }

    private Optional<ValidarDNIRenaperClientResponse> crearRespuestaIdentificacionPorDNI() {
        ValidarDNIRenaperClientResponse validarDNIRenaperClientResponse = new ValidarDNIRenaperClientResponse("10001", "Exito", "PersonaString", "true");
        return Optional.of(validarDNIRenaperClientResponse);
    }

}