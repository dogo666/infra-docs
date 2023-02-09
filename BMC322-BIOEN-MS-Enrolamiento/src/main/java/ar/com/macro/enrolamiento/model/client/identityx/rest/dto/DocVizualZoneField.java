package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocVizualZoneField  implements Serializable {

	private static final long serialVersionUID = 1L;

	String fieldId;
	String name;
	String value;
	String source;
}
