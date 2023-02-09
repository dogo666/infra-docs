package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response;

import java.io.Serializable;

import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CodigoBarrasResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeerPdf417DniResponse implements Serializable {

	private static final long serialVersionUID = 791286510501724664L;
	
	CodigoBarrasResponse codigobarras; 
}
