package ar.com.macro.datosgenerales.domain.parametros.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosConfigRequest {

	private String microservicio;
	private String nombre;
}
