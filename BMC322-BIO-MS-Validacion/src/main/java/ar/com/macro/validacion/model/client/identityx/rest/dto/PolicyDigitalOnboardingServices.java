package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PolicyDigitalOnboardingServices {

	String id;
	String name;
	String description;
	String created;
	String archived;
	String type;
	String status;
	
	String allowedPermissions;
	String href;
}
