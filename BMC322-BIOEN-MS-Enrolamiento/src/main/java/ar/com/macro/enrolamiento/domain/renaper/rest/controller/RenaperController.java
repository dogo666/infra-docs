package ar.com.macro.enrolamiento.domain.renaper.rest.controller;

import java.util.Optional;
import javax.validation.Valid;

import ar.com.macro.biometria.commons.trace.annotations.TraceBiometricsAnnotation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.http.HttpStatusMessage;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.controller.BaseController;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.request.ValidarDniRenaperRequest;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.request.ValidarHuellaEnRenaperRequest;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.request.ValidarRostroRenaperRequest;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ConsultarTcnHuellaEnRenaperResponse;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ValidarHuellaEnRenaperResponse;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ValidarRenaperDNIResponse;
import ar.com.macro.enrolamiento.domain.renaper.rest.dto.response.ValidarRostroRenaperResponse;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;
import ar.com.macro.enrolamiento.model.service.RenaperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "RenaperController", description = "enrolamiento")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class RenaperController extends BaseController {

  private final RenaperService renaperService;

  @Operation(description = "Consultar Persona Renaper", summary = "consultar persona renaper",
      parameters = @Parameter(ref = "X-Application-Id"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = HttpStatusMessage.API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "400",
        description = HttpStatusMessage.API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "409",
        description = HttpStatusMessage.API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
  })
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.rnp.persona.dni.validar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ValidarRenaperDNIResponse>> consultarPersona(
          @Valid @RequestBody ValidarDniRenaperRequest validarDniRenaperRequest,
          @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    Optional<ValidarDNIRenaperClientResponse> validacionRenaperDNIResponse =
        renaperService.validarIdentidadPersonaPorDNI(validarDniRenaperRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Respuesta<>(new ValidarRenaperDNIResponse(validacionRenaperDNIResponse.get())));
  }

  @Operation(description = "Validar rostro Renaper", summary = "Validar rostro renaper",
      parameters = @Parameter(ref = "X-Application-Id"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = HttpStatusMessage.API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "400",
        description = HttpStatusMessage.API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "409",
        description = HttpStatusMessage.API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
  })
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.rnp.persona.rostro.validar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ValidarRostroRenaperResponse>> validarRostro(
      @Valid @RequestBody ValidarRostroRenaperRequest validarRostroRenaperRequest,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    Optional<ValidarRostroRenaperClientResponse> validarRostroRenaperResponse =
        renaperService.validarIdentidadPersonaPorRostro(validarRostroRenaperRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new Respuesta<>(new ValidarRostroRenaperResponse(validarRostroRenaperResponse.get())));
  }

  @Operation(description = "Validar Huella Renaper", summary = "Validar Huella Renaper",
      parameters = @Parameter(ref = "X-Application-Id"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = HttpStatusMessage.API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "400",
        description = HttpStatusMessage.API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "409",
        description = HttpStatusMessage.API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
  })
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.renaper.persona.huella.validar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ValidarHuellaEnRenaperResponse>> validarHuellaRenaper(
      @Valid @RequestBody ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    ValidarHuellaEnRenaperResponse validarHuellaEnRenaperResponse =
        renaperService.validarHuellaEnRenaper(validarHuellaEnRenaperRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Respuesta<>(validarHuellaEnRenaperResponse));
  }

  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.rnp.persona.huella.obtenertcn.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ConsultarTcnHuellaEnRenaperResponse>>
      getTcnValidacionHuellaEnRenaper(
          @Valid @RequestBody ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest,
          @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {

    var tcn = renaperService.getTcnValidacionHuellaEnRenaper(validarHuellaEnRenaperRequest);

    return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(tcn));
  }

  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.rnp.persona.huella.resultadotcn.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ValidarHuellaEnRenaperResponse>>
      getResultadoValidacionHuellaEnRenaper(
          @Valid @RequestBody String tcn,
          @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {

    var validarHuellaEnRenaperResponse = renaperService.getResultadoValidacionHuellaEnRenaper(tcn);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new Respuesta<>(validarHuellaEnRenaperResponse));
  }
}
