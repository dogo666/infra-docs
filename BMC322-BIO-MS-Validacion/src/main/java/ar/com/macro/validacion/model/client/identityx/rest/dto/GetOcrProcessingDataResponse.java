package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOcrProcessingDataResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	String href;
	@JsonDeserialize(converter = DocMRZFieldConverter.class)
	Map<String, DocMRZField> mrz;

	@JsonDeserialize(converter = DocVizualZoneFieldConverter.class)
	Map<String, DocVizualZoneField> visualZone;

	@JsonDeserialize(converter = DocBarcodeFieldConverter.class)
	Map<String, DocBarcodeField> barcode;
	String allowedPermissions;
}
