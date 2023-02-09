package ar.com.macro.validacion.domain.daonengine.rest.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidarHuellaDaonEngineResponse implements Serializable {

	private static final long serialVersionUID = 1097654002021888631L;

	private String idrequesttracking;
	
	private String resultado;
	
	private String score;
	
	private String codigo;
	
	private String mensaje;
}
