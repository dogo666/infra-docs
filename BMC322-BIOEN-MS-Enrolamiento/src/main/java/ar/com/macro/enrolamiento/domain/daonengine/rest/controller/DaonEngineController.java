package ar.com.macro.enrolamiento.domain.daonengine.rest.controller;

import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ar.com.macro.biometria.commons.trace.annotations.TraceBiometricsAnnotation;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.http.HttpStatusMessage;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.GuardarTrazasDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.ActualizarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.GuardarTrazasDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.EnrolarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.controller.BaseController;
import ar.com.macro.enrolamiento.model.service.HuellaDaonEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "DaonEngineController", description = "enrolamiento")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class DaonEngineController extends BaseController {

  private final HuellaDaonEngineService huellaDaonEngineService;

  @Operation(
      description = "Enrolar Huella Daon",
      summary = "Enrolar Huella Daon",
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
      path = "${endpoint.macro.enrolamiento.de.huella.enrolar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<EnrolarHuellaDaonEngineResponse>> enrolarHuellaDaonEngine(
      @Valid @RequestBody EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true)
          String xOperationId) {
    EnrolarHuellaDaonEngineResponse enrolarHuellaDaonEngineResponse =
        huellaDaonEngineService.enrolarHuellaDaonEngine(
            enrolarHuellaDaonEngineRequest, xOperationId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Respuesta<>(enrolarHuellaDaonEngineResponse));
  }

  @Operation(
      description = "Actualizar Huella Daon",
      summary = "Actualizar Huella Daon",
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
      path = "${endpoint.macro.enrolamiento.de.huella.actualizar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ActualizarHuellaDaonEngineResponse>> actualizarHuellaDaonEngine(
      @Valid @RequestBody ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true)
          String xOperationId) {
    ActualizarHuellaDaonEngineResponse actualizarHuellaDaonEngineResponse =
        huellaDaonEngineService.actualizarHuellaDaonEngine(
            actualizarHuellaDaonEngineRequest, xOperationId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Respuesta<>(actualizarHuellaDaonEngineResponse));
  }

  @Operation(
      description = "Permite actualizar los datos de auditoria en Daon Engine",
      summary = "Actualizar datos de auditoria en DAON",
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
      path = "${endpoint.macro.enrolamiento.de.trazas.guardar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<GuardarTrazasDaonEngineResponse>> guardarTrazasDaonEngine(
      @Valid @RequestBody final GuardarTrazasDaonEngineRequest request,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true)
          final String xOperationId) {

    var response = huellaDaonEngineService.guardarTrazasDaonEngine(request, xOperationId);
    return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(response));
  }
}
