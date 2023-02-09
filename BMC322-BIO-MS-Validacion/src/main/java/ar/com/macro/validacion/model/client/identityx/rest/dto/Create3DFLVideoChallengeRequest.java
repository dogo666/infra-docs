package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Create3DFLVideoChallengeRequest {

	String id;
}
