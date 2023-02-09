package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Item extends BaseIdentityXDTO {
  String id;
  String created;
  String type;
  String result;
  double score;
  double threshold;
  double fmr;
  EvaluationPolicy evaluationPolicy;
  ClaimFace claimFace;
}
