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
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladorRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladoresRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.GuardarEnroladorListaRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladorResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladoresResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.GuardarEnroladorResponse;
import ar.com.macro.datosgenerales.model.service.EnroladorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "EnroladorController", description = "enrolador")
@RestController
@RefreshScope
@AllArgsConstructor
public class EnroladorController extends BaseController {

    private EnroladorService enroladorService;

    @Operation(description = "Recuperación datos adicionales enrolador", summary = "Consulta datos enroladores")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = HttpStatusMessage.API_HTTP_STATUS_200,
                    content = @Content(schema = @Schema(implementation = Respuesta.class)))
    })
    @PostMapping(path = "${endpoint.macro.datosgenerales.biometria.enroladores.data.path}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<ConsultarEnroladorResponse>> consultarDatosEnrolador(@RequestBody ConsultarEnroladorRequest consultarEnroladorRequest) {
        return ResponseEntity.ok(new Respuesta<>(enroladorService.consultarDatosEnrolador(consultarEnroladorRequest)));
    }


    @Operation(description = "Guardado de datos enrolador", summary = "Guardar datos enroladores")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = HttpStatusMessage.API_HTTP_STATUS_200,
                    content = @Content(schema = @Schema(implementation = Respuesta.class)))
    })
    @PostMapping(path = "${endpoint.macro.datosgenerales.biometria.enroladores.guardar.data.path}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<GuardarEnroladorResponse>> guardarDatosEnrolador( @RequestBody GuardarEnroladorListaRequest guardarEnroladorListaRequest) {
        return ResponseEntity.ok(new Respuesta<>(enroladorService.guardarDatosEnrolador(guardarEnroladorListaRequest.getEnroladores())));
    }

    @Operation(
        description = "Permite obtener los enroladores que cumplan con los criterios de busqueda solicitados.",
        summary = "Obtener enroladores que cumplan con los criterios de busqueda solicitados.")
    @ApiResponse(
        responseCode = "200",
        description = HttpStatusMessage.API_HTTP_STATUS_200,
        content = @Content(schema = @Schema(implementation = Respuesta.class)))
    @PostMapping(
        path = "${endpoint.macro.datosgenerales.biometria.enroladores.buscar.path}",
        produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<ConsultarEnroladoresResponse>> getUsuariosEnrolador(
        @RequestBody ConsultarEnroladoresRequest consultarEnroladoresRequest) {
      return ResponseEntity.ok(
          new Respuesta<>(enroladorService.getUsuariosEnrolador(consultarEnroladoresRequest)));
    }

}
