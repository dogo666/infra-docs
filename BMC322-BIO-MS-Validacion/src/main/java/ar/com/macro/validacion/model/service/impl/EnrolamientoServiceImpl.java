package ar.com.macro.validacion.model.service.impl;

import static ar.com.macro.commons.values.constants.format.DatePatternsConstants.FECHA_FORMATO_YYYY_MM_DD;
import static ar.com.macro.constant.Errores.ERROR_COMPARAR_HUELLAS_UNO_A_POCOS;
import static ar.com.macro.constant.Errores.ERROR_COMPARAR_HUELLAS_UNO_A_POCOS_ERROR_CODE_DAON;
import static ar.com.macro.constant.Errores.ERROR_FILTRO_BIOMETRIC_DATA;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_FACE_ENROLED_IDENTITYX;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResult.MATCH;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.daon.identityx.rest.model.pojo.User;

import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.utils.generator.IdentifierUtil;
import ar.com.macro.commons.utils.normalization.ReglaNormalizacionUtil;
import ar.com.macro.commons.values.constants.config.TraceConstants;
import ar.com.macro.commons.values.constants.error.ErrorCode;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import ar.com.macro.constant.Constantes;
import ar.com.macro.constant.Errores;
import ar.com.macro.exceptions.CompararHuellasUnoPocosException;
import ar.com.macro.exceptions.ConsultarDatosDniIdentityXException;
import ar.com.macro.exceptions.CrearHashException;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.CompararHuellasDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ValidarHuellaDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.CompararHuellasDaonEngineResponse;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ValidarHuellaDaonEngineResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.EliminarPerfilUsuarioRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoDaonEngineResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoIdentityXResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.LeerPdf417DniResponse;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.CrearHashRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.CrearHashResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ActualizarRostroRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarDatosDniRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.EnrolarRostroRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarDatosDniResponse;
import ar.com.macro.validacion.model.client.daonengine.DaonEngineClient;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.BiographicData;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.DomainIdentifier;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GenericRequestParameters;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityRequest;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityResponse;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.IdentityDataFilter;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.IdentityIdentifier;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.LongItem;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.ObjectFactory;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyRequest;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResponse;
import ar.com.macro.validacion.model.client.datapower.DataPowerClient;
import ar.com.macro.validacion.model.client.datapower.dto.rest.dto.consulta.identificacion.ConsultaIdentificacionClienteResponse;
import ar.com.macro.validacion.model.client.esb.EsbClient;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.cliente.ConsultaClienteAtributosRequest;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.cliente.ConsultaClienteAtributosResponse;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralCliente;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteRequest;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteResponse;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion.ConsultaNormalizacionIndividuo;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion.Individuo;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion.NormalizarIndividuoRequest;
import ar.com.macro.validacion.model.client.esb.rest.dto.utils.ClientesConstantes;
import ar.com.macro.validacion.model.client.identityx.IdentityXClient;
import ar.com.macro.validacion.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CodigoBarrasResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.validacion.model.service.DatosGeneralesService;
import ar.com.macro.validacion.model.service.EnrolamientoService;
import ar.com.macro.validacion.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.validacion.model.service.dto.ManosServiceDto;
import ar.com.macro.validacion.model.service.mapper.ConfiguracionHuellasMapper;
import ar.com.macro.validacion.model.service.mapper.EnrolamientoServiceMapper;
import ar.com.macro.validacion.model.service.mapper.TraduccionHuellasDaonARenaperMapper;
import ar.com.macro.validacion.model.service.parallelizer.ProcessParallelizer;
import ar.com.macro.validacion.model.service.parallelizer.ProcessParallelizer.ParallelJob;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

@RefreshScope
@Slf4j
@Service
public class EnrolamientoServiceImpl implements EnrolamientoService {

    private static final String USER_ENROLLER = "USER_ENROLLER";
    private static final String USER_ENROLLER_HUELLAS = "USER_ENROLLER_HUELLAS";
    private static final String ENROLLED_DATE = "ENROLLED_DATE";
    private static final String ENROLLED_DATE_HUELLAS = "ENROLLED_DATE_HUELLAS";
    private static final String CLIENT_RENAPER_FACE_MTCH_SCORE = "CLIENT_RENAPER_FACE_MTCH_SCORE";

