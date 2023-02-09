package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.constant.Errores.ERROR_CONSULTAR_DATOS_DNI;
import static ar.com.macro.constant.Errores.ERROR_FILTRO_BIOMETRIC_DATA;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_FACE_ENROLED_IDENTITYX;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static java.util.stream.Collectors.toList;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.daon.identityx.rest.model.pojo.User;
import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.commons.utils.generator.IdentifierUtil;
import ar.com.macro.commons.utils.normalization.ReglaNormalizacionUtil;
import ar.com.macro.commons.values.constants.config.TraceConstants;
import ar.com.macro.commons.values.constants.error.ErrorCode;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import ar.com.macro.constant.Constantes;
import ar.com.macro.constant.Errores;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.EliminarPerfilUsuarioRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoIdentityXResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.LeerPdf417DniResponse;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ActualizarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ConsultarDatosDniRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.EnrolarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ConsultarDatosDniResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.EnrolarRostroResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.DaonEngineClient;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.DomainIdentifier;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GenericRequestParameters;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GetIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GetIdentityResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.IdentityDataFilter;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.IdentityIdentifier;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.ObjectFactory;
import ar.com.macro.enrolamiento.model.client.datapower.DataPowerClient;
import ar.com.macro.enrolamiento.model.client.datapower.dto.rest.dto.consulta.identificacion.ConsultaIdentificacionClienteResponse;
import ar.com.macro.enrolamiento.model.client.esb.EsbClient;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.cliente.ConsultaClienteAtributosRequest;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.cliente.ConsultaClienteAtributosResponse;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general.ConsultaGeneralCliente;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteRequest;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteResponse;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.normalizacion.ConsultaNormalizacionIndividuo;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.normalizacion.Individuo;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.normalizacion.NormalizarIndividuoRequest;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.utils.ClientesConstantes;
import ar.com.macro.enrolamiento.model.client.identityx.IdentityXClient;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CodigoBarrasResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.DocBarcodeField;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.EnrolamientoService;
import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.enrolamiento.model.service.dto.ManosServiceDto;
import ar.com.macro.enrolamiento.model.service.mapper.ConfiguracionHuellasMapper;
import ar.com.macro.enrolamiento.model.service.mapper.EnrolamientoServiceMapper;
import ar.com.macro.enrolamiento.model.service.mapper.TraduccionHuellasDaonARenaperMapper;
import ar.com.macro.exceptions.ConsultarDatosDniIdentityXException;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

@RefreshScope
@Slf4j
@Service
public class EnrolamientoServiceImpl implements EnrolamientoService {

    private String ocrSexo;
    private String ocrNumeroDocumento;
    private String ocrNombreApellidos;
    private String ocrApellidos;
    private String ocrNombres;
    private String ocrNumeroTramite;
    private String nombreMicroservicio;
    private final EsbClient esbClient;
    private IdentityXClient identityXClient;
    private ReglaNormalizacionUtil reglaNormalizacionUtil;
    private DatosGeneralesService datosGeneralesService;
    private String nombreMicroServicioDatosGenerales;
    private EnrolamientoServiceMapper enrolamientoServiceMapper;
    private DataPowerClient dataPowerClient;
    private final DaonEngineClient daonEngineClient;
    private final TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper;
    private final ConfiguracionHuellasMapper configuracionHuellasMapper;
    private final ObjectFactory obj = new ObjectFactory();
    private final String nombreParametrosDaon;
    private final String nombreParametrosRenaper;
    private final String applicationUserIdentifier;
    private final String defaultDomainIdentifier;
    private final String pathIdentityIdentifier;
    private int tiempoEsperaGetDni;
    private int intentosMaximosGetDni;

