package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.commons.values.constants.text.CharConstants.CARACTER_GUION_BAJO;
import static ar.com.macro.constant.Errores.ERROR_GUARDANDO_TRAZAS;
import static ar.com.macro.utils.ServiceConvertidorUtil.convertirStringJsonEnObjeto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.values.constants.config.TraceConstants;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import ar.com.macro.commons.values.constants.format.DatePatternsConstants;
import ar.com.macro.constant.Errores;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.GuardarTrazasDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.ActualizarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.GuardarTrazasDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.EnrolarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.DaonEngineClient;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.BiographicData;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CoreData;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.DomainIdentifier;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GenericRequestParameters;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.Identity;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.IdentityIdentifier;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.LongItem;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.ObjectFactory;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.PolicyIdentifier;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.StringItem;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.TimestampItem;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.cliente.ConsultaClienteAtributosResponse;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteRequest;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.EnrolamientoService;
import ar.com.macro.enrolamiento.model.service.HuellaDaonEngineService;
import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.enrolamiento.model.service.dto.ManosServiceDto;
import ar.com.macro.enrolamiento.model.service.dto.TrazasDto;
import ar.com.macro.enrolamiento.model.service.dto.TrazasHuellaDto;
import ar.com.macro.enrolamiento.model.service.mapper.ConfiguracionHuellasMapper;
import ar.com.macro.enrolamiento.model.service.mapper.GenerarListaBiometricDataMapper;
import ar.com.macro.enrolamiento.model.service.mapper.ItemBiographicData;
import ar.com.macro.enrolamiento.model.service.mapper.TraduccionHuellasDaonARenaperMapper;
import ar.com.macro.exceptions.GeneralException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import ar.com.macro.utils.ServiceConvertidorUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HuellaDaonEngineServiceImpl implements HuellaDaonEngineService {

    private static final String ENROLLED_BRANCH_VALUE = "0000";
    /**
    *
    */
    private String nombreParametrosDaon;
    private String nombreParametrosRenaper;
    private String nombreMicroServicioDatosGenerales;
    private String applicationUserIdentifier;
    private String defaultDomainIdentifier;
    private String pathIdentityIdentifier;
    private TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper;
    private ConfiguracionHuellasMapper configuracionHuellasMapper;
    private DatosGeneralesService datosGeneralesService;
    private String transformPolicyIdentifier;
    private GenerarListaBiometricDataMapper generarListaBiometricDataMapper;
    private DaonEngineClient daonEngineClient;
    private EnrolamientoService enrolamientoService;
    private ObjectFactory obj = new ObjectFactory();

    public HuellaDaonEngineServiceImpl(
            @Value("${daonengine.useridentifier}") String applicationUserIdentifier,
            @Value("${daonengine.domainidentifier}") String defaultDomainIdentifier,
            @Value("${daonengine.identityidentifier}") String pathIdentityIdentifier,
            DaonEngineClient daonEngineClient,
            TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper,
            ConfiguracionHuellasMapper configuracionHuellasMapper,
            @Value("${daonengine.nombre.parametros.daon}") String nombreParametrosDaon,
            @Value("${daonengine.nombre.parametros.renaper}") String nombreParametrosRenaper,
            @Value("${daonengine.nombre.parametros.datos.generales}") String nombreMicroServicioDatosGenerales,
            DatosGeneralesService datosGeneralesService,
            @Value("${daonengine.transformpolicyidentifier}") String transformPolicyIdentifier,
            GenerarListaBiometricDataMapper generarListaBiometricDataMapper,
            EnrolamientoService enrolamientoService
    ){
        this.applicationUserIdentifier = applicationUserIdentifier;
        this.defaultDomainIdentifier = defaultDomainIdentifier;
        this.pathIdentityIdentifier = pathIdentityIdentifier;
        this.daonEngineClient = daonEngineClient;
        this.traduccionHuellasDaonARenaperMapper = traduccionHuellasDaonARenaperMapper;
        this.configuracionHuellasMapper = configuracionHuellasMapper;
        this.nombreParametrosDaon = nombreParametrosDaon;
        this.nombreParametrosRenaper = nombreParametrosRenaper;
        this.nombreMicroServicioDatosGenerales = nombreMicroServicioDatosGenerales;
        this.datosGeneralesService = datosGeneralesService;
        this.transformPolicyIdentifier = transformPolicyIdentifier;
        this.generarListaBiometricDataMapper = generarListaBiometricDataMapper;
        this.enrolamientoService = enrolamientoService;
    }

    /**
     * Expone un metodo para realizar el enrolamiento de huellas completando la iformación en ESB
     *
     * @param enrolarHuellaDaonEngineRequest, xOperationID
     * @return EnrolarHuellaDaonEngineResponse enrolarHuellaDaonEngineResponse
     */
    @Override
    public EnrolarHuellaDaonEngineResponse enrolarHuellaDaonEngine(EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest, String xOperationID) {
        EnrolarHuellaDaonEngineResponse enrolarHuellaDaonEngineResponse = new EnrolarHuellaDaonEngineResponse();
        enrolarHuellaDaonEngineResponse.setIdtracking(
                daonEngineClient.crearIdentidad(construccionObjetoEnrolarHuella(enrolarHuellaDaonEngineRequest,xOperationID)).getRequestTrackingUID()
        );
        return enrolarHuellaDaonEngineResponse;
    }

    /**
     * Construye el objeto CreateIdentityRequest y la lista de BiographicData con los datos requeridos para la inserción
     *
     * @param enrolarHuellaDaonEngineRequest, consultarIdentidadResponse, xOperationID
     * @return CreateIdentityRequest createIdentityRequest
     */
    public CreateIdentityRequest construccionObjetoEnrolarHuella(EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest, String xOperationID) {
        CreateIdentityRequest createIdentityRequest = construirRequestDaonEngineCrearIdentidad(enrolarHuellaDaonEngineRequest);
        ConfiguracionHuellasDto configuracionHuellasDtoDaon = datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosDaon);
        ConfiguracionHuellasDto configuracionHuellasDtoRenaper = datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosRenaper);
        if(!Objects.isNull(configuracionHuellasDtoDaon) && !Objects.isNull(configuracionHuellasDtoRenaper)){
            ManosServiceDto manosServiceDtoDaon = configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoDaon);
            ManosServiceDto manosServiceDtoRenaper = configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoRenaper);
            if(!Objects.isNull(enrolarHuellaDaonEngineRequest.getHuellas())){
                enrolarHuellaDaonEngineRequest = traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonHuellas(
                        enrolarHuellaDaonEngineRequest,
                        manosServiceDtoDaon,
                        manosServiceDtoRenaper
                );
                createIdentityRequest = generarListaBiometricDataMapper.generarListaBiometricDataEnrolar(createIdentityRequest, enrolarHuellaDaonEngineRequest);

            }

        } else {
            throw new ObtenerDatosGeneralesCompuestosException(
                    Errores.ERROR_DATOS_GENERALES.getCodigo(),
                    Errores.ERROR_DATOS_GENERALES.getMensaje());
        }
        return createIdentityRequest;
    }

    /**
     * Crea las instancias de los dtos para realizar el Marshal con el WebService
     *
     * @param enrolarHuellaDaonEngineRequest
     * @return CreateIdentityRequest createIdentityRequest
     */
    public CreateIdentityRequest construirRequestDaonEngineCrearIdentidad(EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest) {
        enrolarHuellaDaonEngineRequest.setEnrolledDate(new SimpleDateFormat(DatePatternsConstants.FECHA_FORMATO_DD_MM_YYYY_HH_MM_SS_BARRAS, Locale.ENGLISH).format(new Date()));
        CreateIdentityRequest createIdentityRequest = obj.createCreateIdentityRequest();
        DomainIdentifier domainIdentifier = obj.createDomainIdentifier();
        domainIdentifier.setValue(defaultDomainIdentifier);
        createIdentityRequest.setDomainIdentifier(domainIdentifier);
        GenericRequestParameters genericRequestParameters = obj.createGenericRequestParameters();
        genericRequestParameters.setApplicationIdentifier(MDC.get(TraceConstants.APPLICATION));
        genericRequestParameters.setApplicationUserIdentifier(applicationUserIdentifier);
        createIdentityRequest.setGenericRequestParameters(genericRequestParameters);
        BiographicData biographicData = obj.createBiographicData();
        CoreData coreData = obj.createCoreData();
        coreData.setUniqueName(enrolarHuellaDaonEngineRequest.getIdusuario());
        Identity identity = obj.createIdentity();
        identity.setBiographicData(biographicData);
        identity.setCoreData(coreData);
        createIdentityRequest.setIdentity(identity);
        PolicyIdentifier policyIdentifier = obj.createPolicyIdentifier();
        policyIdentifier.setValue(transformPolicyIdentifier);
        createIdentityRequest.setTransformPolicyIdentifier(policyIdentifier);
        return createIdentityRequest;
    }

    /**
     * Metodo que expone la función de actualizar enrolamiento de huellas, utilizando la busqueda de cliente en ESB
     * realizando la conversión de huellas de renaper a daon
     *
     * @param actualizarHuellaDaonEngineRequest, xOperationId
     * @return ActualizarHuellaDaonEngineResponse actualizarHuellaDaonEngineResponse
     */
    @Override
    public ActualizarHuellaDaonEngineResponse actualizarHuellaDaonEngine(ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest, String xOperationId) {
        ActualizarHuellaDaonEngineResponse actualizarHuellaDaonEngineResponse = new ActualizarHuellaDaonEngineResponse();
        actualizarHuellaDaonEngineResponse.setIdtracking(
                daonEngineClient.actualizarIdentidad(identidadActualizarHuella(actualizarHuellaDaonEngineRequest,xOperationId)).getRequestTrackingUID()
        );
        return actualizarHuellaDaonEngineResponse;
    }

    /**
     * Método encargado de guardar los datos de auditoria (trazas) en el motor biometrico DAON.
     *
     * @param request objeto que almacena la información de las trazas.
     * @param xOperationId el identificador de la operación.
     * @return ActualizarTrazasDaonEngineResponse con información requerida desde el cliente.
     */
    @Override
    public GuardarTrazasDaonEngineResponse guardarTrazasDaonEngine(
      final GuardarTrazasDaonEngineRequest request, final String xOperationId) {

      try {
        var configuracionHuellasDtoDaon =
            datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationId, nombreMicroServicioDatosGenerales, nombreParametrosDaon);
  
        var configuracionHuellasDtoRenaper =
            datosGeneralesService.obtenerDatosGeneralesCompuestos(
                xOperationId, nombreMicroServicioDatosGenerales, nombreParametrosRenaper);
  
        var manosServiceDtoDaon =
            configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoDaon);
  
        var manosServiceDtoRenaper =
            configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoRenaper);
  
        var updateIdentityRequest = buildUpdateIdentityRequest(request.getIdusuario());
        var biographicData = updateIdentityRequest.getUpdateData().getBiographicData();
  
        cargarBiographicData(biographicData, request.getTrazas(), request.getIdusuario());
  
        cargarBiographicDataTrazas(
            biographicData, request.getTrazas(), manosServiceDtoDaon, manosServiceDtoRenaper);
  
        daonEngineClient.actualizarIdentidad(updateIdentityRequest);
  
        return GuardarTrazasDaonEngineResponse.of(1);
  
      } catch (MacroException e) {
        log.error(e.getMessage(), e);
        throw e;
      } catch (Exception e) {
        log.error(ERROR_GUARDANDO_TRAZAS.getMensaje(), e);
        throw new GeneralException(ERROR_GUARDANDO_TRAZAS);
      }
    }

    /**
     * Construye el objeto UpdateIdentityRequest y la lista de BiographicData con los datos requeridos para la inserción
     *
     * @param actualizarHuellaDaonEngineRequest, consultarNormalizacionIndividuos, xOperationId
     * @return UpdateIdentityRequest updateIdentityRequest
     */
    private UpdateIdentityRequest identidadActualizarHuella(ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest, String xOperationId) {
        UpdateIdentityRequest updateIdentityRequest = construirRequestDaonEngineActualizarIdentidad(actualizarHuellaDaonEngineRequest);
        ConfiguracionHuellasDto configuracionHuellasDtoDaon = datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationId, nombreMicroServicioDatosGenerales, nombreParametrosDaon);
        ConfiguracionHuellasDto configuracionHuellasDtoRenaper = datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationId, nombreMicroServicioDatosGenerales, nombreParametrosRenaper);
        if(Objects.nonNull(configuracionHuellasDtoDaon) && Objects.nonNull(configuracionHuellasDtoRenaper)){
            ManosServiceDto manosServiceDtoDaon = configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoDaon);
            ManosServiceDto manosServiceDtoRenaper = configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoRenaper);
            if(!Objects.isNull(actualizarHuellaDaonEngineRequest.getHuellas())){
                actualizarHuellaDaonEngineRequest = traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaonHuellasActualizar(
                        actualizarHuellaDaonEngineRequest,
                        manosServiceDtoDaon,
                        manosServiceDtoRenaper
                );
                updateIdentityRequest = generarListaBiometricDataMapper.generarListaBiometricDataActualizar(updateIdentityRequest, actualizarHuellaDaonEngineRequest);
            }
            
            if(!StringUtils.isBlank(actualizarHuellaDaonEngineRequest.getTrazas())){
              TrazasDto trazasActualizarHuellaDto = convertirStringJsonEnObjeto(actualizarHuellaDaonEngineRequest.getTrazas(), TrazasDto.class);
              cargarBiographicData(updateIdentityRequest.getUpdateData().getBiographicData(), trazasActualizarHuellaDto, actualizarHuellaDaonEngineRequest.getIdusuario());
              cargarBiographicDataTrazas(updateIdentityRequest.getUpdateData().getBiographicData(),trazasActualizarHuellaDto, manosServiceDtoDaon, manosServiceDtoRenaper);
          }

        } else {
            throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje());
        }
        return updateIdentityRequest;
    }

    /**
     *
     * @param biographicData
     * @param actualizarHuellaDaonEngineRequest
     * @param trazasDto
     */
    private void cargarBiographicData(BiographicData biographicData, TrazasDto trazasDto, String idusuario) {
      try {
        StringItem stringItem;
        LongItem longItem;

        if (!StringUtils.isBlank(idusuario)) {
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.CLAVE_PRINCIPAL.name());
          stringItem.setValue(idusuario);
          biographicData.getStringItem().add(stringItem);
        }
  
        ConsultaClienteAtributosResponse consultaCliente = consultarCliente(idusuario);
  
        if (Objects.nonNull(consultaCliente)) {
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.CLIENT_CUIL.name());
          stringItem.setValue(consultaCliente.getNroDocumentoTributario());
          biographicData.getStringItem().add(stringItem);
  
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.CLIENT_ID_COBIS.name());
          stringItem.setValue(String.valueOf(consultaCliente.getClienteId()));
          biographicData.getStringItem().add(stringItem);
        }
  
        stringItem = new StringItem();
        stringItem.setName(ItemBiographicData.ENROLLED_BRANCH.name());
        stringItem.setValue(ENROLLED_BRANCH_VALUE);
        biographicData.getStringItem().add(stringItem);
  
        if (Objects.nonNull(trazasDto.getIp())) {
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.ENROLLED_IP.name());
          stringItem.setValue(trazasDto.getIp());
          biographicData.getStringItem().add(stringItem);
        }
  
        if (Objects.nonNull(trazasDto.getIdenrolador())) {
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.USER_ENROLLER.name());
          stringItem.setValue(trazasDto.getIdenrolador());
          biographicData.getStringItem().add(stringItem);
  
          var timestampItem = new TimestampItem();
          timestampItem.setName(ItemBiographicData.ENROLLED_DATE.name());
          timestampItem.setValue(getXMLGregorianCalendar());
          biographicData.getTimestampItem().add(timestampItem);
        }
  
        if (Objects.nonNull(trazasDto.getIdenroladorhuellas())) {
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.USER_ENROLLER_HUELLAS.name());
          stringItem.setValue(trazasDto.getIdenroladorhuellas());
          biographicData.getStringItem().add(stringItem);
  
          var timestampItem = new TimestampItem();
          timestampItem.setName(ItemBiographicData.ENROLLED_DATE_HUELLAS.name());
          timestampItem.setValue(getXMLGregorianCalendar());
          biographicData.getTimestampItem().add(timestampItem);
        }

        if (Objects.nonNull(trazasDto.getIdsupervisor())) {
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.USER_SUPERVISOR.name());
          stringItem.setValue(trazasDto.getIdsupervisor());
          biographicData.getStringItem().add(stringItem);
        }
  
        if (Objects.nonNull(MDC.get(TraceConstants.APPLICATION))) {
          stringItem = new StringItem();
          stringItem.setName(ItemBiographicData.ENROLLED_CANAL.name());
          stringItem.setValue(MDC.get(TraceConstants.APPLICATION));
          biographicData.getStringItem().add(stringItem);
        }
  
        if (Objects.nonNull(trazasDto.getScorerenaper())) {
          longItem = new LongItem();
          longItem.setName(ItemBiographicData.CLIENT_RENAPER_FACE_MTCH_SCORE.name());
          longItem.setValue(Long.valueOf(trazasDto.getScorerenaper()));
          biographicData.getLongItem().add(longItem);
        }
  
        if (Objects.nonNull(trazasDto.getIndrostrorenaper())) {
          longItem = new LongItem();
          longItem.setName(ItemBiographicData.CLIENT_FACE_ACCEPTANCE.name());
          longItem.setValue(Long.valueOf(trazasDto.getIndrostrorenaper()));
          biographicData.getLongItem().add(longItem);
        }
  
        // TODO: Comentado mientras se retoma Workaround de ReNaPer
        // if (Objects.nonNull(trazasDto.getNumerotramite())) {
        //   stringItem = new StringItem();
        //   stringItem.setName(ItemBiographicData.TRAMIT_NUMBER.name());
        //   stringItem.setValue(trazasDto.getNumerotramite());
        //   biographicData.getStringItem().add(stringItem);
        // }

        // if (Objects.nonNull(trazasDto.getDnivalido())) {
        //   longItem = new LongItem();
        //   longItem.setName(ItemBiographicData.FLAG_VALID_DNI.name());
        //   longItem.setValue(Long.valueOf(trazasDto.getDnivalido()));
        //   biographicData.getLongItem().add(longItem);
        // }

        // if (Objects.nonNull(trazasDto.getRostrovalido())) {
        //   longItem = new LongItem();
        //   longItem.setName(ItemBiographicData.FLAG_VALID_FACE.name());
        //   longItem.setValue(Long.valueOf(trazasDto.getRostrovalido()));
        //   biographicData.getLongItem().add(longItem);
        // }

        // if (Objects.nonNull(trazasDto.getTipoenrolamiento())) {
        //   longItem = new LongItem();
        //   longItem.setName(ItemBiographicData.FLAG_ENROLLMENT_TYPE.name());
        //   longItem.setValue(Long.valueOf(trazasDto.getTipoenrolamiento()));
        //   biographicData.getLongItem().add(longItem);
        // }

      } catch (Exception ex) {
        log.error("Error mapping ", ex);
      }
    }

    private static XMLGregorianCalendar getXMLGregorianCalendar() {
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try{
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(new Date().getTime());
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        }catch(Exception ex){ }
        return xmlGregorianCalendar;
    }

    private ConsultaClienteAtributosResponse consultarCliente(String idusuario){
        ConsultaClienteAtributosResponse consultaCliente = null;
        try {
            String dni = idusuario.substring(2, idusuario.length() - 1);
            consultaCliente = enrolamientoService.consultarCliente(ConsultaGeneralClienteRequest.builder().documento(dni).build());
        }catch (Exception ex ){
            log.error(ErrorMessage.ERROR_CONSULTA_GENERAL_CLIENTE_NO_EXISTE.mensaje(),ex);
        }
        return consultaCliente;
    }
    /**
     *
     * @param biographicData
     * @param trazasDto
     */
    private void cargarBiographicDataTrazas(BiographicData biographicData, TrazasDto trazasDto,
                                            ManosServiceDto manosServiceDtoDaon, ManosServiceDto manosServiceDtoRenaper){

        List<TrazasHuellaDto> huellas = trazasDto.getHuellas();
        if (!CollectionUtils.isEmpty(huellas)) {
          Map<String, Object> mapObjDaon = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoDaon);
          Map<String, Object> mapObjRenaper = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoRenaper);

          huellas.stream().forEach( f ->{

              Integer identificadorDaon = traduccionHuellasDaonARenaperMapper.getIdentificadorDaon(f.getIdentificador(), mapObjDaon, mapObjRenaper);

              LongItem longItem = new LongItem();
              longItem.setName(ItemBiographicData.CLIENT_FINGER_ACCEPTANCE.name() + CARACTER_GUION_BAJO + f.getIdentificador());
              longItem.setValue(identificadorDaon);
              biographicData.getLongItem().add(longItem);

              longItem = new LongItem();
              longItem.setName(ItemBiographicData.CLIENT_FINGER_COINCIDENCIA.name() +  CARACTER_GUION_BAJO + f.getIdentificador());
              longItem.setValue(Integer.valueOf(f.getScorerenaper()));
              biographicData.getLongItem().add(longItem);

              StringItem stringItem = new StringItem();
              stringItem.setName(ItemBiographicData.CLIENT_FINGER_HIT.name() +  CARACTER_GUION_BAJO + f.getIdentificador());
              stringItem.setValue(f.getIndhuellahitrenaper());
              biographicData.getStringItem().add(stringItem);

              stringItem = new StringItem();
              stringItem.setName(ItemBiographicData.CLIENT_FINGER_EXCEPCION.name() +  CARACTER_GUION_BAJO + f.getIdentificador());
              stringItem.setValue(f.getIndhuellaexcepcion());
              biographicData.getStringItem().add(stringItem);
          });
        }
    }

    /**
     * Construye el objeto UpdateIdentityRequest con el Object Factory para la creación de instancias
     *
     * @param actualizarHuellaDaonEngineRequest
     * @return UpdateIdentityRequest updateIdentityRequest
     */
    private UpdateIdentityRequest construirRequestDaonEngineActualizarIdentidad(ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest) {
        actualizarHuellaDaonEngineRequest.setEnrolledDate(new SimpleDateFormat(DatePatternsConstants.FECHA_FORMATO_DD_MM_YYYY_HH_MM_SS_BARRAS, Locale.ENGLISH).format(new Date()));
        
        UpdateIdentityRequest updateIdentityRequest = buildUpdateIdentityRequest(actualizarHuellaDaonEngineRequest.getIdusuario());
        CoreData coreData = updateIdentityRequest.getUpdateData().getCoreData();
       
        if(Objects.nonNull(actualizarHuellaDaonEngineRequest.getBloqueado())){
          
          if(actualizarHuellaDaonEngineRequest.getBloqueado() == 1){
                coreData.setBlocked(true);
            } else if (actualizarHuellaDaonEngineRequest.getBloqueado() == 0) {
                coreData.setBlocked(false);
            }
        }
        
        if(StringUtils.isNotBlank(actualizarHuellaDaonEngineRequest.getNombre())){
            coreData.setName(actualizarHuellaDaonEngineRequest.getNombre());
        }
        
        return updateIdentityRequest;
    }
  
    /**
     * Construye un objeto de tipo UpdateIdentityRequest que será usado para actualizar información
     * del usuario en DAON.
     *
     * @param idusuario el identificador del usuario
     * @return el objeto UpdateIdentityRequest construido
     */
    private UpdateIdentityRequest buildUpdateIdentityRequest(final String idusuario) {
      UpdateIdentityRequest updateIdentityRequest = obj.createUpdateIdentityRequest();
      
      DomainIdentifier domainIdentifier = obj.createDomainIdentifier();
      domainIdentifier.setValue(defaultDomainIdentifier);
      updateIdentityRequest.setDomainIdentifier(domainIdentifier);
      
      GenericRequestParameters genericRequestParameters = obj.createGenericRequestParameters();
      genericRequestParameters.setApplicationIdentifier(MDC.get(TraceConstants.APPLICATION));
      genericRequestParameters.setApplicationUserIdentifier(applicationUserIdentifier);
      updateIdentityRequest.setGenericRequestParameters(genericRequestParameters);
      
      Identity identity = obj.createIdentity();
      identity.setBiographicData(obj.createBiographicData());
      identity.setCoreData(obj.createCoreData());
      updateIdentityRequest.setUpdateData(identity);
      
      PolicyIdentifier policyIdentifier = obj.createPolicyIdentifier();
      policyIdentifier.setValue(transformPolicyIdentifier);
      updateIdentityRequest.setTransformPolicyIdentifier(policyIdentifier);
      
      IdentityIdentifier identityIdentifier = obj.createIdentityIdentifier();
      identityIdentifier.setValue(pathIdentityIdentifier.concat(idusuario));
      updateIdentityRequest.setIdentityIdentifier(identityIdentifier);
      
      return updateIdentityRequest;
  }

}
