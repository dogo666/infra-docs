package ar.com.macro.validacion.domain.daonengine.rest.controller;


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
import ar.com.macro.validacion.domain.BaseController;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.CompararHuellasDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ConsultarValidacionHuellaPersonaRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ValidarHuellaDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.CompararHuellasDaonEngineResponse;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ConsultarValidacionHuellaPersonaResponse;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ValidarHuellaDaonEngineResponse;
import ar.com.macro.validacion.model.service.EnrolamientoService;
import ar.com.macro.validacion.model.service.HuellaDaonEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RefreshScope
@RestController
@RequiredArgsConstructor
@Tag(name = "DaonEngineController", description = "validacion")
public class DaonEngineController extends BaseController {

    private final EnrolamientoService enrolamientoService;
    private final HuellaDaonEngineService huellaDaonEngineService;


    @Operation(
            description = "Consultar request tracking UID" , summary  = "Consultar request tracking UID ")
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
    @PostMapping(path = "${endpoint.macro.validacion.de.persona.huella.validacion.consultar.path}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<ConsultarValidacionHuellaPersonaResponse>> consultarTrackingUID(
            @Valid @RequestBody ConsultarValidacionHuellaPersonaRequest consultarValidacionHuellaPersonaRequest,
            @RequestHeader (value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationId
    ) {
        ConsultarValidacionHuellaPersonaResponse enrolarHuellaDaonEngineResponse =
                huellaDaonEngineService.consultarTrackingUID(consultarValidacionHuellaPersonaRequest,xOperationId);
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(enrolarHuellaDaonEngineResponse));
    }

	@Operation(
			description = "Validar Huella en Daon",
			summary = "Validar Huella en Daon")
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
	@PostMapping(path = "${endpoint.macro.validacion.de.persona.huella.validar.path}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ValidarHuellaDaonEngineResponse>> validarHuella(@Valid @RequestBody ValidarHuellaDaonEngineRequest validarHuellaDaonEngineRequest,
																						  @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		ValidarHuellaDaonEngineResponse validarHuellaDaonEngineResponse = enrolamientoService.validarHuellaDaon(validarHuellaDaonEngineRequest, xOperationId);
		return ResponseEntity.ok(new Respuesta<>(validarHuellaDaonEngineResponse));
	}

	@Operation(description = "Proceso de validación de huellas uno a pocos en Daon", summary = "Validación Huellas Uno a Pocos")
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
        path = "${endpoint.macro.validacion.de.persona.huellas.unoapocos.validar.path}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<CompararHuellasDaonEngineResponse>> validarHuellasUnoaPocos(
        @Valid @RequestBody CompararHuellasDaonEngineRequest request,
        @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
      CompararHuellasDaonEngineResponse response =
          enrolamientoService.validarHuellasUnoaPocosDaon(request, xOperationId);
      return ResponseEntity.ok(new Respuesta<>(response));
    }
}
