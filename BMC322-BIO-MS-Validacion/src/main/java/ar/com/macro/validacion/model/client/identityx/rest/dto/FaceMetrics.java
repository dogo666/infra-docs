package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FaceMetrics {

	Double qualityScore;
	Double livenessScore;
	String allowedPermissions;
	String href;
}