    public EnrolamientoServiceImpl(
            @Value("${macro.microservicio.nombre}") String nombreMicroservicio,
            @Value("${identityx.ocr.indice.sexo}") String ocrSexo,
            @Value("${identityx.ocr.indice.numero.documento}") String ocrNumeroDocumento,
            @Value("${identityx.ocr.indice.nombre.apellidos}") String ocrNombreApellidos,
            @Value("${identityx.ocr.indice.apellidos}") String ocrApellidos,
            @Value("${identityx.ocr.indice.nombres}") String ocrNombres,
            @Value("${identityx.ocr.indice.numerotramite}") String ocrNumeroTramite,
            @Value("${daonengine.nombre.parametros.datos.generales}") String nombreMicroServicioDatosGenerales,
            IdentityXClient identityXClient,
            EsbClient esbClient,
            ReglaNormalizacionUtil reglaNormalizacionUtil,
            DatosGeneralesService datosGeneralesService,
            EnrolamientoServiceMapper enrolamientoServiceMapper,
            DataPowerClient dataPowerClient,
            DaonEngineClient daonEngineClient,
            TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper,
            ConfiguracionHuellasMapper configuracionHuellasMapper,
            @Value("${daonengine.nombre.parametros.daon}") String nombreParametrosDaon,
            @Value("${daonengine.nombre.parametros.renaper}") String nombreParametrosRenaper,
            @Value("${daonengine.useridentifier}") String applicationUserIdentifier,
            @Value("${daonengine.domainidentifier}") String defaultDomainIdentifier,
            @Value("${daonengine.identityidentifier}") String pathIdentityIdentifier,
            @Value("${identityx.ocr.indice.tiempoespera}") int tiempoEsperaGetDni,
            @Value("${identityx.ocr.indice.intentosmaximos}") int intentosMaximosGetDni
    ) {
        this.nombreMicroservicio = nombreMicroservicio;
        this.ocrSexo = ocrSexo;
        this.ocrNumeroDocumento = ocrNumeroDocumento;
        this.ocrNombreApellidos = ocrNombreApellidos;
        this.ocrApellidos = ocrApellidos;
        this.ocrNombres = ocrNombres;
        this.ocrNumeroTramite = ocrNumeroTramite;
        this.nombreMicroServicioDatosGenerales = nombreMicroServicioDatosGenerales;
        this.identityXClient = identityXClient;
        this.esbClient = esbClient;
        this.reglaNormalizacionUtil = reglaNormalizacionUtil;
        this.datosGeneralesService = datosGeneralesService;
        this.enrolamientoServiceMapper = enrolamientoServiceMapper;
        this.dataPowerClient = dataPowerClient;
        this.daonEngineClient = daonEngineClient;
        this.traduccionHuellasDaonARenaperMapper = traduccionHuellasDaonARenaperMapper;
        this.configuracionHuellasMapper = configuracionHuellasMapper;
        this.nombreParametrosDaon = nombreParametrosDaon;
        this.nombreParametrosRenaper = nombreParametrosRenaper;
        this.applicationUserIdentifier = applicationUserIdentifier;
        this.defaultDomainIdentifier = defaultDomainIdentifier;
        this.pathIdentityIdentifier = pathIdentityIdentifier;
        this.tiempoEsperaGetDni = tiempoEsperaGetDni;
        this.intentosMaximosGetDni = intentosMaximosGetDni;
    }

    @Override
    public String obtenerNombreMicroservicio() {
        return nombreMicroservicio;
    }

    @Override
    public CrearPerfilResponse crearPerfilUsuario(CrearPerfilRequest crearPerfilRequest) {
        User usuario = new User();
        usuario.setUserId(crearPerfilRequest.getIdusuario());
        return new CrearPerfilResponse(identityXClient.crearPerfilUsuario(usuario).getId());
    }

    @Override
    public void eliminarPerfilUsuario(EliminarPerfilUsuarioRequest eliminarPerfilUsuarioRequest) {

        User usuario = identityXClient.obtenerRegistroUsuario(eliminarPerfilUsuarioRequest.getUserId());
        identityXClient.eliminarPerfilUsuario(usuario);
    }

    /**
     * Consulta identidad
     *
     * @param consultarIdentidadRequest
     * @return
     */
    @Override
    public ConsultarIdentidadResponse consultarNormalizacionIndividuos(ConsultarIdentidadRequest consultarIdentidadRequest) {
        ConsultarIdentidadResponse consultarIdentidadResponse = null;
        ConsultaIdentificacionClienteResponse consultaIdentificacionClienteResponse = dataPowerClient.consultarIdentificacionCliente(consultarIdentidadRequest);
        if(!consultaIdentificacionClienteResponse.getElements().isEmpty()){
            consultarIdentidadResponse = enrolamientoServiceMapper.mapConsultarNormalizacionIndividuos(consultaIdentificacionClienteResponse.getElements().get(0));
        } else {
            throw new InformacionNoEncontradaException(Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getCodigo(),Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getMensaje());
        }
        return consultarIdentidadResponse;
    }

