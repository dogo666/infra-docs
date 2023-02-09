package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Submit3DFLVideoResponse implements Serializable {

	String id;
	String subtype;
	String captured;
	String created;
	LivenessVideoMetrics clientMetrics;
	LivenessVideoMetrics serverMetrics;
	String videoFormat;
	VideoChallenge[] challenges;
	Boolean sensitiveDataPresent;
	String processingStatus;
	LivenessVideoProcessingErrors processingErrors;		
	String submitted;
	String completed;
	Integer timeToProcess;
	Integer elapsedTime;
	Integer retryCount;
	VideoData sensitiveData;
	String digest;
	String digestAlgorithm;
	String allowedPermissions;
	String href;
}
