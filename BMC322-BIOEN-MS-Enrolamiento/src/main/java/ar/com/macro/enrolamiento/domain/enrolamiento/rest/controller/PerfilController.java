package ar.com.macro.enrolamiento.domain.enrolamiento.rest.controller;

import javax.validation.Valid;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.*;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.*;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PerfilController", description = "enrolamiento")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class PerfilController extends BaseController {

  private final PerfilService perfilService;

  private final DatosGeneralesService datosGeneralesService;

  @Operation(
      description = "Crear Perfil de Usuario",
      summary = "Crear Perfil de Usuario",
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
      path = "${endpoint.macro.enrolamiento.ix.perfil.crear.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<CrearPerfilResponse>> crearPerfilUsuario(
      @Valid @RequestBody CrearPerfilRequest crearPerfilRequest,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    CrearPerfilResponse respuesta = perfilService.crearPerfilUsuario(crearPerfilRequest);
    return ResponseEntity.ok(new Respuesta<>(respuesta));
  }

  @Operation(
      description = "Agregar DNI a perfil de Usuario",
      summary = "Agregar DNI a perfil de Usuario",
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
      path = "${endpoint.macro.enrolamiento.ix.perfil.dni.agregar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<AgregarDniPerfilResponse>> agregarPerfilUsuario(
      @Valid @RequestBody AgregarDniPerfilRequest agregarDniPerfilRequest,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    AgregarDniPerfilResponse respuesta =
        perfilService.agregarDniPerfilUsuario(agregarDniPerfilRequest);
    return ResponseEntity.ok(new Respuesta<>(respuesta));
  }

  @Operation(
          description = "Guardado de datos enrolador",
          summary = "Guardado de datos enrolador",
          parameters = @Parameter(ref = "X-Operation-Id"))
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
  @PostMapping(path = "${endpoint.macro.enrolamiento.biometria.enroladores.guardar.data.path}",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<GuardarEnroladorResponse>> guardarDatosEnrolador(
          @RequestBody GuardarEnroladorListaRequest guardarEnroladorListaRequest,
          @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    return ResponseEntity.ok(new Respuesta<>(datosGeneralesService.guardarDatosEnrolador(guardarEnroladorListaRequest, xOperationId).get()));
  }

  @Operation(
      description =
          "Permite obtener los enroladores que cumplan con los criterios de busqueda solicitados.",
      summary = "Obtener enroladores que cumplan con los criterios de busqueda solicitados.",
      parameters = @Parameter(ref = "X-Operation-Id"))
  @ApiResponse(
      responseCode = "200",
      description = HttpStatusMessage.API_HTTP_STATUS_200,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "400",
      description = HttpStatusMessage.API_HTTP_STATUS_400,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "409",
      description = HttpStatusMessage.API_HTTP_STATUS_409,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.biometria.enroladores.buscar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ConsultarEnroladoresResponse>> getUsuariosEnrolador(
      @RequestBody ConsultarEnroladoresRequest consultarEnroladoresRequest,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    return ResponseEntity.ok(
        new Respuesta<>(
            datosGeneralesService.getUsuariosEnrolador(consultarEnroladoresRequest, xOperationId)));
  }

  @Operation(
      description =
          "Permite obtener las sucursales que tienen al menos un enrolador en estado PENDIENTE.",
      summary = "Sucursales que tienen al menos un enrolador en estado PENDIENTE",
      parameters = @Parameter(ref = "X-Operation-Id"))
  @ApiResponse(
      responseCode = "200",
      description = HttpStatusMessage.API_HTTP_STATUS_200,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "400",
      description = HttpStatusMessage.API_HTTP_STATUS_400,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "409",
      description = HttpStatusMessage.API_HTTP_STATUS_409,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.biometria.enroladores.sucursales.buscar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<SucursalesEnroladorResponse>>
      getSucursalesConEnroladorPendiente(
          @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    return ResponseEntity.ok(
        new Respuesta<>(datosGeneralesService.getSucursalesConEnroladorPendiente(xOperationId)));
  }

  @Operation(
          description =
                  "Permite obtener el estado del enrolador consultado.",
          summary = "Permite obtener el estado del enrolador consultado.",
          parameters = @Parameter(ref = "X-Operation-Id"))
  @ApiResponse(
          responseCode = "200",
          description = HttpStatusMessage.API_HTTP_STATUS_200,
          content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
          responseCode = "400",
          description = HttpStatusMessage.API_HTTP_STATUS_400,
          content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
          responseCode = "409",
          description = HttpStatusMessage.API_HTTP_STATUS_409,
          content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @TraceBiometricsAnnotation
  @PostMapping(
          path = "${endpoint.macro.enrolamiento.biometria.enroladores.enrolador.consular.path}",
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ConsultarEnroladorEstadoResponse>>
      getEstadoEnrolador(
          @RequestBody ConsultarEnroladorRequest consultarEnroladorRequest,
          @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    return ResponseEntity.ok(
            new Respuesta<>(datosGeneralesService.getEstadoEnrolador(consultarEnroladorRequest,xOperationId)));
  }

  @Operation(
      description = "Permite obtener la información del usuario enrolador consultado.",
      summary = "Permite obtener la información de un usuario enrolador.",
      parameters = @Parameter(ref = "X-Operation-Id"))
  @ApiResponse(
      responseCode = "200",
      description = HttpStatusMessage.API_HTTP_STATUS_200,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "400",
      description = HttpStatusMessage.API_HTTP_STATUS_400,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "409",
      description = HttpStatusMessage.API_HTTP_STATUS_409,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.biometria.enroladores.enrolador.info.consultar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ConsultarEnroladorResponse>> getDatosEnrolador(
      @Valid @RequestBody ConsultarInfoEnroladorRequest request,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    return ResponseEntity.ok(
        new Respuesta<>(
            datosGeneralesService.getDatosEnrolador(request, xOperationId)));
  }
  
  @Operation(
      description = "Permite validar si el usuario hace parte de un rol/grupo AD específico.",
      summary = "Permite validar si el usuario hace parte de un rol/grupo AD específico.",
      parameters = @Parameter(ref = "X-Operation-Id"))
  @ApiResponse(
      responseCode = "200",
      description = HttpStatusMessage.API_HTTP_STATUS_200,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "400",
      description = HttpStatusMessage.API_HTTP_STATUS_400,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @ApiResponse(
      responseCode = "409",
      description = HttpStatusMessage.API_HTTP_STATUS_409,
      content = @Content(schema = @Schema(implementation = Respuesta.class)))
  @TraceBiometricsAnnotation
  @PostMapping(
      path = "${endpoint.macro.enrolamiento.biometria.usuario.rol.validar.path}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Respuesta<ValidarRolUsuarioResponse>> validarRolUsuario(
      @Valid @RequestBody ValidarRolUsuarioRequest request,
      @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
    
    return ResponseEntity.ok(
        new Respuesta<>(
            perfilService.validarRolUsuario(request, xOperationId)));
  }
}
