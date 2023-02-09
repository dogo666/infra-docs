package ar.com.macro.validacion.domain.renaper.rest.controller;

import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constants.Constantes;
import ar.com.macro.validacion.domain.renaper.rest.dto.response.ValidarRenaperDNIResponse;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
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

    private Optional<ValidarDNIRenaperClientResponse> crearRespuestaIdentificacionPorDNI() {
        ValidarDNIRenaperClientResponse validarDNIRenaperClientResponse = new ValidarDNIRenaperClientResponse("10001", "Exito", "PersonaString", "true");
        return Optional.of(validarDNIRenaperClientResponse);
    }

}