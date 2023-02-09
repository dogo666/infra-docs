package ar.com.macro.datosgenerales.domain.parametros.rest.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosConfigResponse implements Serializable {

	private static final long serialVersionUID = -9108446381183286548L;
	
	List<ParametrosConfigDto> parametros;
}
