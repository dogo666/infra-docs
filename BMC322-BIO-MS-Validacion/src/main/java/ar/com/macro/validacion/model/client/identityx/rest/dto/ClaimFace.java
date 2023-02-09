package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ClaimFace extends BaseIdentityXDTO {
    String subtype;
    SensitiveData sensitiveData;
    Document document;
}
