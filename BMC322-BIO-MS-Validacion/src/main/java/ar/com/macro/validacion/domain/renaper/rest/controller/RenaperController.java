package ar.com.macro.validacion.domain.renaper.rest.controller;

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
import ar.com.macro.validacion.domain.BaseController;
import ar.com.macro.validacion.domain.renaper.rest.dto.request.ValidarDniRenaperRequest;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import ar.com.macro.validacion.model.service.RenaperService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "RenaperController", description = "validacion")
@RefreshScope
@RestController
@RequiredArgsConstructor
public class RenaperController extends BaseController {

	private final RenaperService renaperService;

	@Operation(
	    description = "Consultar Persona Renaper", summary = "consultar persona renaper", hidden = true)
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
    @PostMapping(path = "${endpoint.macro.validacion.rnp.persona.dni.validar.path}",
    		produces = MediaType.APPLICATION_JSON_VALUE,
    		consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<ar.com.macro.validacion.domain.renaper.rest.dto.response.ValidarRenaperDNIResponse>> consultarPersona(@Valid @RequestBody ValidarDniRenaperRequest validarDniRenaperRequest,
                                                                                                                                          @RequestHeader (value = HeaderConstants.HEADER_X_OPERATION_ID) String xOperationId) {
		Optional<ValidarDNIRenaperClientResponse> validacionRenaperDNIResponse = renaperService.validarIdentidadPersonaPorDNI(validarDniRenaperRequest);
		return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(new ar.com.macro.validacion.domain.renaper.rest.dto.response.ValidarRenaperDNIResponse(validacionRenaperDNIResponse.get())));
    }

}
