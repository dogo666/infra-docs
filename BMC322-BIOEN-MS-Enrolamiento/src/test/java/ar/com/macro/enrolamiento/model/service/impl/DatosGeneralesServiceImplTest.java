package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

import ar.com.macro.ContextoTest;
import ar.com.macro.biometria.commons.trace.feing.dto.request.GuardarEnroladorListaFeingRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.response.GuardarEnroladorFeingResponse;
import ar.com.macro.biometria.commons.trace.feing.dto.response.ParametrosConfigDto;
import ar.com.macro.biometria.commons.trace.feing.dto.response.ParametrosConfigResponse;
import ar.com.macro.biometria.commons.trace.feing.proxy.DatosGeneralesTraceProxyService;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.GuardarEnroladorListaRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.GuardarEnroladorResponse;
import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.exceptions.ComunicacionFeingException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;

@RunWith(SpringRunner.class)
public class DatosGeneralesServiceImplTest extends ContextoTest {

    @Mock
    private DatosGeneralesTraceProxyService datosGeneralesProxyService;

    private DatosGeneralesServiceImpl datosGeneralesService;

    @Before
    public void init() {
        this.datosGeneralesService = new DatosGeneralesServiceImpl(datosGeneralesProxyService);
    }

    @Test
    public void obtenerDatosGeneralesCompuestos() {
        List<ParametrosConfigDto> parametrosConfigDtoList = new ArrayList<>();
        ParametrosConfigDto parametrosConfigDto = new ParametrosConfigDto();
        String jsonResponse = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_HUELLAS_RESPONSE);
        parametrosConfigDto.setValor(jsonResponse);
        parametrosConfigDtoList.add(parametrosConfigDto);
        ParametrosConfigResponse parametrosConfigResponse = new ParametrosConfigResponse();
        parametrosConfigResponse.setParametros(parametrosConfigDtoList);
        var respuesta = new Respuesta<ParametrosConfigResponse>(parametrosConfigResponse);
        when(datosGeneralesProxyService.obtenerDatosGeneralesCompuestos(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(respuesta));
        ConfiguracionHuellasDto configuracionHuellasDto = this.datosGeneralesService.obtenerDatosGeneralesCompuestos(
                Constantes.X_OPERATION_ID_VALUE, Constantes.NOMBRE_MICROSERVICIO_DATOS_GENERALES,
                Constantes.NOMBRE_PARAMETROS_DATOS_GENERALES);
        assertNotNull(configuracionHuellasDto);
    }

    @Test(expected = ObtenerDatosGeneralesCompuestosException.class)
    public void obtenerDatosGeneralesCompuestosLanzarError() {
        when(datosGeneralesProxyService.obtenerDatosGeneralesCompuestos(any(), any()))
                .thenThrow(ObtenerDatosGeneralesCompuestosException.class);
        this.datosGeneralesService.obtenerDatosGeneralesCompuestos(Constantes.X_OPERATION_ID_VALUE,
                Constantes.NOMBRE_MICROSERVICIO_DATOS_GENERALES, Constantes.NOMBRE_PARAMETROS_DATOS_GENERALES);
    }

    @Test
    public void guardarDatosEnroladorOk() throws JsonProcessingException {
        GuardarEnroladorFeingResponse gfe = new GuardarEnroladorFeingResponse();
        gfe.setStatus(1);
        ResponseEntity<Respuesta<GuardarEnroladorFeingResponse>> gef = ResponseEntity.ok(new Respuesta<>(gfe));
        when(datosGeneralesProxyService.guardarDatosEnrolador(any(GuardarEnroladorListaFeingRequest.class),
                anyString())).thenReturn(gef);
        String jsonRequest = retrieveBody(Constantes.JSON_GUARDAR_DATOS_ENROLADOR_REQUEST);
        GuardarEnroladorListaRequest guardarEnroladorListaRequest = mapper.readValue(jsonRequest,
                GuardarEnroladorListaRequest.class);
        Optional<GuardarEnroladorResponse> res = this.datosGeneralesService
                .guardarDatosEnrolador(guardarEnroladorListaRequest, Constantes.X_OPERATION_ID_VALUE);
        assertTrue(res.isPresent());
    }

    @Test(expected = ComunicacionFeingException.class)
    public void guardarDatosEnroladorGuardarEnroladorException() throws JsonProcessingException {
        GuardarEnroladorFeingResponse gfe = null;
        ResponseEntity<Respuesta<GuardarEnroladorFeingResponse>> gef = ResponseEntity.ok(new Respuesta<>(gfe));
        when(datosGeneralesProxyService.guardarDatosEnrolador(any(GuardarEnroladorListaFeingRequest.class),
                anyString())).thenReturn(gef);
        String jsonRequest = retrieveBody(Constantes.JSON_GUARDAR_DATOS_ENROLADOR_REQUEST);
        GuardarEnroladorListaRequest guardarEnroladorListaRequest = mapper.readValue(jsonRequest,
                GuardarEnroladorListaRequest.class);
        this.datosGeneralesService.guardarDatosEnrolador(guardarEnroladorListaRequest, Constantes.X_OPERATION_ID_VALUE);
    }

}
