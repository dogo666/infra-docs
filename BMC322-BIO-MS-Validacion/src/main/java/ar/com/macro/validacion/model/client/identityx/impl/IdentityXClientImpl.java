package ar.com.macro.validacion.model.client.identityx.impl;

import static ar.com.macro.constant.Errores.ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_CREAR_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_ELIMINAR_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE;
import static ar.com.macro.constant.Errores.ERROR_FIDO_CREAR_AUTENTICACION;
import static ar.com.macro.constant.Errores.ERROR_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES;
import static ar.com.macro.constant.Errores.ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE;
import static ar.com.macro.constant.Errores.ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY;
import static ar.com.macro.constant.Errores.ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_SPONSORSHIPTOKEN;
import static ar.com.macro.constant.Errores.ERROR_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO;
import static ar.com.macro.constant.Errores.ERROR_FIDO_GUARDANDO_AUTENTICACION_REQUEST;
import static ar.com.macro.constant.Errores.ERROR_FIDO_GUARDANDO_REGISTRATION_CHALLENGE;
import static ar.com.macro.constant.Errores.ERROR_FIDO_NO_AUTENTICACION_REQUEST;
import static ar.com.macro.constant.Errores.ERROR_FIDO_USER_BLOQUEADO;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_POLITICA_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_REGISTRACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_CONSULTANDO_IMAGENES_DNI;
import static java.util.Objects.nonNull;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import com.daon.identityx.rest.model.def.ApplicationStatusEnum;
import com.daon.identityx.rest.model.def.AuthenticatorStatusEnum;
import com.daon.identityx.rest.model.def.PolicyStatusEnum;
import com.daon.identityx.rest.model.def.RegistrationStatusEnum;
import com.daon.identityx.rest.model.def.UserStatusEnum;
import com.daon.identityx.rest.model.pojo.Application;
import com.daon.identityx.rest.model.pojo.AuthenticationAttempt;
import com.daon.identityx.rest.model.pojo.AuthenticationAttemptItem;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.Authenticator;
import com.daon.identityx.rest.model.pojo.AuthenticatorType;
import com.daon.identityx.rest.model.pojo.DataSample;
import com.daon.identityx.rest.model.pojo.DataSampleFormatEnum;
import com.daon.identityx.rest.model.pojo.DataSampleTypeEnum;
import com.daon.identityx.rest.model.pojo.Enrollment;
import com.daon.identityx.rest.model.pojo.Policy;
import com.daon.identityx.rest.model.pojo.Registration;
import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import com.daon.identityx.rest.model.pojo.User;
import com.daon.identityx.rest.model.support.DataHolder;
import com.identityx.clientSDK.TenantRepoFactory;
import com.identityx.clientSDK.collections.ApplicationCollection;
import com.identityx.clientSDK.collections.AuthenticatorCollection;
import com.identityx.clientSDK.collections.DataSampleCollection;
import com.identityx.clientSDK.collections.EnrollmentCollection;
import com.identityx.clientSDK.collections.PolicyCollection;
import com.identityx.clientSDK.collections.RegistrationCollection;
import com.identityx.clientSDK.collections.UserCollection;
import com.identityx.clientSDK.exceptions.IdxRestException;
import com.identityx.clientSDK.queryHolders.ApplicationQueryHolder;
import com.identityx.clientSDK.queryHolders.AuthenticatorQueryHolder;
import com.identityx.clientSDK.queryHolders.PolicyQueryHolder;
import com.identityx.clientSDK.queryHolders.RegistrationQueryHolder;
import com.identityx.clientSDK.queryHolders.UserQueryHolder;
import com.identityx.clientSDK.repositories.AuthenticationRequestRepository;
import com.identityx.clientSDK.repositories.AuthenticatorRepository;
import com.identityx.clientSDK.repositories.AuthenticatorTypeRepository;
import com.identityx.clientSDK.repositories.RegistrationChallengeRepository;
import com.identityx.clientSDK.repositories.RegistrationRepository;
import com.identityx.clientSDK.repositories.UserRepository;
import ar.com.macro.commons.values.constants.config.TraceConstants;
import ar.com.macro.commons.values.constants.format.DatePatternsConstants;
import ar.com.macro.commons.values.constants.text.CharConstants;
import ar.com.macro.constant.Constantes;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.RegistracionNoEncontradoIdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import ar.com.macro.validacion.model.client.identityx.DigitalOnboardingServices;
import ar.com.macro.validacion.model.client.identityx.IdentityXClient;
import ar.com.macro.validacion.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.ClientDocumentCapture;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Create3DFLVideoChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DniImageData;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DocumentMultiImageData;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DocumentSensitiveImageData;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DocumentCollection;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DocumentCollection.Item;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetFacesResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetIdChecksResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetImagenResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetSensitiveDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.LivenessChallenge;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Submit3DFLVideoRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Submit3DFLVideoResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.VideoChallenge;
import ar.com.macro.validacion.model.client.identityx.rest.dto.VideoData;
import lombok.extern.slf4j.Slf4j;

@RefreshScope
@Slf4j
@Service
public class IdentityXClientImpl implements IdentityXClient {
	
