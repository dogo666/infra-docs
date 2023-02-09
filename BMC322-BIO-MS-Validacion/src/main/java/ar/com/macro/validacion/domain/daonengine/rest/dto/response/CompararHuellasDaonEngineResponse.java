package ar.com.macro.validacion.domain.daonengine.rest.dto.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CompararHuellasDaonEngineResponse
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-26
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CompararHuellasDaonEngineResponse implements Serializable {
	private static final long serialVersionUID = 1097654002021888631L;
	
	private int status;
	
}
