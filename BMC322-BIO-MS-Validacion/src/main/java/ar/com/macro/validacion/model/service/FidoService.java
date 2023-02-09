package ar.com.macro.validacion.model.service;


import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import ar.com.macro.validacion.model.service.dto.FIDORegChallenge;
import ar.com.macro.validacion.model.service.dto.FIDORegChallengeAndId;
import com.daon.identityx.rest.model.pojo.Authenticator;

public interface FidoService {

	FIDORegChallengeAndId crearRegistracion(String idusuario, String sponsorshipToken, String xOperationID);

	FIDORegChallengeAndId consultarRegistracion(String registrationChallengeId);

	FIDORegChallenge processRegistrationResponse(String idxId, String regChallengeHref, String fidoRegChallengeResponse);

	AuthenticationRequest crearAuthenticationRequest(String idusuario, String xOperationID);

	AuthenticationRequest validarAuthenticationRequest(String authenticationRequestId, String fidoAuthenticationResponse);

	AuthenticationRequest getAuthenticationRequestByUser(String idUsuario, String authenticationRequestHref);

	Authenticator[] getAutenticadores(String idUsuario);

	Authenticator archivarAutenticador(String id);
}
