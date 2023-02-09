package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Watchlist {

	String id;
	String name;
	String description;
	String created;
	String archived;
	Integer size;
	String status;
	Tenant tenant;
	PageOfResourcesLink members;
	String allowedPermissions;
	String href;
}
