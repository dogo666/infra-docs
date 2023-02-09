
package ar.com.macro.validacion.model.service.dto;

import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FIDORegChallengeAndId {

	String idXId;
	String idXUsername;
	RegistrationChallenge registrationChallenge;

}
