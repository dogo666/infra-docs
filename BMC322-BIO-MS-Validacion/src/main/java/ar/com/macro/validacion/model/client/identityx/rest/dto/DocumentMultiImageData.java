package ar.com.macro.validacion.model.client.identityx.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentMultiImageData {

	DocumentSensitiveImageData sensitiveData;
	String type;
}
