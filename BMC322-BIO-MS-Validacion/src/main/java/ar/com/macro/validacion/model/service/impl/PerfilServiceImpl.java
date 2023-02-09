package ar.com.macro.validacion.model.service.impl;

import static ar.com.macro.commons.values.constants.text.CharConstants.CARACTER_GUION;
import static ar.com.macro.constant.Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AGREGAR_DNI_PROFILE_IDCHECK_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AGREGAR_DNI_PROFILE_SUBMIT_DOCUMENT;
import static ar.com.macro.constant.Errores.ERROR_CREAR_IDCHECK_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_GET_FACES_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_GET_SENSITIVE_DATA_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_REGISTRACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Status.FAILED;
import static ar.com.macro.constant.Status.FAILED_CONFIDENCE;
import static ar.com.macro.constant.Status.SUCCESS;
import static ar.com.macro.constant.Validacion3DFL.VALIDACION_VIDEO_STATUS_PROCESAMIENTO_FALLA;
import static ar.com.macro.constant.Validacion3DFL.VALIDACION_VIDEO_STATUS_PROCESAMIENTO_PROCESSED;
import static java.lang.String.format;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.daon.identityx.rest.model.pojo.Application;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.DataSample;
import com.daon.identityx.rest.model.pojo.Policy;
import com.daon.identityx.rest.model.pojo.Registration;
import com.daon.identityx.rest.model.pojo.User;
import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.values.constants.text.CharConstants;
import ar.com.macro.constant.Errores;
import ar.com.macro.constant.ImageType;
import ar.com.macro.constant.Status;
import ar.com.macro.exceptions.AgregarDniPerfilException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.RegistracionNoEncontradoIdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.AgregarDniPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.AgregarDniPerfilResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarVerificacionRostroPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ValidarRostroPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ValidarRostroVideoPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarVerificacionRostroPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.CrearEvaluacionPerfilResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ImagenResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ImagenResponse.TipoImagen;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroVideoPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroVideoPersonaResponse.RostroResponse;
import ar.com.macro.validacion.domain.renaper.rest.dto.request.RenaperSelfieRequest;
import ar.com.macro.validacion.model.client.identityx.IdentityXClient;
import ar.com.macro.validacion.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetImagenResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.request.ValidarRostroRenaperRequest;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.response.ValidarRostroRenaperResponse;
import ar.com.macro.validacion.model.feign.enrolamiento.proxy.EnrolamientoProxyService;
import ar.com.macro.validacion.model.service.DatosGeneralesService;
import ar.com.macro.validacion.model.service.PerfilService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PerfilServiceImpl implements PerfilService {
	
	private final IdentityXClient identityXClient;

	private final String referenceIdSeparador;

	private final DatosGeneralesService datosGeneralesService;

	private final EnrolamientoProxyService enrolamientoProxyService;

	private final String nombreMicroServicioDatosGenerales;

	private final String parametroConfigAplicacionIdentityX;

	private final String sufijoIdRegistracion;

	private final String parametroConfigPoliticaIdentityX;

	private final String sufijoIdAuthetication;

	private final String autheticationRequestDescriptionTemplate;

	private final String parametroConfigPoliticaEvaluacionIdentityX;
	
	private final String parametroConfigPoliticaEvaluacion3DFLIdentityX;

	private final String parametroConfigValidacionRenaper3DFLIdentityX;

	private final String parametroMatchCreacionEvaluacion;

	private final String parametroConfigUmbralRenaper3DFLIdentityX;

	public PerfilServiceImpl(
			IdentityXClient identityXClient,
			DatosGeneralesService datosGeneralesService,
			EnrolamientoProxyService enrolamientoProxyService,
		    @Value("${identityx.client.referenceid.separador.value}") String referenceIdSeparador,
			@Value("${daonengine.nombre.parametros.datos.generales}") String nombreMicroServicioDatosGenerales,
			@Value("${identityx.nombre.parametros.configuracion.politica.evaluacion}") String parametroConfigPoliticaEvaluacionIdentityX,
			@Value("${identityx.nombre.parametros.configuracion.politica.evaluacion.match}") String parametroMatchCreacionEvaluacion,
			@Value("${identityx.nombre.parametros.configuracion.aplicacion}") String parametroConfigAplicacionIdentityX,
			@Value("${identityx.registracion.id.sufijo}") String sufijoIdRegistracion,
			@Value("${identityx.nombre.parametros.configuracion.politica}") String parametroConfigPoliticaIdentityX,
			@Value("${identityx.authetication.id.sufijo}") String sufijoIdAuthetication,
			@Value("${identityx.authetication.request.description}") String autheticationRequestDescriptionTemplate,
			@Value("${identityx.nombre.parametros.configuracion.politica.3dfl}") String parametroConfigPolitica3DFLIdentityX,
			@Value("${identityx.nombre.parametros.configuracion.validacion.renaper.3dfl}") String parametroConfigValidacionRenaper3DFLIdentityX,
			@Value("${identityx.nombre.parametros.configuracion.umbral.renaper.3dfl}") String parametroConfigUmbralRenaper3DFLIdentityX

	) {
		this.identityXClient = identityXClient;
		this.datosGeneralesService = datosGeneralesService;
		this.enrolamientoProxyService = enrolamientoProxyService;
		this.referenceIdSeparador = referenceIdSeparador;
		this.nombreMicroServicioDatosGenerales = nombreMicroServicioDatosGenerales;
		this.parametroConfigPoliticaEvaluacionIdentityX = parametroConfigPoliticaEvaluacionIdentityX;
		this.parametroMatchCreacionEvaluacion = parametroMatchCreacionEvaluacion;
		this.parametroConfigAplicacionIdentityX = parametroConfigAplicacionIdentityX;
		this.sufijoIdRegistracion = sufijoIdRegistracion;
		this.parametroConfigPoliticaIdentityX = parametroConfigPoliticaIdentityX;
		this.sufijoIdAuthetication = sufijoIdAuthetication;
		this.autheticationRequestDescriptionTemplate = autheticationRequestDescriptionTemplate;
		this.parametroConfigPoliticaEvaluacion3DFLIdentityX = parametroConfigPolitica3DFLIdentityX;
		this.parametroConfigValidacionRenaper3DFLIdentityX = parametroConfigValidacionRenaper3DFLIdentityX;
		this.parametroConfigUmbralRenaper3DFLIdentityX = parametroConfigUmbralRenaper3DFLIdentityX;
	}

	@Override
	public CrearPerfilResponse crearPerfilUsuario(CrearPerfilRequest crearPerfilRequest) {
		User usuario = new User();
		usuario.setUserId(crearPerfilRequest.getIdusuario());
        return new CrearPerfilResponse(identityXClient.crearPerfilUsuario(usuario).getId());
	}

	@Override
	public AgregarDniPerfilResponse agregarDniPerfilUsuario(AgregarDniPerfilRequest agregarDniPerfilRequest) {

		User usuario = identityXClient.obtenerRegistroUsuario(agregarDniPerfilRequest.getIdusuario());

		String referenceId = generarReferenceId(usuario);

		Optional<CreateEmptyIdCheckResponse> createEmptyIdCheckResponse = identityXClient.crearIdCheck(usuario,referenceId);

		CreateEmptyIdCheckResponse idCheck = createEmptyIdCheckResponse
				.orElseThrow(()-> new AgregarDniPerfilException(ERROR_AGREGAR_DNI_PROFILE_IDCHECK_IDENTITY_X));

		BothSidesDocumentRequest bothSidesDocumentRequest = new BothSidesDocumentRequest(agregarDniPerfilRequest.getFrente(),agregarDniPerfilRequest.getDorso());

		Optional<SubmitBothSidesDocumentResponse> submitBothSidesDocumentResponse = identityXClient
				.enviarFrenteyDorsoDni(usuario.getId(),idCheck.getIdCheck(), bothSidesDocumentRequest);

		SubmitBothSidesDocumentResponse idDocument = submitBothSidesDocumentResponse
				.orElseThrow(()-> new AgregarDniPerfilException(ERROR_AGREGAR_DNI_PROFILE_SUBMIT_DOCUMENT));

		return AgregarDniPerfilResponse.builder()
				.iddaon(usuario.getId())
				.idusuario(usuario.getUserId())
				.idcheck(idCheck.getIdCheck())
				.iddocumento(idDocument.getIdDocument())
				.build();
	}

	@Override
	public ValidarRostroPersonaResponse validarRostroPersona (ValidarRostroPersonaRequest validarRostroPersonaRequest, String xOperationID) {
		try {

			User usuario = identityXClient.obtenerRegistroUsuario(validarRostroPersonaRequest.getIdusuario());

			String aplicacionId = datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(xOperationID, nombreMicroServicioDatosGenerales, parametroConfigAplicacionIdentityX);

			Optional<Application> optAplicacion = identityXClient.obtenerRegistroAplicacion(aplicacionId);

			Optional<Registration> registration = null;

			try {
				registration = identityXClient.obtenerRegistracion(generateRegistrationId(validarRostroPersonaRequest));
			} catch (RegistracionNoEncontradoIdentityXException e) {
				registration = identityXClient.crearRegistracion(generateRegistrationId(validarRostroPersonaRequest),usuario,optAplicacion.get());
			} catch (Exception e) {
				log.error(ERROR_OBTENER_REGISTRACION_IDENTITY_X.getMensaje(), e);
				throw new IdentityXException(ERROR_OBTENER_REGISTRACION_IDENTITY_X);
			}

			String politicaId = datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(xOperationID, nombreMicroServicioDatosGenerales, parametroConfigPoliticaIdentityX);

			Optional<Policy> optPolitica = identityXClient.obtenerRegistroPolitica(politicaId, optAplicacion.get().getHref());

			optPolitica.orElseThrow(() -> new IdentityXException(Errores.ERROR_OBTENER_POLITICA_IDENTITY_X));

			String autheticationRequestId = generateAutheticationRequestId(usuario);

			String autheticationRequestDescription = format(autheticationRequestDescriptionTemplate, autheticationRequestId);

			Optional<AuthenticationRequest> authenticationRequest = identityXClient.crearPedidoAutenticacion(autheticationRequestId, autheticationRequestDescription, optPolitica, registration);

			DataSample verificationFaceDataSample = identityXClient.crearDataSampleParaEvaluacion(validarRostroPersonaRequest.getSelfie());

			Optional<AuthenticationRequest> verificationResponse = identityXClient.actualizarPedidoAutenticacion(authenticationRequest.get(), verificationFaceDataSample);

			verificationResponse.orElseThrow(() -> new IdentityXException(Errores.ERROR_AUTENTICACION_REQUEST_IDENTITY_X));

			return new ValidarRostroPersonaResponse(verificationResponse.get());

		} catch (UsuarioNoEncontradoIdentityXException e) {
			log.error(Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getMensaje(), e);
			throw new IdentityXException(Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getCodigo(), Errores.ERROR_BUSQUEDA_PERFIL_IDENTITY_X.getMensaje());
		}
	}

	/**
	 * @param validarRostroPersonaRequest
	 * @return userId + “-REGID”
	 */
	private String generateRegistrationId(final ValidarRostroPersonaRequest validarRostroPersonaRequest){
		return validarRostroPersonaRequest.getIdusuario() + CharConstants.CARACTER_GUION + sufijoIdRegistracion;
	}

	/**
	 * @param usuario
	 * @return userId + "-AUTHREQID-" + currentTimeMillis
	 */
	private String generateAutheticationRequestId(final User usuario){
		return 	usuario.getUserId() + CharConstants.CARACTER_GUION + sufijoIdAuthetication + CharConstants.CARACTER_GUION + System.currentTimeMillis();
	}
	
	@Override
	public CrearEvaluacionPerfilResponse crearEvaluacion(CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest, String xOperationID) {
		CrearEvaluacionPerfilResponse crearEvaluacionPerfilResponse = new CrearEvaluacionPerfilResponse();
		User usuario = identityXClient.obtenerRegistroUsuario(crearEvaluacionPerfilRequest.getIdusuario());
		String evaluationPolicyName = datosGeneralesService.obtenerConfiguracionPoliticaEvaluacionIdentityX(xOperationID,nombreMicroServicioDatosGenerales,parametroConfigPoliticaEvaluacionIdentityX);
		Optional<CreateEvaluationResponse> createEvaluationResponse =  identityXClient.crearEvaluacion(usuario.getId(),crearEvaluacionPerfilRequest.getIdcheck(),evaluationPolicyName);
		if(createEvaluationResponse.isPresent() && createEvaluationResponse.get().getResults() != null){
			if(createEvaluationResponse.get().getResults().getItems().length == 1){
				if(parametroMatchCreacionEvaluacion.equals(createEvaluationResponse.get().getResults().getItems()[0].getResult())){
					crearEvaluacionPerfilResponse.setResultado(1);
				} else {
					crearEvaluacionPerfilResponse.setResultado(0);
				}
			} else {
				throw new IdentityXException(Errores.ERROR_CREACION_EVALUACION_LISTA_ITEMS.getCodigo(), Errores.ERROR_CREACION_EVALUACION_LISTA_ITEMS.getMensaje());
			}
		} else {
			throw new IdentityXException(Errores.ERROR_CREACION_EVALUACION_VACIO.getCodigo(), Errores.ERROR_CREACION_EVALUACION_VACIO.getMensaje());
		}
		return crearEvaluacionPerfilResponse;
	}

	@Override
	public ConsultarVerificacionRostroPersonaResponse consultarVerificacionRostro(ConsultarVerificacionRostroPersonaRequest consultarVerificacionRostroPersona, String xOperationID) {
		Optional<AuthenticationRequest>  authenticationRequest = identityXClient.consultarVerificacionRostro(consultarVerificacionRostroPersona.getIdverificacion());
		return new ConsultarVerificacionRostroPersonaResponse(authenticationRequest.get());
	}


	@Override
	public String generarReferenceId(User user){
		return user.getUserId() + CARACTER_GUION + referenceIdSeparador + CARACTER_GUION + System.currentTimeMillis();
	}

    @Override
    public ValidarRostroVideoPersonaResponse validarRostroVideoPersona(
        final ValidarRostroVideoPersonaRequest validarRostroVideoPersonaRequest,
        final String xOperationID) {

      ValidarRostroRenaperResponse validarRostroRenaperResponse = null;
      Status status = FAILED;
  
      try {
        var user = obtenerUsuario(validarRostroVideoPersonaRequest.getIdusuario());
  
        var referenceId = generarReferenceId(user);
        log.debug("validarRostroPersona - referenceId {} ", referenceId);
  
        var livenessPolicyName =
            datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(
                xOperationID,
                nombreMicroServicioDatosGenerales,
                parametroConfigPoliticaEvaluacion3DFLIdentityX);
        log.debug("validarRostroPersona - livenessPolicyName {} ", livenessPolicyName);
  
        var createEmptyIdCheckResponse =
            identityXClient.crearIdCheckByUserAndLivenessPolicy(
                user, livenessPolicyName, referenceId);
        log.debug(
            "validarRostroPersona - createEmptyIdCheckResponse {} ", createEmptyIdCheckResponse);
  
        var idCheckResponse =
            createEmptyIdCheckResponse.orElseThrow(
                () -> new IdentityXException(ERROR_CREAR_IDCHECK_IDENTITY_X));
        
        var idCheck = idCheckResponse.getIdCheck();
        log.info("idCheck {}", idCheck);
  
        var optVideoChallengeResponse =
            identityXClient.getVideo3DFL(user.getId(), idCheck, livenessPolicyName);
        log.debug("optVideoChallengeResponse {}", optVideoChallengeResponse);
  
        var optLivenessChallenge =
            Stream.of(optVideoChallengeResponse.get().getItems())
                .findFirst()
                .orElseThrow(() -> new IdentityXException(ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X));
  
        var optSubmitVideoResponse =
            identityXClient.enviarVideo3DFL(
                user.getId(),
                idCheck,
                optLivenessChallenge.getId(),
                validarRostroVideoPersonaRequest.getVideo());
        log.debug("optSubmitVideoResponse {}", optSubmitVideoResponse);
  
        if (VALIDACION_VIDEO_STATUS_PROCESAMIENTO_FALLA
            .getValue()
            .equals(optSubmitVideoResponse.get().getProcessingStatus())) {
         
          log.debug("status {}", FAILED.getValue());
          return new ValidarRostroVideoPersonaResponse(FAILED.getValue(), null);
        }

        var validacionRenaper = obtenerConfiguracionValidacionRenaper3dflIdentityX(xOperationID);
        log.debug("validacionRenaper {}", validacionRenaper);

        if (!validacionRenaper
            && VALIDACION_VIDEO_STATUS_PROCESAMIENTO_PROCESSED
                .getValue()
                .equals(optSubmitVideoResponse.get().getProcessingStatus())) {
  
          log.debug("status {}", SUCCESS.getValue());
          return new ValidarRostroVideoPersonaResponse(SUCCESS.getValue(), null);
        }
  
        if (validacionRenaper
            && VALIDACION_VIDEO_STATUS_PROCESAMIENTO_PROCESSED
                .getValue()
                .equals(optSubmitVideoResponse.get().getProcessingStatus())) {
  
          var facesResponse = identityXClient.getFaces(user, idCheck);
  
          var face =
              Stream.of(facesResponse.get().getItems())
                  .findFirst()
                  .orElseThrow(() -> new IdentityXException(ERROR_GET_FACES_IDENTITY_X));
  
          var imagenResponse = identityXClient.getImagen(user, idCheck, face.getId());
  
          var umbralRenaper =
              datosGeneralesService.obtenerConfiguracionUmbralRenaper3dflIdentityX(
                  xOperationID,
                  nombreMicroServicioDatosGenerales,
                  parametroConfigUmbralRenaper3DFLIdentityX);
  
          var validarRostroRenaperRequest =
              createValidarRostroRenaperRequest(validarRostroVideoPersonaRequest, imagenResponse);
  
          var response =
              enrolamientoProxyService.validarRostro(validarRostroRenaperRequest, xOperationID);
  
          if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IdentityXException(
                response.getBody().getError().getCodigo(),
                response.getBody().getError().getMensaje());
          }
  
          log.debug(
              "Confidence {} umbralRenaper {}",
              response.getBody().getDatos().getConfidence(),
              umbralRenaper);
  
          validarRostroRenaperResponse = response.getBody().getDatos();
          status =
              (validarRostroRenaperResponse.getConfidence() >= umbralRenaper)
                  ? SUCCESS
                  : FAILED_CONFIDENCE;
        }
  
        return new ValidarRostroVideoPersonaResponse(
            status.getValue(), new RostroResponse(validarRostroRenaperResponse));
  
      } catch (IdentityXException ex) {
        log.error("validarRostroPersona - IdentityXException", ex);
        throw ex;
      } catch (MacroException ex) {
        log.error("validarRostroPersona - MacroException", ex);
        throw ex;
      } catch (Exception ex) {
        log.error("validarRostroPersona- IdentityXException", ex);
        throw new IdentityXException(
            Errores.ERROR_VALIDAR_ROSTRO_PERSONA_IDENTITY_X.getCodigo(),
            Errores.ERROR_VALIDAR_ROSTRO_PERSONA_IDENTITY_X.getMensaje());
      }
    }

    private boolean obtenerConfiguracionValidacionRenaper3dflIdentityX(final String xOperationID) {
      boolean validacionRenaper = false;
      
      try {
        validacionRenaper = datosGeneralesService
            .obtenerConfiguracionValidacionRenaper3dflIdentityX(
                xOperationID,
                nombreMicroServicioDatosGenerales,
                parametroConfigValidacionRenaper3DFLIdentityX)
            .equals("true");
        
      } catch (IdentityXException ex) {
        log.warn(ex.getMessage());
      }
      return validacionRenaper;
    }

    @Override
	public User obtenerUsuario(String idusuario){
		User user = null;
		try {
			user = identityXClient.obtenerRegistroUsuario(idusuario);
		} catch (UsuarioNoEncontradoIdentityXException ex) {
			log.info("Usuario no enrolado {} se creara un nuevo perfil", idusuario);
		}
		if(user == null) {
			user = new User();
			user.setUserId(idusuario);
			user = identityXClient.crearPerfilUsuario(user);
			log.debug("validarRostroPersona - crearPerfilUsuario {} ", user);
		}
		return user;
	}

	private ValidarRostroRenaperRequest createValidarRostroRenaperRequest(ValidarRostroVideoPersonaRequest validarRostroVideoPersonaRequest,Optional<GetImagenResponse> imagenResponse){
		String numero = validarRostroVideoPersonaRequest.getIdusuario().substring(2, validarRostroVideoPersonaRequest.getIdusuario().length() - 1);
		String genero = validarRostroVideoPersonaRequest.getIdusuario().substring(validarRostroVideoPersonaRequest.getIdusuario().length() - 1);
		List<RenaperSelfieRequest> selfies = List.of(new RenaperSelfieRequest(imagenResponse.get().getValue(), ImageType.SN.name()));
		return new ValidarRostroRenaperRequest(numero, genero, selfies);
	}

    @Override
    public ConsultarPersonaResponse consultarPersona(
        final ConsultarPersonaRequest request, final String xOperationID) {

      try {
        var usuario = obtenerUsuario(request.getIdusuario());
  
        var user =
            identityXClient
                .getUserByHref(usuario.getHref())
                .orElseThrow(
                    () ->
                        new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
  
        var response = new ConsultarPersonaResponse();
        
        switch (request.getIndicadorValue()) {
          case SELFIE:
            addImagenPersona(response, user);
            break;
  
          case DNI:
            addImagenDni(response, user);
            break;
  
          case TODOS:
            addImagenPersona(response, user);
            addImagenDni(response, user);
            break;
  
          default:
            break;
        }
  
        return response;
  
      } catch (MacroException ex) {
        log.error("consultarPersona - MacroException", ex);
        throw ex;
      } catch (Exception ex) {
        log.error("consultarPersona- IdentityXException", ex);
        throw new IdentityXException(ERROR_GET_SENSITIVE_DATA_IDENTITY_X);
      }
    }

    /**
     * Recupera la selfie registrada para el usuario en Identity-X.
     *
     * @param response objeto para almacenar la selfie del usuario.
     * @param user el usuario para el que se debe recuperar la selfie. 
     */
    private void addImagenPersona(final ConsultarPersonaResponse response, final User user) {
      var sensitiveData =
          identityXClient.getSensitiveData(user.getFace().getSensitiveData().getHref());
  
      if (sensitiveData.isPresent()) {
        response.addImagen(ImagenResponse.of(TipoImagen.SF, sensitiveData.get().getValue()));
      }
    }

    /**
     * Recupera las imagenes del Dni (Frente y Dorso) registradas para el usuario en Identity-X.
     *
     * @param response objeto para almacenar las imagenes del Dni del usuario.
     * @param user el usuario para el que se debe recuperar las imagenes del Dni.
     */
    private void addImagenDni(final ConsultarPersonaResponse response, final User user) {
      var dniImage = identityXClient.getDniImage(user);
  
      if (dniImage.isPresent()) {
        var data = dniImage.get();
        response
            .addImagen(ImagenResponse.of(TipoImagen.DF, data.getFrente()))
            .addImagen(ImagenResponse.of(TipoImagen.DD, data.getDorso()));
      }
    }
}
