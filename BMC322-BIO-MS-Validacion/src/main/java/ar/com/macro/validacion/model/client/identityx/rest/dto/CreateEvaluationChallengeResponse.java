package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEvaluationChallengeResponse implements Serializable {

	String id;
	String created;
	PolicyDigitalOnboardingServices evaluationPolicy;
	EvaluationResults results;
	String allowedPermissions;
	String href;
}
