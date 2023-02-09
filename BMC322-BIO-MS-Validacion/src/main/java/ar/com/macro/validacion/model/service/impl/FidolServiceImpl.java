package ar.com.macro.validacion.model.service.impl;

import static ar.com.macro.constant.Errores.*;
import static com.daon.identityx.rest.model.def.AuthenticationRequestStatusEnum.COMPLETED_FAILURE;
import static com.daon.identityx.rest.model.def.AuthenticationRequestStatusEnum.COMPLETED_SUCCESSFUL;
import static com.daon.identityx.rest.model.def.AuthenticationRequestStatusEnum.PENDING;
import static java.util.Objects.nonNull;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;
import javax.annotation.PostConstruct;

import com.daon.identityx.rest.model.pojo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.daon.identityx.rest.model.def.RegistrationChallengeStatusEnum;
import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.constant.Errores;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.validacion.model.client.identityx.IdentityXClient;
import ar.com.macro.validacion.model.service.DatosGeneralesService;
import ar.com.macro.validacion.model.service.FidoService;
import ar.com.macro.validacion.model.service.PerfilService;
import ar.com.macro.validacion.model.service.dto.FIDORegChallenge;
import ar.com.macro.validacion.model.service.dto.FIDORegChallengeAndId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FidolServiceImpl implements FidoService {

	private static final String FIDO_AUTHENTICATION_TYPE = "FI";

	private static final String FIDO_DESCRIPCION = "Default";

    private static final BiPredicate<AuthenticationRequest, User> AUTHENTICATION_BELONGS_TO_USER =
        (auth, user) -> user != null && auth.getUser().getHref().equals(user.getHref());

	private final String nombreMicroServicioDatosGenerales;

	private final String parametroConfigAplicacionIdentityX;

	private final String parametroConfigFidoPoliticaRegIdentityX;

	private final String parametroConfigFidoPoliticaAuthIdentityX;

	private final IdentityXClient identityXClient;

	private final DatosGeneralesService datosGeneralesService;

	private final PerfilService perfilService;

	public FidolServiceImpl(
			@Value("${daonengine.nombre.parametros.datos.generales}") String nombreMicroServicioDatosGenerales,
			@Value("${identityx.nombre.parametros.configuracion.aplicacion}") String parametroConfigAplicacionIdentityX,
			@Value("${identityx.nombre.parametros.configuracion.fido.politica.reg}") String parametroConfigFidoPoliticaRegIdentityX,
			@Value("${identityx.nombre.parametros.configuracion.fido.politica.auth}") String parametroConfigFidoPoliticaAuthIdentityX,
			IdentityXClient identityXClient,
			DatosGeneralesService datosGeneralesService,
			PerfilService perfilService) {
		this.nombreMicroServicioDatosGenerales = nombreMicroServicioDatosGenerales;
		this.parametroConfigAplicacionIdentityX = parametroConfigAplicacionIdentityX;
		this.parametroConfigFidoPoliticaRegIdentityX = parametroConfigFidoPoliticaRegIdentityX;
		this.parametroConfigFidoPoliticaAuthIdentityX = parametroConfigFidoPoliticaAuthIdentityX;
		this.identityXClient = identityXClient;
		this.datosGeneralesService = datosGeneralesService;
		this.perfilService = perfilService;
	}

	@PostConstruct
	public void init(){
		log.info("identityx.nombre.parametros.configuracion.aplicacion : {} " +
				 "identityx.nombre.parametros.configuracion.fido.politica.reg : {}" +
				 "identityx.nombre.parametros.configuracion.fido.politica.auth : {}",
				parametroConfigAplicacionIdentityX,parametroConfigFidoPoliticaRegIdentityX,parametroConfigFidoPoliticaAuthIdentityX);
	}

	@Override
	public FIDORegChallengeAndId crearRegistracion(String idusuario, String sponsorshipToken, String xOperationID){

		log.debug("crearRegistracion called for " + idusuario + " : " + sponsorshipToken);

		try {

			User user = identityXClient.obtenerRegistroUsuario(idusuario);
			Optional<Registration> registration = identityXClient.findRegistration(user) ;

			 if(registration.isEmpty()){
				 Registration reg = new Registration();
				 reg.setUser(user);
				 reg.setApplication(getApplication(xOperationID).get());
				 String referenceId = perfilService.generarReferenceId(user);
				 reg.setRegistrationId(referenceId);
				 registration = Optional.of(reg);
			 }

			log.debug("User registration {} ", registration.get().getHref());
			Optional<RegistrationChallenge> regChallenge = identityXClient.addRegistrationChallenge(registration.get(), sponsorshipToken, getPolityReg(xOperationID).get());
			FIDORegChallengeAndId regChallengeAndId = new FIDORegChallengeAndId();
			regChallengeAndId.setIdXId(user.getId());
			regChallengeAndId.setIdXUsername(user.getUserId());
			regChallengeAndId.setRegistrationChallenge(regChallenge.get());

			return regChallengeAndId;

		}catch (MacroException ex) {
			log.error(Errores.ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE.getMensaje(), ex);
			throw ex;
		}catch (Exception ex) {
			log.error(Errores.ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE.getMensaje(), ex);
			throw new IdentityXException(Errores.ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE);
		}
	}

	@Override
	public FIDORegChallengeAndId consultarRegistracion(String registrationChallengeId){

		log.debug("consultarRegistracion called for {}" , registrationChallengeId );

		try {
			RegistrationChallenge regChallenge = identityXClient
					.getRegistrationChallengeById(registrationChallengeId)
					.orElseThrow(() -> {
						log.error(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE + " {}", registrationChallengeId);
						return new IdentityXException(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE);
					});

			FIDORegChallengeAndId fidoRegChallenge = new FIDORegChallengeAndId();
			fidoRegChallenge.setRegistrationChallenge(regChallenge);
			return fidoRegChallenge;

		}catch (MacroException ex) {
			log.error(ERROR_FIDO_CONSULTAR_REGISTRATION_CHALLENGE.getMensaje(), ex);
			throw ex;
		}catch (Exception ex) {
			log.error(ERROR_FIDO_CONSULTAR_REGISTRATION_CHALLENGE.getMensaje(), ex);
			throw new IdentityXException(ERROR_FIDO_CONSULTAR_REGISTRATION_CHALLENGE);
		}

	}

	private Optional<Application> getApplication(String xOperationID) {
		String aplicacionId = datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(
				xOperationID,nombreMicroServicioDatosGenerales, parametroConfigAplicacionIdentityX);
		return identityXClient.obtenerRegistroAplicacion(aplicacionId);
	}

	private Optional<Policy> getPolityReg(String xOperationID){
		String politicaId = datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(
				xOperationID, nombreMicroServicioDatosGenerales, parametroConfigFidoPoliticaRegIdentityX);
		log.debug("PolityReg  {} - {} politicaId : {} " ,nombreMicroServicioDatosGenerales ,parametroConfigFidoPoliticaRegIdentityX, politicaId);
		return identityXClient.obtenerRegistroPolitica(politicaId, getApplication(xOperationID).get().getHref());
	}

	private Optional<Policy> getPolityAuth(String xOperationID){
		String politicaId = datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(
				xOperationID, nombreMicroServicioDatosGenerales, parametroConfigFidoPoliticaAuthIdentityX);
		return identityXClient.obtenerRegistroPolitica(politicaId, getApplication(xOperationID).get().getHref());
	}

	@Override
	public FIDORegChallenge processRegistrationResponse(String idxId, String regChallengeHref, String fidoRegChallengeResponse) {

		try {

			RegistrationChallenge regChallenge = identityXClient
					.getRegistrationChallengeById(regChallengeHref)
					.orElseThrow(() -> {
						log.error(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE + regChallengeHref);
						return new IdentityXException(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE);
					});

			Registration registration = identityXClient.getRegistrationFromHref(regChallenge.getRegistration().getHref()).get();
			String userId = this.getIdFromHref(registration.getUser().getHref());
			if (!idxId.equals(userId)) {
				log.error("La respuesta de registro no pertenece al usuario de IdentityX con ID: {} Esperando: {}", idxId, userId );
				throw new IdentityXException(ERROR_FIDO_USER_REGISTRATION_CHALLENGE);
			}

			regChallenge.setFidoRegistrationResponse(fidoRegChallengeResponse);
			regChallenge = identityXClient.saveRegistrationChallenge(regChallenge).get();

			if (regChallenge.getStatus() == RegistrationChallengeStatusEnum.COMPLETED_FAILURE) {
				throw new IdentityXException(Errores.ERROR_FIDO_PROCESAR_REGISTRATION_CHALLENGE);
			}
			return new FIDORegChallenge(regChallenge);
		}catch (MacroException ex) {
			log.error(Errores.ERROR_FIDO_PROCESAR_REGISTRATION_CHALLENGE.getMensaje(), ex);
			throw ex;
		}catch (Exception ex) {
			log.error(Errores.ERROR_FIDO_PROCESAR_REGISTRATION_CHALLENGE.getMensaje(), ex);
			throw new IdentityXException(Errores.ERROR_FIDO_PROCESAR_REGISTRATION_CHALLENGE);
		}
	}

	protected String getIdFromHref(String href) {

		int lastIndex = href.lastIndexOf("/");
		if (lastIndex < 0 || lastIndex > href.length()) {
			return "";
		} else {
			return href.substring(lastIndex+1);
		}
	}

	@Override
	public AuthenticationRequest crearAuthenticationRequest(String idusuario, String xOperationID) {

		try {
			User user = identityXClient.obtenerRegistroUsuario(idusuario);
			AuthenticationRequest request = new AuthenticationRequest();
			request.setUser(user);
			request.setPolicy(getPolityAuth(xOperationID).get());
			request.setApplication( getApplication(xOperationID).get());
			request.setDescription(FIDO_DESCRIPCION);
			request.setType(FIDO_AUTHENTICATION_TYPE);
			request.setAuthenticationRequestId(UUID.randomUUID().toString());
			request.setOneTimePasswordEnabled(false);
			request = identityXClient.createAuthenticationRequest(request).get();
			request.setAuthenticationRequestId(request.getId());
			log.debug("Se agregó una solicitud de autenticación, - authRequestId: {}", request.getId());
			return request;

		}catch (MacroException ex) {
			log.error(Errores.ERROR_FIDO_CREAR_AUTENTICACION.getMensaje(), ex);
			throw ex;
		}catch (Exception ex) {
			log.error(Errores.ERROR_FIDO_CREAR_AUTENTICACION.getMensaje(), ex);
			throw new IdentityXException(Errores.ERROR_FIDO_CREAR_AUTENTICACION);
		}
	}

	@Override
	public AuthenticationRequest validarAuthenticationRequest(String AuthenticationRequestId, String fidoAuthenticationResponse){
		try {
			AuthenticationRequest request = getAuthenticationRequest(AuthenticationRequestId);
			request.setFidoAuthenticationResponse(fidoAuthenticationResponse);
			request = identityXClient.saveAuthenticationRequestRepo(request).get();

			if (PENDING.equals(request.getStatus()) || request.getStatus() == COMPLETED_SUCCESSFUL) {
				if (nonNull(request.getUser())) {
					User user = identityXClient.getUserByHref(request.getUser().getHref()).get();
					request.setUser(user);
				}
				return request;
			}

			if (COMPLETED_FAILURE.equals(request.getStatus())) {
				if (request.getFidoResponseCode() != null) {
					log.error("Fido Response Code 1501");
					if(request.getFidoResponseCode()==1501) {
						return request;
					}
				}
				throw new IdentityXException(Errores.ERROR_FIDO_NO_VALIDAR_AUTENTICACION);
			}

			log.error(Errores.ERROR_FIDO_NO_VALIDAR_AUTENTICACION.getMensaje());
			throw new IdentityXException(Errores.ERROR_FIDO_NO_VALIDAR_AUTENTICACION);

		}catch (MacroException ex) {
			log.error(Errores.ERROR_FIDO_VALIDAR_AUTENTICACION.getMensaje(), ex);
			throw ex;
		}catch (Exception ex) {
			log.error(Errores.ERROR_FIDO_VALIDAR_AUTENTICACION.getMensaje(), ex);
			throw new IdentityXException(Errores.ERROR_FIDO_VALIDAR_AUTENTICACION);
		}
	}

    @Override
    public AuthenticationRequest getAuthenticationRequestByUser(
        final String idUsuario, final String authenticationRequestHref) {

      try {
        var authenticationRequest = getAuthenticationRequest(authenticationRequestHref);
        var user = identityXClient.obtenerRegistroUsuario(idUsuario);
        
        if (AUTHENTICATION_BELONGS_TO_USER.negate().test(authenticationRequest, user)) {
          log.error(
              "El Request de Autenticación no corresponde para el idUsuario recibido.");
          throw new IdentityXException(ERROR_GET_AUTHENTICATION_REQUEST_USERID_NO_CORRESPONDE);
        }

        return authenticationRequest;

      } catch (MacroException ex) {
        throw ex;
      } catch (Exception ex) {
        log.error(ERROR_GET_AUTHENTICATION_REQUEST.getMensaje(), ex);
        throw new IdentityXException(ERROR_GET_AUTHENTICATION_REQUEST);
      }
    }

    private AuthenticationRequest getAuthenticationRequest(final String authenticationRequestHref) {

      return identityXClient
          .getAuthenticationRequestRepo(authenticationRequestHref)
          .orElseThrow(
              () -> {
                log.error(
                    Errores.ERROR_FIDO_NO_AUTENTICACION_REQUEST + "{}", authenticationRequestHref);
                throw new IdentityXException(Errores.ERROR_FIDO_NO_AUTENTICACION_REQUEST);
              });
    }

	@Override
	public	Authenticator[] getAutenticadores(final String idUsuario){
		try {
			User user = identityXClient.obtenerRegistroUsuario(idUsuario);
			var autenticadores = identityXClient
				.getAuthenticators(user.getAuthenticators().getHref())
				.orElseThrow(
					() -> {
						log.error(
						ERROR_FIDO_OBTENIENDO_AUTENTICADORES + " {}", user.getAuthenticators().getHref());
						throw new IdentityXException(ERROR_FIDO_OBTENIENDO_AUTENTICADORES);
					});
			return autenticadores;
		} catch (MacroException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ERROR_FIDO_OBTENIENDO_AUTENTICADORES.getMensaje(), ex);
			throw new IdentityXException(ERROR_FIDO_OBTENIENDO_AUTENTICADORES);
		}
	}

	@Override
	public	Authenticator archivarAutenticador(final String id){
		try {
			var autenticadorArchivado = identityXClient
					.archiveAuthenticator(id)
					.orElseThrow(
							() -> {
								log.error(
										ERROR_FIDO_ARCHIVANDO_AUTENTICADOR + " {}", id);
								throw new IdentityXException(ERROR_FIDO_ARCHIVANDO_AUTENTICADOR);
							});
			return autenticadorArchivado;
		} catch (MacroException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ERROR_FIDO_ARCHIVANDO_AUTENTICADOR.getMensaje(), ex);
			throw new IdentityXException(ERROR_FIDO_ARCHIVANDO_AUTENTICADOR);
		}
	}
}
