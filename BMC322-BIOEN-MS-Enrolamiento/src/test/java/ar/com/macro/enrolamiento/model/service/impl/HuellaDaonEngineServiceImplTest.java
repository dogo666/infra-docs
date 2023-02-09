package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.GuardarTrazasDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.ActualizarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.EnrolarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.DaonEngineClient;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.BiographicData;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.Identity;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.ObjectFactory;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityResponse;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.cliente.ConsultaClienteAtributosResponse;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteRequest;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.HuellaDaonEngineService;
import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.enrolamiento.model.service.dto.ManosServiceDto;
import ar.com.macro.enrolamiento.model.service.dto.TrazasDto;
import ar.com.macro.enrolamiento.model.service.mapper.ConfiguracionHuellasMapper;
import ar.com.macro.enrolamiento.model.service.mapper.GenerarListaBiometricDataMapper;
import ar.com.macro.enrolamiento.model.service.mapper.TraduccionHuellasDaonARenaperMapper;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.exceptions.GeneralException;
import ar.com.macro.utils.ServiceConvertidorUtil;

@RunWith(SpringRunner.class)
public class HuellaDaonEngineServiceImplTest {

    @Spy
    private ObjectMapper mapper;

    private HuellaDaonEngineService huellaDaonEngineService;

    @Value("${daonengine.useridentifier}")
    private String applicationUserIdentifier;

    @Value("${daonengine.domainidentifier}")
    private String defaultDomainIdentifier;

    @Value("${daonengine.identityidentifier}")
    private String pathIdentityIdentifier;

    @Mock
    private DaonEngineClient daonEngineClient;

    @Mock
    private TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper;

    @Mock
    private ConfiguracionHuellasMapper configuracionHuellasMapper;

    @Value("${daonengine.nombre.parametros.daon}")
    private String nombreParametrosDaon;

    @Value("${daonengine.nombre.parametros.renaper}")
    private String nombreParametrosRenaper;

    @Value("${daonengine.nombre.parametros.datos.generales}")
    private String nombreMicroServicioDatosGenerales;

    @Mock
    private DatosGeneralesService datosGeneralesService;

    @Value("${daonengine.transformpolicyidentifier}")
    private String transformPolicyIdentifier;

    @Mock
    private GenerarListaBiometricDataMapper generarListaBiometricDataMapper;

    @Mock
    private EnrolamientoServiceImpl enrolamientoServiceImpl;

    @Value("${huellaDaonEngineService.xOperationID}")
    private String xOperationID;

    @Before
    public void init(){
        this.huellaDaonEngineService = new HuellaDaonEngineServiceImpl(
                applicationUserIdentifier,
                defaultDomainIdentifier,
                pathIdentityIdentifier,
                daonEngineClient,
                traduccionHuellasDaonARenaperMapper,
                configuracionHuellasMapper,
                nombreParametrosDaon,
                nombreParametrosRenaper,
                nombreMicroServicioDatosGenerales,
                datosGeneralesService,
                transformPolicyIdentifier,
                generarListaBiometricDataMapper,
                enrolamientoServiceImpl
        );
    }

