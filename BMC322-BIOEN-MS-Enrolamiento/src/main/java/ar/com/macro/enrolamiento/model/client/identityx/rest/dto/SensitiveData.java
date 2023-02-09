package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SensitiveData extends BaseIdentityXDTO{

}