    /**
     * Actualizar Rostro y Orquestacion con Obtener registro de Usuario
     *
     * @param actualizarRostroRequest
     */
    @Override
    public void actualizarRostro(ActualizarRostroRequest actualizarRostroRequest) {
        User user = identityXClient.obtenerRegistroUsuario(actualizarRostroRequest.getIdcliente());
        identityXClient.agregarImagenRostroaPerfilUsuario(user, actualizarRostroRequest.getArchivo());
    }

    /**
     * Crea un objeto request para la consulta general del cliente por Cuit
     *
     * @param numeroTributario
     * @return consultaGeneralClienteRequest
     */
    private ConsultaGeneralClienteRequest crearRequestConsultaGeneralClientePorCuit(Long numeroTributario) {
        return ConsultaGeneralClienteRequest.builder().documento(String.valueOf(numeroTributario)).build();
    }

    /**
     * consultar Identidad con Atributos dni, apellido y sexo
     *
     * @param consultaClienteAtributosRequest
     * @return
     */
    public ConsultaClienteAtributosResponse consultarClienteAtributos(ConsultaClienteAtributosRequest consultaClienteAtributosRequest) {
        ConsultaGeneralClienteRequest consultaGeneralClienteRequest = crearRequestConsultaGeneralClientePorCuit(validarRespuestaServicioNormalizacion(this.esbClient.normalizarIndividuoV2(crearRequestNormalizacionIndividuoV2(consultaClienteAtributosRequest))));
        return new ConsultaClienteAtributosResponse(validarRespuestaServicioConsultaGeneralCliente(this.esbClient.consultarCliente(consultaGeneralClienteRequest)));
    }

    public ConsultaClienteAtributosResponse consultarCliente(ConsultaGeneralClienteRequest consultaGeneralClienteRequest) {
       return new ConsultaClienteAtributosResponse(validarRespuestaServicioConsultaGeneralCliente(this.esbClient.consultarCliente(consultaGeneralClienteRequest)));
    }

    /**
     * Valida la respuesta del servicio consulta general cliente
     *
     * @param consultaGeneralClienteResponse
     * @return consultaGeneralCliente
     */
    private ConsultaGeneralCliente validarRespuestaServicioConsultaGeneralCliente(ConsultaGeneralClienteResponse consultaGeneralClienteResponse) {
        if (consultaGeneralClienteResponse == null || CollectionUtils.isEmpty(consultaGeneralClienteResponse.getConsultaGeneralClienteList())) {
            throw new InformacionNoEncontradaException(ErrorCode.CODIGO_ERROR_CONSULTA_GENERAL_CLIENTE_NO_EXISTE,
                    ErrorMessage.ERROR_CONSULTA_GENERAL_CLIENTE_NO_EXISTE.mensaje());
        }
        return consultaGeneralClienteResponse.getConsultaGeneralClienteList().get(ClientesConstantes.CERO);
    }

    /**
     * Crea el request para el servicio de normalizar individuo version 2
     *
     * @param consultaClienteAtributosRequest
     * @return normalizarIndividuoRequest
     */
    private NormalizarIndividuoRequest crearRequestNormalizacionIndividuoV2(ConsultaClienteAtributosRequest consultaClienteAtributosRequest) {
        String apellido = reglaNormalizacionUtil.normalizarNombre(consultaClienteAtributosRequest.getApellido());
        return NormalizarIndividuoRequest.builder().dni(consultaClienteAtributosRequest.getDni()).apellido(apellido)
                .sexo(consultaClienteAtributosRequest.getSexo()).build();
    }

