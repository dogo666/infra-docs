package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * GuardarTrazasDaonEngineResponse.
 *
 * @author globant
 * @version 1.0
 * @since 2022-06-09
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuardarTrazasDaonEngineResponse implements Serializable {
  private static final long serialVersionUID = -5685062565370741596L;
 
  Integer status;
}
