package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