    /**
     * Valida el resultado del servicio de normalizacion retornando el numero
     * tributario
     *
     * @param consultaNormalizacionIndividuo
     * @return numeroTributario
     */
    private Long validarRespuestaServicioNormalizacion(ConsultaNormalizacionIndividuo consultaNormalizacionIndividuo) {
        if (consultaNormalizacionIndividuo.getIndividuos().size() > ClientesConstantes.UNO) {
            return filtrarListaRespuestaNormalizacion(consultaNormalizacionIndividuo.getIndividuos());
        }
        if (consultaNormalizacionIndividuo.getIndividuos().get(ClientesConstantes.CERO).getNumeroTributario() == null) {
            throw new InformacionNoEncontradaException(ErrorCode.CODIGO_ERROR_NORMALIZACION_NUMERO_TRIBUTARIO, ErrorMessage.ERROR_NORMALIZACION_NUMERO_TRIBUTARIO.mensaje());
        }
        return consultaNormalizacionIndividuo.getIndividuos().get(ClientesConstantes.CERO).getNumeroTributario();
    }

    /**
     * Filtra la lista de respuesta del servicio de normalizacion Si encuentra el
     * tipo tributario 80 retorna su numero tributario de lo contrario retorna el
     * del primer elemento
     *
     * @param individuos
     * @return Long numeroTributario
     */
    private Long filtrarListaRespuestaNormalizacion(List<Individuo> individuos) {
        Optional<Individuo> optional = individuos.parallelStream().filter(individuo -> ClientesConstantes.OCHENTA == individuo.getTipoTributario()).findFirst();
        if (!optional.isPresent() || optional.get().getNumeroTributario() == null) {
            return individuos.get(ClientesConstantes.CERO).getNumeroTributario();
        }
        return optional.get().getNumeroTributario();
    }

    @Override
    public LeerPdf417DniResponse leerPdf417Dni(BothSidesDocumentRequest bothSidesDocumentRequest) {

        LeerPdf417DniResponse leerPdf147DniResponse = null;
        User usuario = null;
        try {
            usuario = new User();
            usuario.setUserId(IdentifierUtil.newUUIDCode());
            usuario = identityXClient.crearPerfilUsuario(usuario);
            Optional<CreateEmptyIdCheckResponse> optCreateEmptyIdCheckResponse = identityXClient.crearIdCheck(usuario);
            if (optCreateEmptyIdCheckResponse.isPresent()) {
                CreateEmptyIdCheckResponse createEmptyIdCheckResponse = optCreateEmptyIdCheckResponse.get();
                Optional<SubmitBothSidesDocumentResponse> optSubmitBothSidesDocumentResponse = identityXClient.enviarFrenteyDorsoDni(
                        usuario.getId(), createEmptyIdCheckResponse.getIdCheck(), bothSidesDocumentRequest);
                if (optSubmitBothSidesDocumentResponse.isPresent()) {
                    SubmitBothSidesDocumentResponse submitBothSidesDocumentResponse = optSubmitBothSidesDocumentResponse.get();
                    Optional<GetOcrProcessingDataResponse> optOcrProcessingDataResponse = identityXClient.obtenerDatosProcesamientoOCR(
                            usuario.getId(), createEmptyIdCheckResponse.getIdCheck(), submitBothSidesDocumentResponse.getIdDocument());
                    if (optOcrProcessingDataResponse.isPresent()) {
                        GetOcrProcessingDataResponse ocrProcessingDataResponse = optOcrProcessingDataResponse.get();
                        CodigoBarrasResponse codigoBarras = new CodigoBarrasResponse();
                        codigoBarras.setGenero(ocrProcessingDataResponse.getBarcode().get(ocrSexo).getValue());
                        codigoBarras.setApellidos(ocrProcessingDataResponse.getBarcode().get(ocrApellidos).getValue());
                        codigoBarras.setNombres(ocrProcessingDataResponse.getBarcode().get(ocrNombres).getValue());
                        codigoBarras.setNombrecompleto(ocrProcessingDataResponse.getBarcode().get(ocrNombreApellidos).getValue());
                        codigoBarras.setNumero(ocrProcessingDataResponse.getBarcode().get(ocrNumeroDocumento).getValue());
                        leerPdf147DniResponse = new LeerPdf417DniResponse();
                        leerPdf147DniResponse.setCodigobarras(codigoBarras);
                    }
                }
            }
        } finally {
            try {
                if (Objects.nonNull(usuario)) {
                    identityXClient.eliminarPerfilUsuario(usuario);
                }
            } catch (IdentityXException e) {
            }
        }

        return leerPdf147DniResponse;
    }

