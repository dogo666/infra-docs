package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SubmitBothSidesDocumentResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	String href;

	@JsonProperty("id")
	String idDocument;

	String created;
	String captureFormat;
	String captured;
	ClientDocumentCapture clientCapture;
	ServerProcessedDocument serverProcessed;
	Integer resubmissionCount;
	String allowedSensitiveDataPermissions;
}
