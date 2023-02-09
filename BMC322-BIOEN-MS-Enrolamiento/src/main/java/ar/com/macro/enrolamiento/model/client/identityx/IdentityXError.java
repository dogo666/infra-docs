package ar.com.macro.enrolamiento.model.client.identityx;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class IdentityXError {

	String httpStatus;
	String message;
	String code;
}
