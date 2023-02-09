package ar.com.macro.enrolamiento.model.client.identityx;


import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.*;

import java.util.Optional;

import com.daon.identityx.rest.model.pojo.*;

import com.identityx.clientSDK.collections.DataSampleCollection;

public interface IdentityXClient {

	/**
	 * Cliente para consumir servicio Create User Profile
	 *
	 * @param idUsuario datos de creacion de usuario
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

	Optional<Application> obtenerRegistroAplicacion(String ApplicationId);

	Optional<Policy> obtenerRegistroPolitica(String policyId, String applicationHref);

	Registration crearRegistracion(String registrationId, User usuario, Application application);

	Optional<Registration> obtenerRegistracion(String registrationId);

	Optional<AuthenticationRequest> crearPedidoAutenticacion(String authentication, String descripcion, Optional<Policy> policy, Optional<Registration> registracion);

	DataSample crearDataSampleParaEvaluacion(String archivo);

	Optional<CreateEvaluationResponse> crearEvaluacion(String idUser, String idCheck, String politicaEvaluacion);

	Optional<AuthenticationRequest>  actualizarPedidoAutenticacion(AuthenticationRequest createdRequest, DataSample verificationFaceDataSample);
}