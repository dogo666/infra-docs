package ar.com.macro.enrolamiento.model.client.identityx.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.identityx.clientSDK.exceptions.IdxRestException;
import ar.com.macro.constant.Errores;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.enrolamiento.model.service.DigitalOnboardingRepository;
import ar.com.macro.exceptions.IdentityXException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DigitalOnboardingServicesImpl {

  private DigitalOnboardingRepository repository;

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
}
