package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonOfInterest {

	String id;
	String created;
	String userId;
	String archived;
	String status;
	FaceData face;
	String comment;
	Tenant tenant;
	Watchlist watchlists;
	String source;
	String sourceId;
	String sourceAgency;
	String identifier;
	String surnameAndGivenName;
	String surname;
	String givenName;
	String allowedPermissions;
	String href;
}
