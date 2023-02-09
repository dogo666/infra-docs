package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SucursalesEnroladorResponse.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SucursalesEnroladorResponse implements Serializable {
  private static final long serialVersionUID = 2186746497220077737L;

  @JsonProperty("sucursales")
  private List<String> sucursales;
}
