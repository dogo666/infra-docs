package ar.com.macro.validacion.domain.identidad.rest.controller;

import javax.validation.Valid;

import ar.com.macro.biometria.commons.trace.annotations.TraceBiometricsAnnotation;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.CrearHashRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.CrearHashResponse;
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
import ar.com.macro.validacion.domain.BaseController;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.validacion.model.service.EnrolamientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Tag(name = "IdentidadController", description = "validacion")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class IdentidadController extends BaseController {

	private final EnrolamientoService enrolamientoService;

	@Operation(
	    description = "Consultar Identidad", summary  = "consultar Identidad", hidden = false)
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
              responseCode = "404",
              description = HttpStatusMessage.API_HTTP_STATUS_404,
              content = @Content(schema = @Schema(implementation = Respuesta.class))),
      @ApiResponse(
          responseCode = "409",
          description = HttpStatusMessage.API_HTTP_STATUS_409,
          content = @Content(schema = @Schema(implementation = Respuesta.class)))
    })
	@TraceBiometricsAnnotation
    @PostMapping(path = "${endpoint.macro.validacion.identidad.consultar.path}",
    		produces = MediaType.APPLICATION_JSON_VALUE,
    		consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<ConsultarIdentidadResponse>> consultarIdentidad(
			@Valid @RequestBody ConsultarIdentidadRequest consultarIdentidadRequest,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationID
	) {
		ConsultarIdentidadResponse consultaResponse = enrolamientoService.consultarNormalizacionIndividuos(consultarIdentidadRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(consultaResponse));
    }

	@Operation(
			description = "Crear Hash", summary  = "Crear Hash", hidden = false)
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
	@PostMapping(path = "${endpoint.macro.validacion.crear.hash.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<CrearHashResponse>> crearHashCon256(
			@Valid @RequestBody CrearHashRequest crearHashRequest
	) {
		return ResponseEntity.ok(new Respuesta<>(enrolamientoService.crearHashCon256(crearHashRequest).get()));
	}

}
