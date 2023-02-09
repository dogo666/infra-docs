package ar.com.macro.validacion.model.client.identityx;


import java.util.Optional;
import com.daon.identityx.rest.model.pojo.Application;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.Authenticator;
import com.daon.identityx.rest.model.pojo.DataSample;
import com.daon.identityx.rest.model.pojo.Policy;
import com.daon.identityx.rest.model.pojo.Registration;
import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import com.daon.identityx.rest.model.pojo.User;
import com.identityx.clientSDK.collections.DataSampleCollection;
import ar.com.macro.validacion.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Create3DFLVideoChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DniImageData;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetFacesResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetIdChecksResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetImagenResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetSensitiveDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Submit3DFLVideoResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;

public interface IdentityXClient {

	/**
	 * Cliente para consumir servicio Create User Profile
	 *
	 * @param usuario datos de creacion de usuario
	 * @return objeto usuario creado
	 */
	User crearPerfilUsuario(User usuario);

	/**
	 * Cliente para consumir servicio Get User Registration With PNI
	 *
	 * @param idUsuario
	 * @return objeto usuario
	 */
	User obtenerRegistroUsuario(String idUsuario);

	void eliminarPerfilUsuario(User usuario);

	Optional<CreateEmptyIdCheckResponse> crearIdCheck(User usuario);

	Optional<CreateEmptyIdCheckResponse> crearIdCheck(User usuario, String referenceId);

	Optional<SubmitBothSidesDocumentResponse> enviarFrenteyDorsoDni(String id, String idCheck, BothSidesDocumentRequest bothSidesDocumentRequest);

	Optional<GetOcrProcessingDataResponse> obtenerDatosProcesamientoOCR(String id, String idCheck, String idDocument);

	/**
	 * Cliente para agregar Imagen de rostro
	 *
	 * @param user
	 * @param archivo base64
	 * @return
	 */
	DataSampleCollection agregarImagenRostroaPerfilUsuario(User user, String archivo);

	Optional<Application> obtenerRegistroAplicacion(String applicationId);

	Optional<Policy> obtenerRegistroPolitica(String policyId, String applicationHref);

	Optional<Registration> crearRegistracion(String registrationId, User usuario, Application application);

	Optional<Registration> obtenerRegistracion(String registrationId);

	Optional<AuthenticationRequest> crearPedidoAutenticacion(String authentication, String descripcion, Optional<Policy> policy, Optional<Registration> registracion);

	DataSample crearDataSampleParaEvaluacion(String archivo);

	Optional<CreateEvaluationResponse> crearEvaluacion(String idUser, String idCheck, String politicaEvaluacion);

	Optional<AuthenticationRequest>  actualizarPedidoAutenticacion(AuthenticationRequest createdRequest, DataSample verificationFaceDataSample);

	Optional<AuthenticationRequest>  consultarVerificacionRostro(String idverificacion);
	
	Optional<Create3DFLVideoChallengeResponse> crearVideo3DFL(String idUsuario, String idCheck, String livenessPolicyName);
	
	Optional<Submit3DFLVideoResponse> enviarVideo3DFL(String idUsuario, String idCheck, String idChallenge, String video);
	
	Optional<CreateEvaluationChallengeResponse> crearEvaluacionChallenge(String idUsuario, String idCheck, String evaluationPolicyName);

	Optional<CreateEmptyIdCheckResponse> crearIdCheckByUserAndLivenessPolicy(User usuario, String livenessPolicyName, String referenceId);

	Optional<GetFacesResponse> getFaces(User usuario,  String idCheck);

	Optional<GetImagenResponse> getImagen(User usuario,  String idCheck, String faceId);

	Optional<GetIdChecksResponse> getIdCheckByUser(User usuario);

	Optional<Create3DFLVideoChallengeResponse> getVideo3DFL(String idUsuario, String idCheck, String livenessPolicyName);

	Optional<RegistrationChallenge> addRegistrationChallenge(Registration reg, String sponsorshipToken,Policy policy);

	Optional<RegistrationChallenge> getRegistrationChallengeById(String regChallengeId);

	Optional<RegistrationChallenge> getRegistrationChallengeFromHref(String regChallengeFromHref);

	Optional<Registration> getRegistrationFromHref(String regChallengeHref) ;

	Optional<RegistrationChallenge> saveRegistrationChallenge(RegistrationChallenge regChallenge);

	Optional<AuthenticationRequest> createAuthenticationRequest(AuthenticationRequest authenticationRequest);

	Optional<AuthenticationRequest> getAuthenticationRequestRepo(String authenticationRequestHref);

	Optional<AuthenticationRequest> saveAuthenticationRequestRepo(AuthenticationRequest authenticationRequest);

	Optional<User> getUserByHref(String userHref);

	Optional<GetSensitiveDataResponse> getSensitiveData(String sensitiveData);

	Optional<Registration> findRegistration(User user);

	Optional<Authenticator[]> getAuthenticators(String authenticatorsHref);

	Optional<Authenticator> archiveAuthenticator(String authenticatorsHref);

	Optional<DniImageData> getDniImage(User user);
	
}