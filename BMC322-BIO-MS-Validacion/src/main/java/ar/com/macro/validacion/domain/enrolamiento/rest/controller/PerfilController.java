package ar.com.macro.validacion.domain.enrolamiento.rest.controller;

import javax.validation.Valid;

import ar.com.macro.biometria.commons.trace.annotations.TraceBiometricsAnnotation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.AgregarDniPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.AgregarDniPerfilResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoResponse;
import ar.com.macro.validacion.model.service.EnrolamientoService;
import ar.com.macro.validacion.model.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PerfilController", description = "validacion")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class PerfilController extends BaseController {

	private final PerfilService perfilService;

	private final EnrolamientoService enrolamientoService;

	@Operation(
			description = "Agregar DNI a perfil de Usuario",
			summary = "Agregar DNI a perfil de Usuario", hidden = true)
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
	@PostMapping(path = "${endpoint.macro.validacion.ix.perfil.dni.agregar.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<AgregarDniPerfilResponse>> agregarPerfilUsuario(
			@Valid @RequestBody AgregarDniPerfilRequest agregarDniPerfilRequest,
			@RequestHeader (value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		AgregarDniPerfilResponse respuesta = perfilService.agregarDniPerfilUsuario(agregarDniPerfilRequest);
		return ResponseEntity.ok(new Respuesta<>(respuesta));
	}

	@Operation(
			description = "Consultar Enrolamiento",
			summary = "Consultar Enrolamiento")
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
	@PostMapping(path = "${endpoint.macro.validacion.enrolamiento.consultar.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ConsultarEnrolamientoResponse>> consultarEnrolamiento(@Valid @RequestBody ConsultarEnrolamientoRequest consultarEnrolamientoRequest,
																						  @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		ConsultarEnrolamientoResponse consultarEnrolamientoResponse = enrolamientoService.consultarEnrolamiento(consultarEnrolamientoRequest, xOperationId);
		return ResponseEntity.ok(new Respuesta<>(consultarEnrolamientoResponse));
	}

}
