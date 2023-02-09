package ar.com.macro.enrolamiento.model.service;

import com.identityx.clientSDK.base.BaseRepoFactory;
import com.identityx.clientSDK.base.RestClient;
import com.identityx.clientSDK.def.ICredentialsProvider;
import com.identityx.clientSDK.exceptions.IdxRestException;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * DigitalOnboardingRepository.
 *
 * @author globant
 * @version 1.0
 * @since 2021-11-25
 */
@Slf4j
public class DigitalOnboardingRepository extends BaseRepoFactory {

  private static String BASE_URL;
  private static final String CREATE_EVALUATION =
      "rest/v1/users/{idUser}/idchecks/{idCheck}/evaluation";
  private static final String SEND_DOCUMENT = "rest/v1/users/{id}/idchecks/{idCheck}/documents";
  private static final String CREATE_ID_CHECK_URI = "rest/v1/users/{id}/idchecks";
  private static final String GET_OCR_URI =
      "rest/v1/users/{id}/idchecks/{idCheck}/documents/{idDocument}/serverProcessed/ocrData/sensitiveData";

  public DigitalOnboardingRepository(final ICredentialsProvider credentialProvider)
      throws IdxRestException {
    init(
        credentialProvider.getApiKey(),
        credentialProvider.getResponseApiKey(),
        credentialProvider.getBaseUrl(),
        null,
        null,
        true);
  }

  // --

  @Override
  protected void initRepos(final RestClient restClient, final String baseUrl) {
    BASE_URL = baseUrl;
    log.info("repositorio de sdk [identity-x] inicializado exitosamente para url {}", baseUrl);
  }

  // --

  public CreateEvaluationResponse createEvaluation(
      final String idUser, final String idCheck, final String policyName) throws IdxRestException {

    log.info("llamando create-evaluation endpoint [identity-x] desde sdk para idUser {}", idUser);

    var requestDetails =
        DigitalOnboardingSDKUtils.defineRequestArtifacts(CREATE_EVALUATION, idUser, idCheck);
    var uri = requestDetails._3();

    var queryString = requestDetails._2();
    queryString.put("evaluationPolicyName", policyName);

    SDKCall<CreateEvaluationResponse> call =
        () ->
            getRestClient().post(null, BASE_URL + uri, queryString, CreateEvaluationResponse.class);

    return DigitalOnboardingSDKUtils.callAndMeasureTime(call, uri);
  }

  // --

  public SubmitBothSidesDocumentResponse sendDocument(
      final String id, final String idCheck, final SubmitBothSidesDocumentRequest request)
      throws IdxRestException {

    log.info("llamando send-document endpoint [identity-x] desde sdk para id {}", id);
    var requestDetails = DigitalOnboardingSDKUtils.defineRequestArtifacts(SEND_DOCUMENT, id, idCheck);
    var uri = requestDetails._3();

    SDKCall<SubmitBothSidesDocumentResponse> call =
        () ->
            getRestClient()
                .post(
                    request,
                    BASE_URL + uri,
                    SubmitBothSidesDocumentResponse.class,
                    requestDetails._1());

    return DigitalOnboardingSDKUtils.callAndMeasureTime(call, uri);
  }

  // --

  public CreateEmptyIdCheckResponse createIdCheck(
      final String id, final CreateEmptyIdCheckRequest idCheckRequest) throws IdxRestException {

    log.info("llamando create-idCheck endpoint [identity-x] desde sdk para id {}", id);
    var requestDetails = DigitalOnboardingSDKUtils.defineRequestArtifacts(CREATE_ID_CHECK_URI, id);
    var uri = requestDetails._3();

    SDKCall<CreateEmptyIdCheckResponse> call =
        () ->
            getRestClient()
                .post(
                    idCheckRequest,
                    BASE_URL + uri,
                    CreateEmptyIdCheckResponse.class,
                    requestDetails._1());

    return DigitalOnboardingSDKUtils.callAndMeasureTime(call, uri);
  }

  // --

  public GetOcrProcessingDataResponse getOCR(
      final String id, final String idCheck, final String idDocument) throws IdxRestException {

    log.info("llamando get-ocrData endpoint [identity-x] desde sdk para id {}", id);
    var requestDetails =
        DigitalOnboardingSDKUtils.defineRequestArtifacts(GET_OCR_URI, id, idCheck, idDocument);
    var uri = requestDetails._3();

    SDKCall<GetOcrProcessingDataResponse> call =
        () ->
            getRestClient()
                .get(
                    requestDetails._2(),
                    BASE_URL + uri,
                    GetOcrProcessingDataResponse.class,
                    requestDetails._1());

    return DigitalOnboardingSDKUtils.callAndMeasureTime(call, uri);
  }
}