    @Override
    public EnrolarRostroResponse enrolarRostro(
        final EnrolarRostroRequest enrolarRostroRequest, final String xOperationID) {
     
      try {
        var usuario = identityXClient.obtenerRegistroUsuario(enrolarRostroRequest.getIdusuario());
        identityXClient.agregarImagenRostroaPerfilUsuario(usuario, enrolarRostroRequest.getSelfie());
  
        return new EnrolarRostroResponse(Constantes.RESPUESTA_ENROLAR_ROSTRO);
  
      } catch (UsuarioNoEncontradoIdentityXException e) {
        log.error(Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getMensaje(), e);
        throw new IdentityXException(
            Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getCodigo(),
            Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getMensaje());
      }
    }

    @Override
    public ConsultarDatosDniResponse consultarDatosDni(final ConsultarDatosDniRequest dniRequest) {
      var usuario = identityXClient.obtenerRegistroUsuario(dniRequest.getIdusuario());
      Optional<GetOcrProcessingDataResponse> optOCRResponse = getDatosProcesamientoOCR(dniRequest, usuario);
      var ocrDataResponse = optOCRResponse.isPresent() ? optOCRResponse.get() : null;
  
      if (Objects.nonNull(ocrDataResponse)
          && (!isNullOrEmpty(ocrDataResponse.getBarcode())
              || !isNullOrEmpty(ocrDataResponse.getMrz()))) {
  
        boolean barcodeExists = !isNullOrEmpty(ocrDataResponse.getBarcode());
  
        var codigoBarras = new CodigoBarrasResponse();
        if (barcodeExists) {
          codigoBarras.setGenero(ocrDataResponse.getBarcode().get(ocrSexo).getValue());
          codigoBarras.setApellidos(ocrDataResponse.getBarcode().get(ocrApellidos).getValue());
          codigoBarras.setNombres(ocrDataResponse.getBarcode().get(ocrNombres).getValue());
          codigoBarras.setNombrecompleto(ocrDataResponse.getBarcode().get(ocrNombreApellidos).getValue());
          codigoBarras.setNumero(validarCadenaNumeroDocumentoConsultarDatosDni(ocrDataResponse.getBarcode().get(ocrNumeroDocumento).getValue()));
          codigoBarras.setNumerotramite(getValorNumeroTramite(ocrDataResponse.getBarcode()));   
        } else {
          codigoBarras.setGenero(ocrDataResponse.getMrz().get(ocrSexo).getValue());
          codigoBarras.setApellidos(ocrDataResponse.getMrz().get(ocrApellidos).getValue());
          codigoBarras.setNombres(ocrDataResponse.getMrz().get(ocrNombres).getValue());
          codigoBarras.setNombrecompleto(ocrDataResponse.getMrz().get(ocrNombreApellidos).getValue());
          codigoBarras.setNumero(validarCadenaNumeroDocumentoConsultarDatosDni(ocrDataResponse.getMrz().get(ocrNumeroDocumento).getValue()));
        }
  
        return new ConsultarDatosDniResponse(codigoBarras);
  
      } else {
        throw new ConsultarDatosDniIdentityXException(
            ERROR_CONSULTAR_DATOS_DNI.getCodigo(), ERROR_CONSULTAR_DATOS_DNI.getMensaje());
      }
    }

