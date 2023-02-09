package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Create3DFLVideoChallengeResponse implements Serializable {

	String href;	
	String allowedPermissions;	
	PageMetadata metadata;
	PagePaging paging;
	LivenessChallenge[] items;
}
