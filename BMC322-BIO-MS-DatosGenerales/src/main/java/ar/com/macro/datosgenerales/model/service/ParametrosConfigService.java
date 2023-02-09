package ar.com.macro.datosgenerales.model.service;

import ar.com.macro.datosgenerales.domain.parametros.rest.dto.response.ParametrosConfigResponse;

public interface ParametrosConfigService {

	ParametrosConfigResponse buscarConfiguracionSimpleIniciativaServicio(String iniciativa, String microservicio);
	ParametrosConfigResponse buscarConfiguracionCompuestaIniciativaServicioNombre(String iniciativa, String microservicio, String nombre);
}
