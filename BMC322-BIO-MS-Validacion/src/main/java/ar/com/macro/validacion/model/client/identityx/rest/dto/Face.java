package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Face {

	String id;
	String subtype;
	String captured;
	String created;	
	FaceMetrics clientMetrics;
	FaceMetrics serverMetrics;
	Boolean readOnly;
	FaceImageData sensitiveData;
	Document document;
	LivenessVideo livenessVideo;
	EvaluationResult evaluationResult;
	String allowedPermissions;
	String href;
}
