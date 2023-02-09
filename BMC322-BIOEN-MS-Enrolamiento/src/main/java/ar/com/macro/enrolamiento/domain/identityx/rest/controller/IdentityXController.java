package ar.com.macro.enrolamiento.domain.identityx.rest.controller;

import static ar.com.macro.commons.values.constants.comm.HeaderConstants.HEADER_X_OPERATION_ID;
import static ar.com.macro.commons.values.constants.http.HttpStatusMessage.API_HTTP_STATUS_200;
import static ar.com.macro.commons.values.constants.http.HttpStatusMessage.API_HTTP_STATUS_400;
import static ar.com.macro.commons.values.constants.http.HttpStatusMessage.API_HTTP_STATUS_409;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ar.com.macro.biometria.commons.trace.annotations.TraceBiometricsAnnotation;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.controller.BaseController;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ActualizarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ConsultarDatosDniRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.EnrolarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ValidarRostroPersonaRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ConsultarDatosDniResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.CrearEvaluacionPerfilResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.EnrolarRostroResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ValidarRostroPersonaResponse;
import ar.com.macro.enrolamiento.model.service.EnrolamientoService;
import ar.com.macro.enrolamiento.model.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "IdentityXController", description = "enrolamiento")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class IdentityXController extends BaseController {

  private final EnrolamientoService enrolamientoService;

  private final PerfilService perfilService;

  @Operation(
      description = "Actualizar Rostro",
      summary = "Actualizar la selfie previamente enrolada de rostro de un usuario en Identity-X.",
      parameters = @Parameter(ref = "X-Application-Id"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "400",
        description = API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "409",
        description = API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
  })
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.ix.persona.rostro.actualizar.path}",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<?> consultarIdentidad(
      @Valid @RequestBody ActualizarRostroRequest actualizarRostroRequest) {
    enrolamientoService.actualizarRostro(actualizarRostroRequest);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

    @Operation(
        description = "Enrolar Rostro",
        summary = "Agregar una foto de usuario (selfie) al perfil Identity-X.",
        parameters = @Parameter(ref = "X-Application-Id"))
    @ApiResponse(
        responseCode = "200",
        description = API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
    @ApiResponse(
        responseCode = "400",
        description = API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
    @ApiResponse(
        responseCode = "409",
        description = API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
    @TraceBiometricsAnnotation
    @PostMapping(
        path = "${endpoint.macro.enrolamiento.ix.persona.rostro.enrolar.path}",
        produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<EnrolarRostroResponse>> enrolarRostro(
        @Valid @RequestBody EnrolarRostroRequest enrolarRostroRequest,
        @RequestHeader(value = HEADER_X_OPERATION_ID) String xOperationId) {
  
      var enrolarRostroResponse =
          enrolamientoService.enrolarRostro(enrolarRostroRequest, xOperationId);
      return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(enrolarRostroResponse));
    }

  @Operation(
      description = "Consultar Datos Dni",
      summary = "Consultar/leer los datos del dni que se agregó al perfil en el enrolamiento.",
      parameters = @Parameter(ref = "X-Application-Id"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "400",
        description = API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "409",
        description = API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
  })
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamineto.ix.datos.dni.consultar.path}",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ConsultarDatosDniResponse>> consultarDatosDni(
      @Valid @RequestBody ConsultarDatosDniRequest consultarDatosDniRequest,
      @RequestHeader(value = HEADER_X_OPERATION_ID) String xOperationId) {

    ConsultarDatosDniResponse consultarDatosDniResponse =
        enrolamientoService.consultarDatosDni(consultarDatosDniRequest);
    return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(consultarDatosDniResponse));
  }

  @Operation(
      description = "Validar Rostro",
      summary = "Validar una foto de usuario (selfie) contra el perfil Identity-X.",
      parameters = @Parameter(ref = "X-Application-Id"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "400",
        description = API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "409",
        description = API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
  })
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.ix.persona.rostro.validar.path}",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ValidarRostroPersonaResponse>> validarRostro(
      @Valid @RequestBody ValidarRostroPersonaRequest validarRostroPersonaRequest,
      @RequestHeader(value = HEADER_X_OPERATION_ID) String xOperationId) {
    ValidarRostroPersonaResponse validarRostroPersonaResponse =
        perfilService.validarRostroPersona(validarRostroPersonaRequest, xOperationId);
    return ResponseEntity.ok(new Respuesta<>(validarRostroPersonaResponse));
  }

  @Operation(
      description = "Crear Evaluación",
      summary = "Se crea una evaluacion al perfil de Identity-X.",
      parameters = @Parameter(ref = "X-Application-Id"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "400",
        description = API_HTTP_STATUS_400,
        content = @Content(schema = @Schema(implementation = Respuesta.class))),
    @ApiResponse(
        responseCode = "409",
        description = API_HTTP_STATUS_409,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
  })
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.ix.perfil.evaluacion.crear.path}",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<CrearEvaluacionPerfilResponse>> ejecutarCrearEvaluacion(
      @Valid @RequestBody CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest,
      @RequestHeader(value = HEADER_X_OPERATION_ID, required = true)
          String xOperationId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new Respuesta<>(
                perfilService.crearEvaluacion(crearEvaluacionPerfilRequest, xOperationId)));
  }
}
