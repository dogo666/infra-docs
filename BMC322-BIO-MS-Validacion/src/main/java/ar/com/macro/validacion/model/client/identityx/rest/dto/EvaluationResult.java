package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationResult {

	String id;
	String type;
	String created;
	String result;
	String failureReason;
	Double score;
	Double threshold;
	Double possibleMatchThreshold;
	PolicyDigitalOnboardingServices evaluationPolicy;
	String allowedPermissions;
	String href;
	Double fmr;
	Face claimFace;
	LivenessVideo challengeVideo;
	String challengeType;
	LivenessChallenge challenge;
	CandidateList candidates;
	Watchlist[] watchlists;
	RulesResults rulesResults;
}
