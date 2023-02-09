package ar.com.macro.datosgenerales.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import ar.com.macro.datosgenerales.domain.parametros.rest.dto.response.ParametrosConfigResponse;
import ar.com.macro.datosgenerales.domain.parametros.rest.dto.response.ParametrosConfigDto;
import ar.com.macro.datosgenerales.model.repository.MsCompositeParamConfigRepositorio;
import ar.com.macro.datosgenerales.model.repository.MsSingleParamConfigRepositorio;
import ar.com.macro.datosgenerales.model.repository.entity.MsCompositeParamConfig;
import ar.com.macro.datosgenerales.model.repository.entity.MsSingleParamConfig;
import ar.com.macro.datosgenerales.model.service.ParametrosConfigService;

@Service
public class ParametrosConfigServiceImpl implements ParametrosConfigService {
	
	private MsSingleParamConfigRepositorio msSingleParamConfigRepositorio;
	private MsCompositeParamConfigRepositorio msCompositeParamConfigRepositorio;
	
	public ParametrosConfigServiceImpl(
			MsSingleParamConfigRepositorio msSingleParamConfigRepositorio,
			MsCompositeParamConfigRepositorio msCompositeParamConfigRepositorio) {
		this.msSingleParamConfigRepositorio = msSingleParamConfigRepositorio;
		this.msCompositeParamConfigRepositorio = msCompositeParamConfigRepositorio;
	}

	@Override
	public ParametrosConfigResponse buscarConfiguracionSimpleIniciativaServicio(String iniciativa, String microservicio) {
		ParametrosConfigResponse iniciativaConfigResponse = new ParametrosConfigResponse(new ArrayList<>());
		Optional<List<MsSingleParamConfig>> configuracionesOpt = null;
		if(StringUtils.isBlank(microservicio)) {
			configuracionesOpt = this.msSingleParamConfigRepositorio.findByAplicacionId(iniciativa);
		} else {
			configuracionesOpt = this.msSingleParamConfigRepositorio.findByAplicacionIdAndMicroservicioId(iniciativa, microservicio);
		}
		
		if (configuracionesOpt.isPresent()) {
			List<ParametrosConfigDto> configuraciones = configuracionesOpt.get().stream().map(ParametrosConfigDto::new).collect(Collectors.toList());
			iniciativaConfigResponse.setParametros(configuraciones);
		}
		return iniciativaConfigResponse;
	}

	@Override
	public ParametrosConfigResponse buscarConfiguracionCompuestaIniciativaServicioNombre(String iniciativa, String microservicio, String nombre) {
		ParametrosConfigResponse iniciativaConfigResponse = new ParametrosConfigResponse(new ArrayList<>());
		Optional<List<MsCompositeParamConfig>> configuracionesOpt = null;
		if(StringUtils.isNotBlank(iniciativa) && StringUtils.isBlank(microservicio) && StringUtils.isBlank(nombre)) {
			configuracionesOpt = this.msCompositeParamConfigRepositorio.findByAplicacionId(iniciativa);
		} else if (StringUtils.isNotBlank(iniciativa) && StringUtils.isNotBlank(microservicio) && StringUtils.isBlank(nombre)){
			configuracionesOpt = this.msCompositeParamConfigRepositorio.findByAplicacionIdAndMicroservicioId(iniciativa, microservicio);
		} else if (StringUtils.isNotBlank(iniciativa) && StringUtils.isNotBlank(microservicio) && StringUtils.isNotBlank(nombre)){
			configuracionesOpt = this.msCompositeParamConfigRepositorio.findByAplicacionIdAndMicroservicioIdAndNombre(iniciativa, microservicio, nombre);
		}
		
		if (configuracionesOpt.isPresent()) {
			List<ParametrosConfigDto> configuraciones = configuracionesOpt.get().stream().map(ParametrosConfigDto::new).collect(Collectors.toList());
			iniciativaConfigResponse.setParametros(configuraciones);
		}
		return iniciativaConfigResponse;
	}

	
}
