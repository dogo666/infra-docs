package ar.com.macro.enrolamiento.domain.renaper.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidarHuellaEnRenaperResponse implements Serializable {
	
	private static final long serialVersionUID = 6860263150696507258L;
	
	private String puntaje;
	private String resultado;
}
