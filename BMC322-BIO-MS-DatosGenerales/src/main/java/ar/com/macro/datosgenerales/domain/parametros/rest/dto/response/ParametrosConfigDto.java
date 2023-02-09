package ar.com.macro.datosgenerales.domain.parametros.rest.dto.response;

import java.io.Serializable;

import ar.com.macro.datosgenerales.model.repository.entity.MsCompositeParamConfig;
import ar.com.macro.datosgenerales.model.repository.entity.MsSingleParamConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosConfigDto implements Serializable {
	
	private static final long serialVersionUID = -4823934536814252447L;
	private String microservicio;
	private String nombre;
	private String valor;

	public ParametrosConfigDto(MsSingleParamConfig msSingleParamConfig) {
		this.microservicio = msSingleParamConfig.getMicroservicioId();
		this.nombre = msSingleParamConfig.getNombre();
		this.valor = msSingleParamConfig.getValor();
	}
	
	public ParametrosConfigDto(MsCompositeParamConfig msCompositeParamConfig) {
		this.microservicio = msCompositeParamConfig.getMicroservicioId();
		this.nombre = msCompositeParamConfig.getNombre();
		this.valor = msCompositeParamConfig.getValor();
	}
}
