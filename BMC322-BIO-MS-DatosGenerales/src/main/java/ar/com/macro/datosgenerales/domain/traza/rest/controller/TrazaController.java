package ar.com.macro.datosgenerales.domain.traza.rest.controller;

import static ar.com.macro.commons.values.constants.comm.HeaderConstants.HEADER_X_OPERATION_ID;
import javax.validation.constraints.NotBlank;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.exceptions.TarjetaInvalidaException;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.http.HttpStatusMessage;
import ar.com.macro.datosgenerales.domain.datosgenerales.rest.controller.BaseController;
import ar.com.macro.datosgenerales.domain.traza.rest.dto.request.GuardarTrazaRequest;
import ar.com.macro.datosgenerales.model.service.TraceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "TrazaController", description = "datosgenerales")
@RestController
@Validated
@RefreshScope
@RequiredArgsConstructor
public class TrazaController extends BaseController {

    private final TraceService traceService;

    @Operation(description = "guardar Traza", summary = "guardar Traza")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = HttpStatusMessage.API_HTTP_STATUS_200,
                    content = @Content(schema = @Schema(implementation = Respuesta.class)))
    })
    @PostMapping(path = "${endpoint.macro.datosgenerales.traza.guardar.path}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity guardarTraza(@RequestBody GuardarTrazaRequest guardarTrazaRequest,
                                       @RequestHeader(value = HEADER_X_OPERATION_ID) @NotBlank String xOperationId) {
        traceService.guardar(guardarTrazaRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "${endpoint.macro.datosgenerales.traza.outbound.identityx.guardar.path}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity guardarTrazaOutbound(@RequestBody GuardarTrazaRequest guardarTrazaRequest,
                                       @RequestHeader(value = HEADER_X_OPERATION_ID) @NotBlank String xOperationId) {
        traceService.guardarOutboundIdentityX(guardarTrazaRequest);
        return ResponseEntity.ok().build();
    }


}
