package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.commons.values.constants.text.CharConstants.CARACTER_GUION;
import static ar.com.macro.constant.Errores.ERROR_AGREGAR_DNI_PROFILE_IDCHECK_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_AGREGAR_DNI_PROFILE_SUBMIT_DOCUMENT;
import static java.lang.String.format;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.AgregarDniPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ValidarRolUsuarioRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.AgregarDniPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ValidarRolUsuarioResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ValidarRostroPersonaRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.CrearEvaluacionPerfilResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ValidarRostroPersonaResponse;
import ar.com.macro.enrolamiento.model.client.activedirectory.ActiveDirectoryService;
import ar.com.macro.enrolamiento.model.client.identityx.IdentityXClient;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.PerfilService;
import ar.com.macro.exceptions.AgregarDniPerfilException;
import ar.com.macro.exceptions.GeneralException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PerfilServiceImpl implements PerfilService {
	
	private IdentityXClient identityXClient;
	
	private ActiveDirectoryService activeDirectoryService;

	private String referenceIdSeparador;

	private DatosGeneralesService datosGeneralesService;

	private String nombreMicroServicioDatosGenerales;

	private String parametroConfigAplicacionIdentityX;

	private String sufijoIdRegistracion;

	private String parametroConfigPoliticaIdentityX;

	private String sufijoIdAuthetication;

	private String autheticationRequestDescriptionTemplate;

	private String parametroConfigPoliticaEvaluacionIdentityX;

	private String parametroMatchCreacionEvaluacion;
	
	private Environment env;

	public PerfilServiceImpl(
			IdentityXClient identityXClient,
			DatosGeneralesService datosGeneralesService,
			ActiveDirectoryService activeDirectoryService,
			Environment env,
		    @Value("${identityx.client.referenceid.separador.value}") String referenceIdSeparador,
			@Value("${daonengine.nombre.parametros.datos.generales}") String nombreMicroServicioDatosGenerales,
			@Value("${identityx.nombre.parametros.configuracion.politica.evaluacion}") String parametroConfigPoliticaEvaluacionIdentityX,
			@Value("${identityx.nombre.parametros.configuracion.politica.evaluacion.match}") String parametroMatchCreacionEvaluacion,
			@Value("${identityx.nombre.parametros.configuracion.aplicacion}") String parametroConfigAplicacionIdentityX,
			@Value("${identityx.registracion.id.sufijo}") String sufijoIdRegistracion,
			@Value("${identityx.nombre.parametros.configuracion.politica}") String parametroConfigPoliticaIdentityX,
			@Value("${identityx.authetication.id.sufijo}") String sufijoIdAuthetication,
			@Value("${identityx.authetication.request.description}") String autheticationRequestDescriptionTemplate
	) {
		this.identityXClient = identityXClient;
		this.datosGeneralesService = datosGeneralesService;
		this.activeDirectoryService = activeDirectoryService;
		this.env = env;
		this.referenceIdSeparador = referenceIdSeparador;
		this.nombreMicroServicioDatosGenerales = nombreMicroServicioDatosGenerales;
		this.parametroConfigPoliticaEvaluacionIdentityX = parametroConfigPoliticaEvaluacionIdentityX;
		this.parametroMatchCreacionEvaluacion = parametroMatchCreacionEvaluacion;
		this.parametroConfigAplicacionIdentityX = parametroConfigAplicacionIdentityX;
		this.sufijoIdRegistracion = sufijoIdRegistracion;
		this.parametroConfigPoliticaIdentityX = parametroConfigPoliticaIdentityX;
		this.sufijoIdAuthetication = sufijoIdAuthetication;
		this.autheticationRequestDescriptionTemplate = autheticationRequestDescriptionTemplate;
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

		String referenceId = usuario.getUserId() + CARACTER_GUION + referenceIdSeparador + CARACTER_GUION + System.currentTimeMillis();

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

			Optional<Registration> registration = identityXClient.obtenerRegistracion(generateRegistrationId(validarRostroPersonaRequest));

			Optional<Application> optAplicacion = identityXClient.obtenerRegistroAplicacion(aplicacionId);

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
    public ValidarRolUsuarioResponse validarRolUsuario(
        ValidarRolUsuarioRequest request, String xOperationID) {
  
      try {
        var token = activeDirectoryService.getAccessToken();
  
        var userRoles =
            request
                .getRoles()
                .parallelStream()
                .filter(
                    rol ->
                        activeDirectoryService.isUserInGroup(
                            request.getEmail(), env.getProperty(rol.getId()), token))
                .collect(Collectors.toList());
  
        return ValidarRolUsuarioResponse.of(userRoles);
        
      } catch (MacroException e) {
        log.error(e.getMessage(), e);
        throw e;
        
      } catch (Exception e) {
        log.error("Error inesperado validando si el usuario hace parte del rol.", e);
        throw new GeneralException(Errores.ERROR_VALIDANDO_USUARIO_GRUPO_AD);
      }
    }
}
