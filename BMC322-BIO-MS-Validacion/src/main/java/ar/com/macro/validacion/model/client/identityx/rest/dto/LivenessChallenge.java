package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class LivenessChallenge {

	String id;
	String created;
	Integer index;
	String type;
	Integer timeout;
	Integer maxRetries;
	Double detectionThreshold;
	AdditionalConfig additionalConfig;
	String allowedPermissions;
	String href;
	String next;
}
