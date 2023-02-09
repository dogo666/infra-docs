package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodigoBarrasResponse implements Serializable {

	private static final long serialVersionUID = -1596731552888010870L;
	
	String genero;
	String numero;
	String nombrecompleto;
	String nombres;
	String apellidos;
	String numerotramite;
}
