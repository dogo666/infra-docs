package ar.com.macro.validacion.domain.identityx.rest.controller;

import javax.validation.Valid;
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
import ar.com.macro.validacion.domain.BaseController;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarVerificacionRostroPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ValidarRostroPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ValidarRostroVideoPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarVerificacionRostroPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroVideoPersonaResponse;
import ar.com.macro.validacion.model.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "IdentityXController", description = "validacion")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class IdentityXController extends BaseController {

	private final PerfilService perfilService;

	@Operation(
	    description = "Validar Rostro",
	        summary = "Validar una foto de usuario (selfie) contra el perfil Identity-X.", hidden = true)
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
	@PostMapping(path = "${endpoint.macro.validacion.ix.persona.rostro.validar.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ValidarRostroPersonaResponse>> validarRostro(@Valid @RequestBody ValidarRostroPersonaRequest validarRostroPersonaRequest,
																				 @RequestHeader (value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		ValidarRostroPersonaResponse validarRostroPersonaResponse = perfilService.validarRostroPersona(validarRostroPersonaRequest, xOperationId);
		return ResponseEntity.ok(new Respuesta<>(validarRostroPersonaResponse));
	}

	@Operation(
	    description = "Consultar Verificacion Rostro",
	        summary = "Consultar los resultados de una validación de rostro previamente realizada.")
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
	@PostMapping(path = "${endpoint.macro.validacion.ix.persona.rostro.verificacion.consultar.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ConsultarVerificacionRostroPersonaResponse>> consultarVerificacionRostro(
				@Valid @RequestBody ConsultarVerificacionRostroPersonaRequest consultarVerificacionRostroPersonaRequest,
				@RequestHeader (value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		ConsultarVerificacionRostroPersonaResponse consultarVerificacionRostroPersonaResponse = perfilService.consultarVerificacionRostro(consultarVerificacionRostroPersonaRequest, xOperationId);
		return ResponseEntity.ok(new Respuesta<>(consultarVerificacionRostroPersonaResponse));
	}

	@Operation(
			description = "Validar Usuario No Enrolado",
			summary = "Validar Usuario No Enrolado - traza en biometría")
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
	@PostMapping(path = "${endpoint.macro.validacion.ix.persona.video.rostro.validar.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ValidarRostroVideoPersonaResponse>> validarVideoRostro(
			@Valid @RequestBody ValidarRostroVideoPersonaRequest validarRostroPersonaRequest,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		ValidarRostroVideoPersonaResponse validarRostroVideoPersonaResponse = perfilService.validarRostroVideoPersona(validarRostroPersonaRequest, xOperationId);
		return ResponseEntity.ok(new Respuesta<>(validarRostroVideoPersonaResponse));
	}


	@Operation(
			description = "Consultar Persona",
			summary = "Consultar Persona - traza en biometría")
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
	@PostMapping(path = "${endpoint.macro.validacion.ix.persona.consultar.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ConsultarPersonaResponse>> consultar(
			@Valid @RequestBody ConsultarPersonaRequest validarRostroPersonaRequest,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		ConsultarPersonaResponse consultarPersonaResponse = perfilService.consultarPersona(validarRostroPersonaRequest, xOperationId);
		return ResponseEntity.ok(new Respuesta<>(consultarPersonaResponse));
	}

}
