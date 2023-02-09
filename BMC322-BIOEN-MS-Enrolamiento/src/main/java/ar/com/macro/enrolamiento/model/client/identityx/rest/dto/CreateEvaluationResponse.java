package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CreateEvaluationResponse extends BaseIdentityXDTO {
  
  @JsonProperty("id")
  String idDocument;

  String created;
  EvaluationPolicy evaluationPolicy;
  Results results;
}
