package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class GetVideoChallengesResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	String href;
	Metadata metadata;
	Paging paging;

	Create3DFLVideoChallengeResponse[] items;
}



