package ar.com.macro.enrolamiento.model.client.identityx.impl;

import static ar.com.macro.constant.Errores.ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_CREAR_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_ELIMINAR_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_POLITICA_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_REGISTRACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X;
import static java.util.Objects.nonNull;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import com.daon.identityx.rest.model.def.ApplicationStatusEnum;
import com.daon.identityx.rest.model.def.PolicyStatusEnum;
import com.daon.identityx.rest.model.def.RegistrationStatusEnum;
import com.daon.identityx.rest.model.def.UserStatusEnum;
import com.daon.identityx.rest.model.pojo.Application;
import com.daon.identityx.rest.model.pojo.AuthenticationAttempt;
import com.daon.identityx.rest.model.pojo.AuthenticationAttemptItem;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.DataSample;
import com.daon.identityx.rest.model.pojo.DataSampleFormatEnum;
import com.daon.identityx.rest.model.pojo.DataSampleTypeEnum;
import com.daon.identityx.rest.model.pojo.Policy;
import com.daon.identityx.rest.model.pojo.Registration;
import com.daon.identityx.rest.model.pojo.User;
import com.daon.identityx.rest.model.support.DataHolder;
import com.daon.identityx.rest.model.support.DataSampleEvaluation;
import com.identityx.clientSDK.TenantRepoFactory;
import com.identityx.clientSDK.collections.ApplicationCollection;
import com.identityx.clientSDK.collections.DataSampleCollection;
import com.identityx.clientSDK.collections.PolicyCollection;
import com.identityx.clientSDK.collections.RegistrationCollection;
import com.identityx.clientSDK.collections.UserCollection;
import com.identityx.clientSDK.exceptions.IdxRestException;
import com.identityx.clientSDK.queryHolders.ApplicationQueryHolder;
import com.identityx.clientSDK.queryHolders.PolicyQueryHolder;
import com.identityx.clientSDK.queryHolders.RegistrationQueryHolder;
import com.identityx.clientSDK.queryHolders.UserQueryHolder;
import ar.com.macro.commons.values.constants.format.DatePatternsConstants;
import ar.com.macro.commons.values.constants.text.CharConstants;
import ar.com.macro.constant.Constantes;
import ar.com.macro.constant.Errores;
import ar.com.macro.constant.IdentityXErrores;
import ar.com.macro.enrolamiento.model.client.identityx.IdentityXClient;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.ClientDocumentCapture;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.DocumentMultiImageData;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.DocumentSensitiveImageData;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.RegistracionNoEncontradoIdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import lombok.extern.slf4j.Slf4j;

@RefreshScope
@Slf4j
@Service
public class IdentityXClientImpl implements IdentityXClient {	

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePatternsConstants.PATRON_FECHA_ACTUAL_COMPLETO);
	private TenantRepoFactory tenantRepoFactory;
	private DigitalOnboardingServicesImpl digitalOnboardingServices;
	
    String tipoImagenFrente;
    String tipoImagenDorso;
    String formatoCapturaTarjeta;
	
	public IdentityXClientImpl(
			TenantRepoFactory tenantRepoFactory,
			DigitalOnboardingServicesImpl digitalOnboardingServices,
			@Value("${identityx.imagen.frente}") String tipoImagenFrente,
			@Value("${identityx.imagen.dorso}") String tipoImagenDorso,
			@Value("${identityx.formato.captura.tarjeta}") String formatoCapturaTarjeta) {
		this.tenantRepoFactory = tenantRepoFactory;
		this.digitalOnboardingServices = digitalOnboardingServices;
		this.tipoImagenFrente = tipoImagenFrente;
		this.tipoImagenDorso = tipoImagenDorso;
		this.formatoCapturaTarjeta = formatoCapturaTarjeta;
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
			log.error(ERROR_OBTENER_USUARIO_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X);
		}
		return usuario;
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

	/**
	 * Decode imagen rostro en Base64 to bytes
	 * @param archivoBase64
	 * @return
	 */
	private byte[] getBytesFromBase64(String archivoBase64) {
		try {
			return Base64.getDecoder().decode(new String(archivoBase64).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getMensaje(),e);
			throw new IdentityXException(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X);
		}
	}

    /**
     * Validacion de la respuesta de actualizar rostro IdentityX
     *
     * @param dataSampleCollection
     */
    private void validarDataSampleCollection(final DataSampleCollection dataSampleCollection) {
      var dataSample =
          Stream.of(dataSampleCollection.getItems())
              .findFirst()
              .orElseThrow(
                  () ->
                      new IdentityXException(
                          ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X));
  
      var noUsable = Boolean.FALSE.equals(dataSample.getUsable());
      var error = nonNull(dataSample.getEvaluations()) ? getIdentityXError(dataSample) : null;
      
      if (noUsable || nonNull(error)) {
        // Obtiene el codigo de error generado por Identity-X
  
        if (nonNull(error)) {
          log.error(
              "Error recibido desde Identity-X, resultCode: {} y message: {} ",
              error.getResultCode(),
              error.getResultMessage());
  
          // Mapeo a errores conocidos
          var identityXError = IdentityXErrores.getValueOf(error.getResultCode());
          var internalError =
              nonNull(identityXError)
                  ? Errores.getValueOf(identityXError.getInternalCode())
                  : ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X;
  
          throw new IdentityXException(internalError.getCodigo(), internalError.getMensaje());
  
        } else if (noUsable){
          log.error(
              ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X.getMensaje()
                  + " {} ",
              dataSample);
          
          throw new IdentityXException(
              ERROR_VALIDACION_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X);
        }
      }
    }

    private DataSampleEvaluation getIdentityXError(final DataSample dataSample) {
      return Arrays.asList(dataSample.getEvaluations())
          .stream()
          .filter(evaluation -> evaluation.getResultCode() != 0)
          .findFirst()
          .orElse(null);
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
    	
    	var images = new DocumentMultiImageData[] {
    	    imagenFrente, imagenDorso
    	};
    	
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
		if ((coll != null) && (coll.getItems() != null) && (coll.getItems().length > 0)) {
            for (int i = 0; i < coll.getItems().length; i++) {
                if (coll.getItems()[i].getApplication().getHref().equals(applicationHref)) {
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
	public Registration crearRegistracion(String registrationId, User usuario, Application application) {
		Registration registration = new Registration();
        registration.setUser(usuario);
        registration.setApplication(application);
        registration.setRegistrationId(registrationId);
        try {
			registration = tenantRepoFactory.getRegistrationRepo().create(registration);
		} catch (IdxRestException e) {
			mostrarInformacionIdxRestException(e);
			log.error(ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getCodigo(), ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getMensaje());
		}
        return registration;
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
		verificationFaceDataSample.setFormat(DataSampleFormatEnum.JPG);
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


}
