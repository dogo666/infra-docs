package ar.com.macro.validacion.model.client.identityx;

import java.util.Map;
import java.util.Optional;
import com.daon.identityx.rest.model.pojo.User;
import com.identityx.clientSDK.collections.DataSampleCollection;
import com.identityx.clientSDK.collections.EnrollmentCollection;
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

public interface DigitalOnboardingServices {

  Optional<GetSensitiveDataResponse> ejecutarGetSensitiveData(String sensitiveData);

  Optional<GetImagenResponse> ejecutarGetImagen(String id, String idCheck, String faceId);

  Optional<GetFacesResponse> ejecutarGetFaces(String id, String idCheck);

  Optional<CreateEvaluationChallengeResponse> ejecutarCrearEvaluacionChallenge(
      String id, String idCheck, String evaluationPolicyName);

  Optional<Submit3DFLVideoResponse> ejecutar3DFLSubirVideo(
      String id, String idCheck, Submit3DFLVideoRequest submit3DFLVideoRequest);

  Optional<Create3DFLVideoChallengeResponse> ejecutarGet3DFLVideoChallenge(
      String idUser, String idCheck, String livenessPolicyName);

  Optional<Create3DFLVideoChallengeResponse> ejecutar3DFLVideoChallenge(
      String idUser, String idCheck, String livenessPolicyName);

  Optional<CreateEvaluationResponse> ejecutarCrearEvaluacion(
      final String idUser, final String idCheck, final String politicaEvaluacion);

  Optional<GetOcrProcessingDataResponse> ejecutarObtenerDatosOCR(
      String id, String idCheck, String idDocument);

  Optional<SubmitBothSidesDocumentResponse> ejecutarEnviarDocumento(
      final String id,
      final String idCheck,
      final SubmitBothSidesDocumentRequest submitBothSidesDocumentRequest);

  Optional<GetIdChecksResponse> ejecutarGetIdCheckByUser(String id);

  Optional<CreateEmptyIdCheckResponse> ejecutarCrearIdCheckByUserAndLivenessPolic(
      String id, String livenessPolic, CreateEmptyIdCheckRequest idCheckRequest);

  Optional<CreateEmptyIdCheckResponse> ejecutarCrearIdCheck(
      String id, CreateEmptyIdCheckRequest idCheckRequest);

  EnrollmentCollection getEnrollments(String href);
  
  DataSampleCollection getSamples(String href);
  
  CreateEmptyIdCheckResponse getIdCheck(String href);
  
  DocumentCollection getDocuments(String href);

  ClientDocumentCapture getClientDocumentCapture(String href);
  
  Optional<GetIdChecksResponse> getIdchecksByUser(User user, Map<String, String> queryParams);

}
