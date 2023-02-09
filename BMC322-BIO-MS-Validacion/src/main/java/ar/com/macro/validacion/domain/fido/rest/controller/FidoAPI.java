package ar.com.macro.validacion.domain.fido.rest.controller;


import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.http.HttpStatusMessage;
import ar.com.macro.validacion.domain.fido.rest.dto.request.*;
import ar.com.macro.validacion.domain.fido.rest.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Tag(name = "FidoController", description = "fido")
interface FidoAPI {


    @Operation(
            description = "FIDO - Crear Registracion Request",
            summary = "Crear Registracion Request")

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
    ResponseEntity<Respuesta<CrearRegistracionResponse>> createRegRequest(
            @Valid @RequestBody CrearRegistracionRequest crearRegistracionRequest,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID);

    @Operation(
            description = "FIDO - Procesar Registracion Request",
            summary = "Procesar Registracion Request")

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

    @ResponseBody
    ResponseEntity<Respuesta<ProcesarRegistracionResponse>> procesarRegistracion(
            @RequestBody ProcesarRegistracionRequest procesarRegistracionRequest,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID);

    @Operation(
            description = "FIDO - Crear Autenticacion Request",
            summary = "Crear Autenticacion Request")

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

    @ResponseBody
    ResponseEntity<Respuesta<CrearAutenticacionResponse>> crearAutenticacion(
            @RequestBody CrearAutencicacionRequest createAuthRequestRequest,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID);

    @Operation(
            description = "FIDO - Validar Autenticacion",
            summary = "Validar Autenticacion ")

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

    @ResponseBody
    ResponseEntity<Respuesta<ValidarAutenticacionResponse>> validarAutenticacion(
            @Valid @RequestBody ValidarAutenticacionRequest validateTransactionAuth,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID);

    @Operation(
            description = "FIDO - Obtener Request de Authenticación",
            summary = "Request de Authenticación")
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
    @ResponseBody
    ResponseEntity<Respuesta<GetAutenticationResponse>> getAuthenticationRequest(
            @Valid @RequestBody GetAutenticationRequest request,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID);

    @Operation(
            description = "FIDO - Lista autenticadores de usuario",
            summary = "Request de autenticadores")
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
    @ResponseBody
    ResponseEntity<Respuesta<GetAutenticadoresResponse>> getAutenticadores(
            @Valid @RequestBody GetAutenticadoresRequest request,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID);

    @Operation(
            description = "FIDO - Consultar Registracion Request",
            summary = "Consultar Registracion Request")
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
    ResponseEntity<Respuesta<ConsultarRegistracionResponse>> consultarRegistracion(
            @Valid @RequestBody ConsultarRegistracionRequest consultarRegistracionRequest,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID);
}

