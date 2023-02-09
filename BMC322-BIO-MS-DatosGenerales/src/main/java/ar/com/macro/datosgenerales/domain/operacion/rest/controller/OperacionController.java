package ar.com.macro.datosgenerales.domain.operacion.rest.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.http.HttpStatusMessage;
import ar.com.macro.datosgenerales.domain.datosgenerales.rest.controller.BaseController;
import ar.com.macro.datosgenerales.domain.operacion.rest.dto.response.CrearOperationIdResponse;
import ar.com.macro.datosgenerales.model.service.OperacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "OperacionController", description = "datosgenerales")
@RestController
@RefreshScope
public class OperacionController extends BaseController {

    private final OperacionService operacionService;

    public OperacionController(OperacionService operacionService) {
        this.operacionService = operacionService;
    }

    @Operation(
        description = "Creacion del id de operacion",
            summary = "Creacion id operacion")
    @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = HttpStatusMessage.API_HTTP_STATUS_200,
          content = @Content(schema = @Schema(implementation = Respuesta.class)))
    })
    @PostMapping(path = "${endpoint.macro.datosgenerales.generar.operationId.path}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<CrearOperationIdResponse>> crearOperationId() {
        CrearOperationIdResponse crearOperationIdResponse = operacionService.crearOperationId();
        return ResponseEntity.ok(new Respuesta<>(crearOperationIdResponse));
    }
}
