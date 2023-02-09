package ar.com.macro.validacion.model.client.identityx.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;
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