    private final String ocrSexo;
    private final String ocrNumeroDocumento;
    private final String ocrNombreApellidos;
    private final String ocrApellidos;
    private final String ocrNombres;
    private final String ocrNumeroTramite;
    private final String nombreMicroservicio;
    private final EsbClient esbClient;
    private final IdentityXClient identityXClient;
    private final ReglaNormalizacionUtil reglaNormalizacionUtil;
    private final DatosGeneralesService datosGeneralesService;
    private final String nombreMicroServicioDatosGenerales;
    private final EnrolamientoServiceMapper enrolamientoServiceMapper;
    private final DataPowerClient dataPowerClient;
    private final DaonEngineClient daonEngineClient;
    private final String nombreParametrosDaon;
    private final String nombreParametrosRenaper;
    private final String applicationUserIdentifier;
    private final String defaultDomainIdentifier;
    private final String pathIdentityIdentifier;
    private final String policyidentifier;
    private final String policyidentifierValue;
    private final String applicationuseridentifier;
    private final TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper;
    private final ConfiguracionHuellasMapper configuracionHuellasMapper;
    private final ObjectFactory obj = new ObjectFactory();
    private final ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.ObjectFactory factory = 
    		new ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.ObjectFactory();
    private final String unoaPocosParalelo;

    private String entorno;

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
            @Value("${daonengine.nombre.parametros.daon}") String nombreParametrosDaon,
            @Value("${daonengine.nombre.parametros.renaper}") String nombreParametrosRenaper,
            @Value("${daonengine.useridentifier}") String applicationUserIdentifier,
            @Value("${daonengine.domainidentifier}") String defaultDomainIdentifier,
            @Value("${daonengine.identityidentifier}") String pathIdentityIdentifier,
            @Value("${daonengine.policyidentifier}") String policyidentifier,
            @Value("${daonengine.policyidentifier.value}") String policyidentifierValue,
            @Value("${daonengine.applicationuseridentifier}") String applicationuseridentifier,
            TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper,
            ConfiguracionHuellasMapper configuracionHuellasMapper,
            @Value("${api.macro.validacion.huellas.unoapocos.procesamiento.paralelo}") String unoaPocosParalelo,
            @Value("${api.macro.entorno.valor}") String entorno
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
        this.nombreParametrosDaon = nombreParametrosDaon;
        this.nombreParametrosRenaper = nombreParametrosRenaper;
        this.applicationUserIdentifier = applicationUserIdentifier;
        this.defaultDomainIdentifier = defaultDomainIdentifier;
        this.pathIdentityIdentifier = pathIdentityIdentifier;
        this.policyidentifier = policyidentifier;
        this.policyidentifierValue = policyidentifierValue;
        this.applicationuseridentifier = applicationuseridentifier;
        this.traduccionHuellasDaonARenaperMapper = traduccionHuellasDaonARenaperMapper;
        this.configuracionHuellasMapper = configuracionHuellasMapper;
        this.unoaPocosParalelo = unoaPocosParalelo;
        this.entorno = entorno;
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
                if (nonNull(usuario)) {
                    identityXClient.eliminarPerfilUsuario(usuario);
                }
            } catch (IdentityXException e) {
            }
        }

        return leerPdf147DniResponse;
    }

    @Override
    public void enrolarRostro(EnrolarRostroRequest enrolarRostroRequest, String xOperationID) {
        try {
            User usuario = identityXClient.obtenerRegistroUsuario(enrolarRostroRequest.getIdusuario());
            identityXClient.agregarImagenRostroaPerfilUsuario(usuario, enrolarRostroRequest.getSelfie());
        } catch (UsuarioNoEncontradoIdentityXException e) {
            log.error(Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getMensaje(), e);
            throw new IdentityXException(Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getCodigo(), Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getMensaje());
        }

    }

    @Override
    public ConsultarDatosDniResponse consultarDatosDni(ConsultarDatosDniRequest consultarDatosDniRequest) {
        ConsultarDatosDniResponse consultarDatosDniResponse = null;
        User usuario = identityXClient.obtenerRegistroUsuario(consultarDatosDniRequest.getIdusuario());
        Optional<GetOcrProcessingDataResponse> optOcrProcessingDataResponse = identityXClient.obtenerDatosProcesamientoOCR(
                    usuario.getId(), consultarDatosDniRequest.getIdcheck(), consultarDatosDniRequest.getIddocument());
        if (optOcrProcessingDataResponse.isPresent()) {
                GetOcrProcessingDataResponse ocrProcessingDataResponse = optOcrProcessingDataResponse.get();
                CodigoBarrasResponse codigoBarras = new CodigoBarrasResponse();
                if(!ocrProcessingDataResponse.getBarcode().isEmpty()) {
                	codigoBarras.setGenero(ocrProcessingDataResponse.getBarcode().get(ocrSexo).getValue());
                    codigoBarras.setApellidos(ocrProcessingDataResponse.getBarcode().get(ocrApellidos).getValue());
                    codigoBarras.setNombres(ocrProcessingDataResponse.getBarcode().get(ocrNombres).getValue());
                    codigoBarras.setNombrecompleto(ocrProcessingDataResponse.getBarcode().get(ocrNombreApellidos).getValue());
                    codigoBarras.setNumero(ocrProcessingDataResponse.getBarcode().get(ocrNumeroDocumento).getValue());
                    codigoBarras.setNumerotramite(ocrProcessingDataResponse.getBarcode().get(ocrNumeroTramite).getValue());
                } else {
                	codigoBarras.setGenero(ocrProcessingDataResponse.getMrz().get(ocrSexo).getValue());
                	codigoBarras.setApellidos(ocrProcessingDataResponse.getMrz().get(ocrApellidos).getValue());
                    codigoBarras.setNombres(ocrProcessingDataResponse.getMrz().get(ocrNombres).getValue());
                    codigoBarras.setNombrecompleto(ocrProcessingDataResponse.getMrz().get(ocrNombreApellidos).getValue());
                    codigoBarras.setNumero(ocrProcessingDataResponse.getMrz().get(ocrNumeroDocumento).getValue());
                }
                
                consultarDatosDniResponse = new ConsultarDatosDniResponse();
                consultarDatosDniResponse.setCodigobarras(codigoBarras);
        }
        else if(Objects.isNull(consultarDatosDniResponse))
            throw new ConsultarDatosDniIdentityXException(Errores.ERROR_CONSULTAR_DATOS_DNI.getCodigo(),Errores.ERROR_CONSULTAR_DATOS_DNI.getMensaje());

        return consultarDatosDniResponse;
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
        if (Objects.isNull(consultarEnrolamientoRequest.getIndicador())
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.IDENTITYX)
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.AMBOS)) {
            optionalConsultarEnrolamientoIdentityXResponse = consultarEnrolamientoIdentityX(
                    consultarEnrolamientoRequest);
            if (optionalConsultarEnrolamientoIdentityXResponse.isPresent()) {
                consultarEnrolamientoResponse.setConsultarEnrolamientoIdentityXResponse(
                        optionalConsultarEnrolamientoIdentityXResponse.get());
            } else {
                throw new IdentityXException(ERROR_OBTENER_FACE_ENROLED_IDENTITYX);
            }
        }

        Optional<DatosBiograficos> datosBiograficos = Optional.empty();

        if (Objects.isNull(consultarEnrolamientoRequest.getIndicador())
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.DAONENGINE)
                || consultarEnrolamientoRequest.getIndicador().equals(Constantes.AMBOS)) {

            Optional<GetIdentityResponse> identityResponse = obtenerIdentidadParaConsultarEnrolamientoDaonEngine(
                    consultarEnrolamientoRequest, xOperationID);
            optionalConsultarEnrolamientoDaonEngineResponse = consultarEnrolamientoDaonEngine(
                    consultarEnrolamientoRequest, xOperationID, identityResponse);

            if (identityResponse.isPresent()) {
                datosBiograficos = buscarDatosBiograficos(identityResponse.get());
            }

            if (optionalConsultarEnrolamientoDaonEngineResponse.isPresent()) {
                consultarEnrolamientoResponse.setConsultarEnrolamientoDaonEngineResponse(
                        optionalConsultarEnrolamientoDaonEngineResponse.get());
            } else {
                throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(),
                        Errores.ERROR_DATOS_GENERALES.getMensaje());
            }
        }

        if (nonNull(consultarEnrolamientoResponse.getConsultarEnrolamientoIdentityXResponse())
                && !datosBiograficos.isEmpty()) {
            consultarEnrolamientoResponse.getConsultarEnrolamientoIdentityXResponse()
                    .setFecha(obtenerFechaEnrolamientoRostro(datosBiograficos));
            consultarEnrolamientoResponse.getConsultarEnrolamientoIdentityXResponse()
                    .setEnrolador(datosBiograficos.get().getNombreEnroladorRostro().orElse(null));
            consultarEnrolamientoResponse.getConsultarEnrolamientoIdentityXResponse()
                    .setConfidence(datosBiograficos.get().getConfidence().orElse(null));
        }

        if (nonNull(consultarEnrolamientoResponse.getConsultarEnrolamientoDaonEngineResponse())
                && !datosBiograficos.isEmpty()) {
            consultarEnrolamientoResponse.getConsultarEnrolamientoDaonEngineResponse()
                    .setFecha(obtenerFechaEnrolamientoHuella(datosBiograficos));
            consultarEnrolamientoResponse.getConsultarEnrolamientoDaonEngineResponse()
                    .setEnrolador(datosBiograficos.get().getNombreEnroladorHuella().orElse(null));
        }
        return consultarEnrolamientoResponse;
    }

    private String obtenerFechaEnrolamientoHuella(Optional<DatosBiograficos> datosBiograficos) {
        return datosBiograficos.get().getFechaEnrolamientoHuella().isEmpty() ? null
                : datosBiograficos.get()
                        .getFechaEnrolamientoHuella().get().format(ofPattern(FECHA_FORMATO_YYYY_MM_DD));
    }

    private String obtenerFechaEnrolamientoRostro(Optional<DatosBiograficos> datosBiograficos) {
        return datosBiograficos.get().getFechaEnrolamientoRostro().isEmpty() ? null
                : datosBiograficos.get()
                        .getFechaEnrolamientoRostro().get().format(ofPattern(FECHA_FORMATO_YYYY_MM_DD));
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
            user = identityXClient.obtenerRegistroUsuario(consultarEnrolamientoRequest.getIdUsuario());
        } catch (Exception e) {
            if(e.getClass().equals(UsuarioNoEncontradoIdentityXException.class)){
                consultarEnrolamientoIdentityXResponse.setRegistrado(0);
                consultarEnrolamientoIdentityXResponse.setEnrolado(0);
            } else {
                log.error(ERROR_OBTENER_USUARIO_IDENTITY_X.getMensaje(), e);
                throw new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X);
            }
        }
        if(nonNull(user)){
            consultarEnrolamientoIdentityXResponse.setRegistrado(1);
            if(nonNull(user.getFaceEnrolled())){
                if(user.getFaceEnrolled()){
                    consultarEnrolamientoIdentityXResponse.setEnrolado(1);
                } else {
                    consultarEnrolamientoIdentityXResponse.setEnrolado(0);
                }
                var dt = new SimpleDateFormat(FECHA_FORMATO_YYYY_MM_DD);
                if(nonNull(user.getUpdated()))
                    consultarEnrolamientoIdentityXResponse.setFecha(dt.format(user.getUpdated()));

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
    private Optional<GetIdentityResponse>  obtenerIdentidadParaConsultarEnrolamientoDaonEngine(ConsultarEnrolamientoRequest consultarEnrolamientoRequest,String xOperationID) {
        GetIdentityRequest getIdentityRequest = construirRequestDaonEngineObtenerIdentidad(consultarEnrolamientoRequest);
        return daonEngineClient.obtenerIdentidad(getIdentityRequest);
    }

    private Optional<ConsultarEnrolamientoDaonEngineResponse> consultarEnrolamientoDaonEngine(ConsultarEnrolamientoRequest consultarEnrolamientoRequest,
                                                                                                  String xOperationID,Optional<GetIdentityResponse> getIdentityResponse){
        ConsultarEnrolamientoDaonEngineResponse consultarEnrolamientoDaonEngineResponse = new ConsultarEnrolamientoDaonEngineResponse();
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
        identityIdentifier.setValue(pathIdentityIdentifier.concat(consultarEnrolamientoRequest.getIdUsuario()));
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
        if(nonNull(configuracionHuellasDto)){
            ManosServiceDto manosServiceDto = configuracionHuellasMapper.mapperEntity(configuracionHuellasDto);
            if(nonNull(manosServiceDto)){
                return Optional.of(manosServiceDto);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

	@Override
	public ValidarHuellaDaonEngineResponse validarHuellaDaon(
			ValidarHuellaDaonEngineRequest validarHuellaDaonEngineRequest, String xOperationID) {
		
		List<Integer> dedos = new ArrayList<>();
		dedos.add(Integer.valueOf(validarHuellaDaonEngineRequest.getIdentificador()));
		ConfiguracionHuellasDto configuracionHuellasDtoDaon = datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosDaon);
        ConfiguracionHuellasDto configuracionHuellasDtoRenaper = datosGeneralesService.obtenerDatosGeneralesCompuestos(xOperationID, nombreMicroServicioDatosGenerales, nombreParametrosRenaper);
        if(!Objects.isNull(configuracionHuellasDtoDaon) && !Objects.isNull(configuracionHuellasDtoRenaper)) {
        	ManosServiceDto manosServiceDtoDaon = configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoDaon);
            ManosServiceDto manosServiceDtoRenaper = configuracionHuellasMapper.mapperEntity(configuracionHuellasDtoRenaper);
            
            dedos = traduccionHuellasDaonARenaperMapper.traducirDedosRenaperADaon(
            		dedos,
            		manosServiceDtoDaon,
            		manosServiceDtoRenaper
            		);
        }
		
		VerifyRequest verifyRequest = factory.createVerifyRequest();
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.GenericRequestParameters grp = factory.createGenericRequestParameters();
		grp.setApplicationIdentifier(MDC.get(TraceConstants.APPLICATION));
		grp.setApplicationUserIdentifier(applicationuseridentifier);
		
		verifyRequest.setGenericRequestParameters(grp);
		
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.DomainIdentifier domainIdentifier = factory.createDomainIdentifier();
		domainIdentifier.setValue(defaultDomainIdentifier);
		verifyRequest.setDomainIdentifier(domainIdentifier);
		
		String verificationPolicyIdentifierValue = policyidentifier + policyidentifierValue;
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.PolicyIdentifier verificationPolicyIdentifier = factory.createPolicyIdentifier();
		verificationPolicyIdentifier.setValue(verificationPolicyIdentifierValue);
		verifyRequest.setVerificationPolicyIdentifier(verificationPolicyIdentifier);

		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.BiometricDataElement.Data data = factory.createBiometricDataElementData();
		data.setValue(validarHuellaDaonEngineRequest.getHuella());
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.BiometricDataElement biometricDataElement = factory.createBiometricDataElement();
		biometricDataElement.setTypeQualifier(Constantes.DAON_VERIFY_TYPE_QUALIFIER);
		biometricDataElement.setUsageQualifier(dedos.get(0));
		biometricDataElement.setData(data);
		
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.Identity claimedIdentity = factory.createIdentity();
        claimedIdentity.getBiometricData().add(biometricDataElement);
		verifyRequest.setClaimedIdentity(claimedIdentity);
		
		String identityIdentifierValue = pathIdentityIdentifier + validarHuellaDaonEngineRequest.getIdusuario();
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.IdentityIdentifier identityIdentifier = factory.createIdentityIdentifier();
		identityIdentifier.setValue(identityIdentifierValue);
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.Identity referenceIdentity = factory.createIdentity();
		referenceIdentity.setIdentityIdentifier(identityIdentifier);		
		verifyRequest.setReferenceIdentity(referenceIdentity);
		
		Optional<VerifyResponse> optVerifyResponse = daonEngineClient.verificarIdentificacion(verifyRequest);
		
		ValidarHuellaDaonEngineResponse response = new ValidarHuellaDaonEngineResponse();
		response.setIdrequesttracking(null);
		response.setResultado(null);
		response.setScore(null);
		if(optVerifyResponse.isPresent()) {
			VerifyResponse verifyResponse = optVerifyResponse.get();
			if(verifyResponse.getResponseStatus().getReturnCode() == 0) {
				response.setIdrequesttracking(verifyResponse.getRequestTrackingUID());
				response.setResultado(verifyResponse.getResponseData().getVerifyResult().value());
				response.setScore(verifyResponse.getResponseData().getScore());
			}
			response.setCodigo(String.valueOf(verifyResponse.getResponseStatus().getReturnCode()));
			response.setMensaje(verifyResponse.getResponseStatus().getMessage());
			
		}
		
		return response;
	}

    @Override
    public Optional<CrearHashResponse> crearHashCon256(CrearHashRequest crearHashRequest) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte hashBytes[] = messageDigest.digest(crearHashRequest.getIdentificador().getBytes(StandardCharsets.UTF_8));
            BigInteger noHash = new BigInteger(1, hashBytes);
            return Optional.of(new CrearHashResponse(noHash.toString(16)));
        } catch (NoSuchAlgorithmException e){
            log.error(Errores.ERROR_CREAR_SHA256.getMensaje(), e);
            throw new CrearHashException(Errores.ERROR_CREAR_SHA256.getCodigo(), Errores.ERROR_CREAR_SHA256.getMensaje());
        }
    }

    /**
     * Busqueda de datos biograficos en GetIdentityResponse
     *
     * @param getIdentityResponse
     * @return Static ENROLAMIENTO
     */
    private Optional<DatosBiograficos> buscarDatosBiograficos(GetIdentityResponse getIdentityResponse) {

        var identity = getIdentityResponse.getResponseData().getIdentity();

        if (nonNull(identity) && ofNullable(identity.getBiographicData()).isPresent()) {
            final var datosBiograficos = new DatosBiograficos();
            try {
                ofNullable(identity.getBiographicData())
                        .orElse(new BiographicData())
                        .getStringItem()
                        .stream()
                        .filter(i -> USER_ENROLLER.equals(i.getName()) || USER_ENROLLER_HUELLAS.equals(i.getName()))
                        .forEach(item -> {
                            switch (item.getName()) {
                                case USER_ENROLLER:
                                    datosBiograficos.setNombreEnroladorRostro(
                                        Optional.of(item.getValue()));
                                    break;
                                case USER_ENROLLER_HUELLAS:
                                    datosBiograficos.setNombreEnroladorHuella(
                                        Optional.of(item.getValue()));                                
                                    break;
                            }
                        });

                ofNullable(identity.getBiographicData())
                        .orElse(new BiographicData())
                        .getTimestampItem()
                        .stream()
                        .filter(i -> ENROLLED_DATE.equals(i.getName()) || ENROLLED_DATE_HUELLAS.equals(i.getName()))
                        .forEach(item -> {
                            var value = item.getValue();
                            switch (item.getName()) {
                                case ENROLLED_DATE:
                                    datosBiograficos.setFechaEnrolamientoRostro(
                                        Optional.of(LocalDate.of(value.getYear(), value.getMonth(), value.getDay())));
                                    break;
                                case ENROLLED_DATE_HUELLAS:
                                    datosBiograficos.setFechaEnrolamientoHuella(
                                        Optional.of(LocalDate.of(value.getYear(), value.getMonth(), value.getDay())));                                
                                    break;
                            }
                        });

                var cf = ofNullable(getIdentityResponse
                        .getResponseData()
                        .getIdentity()
                        .getBiographicData())
                        .orElse(new BiographicData())
                        .getLongItem()
                        .stream()
                        .filter(i -> CLIENT_RENAPER_FACE_MTCH_SCORE.equals(i.getName()));

                if (nonNull(cf)) {
                    datosBiograficos.setConfidence(ofNullable(cf.findFirst().orElse(new LongItem()).getValue()));
                }

                if (!Collections.isEmpty(identity.getBiometricData())) {
                    if(!datosBiograficos.getNombreEnroladorHuella().isPresent()) {
                        datosBiograficos.setNombreEnroladorHuella(datosBiograficos.getNombreEnroladorRostro());
                    }

                    if(!datosBiograficos.getFechaEnrolamientoHuella().isPresent()) {
                        datosBiograficos.setFechaEnrolamientoHuella(datosBiograficos.getFechaEnrolamientoRostro());
                    }
                }

            } catch (NullPointerException | IllegalArgumentException e) {
                log.error(ERROR_FILTRO_BIOMETRIC_DATA.getMensaje(), e);
                throw new DaonEngineException(
                        ERROR_FILTRO_BIOMETRIC_DATA.getCodigo(),
                        ERROR_FILTRO_BIOMETRIC_DATA.getMensaje());
            }
            return Optional.of(datosBiograficos);
        }
        return Optional.empty();
    }

    /**
     * Servicio que valida las huellas capturadas para un enrolamiento particular. El servicio recibe
     * la lista de huellas a comparar (máximo 10 huellas), arma la combinatoria de comparaciones
     * (máximo 9 comparaciones), e invoca el servicio de DAON para todas las combinaciones. A traves
     * de un parametro de configuración general, se puede controlar la validación de cada combinatoria
     * de manera secuencial o paralela.
     */
    @Override
    public CompararHuellasDaonEngineResponse validarHuellasUnoaPocosDaon(
        final CompararHuellasDaonEngineRequest request, final String xOperationID) {

      ExecutorService executorService = null;
      
      try {
        executorService =
            Boolean.parseBoolean(unoaPocosParalelo)
                ? Executors.newFixedThreadPool(10)
                : Executors.newSingleThreadExecutor();

        var renaperToDaonMap = getFingerRenaperToDaonMap(xOperationID);
        var verifyRequestList = new ArrayList<ParallelJob>();

        var identityIdentifier = factory.createIdentityIdentifier();
        identityIdentifier.setValue(pathIdentityIdentifier + request.getIdusuario());
        
        // Crea las diferentes combinaciones de uno a pocos, siendo como maximo 9 combinaciones
        for (int i = 0; i < request.getHuellas().size() - 1; i++) {
          var combination = new StringBuilder();
          
          var verifyRequest = factory.createVerifyRequest();
          var grp = factory.createGenericRequestParameters();
          grp.setApplicationIdentifier(MDC.get(TraceConstants.APPLICATION));
          grp.setApplicationUserIdentifier(applicationuseridentifier);
          verifyRequest.setGenericRequestParameters(grp);
  
          var domainIdentifier = factory.createDomainIdentifier();
          domainIdentifier.setValue(defaultDomainIdentifier);
          verifyRequest.setDomainIdentifier(domainIdentifier);
  
          var verificationPolicyIdentifier = factory.createPolicyIdentifier();
          verificationPolicyIdentifier.setValue(policyidentifier + policyidentifierValue);
          verifyRequest.setVerificationPolicyIdentifier(verificationPolicyIdentifier);

          var claimedFingerprint = request.getHuellas().get(i);
          combination.append(String.format("Huella para comparar: %d, ", claimedFingerprint.getIdentificador()));
          
          // ClaimedIdentity
          var data = factory.createBiometricDataElementData();
          data.setValue(claimedFingerprint.getHuella());
          
          var biometricDataElement = factory.createBiometricDataElement();
          biometricDataElement.setTypeQualifier(Constantes.DAON_VERIFY_TYPE_QUALIFIER);
          biometricDataElement.setUsageQualifier(1); // Siempre 1 para la huella que viaja en el claimed
          biometricDataElement.setData(data);
          
          var claimedIdentity = factory.createIdentity();
          claimedIdentity.setIdentityIdentifier(identityIdentifier);
          claimedIdentity.getBiometricData().add(biometricDataElement);
          verifyRequest.setClaimedIdentity(claimedIdentity);
         
          var referenceIdentity = factory.createIdentity();
          verifyRequest.setReferenceIdentity(referenceIdentity);
  
          // ReferenceIdentity
          combination.append("huellas objetivo: ");
          for (int j = i + 1; j < request.getHuellas().size(); j++) {
            var referenceFingerpint = request.getHuellas().get(j);
  
            combination.append(String.format("%d,", referenceFingerpint.getIdentificador()));
            
            data = factory.createBiometricDataElementData();
            data.setValue(referenceFingerpint.getHuella());
            biometricDataElement = factory.createBiometricDataElement();
            biometricDataElement.setTypeQualifier(Constantes.DAON_VERIFY_TYPE_QUALIFIER);
            biometricDataElement.setUsageQualifier(renaperToDaonMap.get(referenceFingerpint.getIdentificador()));
            biometricDataElement.setData(data);
  
            referenceIdentity.getBiometricData().add(biometricDataElement);
          }

          log.info("Combinación {} = [{}]", (i + 1), combination.toString());

          // Se crea el ParallelJob para cada combinación
          verifyRequestList.add(
              ParallelJob.of(() -> daonEngineClient.verificarIdentificacion(verifyRequest)));
        }

        Predicate<Optional<VerifyResponse>> matchCondition =
            vr -> vr.isPresent() && vr.get().getResponseData().getVerifyResult() == MATCH;

        var resultList =
            new ProcessParallelizer<Optional<VerifyResponse>>()
                .callAndJoin(verifyRequestList, executorService, matchCondition);
  
        verifyAnyError(resultList);
  
        // Busca por algun resultado de MATCH
        var anyMatch = resultList.stream().filter(matchCondition).findFirst();
        return CompararHuellasDaonEngineResponse.of(anyMatch.isEmpty() ? 0 : 1);

      } catch (MacroException e) {
        throw e;
      } catch (Exception e) {
        if (e.getCause() instanceof MacroException) {
          throw (MacroException) e.getCause();
        }
        throw new CompararHuellasUnoPocosException(
            ERROR_COMPARAR_HUELLAS_UNO_A_POCOS.getCodigo(),
            ERROR_COMPARAR_HUELLAS_UNO_A_POCOS.getMensaje());
        
      } finally {
        if (nonNull(executorService)) {
          executorService.shutdown();
        }
      }
    }

    /**
     * Método encargado de verificar las respuestas recibidas desde DAON en busqueda de alguna con
     * código de error (returnCode != 0)
     *
     * @param resultList lista de respuestas recibidas desde DAON.
     */
    private void verifyAnyError(final List<Optional<VerifyResponse>> resultList) {
      var errorList =
          resultList
              .stream()
              .filter(vr -> vr.isPresent() && vr.get().getResponseStatus().getReturnCode() != 0)
              .collect(Collectors.toList());
  
      if (!errorList.isEmpty()) {
        var errorMsg =
            errorList
                .stream()
                .map(e -> e.get().getResponseStatus().getMessage())
                .distinct()
                .collect(Collectors.joining("|"));

        // Cuando solo hay un error el codigo de error llega hasta front, caso contrario va
        // un codigo de error generico.
        var errorCode =
            errorMsg.split("\\|").length == 1
                ? errorList.get(0).get().getResponseStatus().getReturnCode()
                : ERROR_COMPARAR_HUELLAS_UNO_A_POCOS_ERROR_CODE_DAON.getCodigo();
  
        // Genera exceción con información para front
        throw new DaonEngineException(errorCode, errorMsg);
      }
    }

    /**
     * Retorna un mapa que permite obtener el identificador del dedo en Daon. El key es el
     * identificador del dedo en Renaper, el valor es el identificador del dedo en Daon
     *
     * @param xOperationID
     * @return
     */
    private Map<Integer, Integer> getFingerRenaperToDaonMap(final String xOperationID) {

      // Configuración Huellas en DAON
      var confHuellaDaon =
          datosGeneralesService.obtenerDatosGeneralesCompuestos(
              xOperationID, this.nombreMicroServicioDatosGenerales, this.nombreParametrosDaon);
  
      // Configuración Huellas en RENAPER
      var confHuellasRenaper =
          datosGeneralesService.obtenerDatosGeneralesCompuestos(
              xOperationID, this.nombreMicroServicioDatosGenerales, this.nombreParametrosRenaper);
  
      Map<Integer, Integer> map = new HashMap<>();
  
      var manoDerechaRenaper = confHuellasRenaper.getManoDerecha();
      var manoDerechaDaon = confHuellaDaon.getManoDerecha();
      map.put(manoDerechaRenaper.getPulgar(), manoDerechaDaon.getPulgar());
      map.put(manoDerechaRenaper.getIndice(), manoDerechaDaon.getIndice());
      map.put(manoDerechaRenaper.getMayor(), manoDerechaDaon.getMayor());
      map.put(manoDerechaRenaper.getAnular(), manoDerechaDaon.getAnular());
      map.put(manoDerechaRenaper.getMenique(), manoDerechaDaon.getMenique());
  
      var manoIzquierdaRenaper = confHuellasRenaper.getManoIzquierda();
      var manoIzquierdaDaon = confHuellaDaon.getManoIzquierda();
      map.put(manoIzquierdaRenaper.getPulgar(), manoIzquierdaDaon.getPulgar());
      map.put(manoIzquierdaRenaper.getIndice(), manoIzquierdaDaon.getIndice());
      map.put(manoIzquierdaRenaper.getMayor(), manoIzquierdaDaon.getMayor());
      map.put(manoIzquierdaRenaper.getAnular(), manoIzquierdaDaon.getAnular());
      map.put(manoIzquierdaRenaper.getMenique(), manoIzquierdaDaon.getMenique());
  
      return map;
    }
}