    /**
     * Permite consultar los datos de procesamiento OCR desde IdentityX. Implementa mecanismo Retry
     * esperando tener respuesta exitosa desde IdentityX.
     *
     * @param consultarDatosDniRequest
     * @param usuario
     * @return
     */
    private Optional<GetOcrProcessingDataResponse> getDatosProcesamientoOCR(
        final ConsultarDatosDniRequest consultarDatosDniRequest, final User usuario) {
  
      var config =
          RetryConfig.<Optional<GetOcrProcessingDataResponse>>custom()
              .maxAttempts(intentosMaximosGetDni - 1)
              .waitDuration(Duration.of(tiempoEsperaGetDni, ChronoUnit.MILLIS))
              .retryExceptions(Exception.class)
              .retryOnResult(
                  optOcrResponse -> {
                    var ocrData = optOcrResponse.isPresent() ? optOcrResponse.get() : null;
  
                    return Objects.isNull(ocrData)
                        || (isNullOrEmpty(ocrData.getBarcode()) && isNullOrEmpty(ocrData.getMrz()));
                  })
              .build();
  
      var retry = RetryRegistry.of(config).retry("OCR", config);

      return retry.executeSupplier(
          () -> {
            Optional<GetOcrProcessingDataResponse> ocrResponse = Optional.empty();
  
            try {
              ocrResponse =
                  identityXClient.obtenerDatosProcesamientoOCR(
                      usuario.getId(),
                      consultarDatosDniRequest.getIdcheck(),
                      consultarDatosDniRequest.getIddocument());
  
            } catch (Exception e) {
              log.error(ERROR_CONSULTAR_DATOS_DNI.getMensaje(), e);
            }
            return ocrResponse;
          });
    }

    /**
     * Verifica que el map este null o se encuentre vacio.
     *
     * @param map el mapa que se requiere verificar
     * @return true indicando que el mapa es null o empty
     */
    private boolean isNullOrEmpty(final Map<?, ?> map) {
      return Objects.isNull(map) || map.isEmpty();
    }

