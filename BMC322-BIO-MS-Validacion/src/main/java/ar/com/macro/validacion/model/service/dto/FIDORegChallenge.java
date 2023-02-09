
package ar.com.macro.validacion.model.service.dto;

import com.daon.identityx.rest.model.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FIDORegChallenge implements Serializable {

	String id;
	String challenge;
	String fidoRegistrationRequest;
	String fidoRegistrationResponse;
	String w3cMakeCredentialRequest;
	String w3cMakeCredentialResponse;
	byte[] tlsCertificate;
	String tlsUnique;
	byte[] cidPublicKey;
	String serverData;
	Long fidoResponseCode;
	String fidoResponseMsg;
	String relyingPartyUsername;
	String sponsorshipToken;
	String deviceStatus;
	
	public FIDORegChallenge(RegistrationChallenge regChallenge) {
		id=regChallenge.getId();
		challenge=regChallenge.getChallenge();
		fidoRegistrationRequest=regChallenge.getFidoRegistrationRequest();
		fidoRegistrationResponse=regChallenge.getFidoRegistrationResponse();
		w3cMakeCredentialRequest=regChallenge.getW3cMakeCredentialRequest();
		w3cMakeCredentialResponse=regChallenge.getW3cMakeCredentialResponse();
		tlsCertificate=regChallenge.getTlsCertificate();
		tlsUnique=regChallenge.getTlsUnique();
		cidPublicKey=regChallenge.getCidPublicKey();
		serverData=regChallenge.getServerData();
		fidoResponseCode=regChallenge.getFidoResponseCode();
		fidoResponseMsg=regChallenge.getFidoResponseMsg();
		relyingPartyUsername=regChallenge.getRelyingPartyUsername();
		sponsorshipToken=regChallenge.getSponsorshipToken();
		deviceStatus=regChallenge.getDeviceStatus();
	}
}