	public static final String FECHA_HORA_FORMATO_YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePatternsConstants.PATRON_FECHA_ACTUAL_COMPLETO);

	private Integer ERROR_CODE_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY = 10;
	private Integer ERROR_CODE_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO = 681;
	private Integer ERROR_CODE_FIDO_CREAR_REGISTRATION_CHALLENGE_SPONSORSHIPTOKENO = 675;
	private Integer ERROR_CODE_FIDO_USER_BLOQUEADO = 91;
	private Integer ERROR_CODE_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES = 272;
	private Integer ERROR_CODE_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES_1 = 1357;
	private Integer ERROR_CODE_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES_2 = 92;

	private final TenantRepoFactory tenantRepoFactory;
	private final DigitalOnboardingServices digitalOnboardingServices;

	private final String tipoImagenFrente;
	private final String tipoImagenDorso;
	private final String formatoCapturaTarjeta;
    
    private final String video3DFLSubtipo;
	private final String video3DFLFormato;
	private final Integer video3DFLChallengeInicio;
	private final Integer video3DFLChallengeFin;

	private List<String> aplicacionesConValidacionJpg;

	private final Map<String, AuthenticatorType> authenticatorTypesCache = new Hashtable<>();
	
	@Value("${api.macro.validacion.ix.persona.consultar.idchecks.rango.minutos}")
	private int idchecksWithinRange;

	public IdentityXClientImpl(
			TenantRepoFactory tenantRepoFactory,
			DigitalOnboardingServices digitalOnboardingServices,
			@Value("${identityx.imagen.frente}") String tipoImagenFrente,
			@Value("${identityx.imagen.dorso}") String tipoImagenDorso,
			@Value("${identityx.formato.captura.tarjeta}") String formatoCapturaTarjeta,
			@Value("${identityx.3dfl.video.subtipo}") String video3DFLSubtipo,
			@Value("${identityx.3dfl.video.formato}") String video3DFLFormato,
			@Value("${identityx.3dfl.video.challenge.inicio}") Integer video3DFLChallengeInicio,
			@Value("${identityx.3dfl.video.challenge.fin}") Integer video3DFLChallengeFin,
			@Value("#{'${biometria.lista.aplicaciones.validacion.rostro.jpg:}'.split(',')}") List<String> aplicacionesConValidacionJpg
			) {
		this.tenantRepoFactory = tenantRepoFactory;
		this.digitalOnboardingServices = digitalOnboardingServices;
		this.tipoImagenFrente = tipoImagenFrente;
		this.tipoImagenDorso = tipoImagenDorso;
		this.formatoCapturaTarjeta = formatoCapturaTarjeta;
		this.video3DFLSubtipo = video3DFLSubtipo;
		this.video3DFLFormato = video3DFLFormato;
		this.video3DFLChallengeInicio = video3DFLChallengeInicio;
		this.video3DFLChallengeFin = video3DFLChallengeFin;
		this.aplicacionesConValidacionJpg = aplicacionesConValidacionJpg;
	}
	
	@Override
	public User crearPerfilUsuario(User usuario) {
    	try {
    		usuario = tenantRepoFactory.getUserRepo().create(usuario);
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_CREAR_USUARIO_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_CREAR_USUARIO_IDENTITY_X.getCodigo(), ERROR_CREAR_USUARIO_IDENTITY_X.getMensaje());
		}

		return usuario;
	}

	@Override
	public User obtenerRegistroUsuario(String idUsuario) {
		User usuario = null;

		try {
			UserQueryHolder qh = new UserQueryHolder();
			qh.getSearchSpec().setUserId(idUsuario);
			qh.getPageSpec().setLimit(1);
			qh.getSearchSpec().setStatus(UserStatusEnum.ACTIVE);
			qh.getSortSpec().setSortAscending(false);
			qh.getSortSpec().setSortField("created");
			UserCollection userCollection = tenantRepoFactory.getUserRepo().list(qh);
			if(userCollection.getItems().length == 0) {
				throw new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X);
			}
			usuario = userCollection.getItems()[0];
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_OBTENER_USUARIO_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X);


		}
		return usuario;
	}

	private void validarErroresFIDO(IdxRestException e){

		log.error(e.getDeveloperMessage(), e);

		if (e.getCode() == ERROR_CODE_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY) {
			log.error(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY);
		}else if (e.getCode() == ERROR_CODE_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO){
			log.error(ERROR_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO);
		}else if (e.getCode() == ERROR_CODE_FIDO_CREAR_REGISTRATION_CHALLENGE_SPONSORSHIPTOKENO){
			log.error(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_SPONSORSHIPTOKEN.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_SPONSORSHIPTOKEN);
		}else if (e.getCode() == ERROR_CODE_FIDO_USER_BLOQUEADO){
			log.error(ERROR_FIDO_USER_BLOQUEADO.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_USER_BLOQUEADO);
		}else if (e.getCode() == ERROR_CODE_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES){
			log.error(ERROR_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES);
		}else if (e.getCode() == ERROR_CODE_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES_1){
			log.error(e.getDeveloperMessage(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES);
		}else if (e.getCode() == ERROR_CODE_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES_2){
			log.error(e.getDeveloperMessage(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_AUTENTICACION_SIN_AUTENTICADORES);
		}

	}

	@Override
	public void eliminarPerfilUsuario(User usuario) {

		try{
			tenantRepoFactory.getUserRepo().delete(usuario);
			
		} catch (IdxRestException e) {

			mostrarInformacionIdxRestException(e);
			log.error(ERROR_ELIMINAR_USUARIO_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_ELIMINAR_USUARIO_IDENTITY_X.getCodigo(), ERROR_ELIMINAR_USUARIO_IDENTITY_X.getMensaje());
		}
	}

	private void mostrarInformacionIdxRestException(IdxRestException e) {
		log.error("Identity-X Error - status: {}, mensaje: {}, mensaje técnico: {}, codigo: {}",
				e.getHttpStatus(), e.getMessage(),StringUtils.isNotBlank(e.getDeveloperMessage()) ? e.getDeveloperMessage() : StringUtils.EMPTY,e.getCode());
	}

	public DataSampleCollection agregarImagenRostroaPerfilUsuario(User user, String archivo){
		DataSampleCollection dataSampleCollection = null;
		try {
			DataSample enrollmentFaceDataSample = new DataSample();
			enrollmentFaceDataSample.setType(DataSampleTypeEnum.Face);
			enrollmentFaceDataSample.setFormat(DataSampleFormatEnum.JPG);
			enrollmentFaceDataSample.setData(new DataHolder());
			enrollmentFaceDataSample.getData().setValue(getBytesFromBase64(archivo));
			dataSampleCollection = tenantRepoFactory.getUserRepo().addData(user, enrollmentFaceDataSample);
			validarDataSampleCollection(dataSampleCollection);
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getMensaje(),e);
			throw new IdentityXException(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X);
		}
		return dataSampleCollection;
	}

	private byte[] getBytesFromBase64(String archivoBase64) {
		return Base64.getDecoder().decode(new String(archivoBase64).getBytes(StandardCharsets.UTF_8));
	}

	private void validarDataSampleCollection(DataSampleCollection dataSampleCollection){

		DataSample dataSample = Stream.of(dataSampleCollection.getItems()).findFirst()
				.orElseThrow(()-> new IdentityXException(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X));

		if(nonNull(dataSample.getUsable()) && dataSample.getUsable() == false){
			log.error(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getMensaje() + " {} " , dataSample);
			throw new IdentityXException(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X);
		}

		if(nonNull(dataSample.getEvaluations()) && dataSample.getEvaluations().length > 0 && dataSample.getEvaluations()[0].getResultCode() != 0) {
			log.error(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getMensaje() + " {} " , dataSample);
			throw new IdentityXException(ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X);
		}
	}

	@Override
	public Optional<CreateEmptyIdCheckResponse> crearIdCheck(User usuario) {
		String referenceId = usuario.getUserId() + CharConstants.CARACTER_GUION + LocalDateTime.now().format(formatter);
    	CreateEmptyIdCheckRequest idCheckRequest = new CreateEmptyIdCheckRequest(referenceId);
    	return digitalOnboardingServices.ejecutarCrearIdCheck(usuario.getId(), idCheckRequest);
	}

	@Override
	public Optional<CreateEmptyIdCheckResponse> crearIdCheck(User usuario, String referenceId) {
		CreateEmptyIdCheckRequest idCheckRequest = new CreateEmptyIdCheckRequest(referenceId);
		return digitalOnboardingServices.ejecutarCrearIdCheck(usuario.getId(), idCheckRequest);
	}

	@Override
	public Optional<CreateEmptyIdCheckResponse> crearIdCheckByUserAndLivenessPolicy(User usuario, String livenessPolicyName, String referenceId) {
		CreateEmptyIdCheckRequest idCheckRequest = new CreateEmptyIdCheckRequest(referenceId);
		return digitalOnboardingServices.ejecutarCrearIdCheckByUserAndLivenessPolic(usuario.getId(), livenessPolicyName, idCheckRequest);
	}

	@Override
	public Optional<GetIdChecksResponse> getIdCheckByUser(User usuario) {
		return digitalOnboardingServices.ejecutarGetIdCheckByUser(usuario.getId());
	}

	@Override
	public Optional<GetFacesResponse> getFaces(User usuario,  String idCheck) {
		return digitalOnboardingServices.ejecutarGetFaces(usuario.getId(), idCheck);
	}

	@Override
	public Optional<GetImagenResponse> getImagen(User usuario,  String idCheck, String faceId) {
		return digitalOnboardingServices.ejecutarGetImagen(usuario.getId(), idCheck, faceId);
	}

	@Override
	public Optional<SubmitBothSidesDocumentResponse> enviarFrenteyDorsoDni(String id, String idCheck,
			BothSidesDocumentRequest bothSidesDocumentRequest) {
		
		SubmitBothSidesDocumentRequest submitBothSidesDocumentRequest = new SubmitBothSidesDocumentRequest();
    	submitBothSidesDocumentRequest.setCaptured(System.currentTimeMillis());
    	submitBothSidesDocumentRequest.setCaptureFormat(formatoCapturaTarjeta);
    	
    	DocumentSensitiveImageData sensitiveDataFrente = new DocumentSensitiveImageData();
    	sensitiveDataFrente.setImageFormat(Constantes.FORMATO_IMAGEN_JPG);
    	sensitiveDataFrente.setValue(bothSidesDocumentRequest.getFrente());
    	
    	DocumentSensitiveImageData sensitiveDataDorso = new DocumentSensitiveImageData();
    	sensitiveDataDorso.setImageFormat(Constantes.FORMATO_IMAGEN_JPG);
    	sensitiveDataDorso.setValue(bothSidesDocumentRequest.getDorso());
    	
    	DocumentMultiImageData imagenFrente = new DocumentMultiImageData();
    	imagenFrente.setSensitiveData(sensitiveDataFrente);
    	imagenFrente.setType(tipoImagenFrente);
    	
    	DocumentMultiImageData imagenDorso = new DocumentMultiImageData();
    	imagenDorso.setSensitiveData(sensitiveDataDorso);
    	imagenDorso.setType(tipoImagenDorso);

		var images = new DocumentMultiImageData[] {imagenFrente, imagenDorso};
		ClientDocumentCapture clientCapture = new ClientDocumentCapture(images);
    	submitBothSidesDocumentRequest.setClientCapture(clientCapture);
    	return digitalOnboardingServices.ejecutarEnviarDocumento(id, idCheck, submitBothSidesDocumentRequest);
	}

	@Override
	public Optional<GetOcrProcessingDataResponse> obtenerDatosProcesamientoOCR(String id, String idCheck, String idDocument) {
		return digitalOnboardingServices.ejecutarObtenerDatosOCR(id, idCheck, idDocument);
	}

	@Override
	public Optional<Application> obtenerRegistroAplicacion(String applicationId) {
		
		ApplicationQueryHolder queryHolder = new ApplicationQueryHolder();
        queryHolder.getSearchSpec().setApplicationId(applicationId);
        queryHolder.getSearchSpec().setStatus(ApplicationStatusEnum.ACTIVE);

        Optional<Application> application = Optional.empty();
        ApplicationCollection collection;
		try {
			collection = tenantRepoFactory.getApplicationRepo().list(queryHolder);
			application = Optional.of(validarAplicacionObtenida(collection));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_OBTENER_APLICACION_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_OBTENER_APLICACION_IDENTITY_X);
		}
        return application;
	}
	
	private Application validarAplicacionObtenida(ApplicationCollection collection) {
		Application application = null;
		if ((collection != null) && (collection.getItems() != null) && (collection.getItems().length > 0)) {
            application = collection.getItems()[0];
        } else {
        	log.error(ERROR_OBTENER_APLICACION_IDENTITY_X.getMensaje());
			throw new IdentityXException(ERROR_OBTENER_APLICACION_IDENTITY_X);
        }
		return application;
	}

	@Override
	public Optional<Policy> obtenerRegistroPolitica(String policyId, String applicationHref) {
		
		PolicyQueryHolder query = new PolicyQueryHolder();
        query.getSearchSpec().setPolicyId(policyId);
        query.getSearchSpec().setStatus(PolicyStatusEnum.ACTIVE);
        PolicyCollection coll;
        Optional<Policy> policy = Optional.empty();
		try {
			coll = tenantRepoFactory.getPolicyRepo().list(query);
			policy = Optional.of(validarPoliticaObtenida(coll, applicationHref));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_OBTENER_POLITICA_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X);
		}
		return policy;
	}
	
	private Policy validarPoliticaObtenida(PolicyCollection coll, String applicationHref) {
		Policy politica = null;
		if (Objects.nonNull(coll) && Objects.nonNull(coll.getItems()) && (coll.getItems().length > 0)) {
            for (int i = 0; i < coll.getItems().length; i++) {
                if (coll.getItems()[i].getApplication().getHref().equals(applicationHref)) {
					log.info("validarPoliticaObtenida PolicyId {} applicationHref {} " , applicationHref , coll.getItems()[i].getPolicyId());
                    politica = coll.getItems()[i];
                }
            }
            if(Objects.isNull(politica)) {
            	log.error(ERROR_OBTENER_POLITICA_IDENTITY_X.getMensaje());
    			throw new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X);
            }
        } else {
        	log.error(ERROR_OBTENER_POLITICA_IDENTITY_X.getMensaje());
			throw new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X);
        }
		return politica;
	}

	@Override
	public Optional<Registration> crearRegistracion(String registrationId, User usuario, Application application) {
		Optional<Registration> optionalRegistration = Optional.empty();
		Registration registration = new Registration();
        registration.setUser(usuario);
        registration.setApplication(application);
        registration.setRegistrationId(registrationId);
        try {
			optionalRegistration = Optional.of(tenantRepoFactory.getRegistrationRepo().create(registration));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getCodigo(), ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getMensaje());
		}
        return optionalRegistration;
	}

	@Override
	public Optional<Registration> obtenerRegistracion(String registrationId){
		Optional<Registration> registration = Optional.empty();

		try {

			RegistrationQueryHolder rqh = new RegistrationQueryHolder();
			rqh.getSearchSpec().setRegistrationId(registrationId);
			rqh.getSearchSpec().setStatus(RegistrationStatusEnum.ACTIVE);
			rqh.getPageSpec().setLimit(1);

			Optional<RegistrationCollection> registrationCollection = Optional.of(tenantRepoFactory.getRegistrationRepo().list(rqh));

			registrationCollection.orElseThrow(()->new IdentityXException(ERROR_OBTENER_REGISTRACION_IDENTITY_X));

			if(registrationCollection.get().getItems().length == 0) {
				throw new RegistracionNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X);
			}

			registration = Optional.of(registrationCollection.get().getItems()[0]);

			return registration;

		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_OBTENER_REGISTRACION_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_OBTENER_REGISTRACION_IDENTITY_X);
		}
	}

	@Override
	public Optional<AuthenticationRequest> crearPedidoAutenticacion(String autenticacionRequestId, String descripcion, Optional<Policy>  politica,  Optional<Registration> registracion){
		Optional<AuthenticationRequest> createdRequest = Optional.empty();

		try {

			AuthenticationRequest request = new AuthenticationRequest();
			request.setPolicy(politica.get());
			request.setRegistration(registracion.get());
			request.setAuthenticationRequestId(autenticacionRequestId);
			request.setDescription(descripcion);
			request.setType(Policy.PolicyTypeEnum.RA.toString());

			createdRequest = Optional.of(tenantRepoFactory.getAuthenticationRequestRepo().create(request));

			createdRequest.orElseThrow(()-> new IdentityXException(ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X));

		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_AUTENTICACION_REQUEST_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_AUTENTICACION_REQUEST_IDENTITY_X);
		}

		return createdRequest;
	}

	@Override
	public DataSample crearDataSampleParaEvaluacion(String archivo){
		DataSample verificationFaceDataSample = new DataSample();
		verificationFaceDataSample.setType(DataSampleTypeEnum.Face);

		if(aplicacionesConValidacionJpg.contains(MDC.get(TraceConstants.APPLICATION))){
			verificationFaceDataSample.setFormat(DataSampleFormatEnum.JPG);
		}else{
			verificationFaceDataSample.setFormat(DataSampleFormatEnum.Liveness3D);
		}

		verificationFaceDataSample.setData(new DataHolder());
		verificationFaceDataSample.getData().setValue(getBytesFromBase64(archivo));
		return verificationFaceDataSample;
	}

	@Override
	public Optional<AuthenticationRequest> actualizarPedidoAutenticacion(AuthenticationRequest crearRequest, DataSample verificacionDeRostro){
		Optional<AuthenticationRequest> verificationResponse = Optional.empty();

		try {

			crearRequest.setAuthenticationAttempts(new AuthenticationAttempt[] { new AuthenticationAttempt() });
			crearRequest.getAuthenticationAttempts()[0].setItems(new AuthenticationAttemptItem[] { new AuthenticationAttemptItem() });
			crearRequest.getAuthenticationAttempts()[0].getItems()[0].setDataSample(verificacionDeRostro);
			crearRequest.setComplete(true);
			verificationResponse = Optional.of(tenantRepoFactory.getAuthenticationRequestRepo().update(crearRequest));
			verificationResponse.orElseThrow(()-> new IdentityXException(ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X));
			return verificationResponse;

		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_AUTENTICACION_REQUEST_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_AUTENTICACION_REQUEST_IDENTITY_X);
		}

	}

	@Override
	public Optional<CreateEvaluationResponse> crearEvaluacion(String idUser, String idCheck, String politicaEvaluacion) {
		return digitalOnboardingServices.ejecutarCrearEvaluacion(idUser, idCheck, politicaEvaluacion);
	}

	public Optional<AuthenticationRequest>  consultarVerificacionRostro(String idverificacion) {
		try {
			Optional<AuthenticationRequest> verificationResponse =  Optional.of(tenantRepoFactory.getAuthenticationRequestRepo().getById(idverificacion));
			verificationResponse.orElseThrow(()-> new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X));
			return verificationResponse;
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X);
		}
	}

	@Override
	public Optional<Create3DFLVideoChallengeResponse> crearVideo3DFL(String idUsuario, String idCheck,
			String livenessPolicyName) {
		return digitalOnboardingServices.ejecutar3DFLVideoChallenge(idUsuario, idCheck, livenessPolicyName);
	}

	@Override
	public Optional<Create3DFLVideoChallengeResponse> getVideo3DFL(String idUsuario, String idCheck,
																	 String livenessPolicyName) {
		return digitalOnboardingServices.ejecutarGet3DFLVideoChallenge(idUsuario, idCheck, livenessPolicyName);
	}


	@Override
	public Optional<Submit3DFLVideoResponse> enviarVideo3DFL(String idUsuario, String idCheck,
			String idChallenge, String video) {

		Submit3DFLVideoRequest submit3DFLVideoRequest = new Submit3DFLVideoRequest();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FECHA_HORA_FORMATO_YYYY_MM_DD_T_HH_MM_SS_SSS);
		submit3DFLVideoRequest.setCaptured(LocalDateTime.now().format(formatter));
		submit3DFLVideoRequest.setSubtype(video3DFLSubtipo);
		submit3DFLVideoRequest.setVideoFormat(video3DFLFormato);
		
		LivenessChallenge livenessChallenge = new LivenessChallenge();
		livenessChallenge.setId(idChallenge);
		livenessChallenge.setType(video3DFLFormato);
		
		VideoChallenge videoChallenge = new VideoChallenge();
		videoChallenge.setStart(video3DFLChallengeInicio);
		videoChallenge.setCompleted(video3DFLChallengeFin);
		videoChallenge.setChallenge(livenessChallenge);

		submit3DFLVideoRequest.setChallenges(new VideoChallenge []{videoChallenge});
		VideoData videoData = new VideoData();
		videoData.setFormat(video3DFLFormato);
		videoData.setValue(video);		
		submit3DFLVideoRequest.setSensitiveData(videoData);
		
		return digitalOnboardingServices.ejecutar3DFLSubirVideo(idUsuario, idCheck, submit3DFLVideoRequest);
	}

	@Override
	public Optional<CreateEvaluationChallengeResponse> crearEvaluacionChallenge(String idUsuario, String idCheck,
			String evaluationPolicyName) {
		return digitalOnboardingServices.ejecutarCrearEvaluacionChallenge(idUsuario, idCheck, evaluationPolicyName);
	}


	public User getUser(String id)  {
		try {
			UserRepository userRepo = tenantRepoFactory.getUserRepo();
			if (userRepo == null)
				return null;
			return userRepo.getById(id);
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_OBTENER_USUARIO_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X);
		}
	}

	public Optional<RegistrationChallenge> addRegistrationChallenge(Registration reg, String sponsorshipToken,Policy policy)  {

		try{
			if (log.isDebugEnabled()) {
				log.debug("addRegistrationChallenge called for " + reg.getId() + " : " + sponsorshipToken);
			}

			RegistrationChallengeRepository regChallengeRepository = tenantRepoFactory.getRegistrationChallengeRepo();
			RegistrationChallenge regChallenge = new RegistrationChallenge();
			regChallenge.setRegistration(reg);
			regChallenge.setPolicy(policy);
			if ((sponsorshipToken != null) && (sponsorshipToken.length() > 0)) {
				regChallenge.setSponsorshipToken(sponsorshipToken);
			}
			regChallenge = regChallengeRepository.create(regChallenge);
			log.debug("Added a registration challenge for Registration with registrationId: {}, - regChallengeId: {}", reg.getRegistrationId(), regChallenge.getId());
			return Optional.of(regChallenge);

		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE);
		}
	}

	public Optional<RegistrationChallenge> getRegistrationChallengeById(String regChallengeId)  {

		try{
			RegistrationChallengeRepository regChallengeRepository = tenantRepoFactory.getRegistrationChallengeRepo();
			return Optional.ofNullable(regChallengeRepository.getById(regChallengeId));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE);
		}
	}

	public Optional<RegistrationChallenge> getRegistrationChallengeFromHref(String regChallengeFromHref)  {

		try{
			RegistrationChallengeRepository regChallengeRepository = tenantRepoFactory.getRegistrationChallengeRepo();
			return Optional.ofNullable(regChallengeRepository.get(regChallengeFromHref));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE);
		}
	}

	public Optional<Registration> getRegistrationFromHref(String href)  {
		try{
			RegistrationRepository regRepo = tenantRepoFactory.getRegistrationRepo();
			return Optional.of(regRepo.get(href));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X);
		}
	}

	public Optional<Registration> findRegistration(User user) {

		try{
			RegistrationRepository regRepo = tenantRepoFactory.getRegistrationRepo();
			RegistrationQueryHolder holder = new RegistrationQueryHolder();
			holder.getSearchSpec().setStatus(RegistrationStatusEnum.ACTIVE);
			RegistrationCollection registrationCollection = regRepo.list(user.getRegistrations().getHref(), holder);

			if (registrationCollection.getItems() == null) {
				return Optional.empty();
			}

			switch (registrationCollection.getItems().length) {
				case 0:
					return Optional.empty();
				case 1:
					return Optional.ofNullable(registrationCollection.getItems()[0]);
				default:
					throw new IdentityXException(ERROR_OBTENER_REGISTRACION_IDENTITY_X);
			}
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_OBTENER_REGISTRACION_IDENTITY_X.getMensaje(), e);
			throw  new IdentityXException(ERROR_OBTENER_REGISTRACION_IDENTITY_X);
		}

	}

	public Optional<RegistrationChallenge> saveRegistrationChallenge(RegistrationChallenge regChallenge)  {

		try{
			RegistrationChallengeRepository regChallengeRepository = tenantRepoFactory.getRegistrationChallengeRepo();
			return Optional.of(regChallengeRepository.update(regChallenge));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_FIDO_GUARDANDO_REGISTRATION_CHALLENGE.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_GUARDANDO_REGISTRATION_CHALLENGE);
		}
	}

	public Optional<AuthenticationRequest> createAuthenticationRequest(AuthenticationRequest authenticationRequest){
		try{
			AuthenticationRequestRepository authenticationRequestRepo = this.tenantRepoFactory.getAuthenticationRequestRepo();
			return Optional.ofNullable(authenticationRequestRepo.create(authenticationRequest));
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_FIDO_CREAR_AUTENTICACION.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_CREAR_AUTENTICACION);
		}
	}


	public Optional<AuthenticationRequest> getAuthenticationRequestRepo(String authenticationRequestId){
		try{
			AuthenticationRequestRepository authenticationRequestRepo = this.tenantRepoFactory.getAuthenticationRequestRepo();
			AuthenticationRequest request = authenticationRequestRepo.getById(authenticationRequestId);
			return Optional.ofNullable(request);
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_FIDO_NO_AUTENTICACION_REQUEST.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_NO_AUTENTICACION_REQUEST);
		}
	}

	public Optional<AuthenticationRequest> saveAuthenticationRequestRepo(AuthenticationRequest authenticationRequest){
		try{
			AuthenticationRequestRepository authenticationRequestRepo = this.tenantRepoFactory.getAuthenticationRequestRepo();
			AuthenticationRequest request = authenticationRequestRepo.update(authenticationRequest);
			return Optional.ofNullable(request);
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_FIDO_GUARDANDO_AUTENTICACION_REQUEST.getMensaje(), e);
			throw new IdentityXException(ERROR_FIDO_GUARDANDO_AUTENTICACION_REQUEST);
		}
	}

	public Optional<User> getUserByHref(String userHref){
		try{
			UserRepository authenticationRequestRepo = this.tenantRepoFactory.getUserRepo();
			User request = authenticationRequestRepo.get(userHref);
			return Optional.ofNullable(request);
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X);
		}
	}

	@Override
	public Optional<GetSensitiveDataResponse> getSensitiveData(String sensitiveData) {
		return digitalOnboardingServices.ejecutarGetSensitiveData(sensitiveData);
	}

	public Optional<Authenticator[]> getAuthenticators(String authenticatorsHref)  {
		try{
			AuthenticatorRepository authRepo = this.tenantRepoFactory.getAuthenticatorRepo();
			AuthenticatorQueryHolder holder = new AuthenticatorQueryHolder();
			AuthenticatorCollection authenticatorCollection = authRepo.list( holder);

			for (Authenticator authenticator : authenticatorCollection.getItems()) {
				if (authenticator.getAuthenticatorType() != null) {
					authenticator.setAuthenticatorType(this.getAuthenticatorType(authenticator.getAuthenticatorType().getHref()));
				}
			}
			return Optional.of(authenticatorCollection.getItems());

		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X);
		}
	}

	protected AuthenticatorType getAuthenticatorType(String href) throws IdxRestException {

		if (this.getAuthenticatorTypesCache().containsKey(href)) {
			return this.getAuthenticatorTypesCache().get(href);
		} else {
			AuthenticatorTypeRepository typeRepo = this.tenantRepoFactory.getAuthenticatorTypeRepo();
			AuthenticatorType type = typeRepo.get(href);
			this.getAuthenticatorTypesCache().put(href, type);
			return type;
		}
	}

	@Override
	public Optional<Authenticator> archiveAuthenticator(String authenticatorsHref)  {
		try{
			AuthenticatorRepository authRepo = this.tenantRepoFactory.getAuthenticatorRepo();
			Authenticator authenticator = authRepo.getById(authenticatorsHref);

			if (authenticator.getStatus() != AuthenticatorStatusEnum.ARCHIVED) {
				Authenticator authenticatorArchive = authRepo.archive(authenticator);
				authenticator = authenticatorArchive;
			}
			return Optional.of(authenticator);

		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			validarErroresFIDO(e);
			log.error(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X);
		}
	}

    /**
     * Permite recuperar las imagenes del DNI registradas para el usuario.
     *
     * @param user el usuario para el que se requiere obtener las imagenes del DNI.
     * @return un objeto de tipo <code>Optional<DniImageData></code> con las imagenes de frente y
     *     dorso para el DNI.
     */
    @Override
    public Optional<DniImageData> getDniImage(final User user) {
      try {
        // Consulta los enrollments del usuario
        EnrollmentCollection enrollmentCollection =
            digitalOnboardingServices.getEnrollments(user.getEnrollments().getHref());
  
        // Filtra enrollments de tipo 'Face' y obtiene el mas reciente
        Optional<Enrollment> enrollment =
            Arrays.stream(enrollmentCollection.getItems())
                .filter(e -> e.getSubtype().equals("Face"))
                .sorted(Comparator.comparing(Enrollment::getCreated).reversed())
                .findFirst();
  
        if (enrollment.isEmpty()) {
          log.warn(
              "El usuario {} no tiene enrollments de subtype: Face. Imposible obtener imagen del DNI.",
              user.getUserId());
          return Optional.empty();
        }
  
        // Consulta los samples asociados al enrollment
        DataSampleCollection sampleCollection =
            digitalOnboardingServices.getSamples(enrollment.get().getSamples().getHref());
  
        // Recupera el sample mas reciente
        Optional<DataSample> dataSample =
            Arrays.stream(sampleCollection.getItems())
                .sorted(Comparator.comparing(DataSample::getLastEvaluated).reversed())
                .findFirst();
  
        if (dataSample.isEmpty()) {
          log.warn(
              "El usuario {} no tiene samples para el enrollment. Imposible obtener imagen del DNI.",
              user.getUserId());
          return Optional.empty();
        }
  
        Date lastEvaluatedDate = dataSample.get().getLastEvaluated();
        Optional<GetIdChecksResponse> idChecks = getIdcheckMostClosetoDate(user, lastEvaluatedDate);
  
        if (idChecks.isEmpty() || idChecks.get().getItems().length == 0) {
          log.warn(
              "El usuario {} no tiene idcheck reciente. Imposible obtener imagen del DNI.",
              user.getUserId());
          return Optional.empty();
        }
  
        // El item[0] es el idcheck más cercano a lastEvaluated
        CreateEmptyIdCheckResponse idCheckResponse =
            digitalOnboardingServices.getIdCheck(idChecks.get().getItems()[0].getHref());
  
        // Consulta los documentos asociados al idcheck
        DocumentCollection documents =
            digitalOnboardingServices.getDocuments(idCheckResponse.getDocuments().getHref());
  
        //  Obtiene el document mas reciente
        Optional<Item> document =
            Arrays.stream(documents.getItems())
                .sorted(Comparator.comparing(Item::getCaptured).reversed())
                .findFirst();
  
        if (document.isEmpty() || Objects.isNull(document.get().getClientCapture())) {
          log.warn(
              "El idcheck del usuario {} no tiene documents. Imposible obtener imagen del DNI.",
              user.getUserId());
          return Optional.empty();
        }
  
        // Consulta los clientCapture asociados al document
        ClientDocumentCapture clientCapture =
            digitalOnboardingServices.getClientDocumentCapture(
                document.get().getClientCapture().getHref());
  
        // Frente DNI
        Optional<DocumentMultiImageData> frontImageData =
            Arrays.stream(clientCapture.getImages())
                .filter(e -> e.getType().equals(tipoImagenFrente))
                .findFirst();
  
        // Dorso DNI
        Optional<DocumentMultiImageData> backImageData =
            Arrays.stream(clientCapture.getImages())
                .filter(e -> e.getType().equals(tipoImagenDorso))
                .findFirst();
  
        var dniImageData = new DniImageData();
        if (frontImageData.isPresent()) {
          var sensitiveData =
              digitalOnboardingServices.ejecutarGetSensitiveData(
                  frontImageData.get().getSensitiveData().getHref());
  
          dniImageData.setFrente(sensitiveData.isPresent() ? sensitiveData.get().getValue() : null);
        }
  
        if (backImageData.isPresent()) {
          var sensitiveData =
              digitalOnboardingServices.ejecutarGetSensitiveData(
                  backImageData.get().getSensitiveData().getHref());
  
          dniImageData.setDorso(sensitiveData.isPresent() ? sensitiveData.get().getValue() : null);
        }
  
        return Optional.of(dniImageData);

      } catch (Exception e) {
        log.error(ERROR_CONSULTANDO_IMAGENES_DNI.getMensaje(), e);
        throw new IdentityXException(ERROR_CONSULTANDO_IMAGENES_DNI);
      }
    }

    /**
     * Obtiene el idcheck del usuario mas cercano a la fecha recibida.
     *
     * @param user el usuario para el que se requiere consultar el idcheck.
     * @param lastEvaluatedDate la fecha de referencia para consultar el idcheck mas cercano.
     * @return un objeto GetIdChecksResponse que contiene el idcheck.
     */
    private Optional<GetIdChecksResponse> getIdcheckMostClosetoDate(
        final User user, Date lastEvaluatedDate) {
  
      Date startDate = addDate(lastEvaluatedDate, Calendar.MINUTE, idchecksWithinRange);
  
      var dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  
      // Parametros para filtrar el idcheck mas cercano a la ultima fecha de evaluación
      var queryParams = new HashMap<String, String>();
      queryParams.put("limit", "1");
      queryParams.put("page", "1");
      queryParams.put("status", "ACTIVE");
      queryParams.put("sortField", "created");
      queryParams.put("sortAscending", "false");
      queryParams.put("start", dateFormat.format(startDate));
      queryParams.put("end", dateFormat.format(lastEvaluatedDate));
  
      return digitalOnboardingServices.getIdchecksByUser(user, queryParams);
    }

    /**
     * Adds or subtracts the specified amount of time to the given calendar field, based on the
     * calendar's rules. For example, to subtract 5 days from the current time of the calendar, you
     * can achieve it by calling:
     *
     * <p><code>addDate(date, Calendar.DAY_OF_MONTH, -5)</code>.
     *
     * @param date the initial value.
     * @param field the calendar field.
     * @param amount the amount of date or time to be added to the field.
     */
    private Date addDate(final Date date, int field, int amount) {
      var cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(field, amount);
      
      return cal.getTime();
    }
    
    public Map<String, AuthenticatorType> getAuthenticatorTypesCache() {
      return authenticatorTypesCache;
  }
}
