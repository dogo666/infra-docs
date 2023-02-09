package ar.com.macro.validacion.model.service;

import static ar.com.macro.validacion.model.service.DigitalOnboardingSDKUtils.callAndMeasureTime;
import static ar.com.macro.validacion.model.service.DigitalOnboardingSDKUtils.defineRequestArtifacts;
import static java.lang.String.format;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import com.identityx.clientSDK.base.BaseRepoFactory;
import com.identityx.clientSDK.base.RestClient;
import com.identityx.clientSDK.collections.DataSampleCollection;
import com.identityx.clientSDK.collections.EnrollmentCollection;
import com.identityx.clientSDK.def.ICredentialsProvider;
import com.identityx.clientSDK.exceptions.IdxRestException;
import ar.com.macro.validacion.model.client.identityx.rest.dto.ClientDocumentCapture;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Create3DFLVideoChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DocumentCollection;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetFacesResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetIdChecksResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetImagenResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetSensitiveDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Submit3DFLVideoRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Submit3DFLVideoResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
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

    private static final String CREATE_EVALUATION = "rest/v1/users/{idUser}/idchecks/{idCheck}/evaluation";
    private static final String SEND_DOCUMENT = "rest/v1/users/{id}/idchecks/{idCheck}/documents";
    private static final String CREATE_ID_CHECK_URI = "rest/v1/users/{id}/idchecks";
    private static final String GET_OCR_URI =  "rest/v1/users/{id}/idchecks/{idCheck}/documents/{idDocument}/serverProcessed/ocrData/sensitiveData";
    private static final String CREATE_VIDEO_CHALLENGE_URI = "rest/v1/users/{id}/idchecks/{idCheck}/challenges";
    private static final String SUBIR_VIDEO_URI = "rest/v1/users/{id}/idchecks/{idCheck}/videos";
    private static final String EJECUTAR_CREATE_EVALUATION = "rest/v1/users/{id}/idchecks/{idCheck}/documents/evaluation";
    private static final String CREATE_ID_CHECK_2_URI = "rest/v1/users/{userId}/idchecks";
    private static final String GET_FACES_URI = "rest/v1/users/{userId}/idchecks/{idcheck}/faces";
    private static final String GET_IMAGEN_URI = "/rest/v1/users/{userId}/idchecks/{idcheck}/faces/{faceId}/sensitiveData";
    private String baseUrl;

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

    @Override
    protected void initRepos(final RestClient restClient, final String baseUrl) {
        this.baseUrl = baseUrl;
        log.info("repositorio de sdk [identity-x] inicializado exitosamente para url {}", baseUrl);
    }

    public CreateEvaluationResponse createEvaluation(
            final String idUser, final String idCheck, final String policyName) throws IdxRestException {

        log.info("llamando create-evaluation endpoint [identity-x] desde sdk para idUser {}", idUser);

        var requestDetails = defineRequestArtifacts(CREATE_EVALUATION, idUser, idCheck);
        var uri = requestDetails._3();
        var queryString = requestDetails._2();
        queryString.put("evaluationPolicyName", policyName);

        SDKCall<CreateEvaluationResponse> call =
                () ->
                        getRestClient()
                                .post(
                                    null, baseUrl + uri,
                                    queryString,
                                    CreateEvaluationResponse.class);

        return callAndMeasureTime(call, uri);
    }

    public SubmitBothSidesDocumentResponse sendDocument(
            final String id, final String idCheck, final SubmitBothSidesDocumentRequest request)
            throws IdxRestException {

        log.info("llamando send-document endpoint [identity-x] desde sdk para id {}", id);

        var requestDetails = defineRequestArtifacts(SEND_DOCUMENT, id, idCheck);
        var uri = requestDetails._3();

        SDKCall<SubmitBothSidesDocumentResponse> call =
                () ->
                        getRestClient()
                                .post(
                                        request,
                                        baseUrl + uri,
                                        SubmitBothSidesDocumentResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public CreateEmptyIdCheckResponse createIdCheck(
            final String id, final CreateEmptyIdCheckRequest idCheckRequest) throws IdxRestException {

        log.info("llamando create-idCheck endpoint [identity-x] desde sdk para id {}", id);

        var requestDetails = defineRequestArtifacts(CREATE_ID_CHECK_URI, id);
        var uri = requestDetails._3();

        SDKCall<CreateEmptyIdCheckResponse> call =
                () ->
                        getRestClient()
                                .post(
                                        idCheckRequest,
                                        baseUrl + uri,
                                        CreateEmptyIdCheckResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public CreateEmptyIdCheckResponse ejecutarCrearIdCheckByUserAndLivenessPolic(
            final String id, final String livenessPolic, CreateEmptyIdCheckRequest idCheckRequest) throws IdxRestException {

        log.info("llamando create-idCheck endpoint [identity-x] desde sdk para id {}", id);

        var requestDetails = defineRequestArtifacts(CREATE_ID_CHECK_2_URI, id);
        var uri = requestDetails._3();
        var queryString = requestDetails._2();
        queryString.put("livenessPolicyName", livenessPolic);

        SDKCall<CreateEmptyIdCheckResponse> call =
                () ->
                        getRestClient()
                                .post(
                                        idCheckRequest,
                                        baseUrl + uri,
                                        queryString,
                                        CreateEmptyIdCheckResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public GetIdChecksResponse ejecutarGetIdCheckByUser(
            final String id) throws IdxRestException {

        log.info("llamando get-idCheck endpoint [identity-x] desde sdk para id {}", id);

        var requestDetails = defineRequestArtifacts(CREATE_ID_CHECK_2_URI, id);
        var uri = requestDetails._3();

        SDKCall<GetIdChecksResponse> call =
                () ->
                        getRestClient()
                                .get(
                                        requestDetails._2(),
                                        baseUrl + uri,
                                        GetIdChecksResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public GetOcrProcessingDataResponse getOCR(
            final String id, final String idCheck, final String idDocument) throws IdxRestException {

        log.info("llamando get-ocrData endpoint [identity-x] desde sdk para id {}", id);

        var requestDetails = defineRequestArtifacts(GET_OCR_URI, id, idCheck, idDocument);
        var uri = requestDetails._3();

        SDKCall<GetOcrProcessingDataResponse> call =
                () ->
                        getRestClient()
                                .get(
                                        requestDetails._2(),
                                        baseUrl + uri,
                                        GetOcrProcessingDataResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public Create3DFLVideoChallengeResponse crearVideoChallenge(
            final String id, final String idCheck, final String livenessPolicyName) throws IdxRestException {

        log.info("llamando create-video-Challenge endpoint [identity-x] desde sdk para id {}", id);

        var requestDetails = defineRequestArtifacts(CREATE_VIDEO_CHALLENGE_URI, id, idCheck);
        var uri = requestDetails._3();
        var queryString = requestDetails._2();
        queryString.put("livenessPolicyName", livenessPolicyName);

        SDKCall<Create3DFLVideoChallengeResponse> call =
                () ->
                        getRestClient()
                                .post(
                                        null,
                                        baseUrl + uri,
                                        queryString,
                                        Create3DFLVideoChallengeResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public Create3DFLVideoChallengeResponse getVideoChallenge(
            final String id, final String idCheck, final String livenessPolicyName) throws IdxRestException {

        log.info("llamando get-video-Challenge endpoint [identity-x] desde sdk para id {} idCheck {} livenessPolicyName {}", id, idCheck, livenessPolicyName);

        var requestDetails = defineRequestArtifacts(CREATE_VIDEO_CHALLENGE_URI, id, idCheck);
        var uri = requestDetails._3();
        var queryString = requestDetails._2();
        queryString.put("livenessPolicyName", livenessPolicyName);

        SDKCall<Create3DFLVideoChallengeResponse> call =
                () ->
                        getRestClient()
                                .get(
                                        queryString,
                                        baseUrl + uri,
                                        Create3DFLVideoChallengeResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public Submit3DFLVideoResponse subirVideo(
            final String id, final String idCheck, final Submit3DFLVideoRequest submit3DFLVideoRequest) throws IdxRestException {

        log.info("llamando subir-Video endpoint [identity-x] desde sdk para id {}", id);

        var requestDetails = defineRequestArtifacts(SUBIR_VIDEO_URI, id,idCheck);
        var uri = requestDetails._3();

        SDKCall<Submit3DFLVideoResponse> call =
                () ->
                        getRestClient()
                                .post(
                                        submit3DFLVideoRequest,
                                        baseUrl + uri,
                                        Submit3DFLVideoResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public CreateEvaluationChallengeResponse ejecutarCreateEvaluation(
            final String idUser, final String idCheck, final String policyName) throws IdxRestException {

        log.info("llamando ejecutar-create-evaluation endpoint [identity-x] desde sdk para idUser {}", idUser);

        var requestDetails = defineRequestArtifacts(EJECUTAR_CREATE_EVALUATION, idUser, idCheck);
        var uri = requestDetails._3();
        var queryString = requestDetails._2();
        queryString.put("evaluationPolicyName", policyName);

        SDKCall<CreateEvaluationChallengeResponse> call =
                () ->
                        getRestClient().post(null,
                                        baseUrl + uri,
                                            queryString,
                                            CreateEvaluationChallengeResponse.class);

        return callAndMeasureTime(call, uri);
    }

    public GetFacesResponse getFaces(final String userId, final String idCheck) throws IdxRestException {

        log.info("llamando get-Faces endpoint [identity-x] desde sdk para userId {} idCheck {}", userId,idCheck);

        var requestDetails = defineRequestArtifacts(GET_FACES_URI, userId, idCheck);
        var uri = requestDetails._3();

        SDKCall<GetFacesResponse> call =
                () ->
                        getRestClient()
                                .get(
                                        requestDetails._2(),
                                        baseUrl + uri,
                                        GetFacesResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public GetImagenResponse getImagen(final String userId, final String idCheck, final String faceId) throws IdxRestException {

        log.info("llamando get-Faces endpoint [identity-x] desde sdk para userId {} idCheck {} faceId {}", userId,idCheck, faceId);

        var requestDetails = defineRequestArtifacts(GET_IMAGEN_URI, userId, idCheck,faceId);
        var uri = requestDetails._3();

        SDKCall<GetImagenResponse> call =
                () ->
                        getRestClient()
                                .get(
                                        requestDetails._2(),
                                        baseUrl + uri,
                                        GetImagenResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }


    public GetSensitiveDataResponse getSensitiveData(final String urlGeTSensitiveData) throws IdxRestException, MalformedURLException {

        log.info("llamando get-sensitive-data endpoint [identity-x] desde sdk para url {}", urlGeTSensitiveData);

        var requestDetails = defineRequestArtifacts(urlGeTSensitiveData);
        var uri = requestDetails._3();
        var url = getURL();

        SDKCall<GetSensitiveDataResponse> call =
                () ->
                        getRestClient()
                                .get(
                                        requestDetails._2(),
                                        url + uri ,
                                        GetSensitiveDataResponse.class,
                                        requestDetails._1());

        return callAndMeasureTime(call, uri);
    }

    public EnrollmentCollection getEnrollments(final String href)
        throws IdxRestException, MalformedURLException {
  
      log.info("llamando enrollments endpoint [identity-x] desde sdk para url {}", href);
  
      var requestDetails = defineRequestArtifacts(href);
      var uri = requestDetails._3();
      var url = getURL();
  
      SDKCall<EnrollmentCollection> call =
          () ->
              getRestClient()
                  .get(
                      requestDetails._2(),
                      url + uri,
                      EnrollmentCollection.class,
                      requestDetails._1());
  
      return callAndMeasureTime(call, uri);
    }

    public DataSampleCollection getSamples(final String href)
        throws IdxRestException, MalformedURLException {
      
      log.info("llamando samples endpoint [identity-x] desde sdk para url {}", href);
  
      var requestDetails = defineRequestArtifacts(href);
      var uri = requestDetails._3();
      var url = getURL();
  
      SDKCall<DataSampleCollection> call =
          () ->
              getRestClient()
                  .get(
                      requestDetails._2(),
                      url + uri,
                      DataSampleCollection.class,
                      requestDetails._1());
  
      return callAndMeasureTime(call, uri);
    }

    public GetIdChecksResponse getIdchecksByUser(final String daonId, Map<String, String> queryParams)
        throws IdxRestException {
  
      log.info("llamando idchecks endpoint [identity-x] desde sdk para id {}", daonId);
  
      var requestDetails = defineRequestArtifacts(CREATE_ID_CHECK_URI, daonId);
      var uri = requestDetails._3();
      
      if (Objects.nonNull(queryParams)) {
        queryParams
            .entrySet()
            .stream()
            .forEach(
                entrySet -> requestDetails._2.put(entrySet.getKey(), queryParams.get(entrySet.getKey())));
      }
  
      SDKCall<GetIdChecksResponse> call =
          () ->
              getRestClient()
                  .get(
                      requestDetails._2(),
                      baseUrl + uri,
                      GetIdChecksResponse.class,
                      requestDetails._1());
  
      return callAndMeasureTime(call, uri);
    }

    public CreateEmptyIdCheckResponse getIdChecks(final String href) throws MalformedURLException, IdxRestException {
      log.info("llamando idchecks endpoint [identity-x] desde sdk para url {}", href);
      
      var requestDetails = defineRequestArtifacts(href);
      var uri = requestDetails._3();
      var url = getURL();
  
      SDKCall<CreateEmptyIdCheckResponse> call =
          () ->
              getRestClient()
                  .get(
                      requestDetails._2(),
                      url + uri,
                      CreateEmptyIdCheckResponse.class,
                      requestDetails._1());
  
      return callAndMeasureTime(call, uri);
    }

    public DocumentCollection getDocuments(final String href) throws MalformedURLException, IdxRestException {
      log.info("llamando documents endpoint [identity-x] desde sdk para url {}", href);
      
      var requestDetails = defineRequestArtifacts(href);
      var uri = requestDetails._3();
      var url = getURL();
  
      SDKCall<DocumentCollection> call =
          () ->
              getRestClient()
                  .get(
                      requestDetails._2(),
                      url + uri,
                      DocumentCollection.class,
                      requestDetails._1());
  
      return callAndMeasureTime(call, uri);
    }

    public ClientDocumentCapture getClientDocumentCapture(final String href)
        throws MalformedURLException, IdxRestException {
      log.info("llamando clientCapture endpoint [identity-x] desde sdk para url {}", href);
  
      var requestDetails = defineRequestArtifacts(href);
      var uri = requestDetails._3();
      var url = getURL();
  
      SDKCall<ClientDocumentCapture> call =
          () ->
              getRestClient()
                  .get(
                      requestDetails._2(),
                      url + uri,
                      ClientDocumentCapture.class,
                      requestDetails._1());
  
      return callAndMeasureTime(call, uri);
    }

    private String getURL() throws MalformedURLException {
        URL url = new URL(baseUrl);
        String protocol = url.getProtocol();
        String host = url.getHost();
        return (url.getPort() == -1) ? format("%s://%s", protocol, host) : format("%s://%s:%d", protocol, host, url.getPort());
    }

}
