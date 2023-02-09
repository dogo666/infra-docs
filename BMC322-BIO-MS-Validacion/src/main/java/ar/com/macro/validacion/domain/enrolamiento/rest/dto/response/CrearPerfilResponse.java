package ar.com.macro.validacion.domain.enrolamiento.rest.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearPerfilResponse implements Serializable {

	private static final long serialVersionUID = 9036075935314334408L;
	
	private String iddaon;
}