    /**
     * Expone el metodo para realizar la consulta de enrolamiento a travez de la interfaz consume el cliente
     * de IdentityX y utiliza el face enroled del objeto User, y ordena obtener la identidad en daon y filtrar
     * la información
     *
     * @param consultarEnrolamientoRequest, xOperationID
     * @return ConsultarEnrolamientoResponse consultarEnrolamientoResponse
     */
    @Override
    public ConsultarEnrolamientoResponse consultarEnrolamiento(ConsultarEnrolamientoRequest consultarEnrolamientoRequest, String xOperationID) {
        ConsultarEnrolamientoResponse consultarEnrolamientoResponse = new ConsultarEnrolamientoResponse();
        Optional<ConsultarEnrolamientoIdentityXResponse> optionalConsultarEnrolamientoIdentityXResponse = null;
        Optional<ConsultarEnrolamientoDaonEngineResponse> optionalConsultarEnrolamientoDaonEngineResponse = null;
        if(Objects.isNull(consultarEnrolamientoRequest.getIndicador())
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.IDENTITYX)
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.AMBOS)){
            optionalConsultarEnrolamientoIdentityXResponse = consultarEnrolamientoIdentityX(consultarEnrolamientoRequest);
            if(optionalConsultarEnrolamientoIdentityXResponse.isPresent()){
                consultarEnrolamientoResponse.setConsultarEnrolamientoIdentityXResponse(optionalConsultarEnrolamientoIdentityXResponse.get());
            } else {
                throw new IdentityXException(ERROR_OBTENER_FACE_ENROLED_IDENTITYX);
            }
        }
        if(Objects.isNull(consultarEnrolamientoRequest.getIndicador())
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.DAONENGINE)
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.AMBOS)){
            optionalConsultarEnrolamientoDaonEngineResponse = consultarEnrolamientoDaonEngine(consultarEnrolamientoRequest,xOperationID);
            if(optionalConsultarEnrolamientoDaonEngineResponse.isPresent()){
                consultarEnrolamientoResponse.setConsultarEnrolamientoDaonEngineResponse(optionalConsultarEnrolamientoDaonEngineResponse.get());
            } else {
                throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(),Errores.ERROR_DATOS_GENERALES.getMensaje());
            }
        }
        return consultarEnrolamientoResponse;
    }

  /**
   * El numero de tramite puede llegar en diferente numero de campo dentro del barcode.
   *
   * @param barcode mapa que contiene los valores del barcode
   * @return el numero de tramite
   */
    private String getValorNumeroTramite(final Map<String, DocBarcodeField> barcode) {
      Optional<DocBarcodeField> docBarcodeField =
          Arrays.asList(ocrNumeroTramite.split(","))
              .stream()
              .map(String::trim)
              .filter(barcode::containsKey)
              .findFirst()
              .map(barcode::get);
  
      return docBarcodeField.isPresent() ? docBarcodeField.get().getValue() : null;
    }

    /**
     * Contiene la logica de la consulta a IdentityX
     *
     * @param consultarEnrolamientoRequest
     * @return Optional<ConsultarEnrolamientoIdentityXResponse>
     */
    private Optional<ConsultarEnrolamientoIdentityXResponse> consultarEnrolamientoIdentityX(ConsultarEnrolamientoRequest consultarEnrolamientoRequest){
        ConsultarEnrolamientoIdentityXResponse consultarEnrolamientoIdentityXResponse = new ConsultarEnrolamientoIdentityXResponse();
        User user = null;
        try {
            user = identityXClient.obtenerRegistroUsuario(consultarEnrolamientoRequest.getIdCliente());
        } catch (Exception e) {
            if(e.getClass().equals(UsuarioNoEncontradoIdentityXException.class)){
                consultarEnrolamientoIdentityXResponse.setRegistrado(0);
                consultarEnrolamientoIdentityXResponse.setEnrolado(0);
            } else {
                log.error(ERROR_OBTENER_USUARIO_IDENTITY_X.getMensaje(), e);
                throw new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X);
            }
        }
        if(Objects.nonNull(user)){
            consultarEnrolamientoIdentityXResponse.setRegistrado(1);
            if(Objects.nonNull(user.getFaceEnrolled())){
                if(user.getFaceEnrolled()){
                    consultarEnrolamientoIdentityXResponse.setEnrolado(1);
                } else {
                    consultarEnrolamientoIdentityXResponse.setEnrolado(0);
                }
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(consultarEnrolamientoIdentityXResponse);
    }

    /**
     * Contiene la logica de la consulta a DaonEngine
     *
     * @param consultarEnrolamientoRequest, xOperationID
     * @return Optional<ConsultarEnrolamientoDaonEngineResponse>
     */
    private Optional<ConsultarEnrolamientoDaonEngineResponse> consultarEnrolamientoDaonEngine(ConsultarEnrolamientoRequest consultarEnrolamientoRequest,String xOperationID){
        ConsultarEnrolamientoDaonEngineResponse consultarEnrolamientoDaonEngineResponse = new ConsultarEnrolamientoDaonEngineResponse();
        GetIdentityRequest getIdentityRequest = construirRequestDaonEngineObtenerIdentidad(consultarEnrolamientoRequest);
        Optional<GetIdentityResponse> getIdentityResponse = daonEngineClient.obtenerIdentidad(getIdentityRequest);
        if(getIdentityResponse.isPresent()){
            Optional<List<Integer>> listInteger = filtrarResponseObtenerIdentidadDaonEngine(getIdentityResponse.get());
            if(listInteger.isPresent()){
                List<Integer> integerList = new ArrayList<>();
                consultarEnrolamientoDaonEngineResponse.setRegistrado(1);
                Optional<ManosServiceDto> optionalManosServiceDtoDaon = llamarDatosGeneralesConsultarEnrolamiento(xOperationID,nombreParametrosDaon);
                Optional<ManosServiceDto> optionalManosServiceDtoRenaper = llamarDatosGeneralesConsultarEnrolamiento(xOperationID,nombreParametrosRenaper);
                if(optionalManosServiceDtoDaon.isPresent() && optionalManosServiceDtoRenaper.isPresent()){
                    integerList = traduccionHuellasDaonARenaperMapper.traducirDedosDaonARenaper(listInteger.get(),optionalManosServiceDtoDaon.get(),optionalManosServiceDtoRenaper.get());
                    consultarEnrolamientoDaonEngineResponse.setCalificadores(integerList);
                } else {
                    return Optional.empty();
                }
            } else {
                consultarEnrolamientoDaonEngineResponse.setRegistrado(1);
                consultarEnrolamientoDaonEngineResponse.setCalificadores(null);
            }
        } else {
            consultarEnrolamientoDaonEngineResponse.setRegistrado(0);
            consultarEnrolamientoDaonEngineResponse.setCalificadores(null);
        }
        return Optional.of(consultarEnrolamientoDaonEngineResponse);
    }

    /**
     * Construye el Objeto GetIdentityRequest para ser utilizado en el cliente daonengine, coloca los headers y
     * valores de identidad
     *
     * @param consultarEnrolamientoRequest
     * @return GetIdentityRequest
     */
    private GetIdentityRequest construirRequestDaonEngineObtenerIdentidad(ConsultarEnrolamientoRequest consultarEnrolamientoRequest) {
        GetIdentityRequest getIdentityRequest = obj.createGetIdentityRequest();
        DomainIdentifier domainIdentifier = obj.createDomainIdentifier();
        domainIdentifier.setValue(defaultDomainIdentifier);
        getIdentityRequest.setDomainIdentifier(domainIdentifier);
        GenericRequestParameters genericRequestParameters = obj.createGenericRequestParameters();
        genericRequestParameters.setApplicationIdentifier(MDC.get(TraceConstants.APPLICATION));
        genericRequestParameters.setApplicationUserIdentifier(applicationUserIdentifier);
        getIdentityRequest.setGenericRequestParameters(genericRequestParameters);
        IdentityIdentifier identityIdentifier = obj.createIdentityIdentifier();
        identityIdentifier.setValue(pathIdentityIdentifier.concat(consultarEnrolamientoRequest.getIdCliente()));
        getIdentityRequest.setIdentityIdentifier(identityIdentifier);
        IdentityDataFilter identityDataFilter = obj.createIdentityDataFilter();
        identityDataFilter.setBiographicDataFilter(obj.createIncludeAllBiographicDataFilter());
        identityDataFilter.setBiometricDataFilter(obj.createIncludeAllBiometricDataFilter());
        getIdentityRequest.setIdentityDataFilter(identityDataFilter);
        return getIdentityRequest;
    }

    /**
     * Filtrar la lista de enteros repetidos que contiene GetIdentityResponse
     *
     * @param getIdentityResponse
     * @return List<Integer>
     */
    private Optional<List<Integer>> filtrarResponseObtenerIdentidadDaonEngine(GetIdentityResponse getIdentityResponse) {
        List<Integer> listint = new ArrayList<>();
        if(
                Objects.isNull(getIdentityResponse.getResponseData())
                        || Objects.isNull(getIdentityResponse.getResponseData().getIdentity())
                        || Objects.isNull(getIdentityResponse.getResponseData().getIdentity().getBiometricData())
                        || Objects.isNull(getIdentityResponse.getResponseData().getIdentity().getBiometricData().isEmpty())
        ){
            return Optional.empty();
        }
        try{
            getIdentityResponse.getResponseData().getIdentity().getBiometricData().stream().forEach(i -> listint.add(i.getUsageQualifier()));
            return Optional.of(listint.stream().distinct().collect(toList()));
        } catch (NullPointerException | IllegalArgumentException e) {
            log.error(ERROR_FILTRO_BIOMETRIC_DATA.getMensaje(), e);
            throw new DaonEngineException(
                    ERROR_FILTRO_BIOMETRIC_DATA.getCodigo(),
                    ERROR_FILTRO_BIOMETRIC_DATA.getMensaje()
            );
        }
    }

    /**
     * Llamar a Datos Generales y Mappear la Respuesta
     *
     * @param xOperationID,nombreParametros
     * @return Optional<ManosServiceDto>
     */
    private Optional<ManosServiceDto> llamarDatosGeneralesConsultarEnrolamiento(String xOperationID, String nombreParametros) {
        ConfiguracionHuellasDto configuracionHuellasDto = datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID, nombreMicroServicioDatosGenerales, nombreParametros);
        if(Objects.nonNull(configuracionHuellasDto)){
            ManosServiceDto manosServiceDto = configuracionHuellasMapper.mapperEntity(configuracionHuellasDto);
            if(Objects.nonNull(manosServiceDto)){
                return Optional.of(manosServiceDto);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public String validarCadenaNumeroDocumentoConsultarDatosDni(String cd){
        if(!cd.contains("[a-zA-Z]+")){
            IntStream in = cd.chars().filter(chari -> Character.isDigit(chari));
            return in.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        } else {
            return cd;
        }
    }

}