    @Test
    public void enrolarHuellaDaonEngineOk() throws JsonProcessingException {
        String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_HUELLA_DAONENGINE_REQUEST);
        EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest = mapper.readValue(jsonRequest, EnrolarHuellaDaonEngineRequest.class);
        ConsultarIdentidadResponse consultarIdentidadResponse = mock(ConsultarIdentidadResponse.class);
        CreateIdentityResponse createIdentityResponse = mock(CreateIdentityResponse.class);
        ManosServiceDto manosServiceDto = mock(ManosServiceDto.class);
        ConfiguracionHuellasDto configuracionHuellasDto = mock(ConfiguracionHuellasDto.class);
        ObjectFactory objectFactory = new ObjectFactory();
        CreateIdentityRequest createIdentityRequest = objectFactory.createCreateIdentityRequest();
        Identity identity = objectFactory.createIdentity();
        BiographicData biographicData = objectFactory.createBiographicData();
        identity.setBiographicData(biographicData);
        createIdentityRequest.setIdentity(identity);
        TrazasDto trazasEnrolarHuellaDto = mock(TrazasDto.class);
        when(enrolamientoServiceImpl.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenReturn(consultarIdentidadResponse);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosDaon)).thenReturn(configuracionHuellasDto);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosRenaper)).thenReturn(configuracionHuellasDto);
        when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class))).thenReturn(manosServiceDto);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonHuellas(any(EnrolarHuellaDaonEngineRequest.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(enrolarHuellaDaonEngineRequest);
        when(generarListaBiometricDataMapper.generarListaBiometricDataEnrolar(any(CreateIdentityRequest.class),any(EnrolarHuellaDaonEngineRequest.class))).thenReturn(createIdentityRequest);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonTrazasEnrolar(any(TrazasDto.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(trazasEnrolarHuellaDto);
        when(daonEngineClient.crearIdentidad(any(CreateIdentityRequest.class))).thenReturn(createIdentityResponse);
        EnrolarHuellaDaonEngineResponse enrolarHuellaDaonEngineResponse = huellaDaonEngineService.enrolarHuellaDaonEngine(enrolarHuellaDaonEngineRequest,xOperationID);
        assertNotNull(enrolarHuellaDaonEngineResponse);
    }

    @Test(expected = DaonEngineException.class)
    public void enrolarHuellaDaonEngineError() throws JsonProcessingException {
        String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_HUELLA_DAONENGINE_REQUEST);
        EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest = mapper.readValue(jsonRequest, EnrolarHuellaDaonEngineRequest.class);
        ConsultarIdentidadResponse consultarIdentidadResponse = mock(ConsultarIdentidadResponse.class);
        ManosServiceDto manosServiceDto = mock(ManosServiceDto.class);
        ConfiguracionHuellasDto configuracionHuellasDto = mock(ConfiguracionHuellasDto.class);
        ObjectFactory objectFactory = new ObjectFactory();
        CreateIdentityRequest createIdentityRequest = objectFactory.createCreateIdentityRequest();
        Identity identity = objectFactory.createIdentity();
        BiographicData biographicData = objectFactory.createBiographicData();
        identity.setBiographicData(biographicData);
        createIdentityRequest.setIdentity(identity);
        TrazasDto trazasEnrolarHuellaDto = mock(TrazasDto.class);
        when(enrolamientoServiceImpl.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenReturn(consultarIdentidadResponse);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosDaon)).thenReturn(configuracionHuellasDto);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosRenaper)).thenReturn(configuracionHuellasDto);
        when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class))).thenReturn(manosServiceDto);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonHuellas(any(EnrolarHuellaDaonEngineRequest.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(enrolarHuellaDaonEngineRequest);
        when(generarListaBiometricDataMapper.generarListaBiometricDataEnrolar(any(CreateIdentityRequest.class),any(EnrolarHuellaDaonEngineRequest.class))).thenReturn(createIdentityRequest);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonTrazasEnrolar(any(TrazasDto.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(trazasEnrolarHuellaDto);
        when(daonEngineClient.crearIdentidad(any(CreateIdentityRequest.class))).thenThrow(DaonEngineException.class);
        huellaDaonEngineService.enrolarHuellaDaonEngine(enrolarHuellaDaonEngineRequest,xOperationID);
    }

    @Test
    public void actualizarHuellaDaonEngineOk() throws JsonProcessingException {
        String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_HUELLA_DAONENGINE_REQUEST);
        ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest = mapper.readValue(jsonRequest, ActualizarHuellaDaonEngineRequest.class);
        ConsultarIdentidadResponse consultarIdentidadResponse = mock(ConsultarIdentidadResponse.class);
        ConfiguracionHuellasDto configuracionHuellasDto = mock(ConfiguracionHuellasDto.class);
        ManosServiceDto manosServiceDto = mock(ManosServiceDto.class);
        ObjectFactory objectFactory = new ObjectFactory();
        UpdateIdentityRequest updateIdentityRequest = objectFactory.createUpdateIdentityRequest();
        Identity identity = objectFactory.createIdentity();
        BiographicData biographicData = objectFactory.createBiographicData();
        identity.setBiographicData(biographicData);
        updateIdentityRequest.setUpdateData(identity);
        TrazasDto trazasActualizarHuellaDto = mock(TrazasDto.class);
        UpdateIdentityResponse updateIdentityResponse = mock(UpdateIdentityResponse.class);
        when(enrolamientoServiceImpl.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenReturn(consultarIdentidadResponse);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosDaon)).thenReturn(configuracionHuellasDto);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosRenaper)).thenReturn(configuracionHuellasDto);
        when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class))).thenReturn(manosServiceDto);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonHuellasActualizar(any(ActualizarHuellaDaonEngineRequest.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(actualizarHuellaDaonEngineRequest);
        when(generarListaBiometricDataMapper.generarListaBiometricDataActualizar(any(UpdateIdentityRequest.class),any(ActualizarHuellaDaonEngineRequest.class))).thenReturn(updateIdentityRequest);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonTrazasActualizar(any(TrazasDto.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(trazasActualizarHuellaDto);
        when(daonEngineClient.actualizarIdentidad(any(UpdateIdentityRequest.class))).thenReturn(updateIdentityResponse);
        ActualizarHuellaDaonEngineResponse actualizarHuellaDaonEngineResponse = huellaDaonEngineService.actualizarHuellaDaonEngine(actualizarHuellaDaonEngineRequest,xOperationID);
        assertNotNull(actualizarHuellaDaonEngineResponse);
    }

    @Test(expected = DaonEngineException.class)
    public void actualizarHuellaDaonEngineError() throws JsonProcessingException {
        String jsonRequest = retrieveBody(Constantes.JSON_ACTUALIZAR_HUELLA_DAONENGINE_REQUEST);
        ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest = mapper.readValue(jsonRequest, ActualizarHuellaDaonEngineRequest.class);
        ConsultarIdentidadResponse consultarIdentidadResponse = mock(ConsultarIdentidadResponse.class);
        ConfiguracionHuellasDto configuracionHuellasDto = mock(ConfiguracionHuellasDto.class);
        ManosServiceDto manosServiceDto = mock(ManosServiceDto.class);
        ObjectFactory objectFactory = new ObjectFactory();
        UpdateIdentityRequest updateIdentityRequest = objectFactory.createUpdateIdentityRequest();
        Identity identity = objectFactory.createIdentity();
        BiographicData biographicData = objectFactory.createBiographicData();
        identity.setBiographicData(biographicData);
        updateIdentityRequest.setUpdateData(identity);
        TrazasDto trazasActualizarHuellaDto = mock(TrazasDto.class);
        when(enrolamientoServiceImpl.consultarNormalizacionIndividuos(any(ConsultarIdentidadRequest.class))).thenReturn(consultarIdentidadResponse);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosDaon)).thenReturn(configuracionHuellasDto);
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID,nombreMicroServicioDatosGenerales,nombreParametrosRenaper)).thenReturn(configuracionHuellasDto);
        when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class))).thenReturn(manosServiceDto);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonHuellasActualizar(any(ActualizarHuellaDaonEngineRequest.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(actualizarHuellaDaonEngineRequest);
        when(generarListaBiometricDataMapper.generarListaBiometricDataActualizar(any(UpdateIdentityRequest.class),any(ActualizarHuellaDaonEngineRequest.class))).thenReturn(updateIdentityRequest);
        when(traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonTrazasActualizar(any(TrazasDto.class),any(ManosServiceDto.class),any(ManosServiceDto.class))).thenReturn(trazasActualizarHuellaDto);
        when(daonEngineClient.actualizarIdentidad(any(UpdateIdentityRequest.class))).thenThrow(DaonEngineException.class);
        huellaDaonEngineService.actualizarHuellaDaonEngine(actualizarHuellaDaonEngineRequest,xOperationID);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void actualizarTrazasDaonEngineOk() throws JsonProcessingException {
      try (MockedStatic<ServiceConvertidorUtil> utilities =
          Mockito.mockStatic(ServiceConvertidorUtil.class)) {
  
        var request =
            mapper.readValue(
                retrieveBody(Constantes.JSON_ACTUALIZAR_TRAZAS_DAONENGINE_REQUEST),
                GuardarTrazasDaonEngineRequest.class);
  
        var manosServiceDto = new ManosServiceDto();
        var configuracionHuellasDto = mock(ConfiguracionHuellasDto.class);
        var updateIdentityResponse = mock(UpdateIdentityResponse.class);
  
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosDaon))
            .thenReturn(configuracionHuellasDto);
  
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosRenaper))
            .thenReturn(configuracionHuellasDto);
  
        when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class)))
            .thenReturn(manosServiceDto);
  
        utilities
            .when(() -> ServiceConvertidorUtil.convertirObjetoEnMapa(any(Object.class)))
            .thenReturn(new HashMap<>());
  
        when(traduccionHuellasDaonARenaperMapper.getIdentificadorDaon(
                any(Integer.class), any(Map.class), any(Map.class)))
            .thenReturn(0);
  
        when(enrolamientoServiceImpl.consultarCliente(any(ConsultaGeneralClienteRequest.class)))
            .thenReturn(new ConsultaClienteAtributosResponse());
  
        when(daonEngineClient.actualizarIdentidad(any(UpdateIdentityRequest.class)))
            .thenReturn(updateIdentityResponse);
  
        var response = huellaDaonEngineService.guardarTrazasDaonEngine(request, xOperationID);
  
        assertNotNull(response);
      }
    }
    
    @SuppressWarnings("unchecked")
    @Test(expected = DaonEngineException.class)
    public void actualizarTrazasDaonEngineException() throws JsonProcessingException {
      try (MockedStatic<ServiceConvertidorUtil> utilities =
          Mockito.mockStatic(ServiceConvertidorUtil.class)) {
  
        var request =
            mapper.readValue(
                retrieveBody(Constantes.JSON_ACTUALIZAR_TRAZAS_DAONENGINE_REQUEST),
                GuardarTrazasDaonEngineRequest.class);
  
        var manosServiceDto = new ManosServiceDto();
        var configuracionHuellasDto = mock(ConfiguracionHuellasDto.class);
  
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosDaon))
            .thenReturn(configuracionHuellasDto);
  
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosRenaper))
            .thenReturn(configuracionHuellasDto);
  
        when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class)))
            .thenReturn(manosServiceDto);
  
        utilities
            .when(() -> ServiceConvertidorUtil.convertirObjetoEnMapa(any(Object.class)))
            .thenReturn(new HashMap<>());
  
        when(traduccionHuellasDaonARenaperMapper.getIdentificadorDaon(
                any(Integer.class), any(Map.class), any(Map.class)))
            .thenReturn(0);
  
        when(enrolamientoServiceImpl.consultarCliente(any(ConsultaGeneralClienteRequest.class)))
            .thenReturn(new ConsultaClienteAtributosResponse());
  
        when(daonEngineClient.actualizarIdentidad(any(UpdateIdentityRequest.class)))
            .thenThrow(DaonEngineException.class);
  
        huellaDaonEngineService.guardarTrazasDaonEngine(request, xOperationID);
      }
    }
    
    @Test(expected = MacroException.class)
    public void actualizarTrazasMacroException() throws JsonProcessingException {
      try (MockedStatic<ServiceConvertidorUtil> utilities =
          Mockito.mockStatic(ServiceConvertidorUtil.class)) {
  
        var request =
            mapper.readValue(
                retrieveBody(Constantes.JSON_ACTUALIZAR_TRAZAS_DAONENGINE_REQUEST),
                GuardarTrazasDaonEngineRequest.class);
  
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosDaon))
            .thenThrow(MacroException.class);
  
        huellaDaonEngineService.guardarTrazasDaonEngine(request, xOperationID);
      }
    }
    
    @Test(expected = GeneralException.class)
    public void actualizarTrazasGeneralException() throws JsonProcessingException {
      try (MockedStatic<ServiceConvertidorUtil> utilities =
          Mockito.mockStatic(ServiceConvertidorUtil.class)) {
  
        var request =
            mapper.readValue(
                retrieveBody(Constantes.JSON_ACTUALIZAR_TRAZAS_DAONENGINE_REQUEST),
                GuardarTrazasDaonEngineRequest.class);
  
        when(datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosDaon))
            .thenThrow(NullPointerException.class);
  
        huellaDaonEngineService.guardarTrazasDaonEngine(request, xOperationID);
      }
    }
}
