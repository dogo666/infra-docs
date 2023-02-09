package ar.com.macro.validacion.model.service.impl;

import ar.com.macro.biometria.commons.trace.feing.dto.response.ParametrosConfigDto;
import ar.com.macro.biometria.commons.trace.feing.dto.response.ParametrosConfigResponse;
import ar.com.macro.biometria.commons.trace.feing.proxy.DatosGeneralesTraceProxyService;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constants.Constantes;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DatosGeneralesServiceImplTest {

    @Mock
    private DatosGeneralesTraceProxyService datosGeneralesProxyService;

    private DatosGeneralesServiceImpl datosGeneralesService;

    @Before
    public void init(){
        this.datosGeneralesService = new DatosGeneralesServiceImpl(datosGeneralesProxyService);
    }

    @Test(expected = ObtenerDatosGeneralesCompuestosException.class)
    public void obtenerDatosGeneralesCompuestosError(){
        List<ParametrosConfigDto> parametrosConfigDtoList = new ArrayList<>();
        ParametrosConfigDto parametrosConfigDto = new ParametrosConfigDto();
        parametrosConfigDtoList.add(parametrosConfigDto);
        ParametrosConfigResponse parametrosConfigResponse = new ParametrosConfigResponse();
        parametrosConfigResponse.setParametros(parametrosConfigDtoList);
        Respuesta respuesta = new Respuesta<>(parametrosConfigResponse);
        when(datosGeneralesProxyService.obtenerDatosGeneralesCompuestos(any(), any())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(respuesta));
        this.datosGeneralesService.obtenerDatosGeneralesCompuestos(Constantes.X_OPERATION_ID_VALUE,Constantes.NOMBRE_MICROSERVICIO_DATOS_GENERALES,Constantes.NOMBRE_PARAMETROS_DATOS_GENERALES);
    }

    @Test(expected = ObtenerDatosGeneralesCompuestosException.class)
    public void obtenerDatosGeneralesCompuestosLanzarError(){
        when(datosGeneralesProxyService.obtenerDatosGeneralesCompuestos(any(), any())).thenThrow(ObtenerDatosGeneralesCompuestosException.class);
        this.datosGeneralesService.obtenerDatosGeneralesCompuestos(Constantes.X_OPERATION_ID_VALUE,Constantes.NOMBRE_MICROSERVICIO_DATOS_GENERALES,Constantes.NOMBRE_PARAMETROS_DATOS_GENERALES);
    }

}
