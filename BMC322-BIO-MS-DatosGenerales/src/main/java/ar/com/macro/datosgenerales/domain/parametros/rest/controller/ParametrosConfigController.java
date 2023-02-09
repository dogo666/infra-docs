package ar.com.macro.datosgenerales.domain.parametros.rest.controller;

import org.slf4j.MDC;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.config.TraceConstants;
import ar.com.macro.commons.values.constants.http.HttpStatusMessage;
import ar.com.macro.datosgenerales.domain.datosgenerales.rest.controller.BaseController;
import ar.com.macro.datosgenerales.domain.parametros.rest.dto.request.ParametrosConfigRequest;
import ar.com.macro.datosgenerales.domain.parametros.rest.dto.response.ParametrosConfigResponse;
import ar.com.macro.datosgenerales.model.service.ParametrosConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ParametrosConfigController", description = "datosgenerales")
@RestController
@RefreshScope
public class ParametrosConfigController extends BaseController {

	private ParametrosConfigService parametrosConfigService;

	public ParametrosConfigController(ParametrosConfigService parametrosConfigService) {
		this.parametrosConfigService = parametrosConfigService;
	}

	@Operation(description = "Consulta los parámetros de configuración de una iniciativa", summary = "Consulta los parámetros de configuración de una iniciativa")
	@ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = HttpStatusMessage.API_HTTP_STATUS_200,
          content = @Content(schema = @Schema(implementation = Respuesta.class)))
    })
	@PostMapping(path = "${endpoint.macro.datosgenerales.configuracion.simple.parametros.consultar.path}", 
			produces = MediaType.APPLICATION_JSON_VALUE,
    		consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ParametrosConfigResponse>> buscarConfiguracionSimpleIniciativa(@RequestBody ParametrosConfigRequest parametrosConfigRequest) {
		ParametrosConfigResponse response = this.parametrosConfigService.buscarConfiguracionSimpleIniciativaServicio(MDC.get(TraceConstants.APPLICATION), parametrosConfigRequest.getMicroservicio());
		return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<ParametrosConfigResponse>(response));
	}
	
	@Operation(description = "Consulta los parámetros de configuración de una iniciativa", summary = "Consulta los parámetros de configuración de una iniciativa")
	@ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = HttpStatusMessage.API_HTTP_STATUS_200,
          content = @Content(schema = @Schema(implementation = Respuesta.class)))
    })
	@PostMapping(path = "${endpoint.macro.datosgenerales.configuracion.compuesta.parametros.consultar.path}", 
			produces = MediaType.APPLICATION_JSON_VALUE,
    		consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<ParametrosConfigResponse>> buscarConfiguracionCompuestaIniciativa(@RequestBody ParametrosConfigRequest parametrosConfigRequest) {
		ParametrosConfigResponse response = this.parametrosConfigService.buscarConfiguracionCompuestaIniciativaServicioNombre(MDC.get(TraceConstants.APPLICATION), parametrosConfigRequest.getMicroservicio(), parametrosConfigRequest.getNombre());
		return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<ParametrosConfigResponse>(response));
	}
}
