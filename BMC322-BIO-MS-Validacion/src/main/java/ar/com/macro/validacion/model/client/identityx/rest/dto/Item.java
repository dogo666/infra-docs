package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.*;
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
