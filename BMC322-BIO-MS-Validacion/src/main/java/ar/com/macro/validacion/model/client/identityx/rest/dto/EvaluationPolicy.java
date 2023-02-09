package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class EvaluationPolicy extends BaseIdentityXDTO {
    String name;
}
