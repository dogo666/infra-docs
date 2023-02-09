package ar.com.macro.validacion.domain.fido.rest.controller;

import ar.com.macro.biometria.commons.trace.annotations.TraceBiometricsAnnotation;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.validacion.domain.BaseController;
import ar.com.macro.validacion.domain.fido.rest.dto.request.*;
import ar.com.macro.validacion.domain.fido.rest.dto.response.*;
import ar.com.macro.validacion.model.service.FidoService;
import ar.com.macro.validacion.model.service.dto.FIDORegChallenge;
import ar.com.macro.validacion.model.service.dto.FIDORegChallengeAndId;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RefreshScope
@RestController
@RequiredArgsConstructor
public class FidoController extends BaseController implements FidoAPI{

	private final FidoService fidoService;

	@TraceBiometricsAnnotation
    @PostMapping(path = "${endpoint.macro.validacion.fido.registracion.crear.path}",
    		produces = APPLICATION_JSON_VALUE,
    		consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<CrearRegistracionResponse>> createRegRequest(
			@Valid @RequestBody CrearRegistracionRequest crearRegistracionRequest,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationID) {

		log.debug("createRegRequest: {}", crearRegistracionRequest);
		FIDORegChallengeAndId fidoRegChallengeAndId = fidoService.crearRegistracion(
				crearRegistracionRequest.getIdUsuario(),
				crearRegistracionRequest.getSponsorshipToken(),
				xOperationID);
        return ResponseEntity.ok(new Respuesta<CrearRegistracionResponse>(new CrearRegistracionResponse(fidoRegChallengeAndId)));
    }

	@TraceBiometricsAnnotation
	@PostMapping(value = "${endpoint.macro.validacion.fido.registracion.procesar.path}",
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Respuesta<ProcesarRegistracionResponse>>  procesarRegistracion(
			@RequestBody ProcesarRegistracionRequest procesarRegistracionRequest,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationID) {

		log.debug("procesarRegistracion: {}", procesarRegistracionRequest);
		FIDORegChallenge challenge = fidoService.processRegistrationResponse(
				procesarRegistracionRequest.getIdXId(),
				procesarRegistracionRequest.getRegistrationChallengeId(),
				procesarRegistracionRequest.getFidoRegistrationResponse());
		return ResponseEntity.ok(new Respuesta<ProcesarRegistracionResponse>(new ProcesarRegistracionResponse(challenge)));
	}

    @TraceBiometricsAnnotation
    @PostMapping(value = "${endpoint.macro.validacion.fido.autenticacion.crear.path}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Respuesta<CrearAutenticacionResponse>> crearAutenticacion(
            @RequestBody CrearAutencicacionRequest createAuthRequestRequest,
            @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationID) {

        log.debug("crearAutenticacion: {}", createAuthRequestRequest);
        AuthenticationRequest authenticationRequest = fidoService.crearAuthenticationRequest(
				createAuthRequestRequest.getIdUsuario(), xOperationID);
        return ResponseEntity.ok(
				new Respuesta<CrearAutenticacionResponse>(new CrearAutenticacionResponse(authenticationRequest)));
    }

	@TraceBiometricsAnnotation
	@PostMapping(value = "${endpoint.macro.validacion.fido.autenticacion.validar.path}",
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Respuesta<ValidarAutenticacionResponse>>  validarAutenticacion(
			@Valid @RequestBody ValidarAutenticacionRequest validateTransactionAuth,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationID) {

		log.debug("validarAutenticacion: {}",validateTransactionAuth);
		AuthenticationRequest authRequest = fidoService.validarAuthenticationRequest(
				validateTransactionAuth.getAuthenticationRequestId(),
				validateTransactionAuth.getFidoAuthenticationResponse());
		return ResponseEntity.ok(
				new Respuesta<ValidarAutenticacionResponse>(new ValidarAutenticacionResponse(authRequest)));
	}

    @TraceBiometricsAnnotation
    @PostMapping(
        value = "${endpoint.macro.validacion.fido.autenticacion.consultar.path}",
        produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Respuesta<GetAutenticationResponse>> getAuthenticationRequest(
        @Valid @RequestBody GetAutenticationRequest request,
        @RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID) {
  
      log.debug("getAuthenticationRequest: {}", request);
  
      var authRequest = fidoService.getAuthenticationRequestByUser(
              request.getIdUsuario(), request.getAuthenticationRequestId());
  
      return ResponseEntity.ok(
          new Respuesta<GetAutenticationResponse>(new GetAutenticationResponse(authRequest)));
    }

	@TraceBiometricsAnnotation
	@PostMapping(
			value = "${endpoint.macro.validacion.fido.autenticadores.consultar.path}",
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Respuesta<GetAutenticadoresResponse>> getAutenticadores(
			@Valid @RequestBody GetAutenticadoresRequest request,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID) {

		log.debug("getAutenticadores: {}", request);
		var autenticadores = fidoService.getAutenticadores(request.getIdUsuario());
		return ResponseEntity.ok(
				new Respuesta<GetAutenticadoresResponse>(new GetAutenticadoresResponse(autenticadores)));
	}

	@TraceBiometricsAnnotation
	@PostMapping(
			value = "${endpoint.macro.validacion.fido.autenticadores.archivar.path}",
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Respuesta<ArchivarAutenticadorResponse>> archivarAutenticador(
			@Valid @RequestBody ArchivarAutenticadorRequest request,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) final String xOperationID) {

		log.debug("archivarAutenticador: {}", request);
		var autenticador = fidoService.archivarAutenticador(request.getId());
		return ResponseEntity.ok(
				new Respuesta<ArchivarAutenticadorResponse>(new ArchivarAutenticadorResponse(autenticador)));
	}

	@TraceBiometricsAnnotation
	@PostMapping(path = "${endpoint.macro.validacion.fido.registracion.consultar.path}",
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ConsultarRegistracionResponse>> consultarRegistracion(
			@Valid @RequestBody ConsultarRegistracionRequest consultarRegistracionRequest,
			@RequestHeader(value = HeaderConstants.HEADER_X_OPERATION_ID, required = true) String xOperationID) {

		log.debug("consultarRegistracion: {}", consultarRegistracionRequest);
		var registracion = fidoService.consultarRegistracion(
				consultarRegistracionRequest.getRegistrationChallengeId());
		return ResponseEntity.ok(
				new Respuesta<ConsultarRegistracionResponse>(new ConsultarRegistracionResponse(registracion)));
	}
  }
