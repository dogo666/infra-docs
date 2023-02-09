package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEmptyIdCheckResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    String href;

    @JsonProperty("id")
    String idCheck;

    String created;
    String referenceId;
    String status;

    Documents documents;
    Faces faces;
    Evaluation evaluation;
    LivenessChallenges challenges;
    LivenessVideos videos;
    IdCheckSummary summary;
    PageOfResourcesLink reviews;
    Webhooks webhooks;
}
