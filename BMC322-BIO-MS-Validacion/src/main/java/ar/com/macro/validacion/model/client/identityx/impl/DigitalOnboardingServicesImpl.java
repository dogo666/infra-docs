package ar.com.macro.validacion.model.client.identityx.impl;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.daon.identityx.rest.model.pojo.User;
import com.identityx.clientSDK.collections.DataSampleCollection;
import com.identityx.clientSDK.collections.EnrollmentCollection;
import com.identityx.clientSDK.exceptions.IdxRestException;
import ar.com.macro.constant.Errores;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.validacion.model.client.identityx.DigitalOnboardingServices;
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
import ar.com.macro.validacion.model.service.DigitalOnboardingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@AllArgsConstructor
public class DigitalOnboardingServicesImpl implements DigitalOnboardingServices{

    private final DigitalOnboardingRepository repository;
  
    @Override
    public Optional<CreateEmptyIdCheckResponse> ejecutarCrearIdCheck(
            String id, CreateEmptyIdCheckRequest idCheckRequest) {

        log.info("llamando create-IdCheck endpoint [identity-x] para id {}", id);

        try {
            return Optional.of(repository.createIdCheck(id, idCheckRequest));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint create-IdCheck [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getCodigo(),
                    Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<CreateEmptyIdCheckResponse> ejecutarCrearIdCheckByUserAndLivenessPolic(String id, String livenessPolic,CreateEmptyIdCheckRequest idCheckRequest) {

        log.info("llamando create-IdCheck endpoint [identity-x] para id {}", id);

        try {
            return Optional.of(repository.ejecutarCrearIdCheckByUserAndLivenessPolic(id, livenessPolic, idCheckRequest));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint create-IdCheck [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getCodigo(),
                    Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getMensaje());
        }
    }
    
    @Override
    public Optional<GetIdChecksResponse> ejecutarGetIdCheckByUser(String id) {

        log.info("llamando Get-IdCheck endpoint [identity-x] para id {}", id);

        try {
            return Optional.of(repository.ejecutarGetIdCheckByUser(id));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint create-IdCheck [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getCodigo(),
                    Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<SubmitBothSidesDocumentResponse> ejecutarEnviarDocumento(
            final String id,
            final String idCheck,
            final SubmitBothSidesDocumentRequest submitBothSidesDocumentRequest) {

        log.info("llamando send-document endpoint [identity-x] desde sdk para id {}", id);

        try {
            return Optional.of(repository.sendDocument(id, idCheck, submitBothSidesDocumentRequest));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint send-document [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_ENVIAR_DOCUMENTO_IDENTITY_X.getCodigo(),
                    Errores.ERROR_ENVIAR_DOCUMENTO_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<GetOcrProcessingDataResponse> ejecutarObtenerDatosOCR(
            String id, String idCheck, String idDocument) {

        log.info("llamando get-ocrData endpoint [identity-x] para id {}", id);

        try {
            return Optional.of(repository.getOCR(id, idCheck, idDocument));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint get-ocrData [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getCodigo(),
                    Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<CreateEvaluationResponse> ejecutarCrearEvaluacion(
            final String idUser, final String idCheck, final String politicaEvaluacion) {

        log.info("llamando create-evaluation endpoint [identity-x] para idUser {}", idUser);

        try {
            return Optional.of(repository.createEvaluation(idUser, idCheck, politicaEvaluacion));
        } catch (IdentityXException | IdxRestException e) {
            log.error("error llamando endpoint create-evaluation [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_OBTENER_CREAR_EVALUACION_IDENTITY_X.getCodigo(),
                    Errores.ERROR_OBTENER_CREAR_EVALUACION_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<Create3DFLVideoChallengeResponse> ejecutar3DFLVideoChallenge(String idUser, String idCheck, String livenessPolicyName) {

        log.info("llamando ejecutar-3DFL-Video-Challenge endpoint [identity-x] para idUser {}", idUser);

        try {
            return Optional.of(repository.crearVideoChallenge(idUser, idCheck, livenessPolicyName));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint ejecutar-3DFL-Video-Challenge [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X.getCodigo(),
                    Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<Create3DFLVideoChallengeResponse> ejecutarGet3DFLVideoChallenge(String idUser, String idCheck, String livenessPolicyName) {

        log.info("llamando ejecutar-Get-3DFL-Video-Challenge endpoint [identity-x] para idUser {}", idUser);

        try {
            return Optional.of(repository.getVideoChallenge(idUser, idCheck, livenessPolicyName));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint ejecutar-3DFL-Video-Challenge [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X.getCodigo(),
                    Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X.getMensaje());
        }
    }
    
    @Override
    public Optional<Submit3DFLVideoResponse> ejecutar3DFLSubirVideo(String id, String idCheck, Submit3DFLVideoRequest submit3DFLVideoRequest) {
    	
        log.info("llamando ejecutar-3DFL-Subir-Video endpoint [identity-x] para id {}", id);

        try {
            return Optional.of(repository.subirVideo(id, idCheck, submit3DFLVideoRequest));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint ejecutar-3DFL-Subir-Video [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_3DFL_SUBIR_VIDEO_IDENTITY_X.getCodigo(),
                    Errores.ERROR_3DFL_SUBIR_VIDEO_IDENTITY_X.getMensaje());
        }
    }
    
    @Override
    public Optional<CreateEvaluationChallengeResponse> ejecutarCrearEvaluacionChallenge(String id, String idCheck, String evaluationPolicyName ) {

        log.info("llamando ejecutar-Crear-Evaluacion-Challenge endpoint [identity-x] para id {}", id);

        try {
            return Optional.of(repository.ejecutarCreateEvaluation(id, idCheck,evaluationPolicyName));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint ejecutar-3DFL-Video-Challenge [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_CREAR_EVALUACION_IDENTITY_X.getCodigo(),
                    Errores.ERROR_CREAR_EVALUACION_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<GetFacesResponse> ejecutarGetFaces(String id, String idCheck ) {

        log.info("llamando ejecutar-Get-Faces endpoint [identity-x] para id {} idCheck {}", id,idCheck );

        try {
            return Optional.of(repository.getFaces(id,idCheck));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint ejecutar-Get-User [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_GET_FACES_IDENTITY_X.getCodigo(),
                    Errores.ERROR_GET_FACES_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<GetImagenResponse > ejecutarGetImagen(String id, String idCheck , String faceId ) {

        log.info("llamando ejecutar-Get-Faces endpoint [identity-x] para id {} idCheck {} faceId {}", id,idCheck, faceId  );

        try {
            return Optional.of(repository.getImagen(id,idCheck,faceId));
        } catch (IdentityXException | IdxRestException e) {

            log.error("error llamando endpoint ejecutar-Get-User [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_GET_FACES_IDENTITY_X.getCodigo(),
                    Errores.ERROR_GET_FACES_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public Optional<GetSensitiveDataResponse > ejecutarGetSensitiveData(String sensitiveData ) {

        log.info("llamando ejecutar-Get-Sensitive-Data endpoint [identity-x] para " , sensitiveData );

        try {
            return Optional.of(repository.getSensitiveData(sensitiveData));
        } catch (IdentityXException | IdxRestException | MalformedURLException e) {

            log.error("error llamando endpoint ejecutar-Get-Sensitive-Data [identity-x]", e);
            throw new IdentityXException(
                    Errores.ERROR_GET_SENSITIVE_DATA_IDENTITY_X.getCodigo(),
                    Errores.ERROR_GET_SENSITIVE_DATA_IDENTITY_X.getMensaje());
        }
    }

    @Override
    public EnrollmentCollection getEnrollments(final String href) {
      log.info("llamando enrollments endpoint [identity-x] desde sdk para url {}", href);
  
      try {
        return repository.getEnrollments(href);
      } catch (IdentityXException | IdxRestException | MalformedURLException e) {
        log.error("error llamando endpoint enrollments [identity-x]", e);
        throw new IdentityXException(Errores.ERROR_GET_ENROLLMENTS_IDENTITY_X);
      }
    }
  
    @Override
    public DataSampleCollection getSamples(final String href) {
      log.info("llamando samples endpoint [identity-x] desde sdk para url {}", href);
  
      try {
        return repository.getSamples(href);
      } catch (IdentityXException | IdxRestException | MalformedURLException e) {
        log.error("error llamando endpoint samples [identity-x]", e);
        throw new IdentityXException(Errores.ERROR_GET_SAMPLES_IDENTITY_X);
      }
    }
  
    @Override
    public Optional<GetIdChecksResponse> getIdchecksByUser(
        final User user, final Map<String, String> queryParams) {
  
      log.info("llamando Get-IdChecks endpoint [identity-x] para id {}", user.getId());
  
      try {
        return Optional.of(repository.getIdchecksByUser(user.getId(), queryParams));
      } catch (IdentityXException | IdxRestException e) {
        log.error("error llamando endpoint create-IdChecks [identity-x]", e);
        throw new IdentityXException(Errores.ERROR_GET_IDCHECKS_IDENTITY_X);
      }
    }
  
    @Override
    public CreateEmptyIdCheckResponse getIdCheck(final String href) {
      log.info("llamando Get-IdCheck endpoint [identity-x] desde sdk para url {}", href);
  
      try {
        return repository.getIdChecks(href);
      } catch (IdentityXException | IdxRestException | MalformedURLException e) {
        log.error("error llamando endpoint create-IdCheck [identity-x]", e);
        throw new IdentityXException(Errores.ERROR_GET_IDCHECKS_IDENTITY_X);
      }
    }
  
    @Override
    public DocumentCollection getDocuments(final String href) {
      log.info("llamando documents endpoint [identity-x] desde sdk para url {}", href);
  
      try {
        return repository.getDocuments(href);
      } catch (IdentityXException | IdxRestException | MalformedURLException e) {
        log.error("error llamando endpoint documents [identity-x]", e);
        throw new IdentityXException(Errores.ERROR_GET_DOCUMENTS_IDENTITY_X);
      }
    }
  
    @Override
    public ClientDocumentCapture getClientDocumentCapture(final String href) {
      log.info("llamando clientCapture endpoint [identity-x] desde sdk para url {}", href);
  
      try {
        return repository.getClientDocumentCapture(href);
      } catch (IdentityXException | IdxRestException | MalformedURLException e) {
        log.error("error llamando endpoint clientCapture [identity-x]", e);
        throw new IdentityXException(Errores.ERROR_GET_CLIENTS_CAPTURE_IDENTITY_X);
      }
    }
}
