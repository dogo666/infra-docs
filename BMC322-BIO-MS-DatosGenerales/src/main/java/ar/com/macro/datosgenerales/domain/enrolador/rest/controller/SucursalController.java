package ar.com.macro.datosgenerales.domain.enrolador.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.http.HttpStatusMessage;
import ar.com.macro.datosgenerales.domain.datosgenerales.rest.controller.BaseController;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarSucursalesRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.SucursalesEnroladorResponse;
import ar.com.macro.datosgenerales.model.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * SucursalController.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-21
 */
@Tag(name = "SucursalController", description = "sucursal")
@RestController
@RefreshScope
@AllArgsConstructor
public class SucursalController extends BaseController {

    private SucursalService sucursalService;

    @Operation(
        description =
            "Permite obtener las sucursales que tienen al menos un Enrolador en estado PENDIENTE.",
        summary = "Sucursales que tienen al menos un Enrolador en estado PENDIENTE")
    @ApiResponse(
        responseCode = "200",
        description = HttpStatusMessage.API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
    @PostMapping(
        path = "${endpoint.macro.datosgenerales.biometria.enroladores.sucursales.buscar.path}",
        produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<SucursalesEnroladorResponse>>
      getSucursalesConEnroladorPendiente() {
      return ResponseEntity.ok(
          new Respuesta<>(sucursalService.getSucursalesConEnroladorPendiente()));
    }

    @Operation(
        description = "Permite obtener las sucursales que tienen al menos un enrolador en los estados solicitados.",
        summary = "Sucursales que tienen al menos un enrolador en los estados solicitados")
    @ApiResponse(
        responseCode = "200",
        description = HttpStatusMessage.API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
    @PostMapping(
        path = "${endpoint.macro.datosgenerales.biometria.enroladores.sucursales.buscar.v2.path}",
        produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<SucursalesEnroladorResponse>>
      getSucursalesConEnroladorEnEstados(@RequestBody ConsultarSucursalesRequest consultarSucursalesRequest) {
      return ResponseEntity.ok(
          new Respuesta<>(sucursalService.getSucursalesConEnroladorEnEstados(consultarSucursalesRequest)));
    }
}
