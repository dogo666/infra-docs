package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import ar.com.macro.commons.values.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ValidarRolUsuarioResponse
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-18
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ValidarRolUsuarioResponse implements Serializable {
  private static final long serialVersionUID = 8384254638694408728L;

  @JsonProperty("roles")
  private List<Rol> roles = new ArrayList<>();
  
}
