package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * SucursalesEnroladorResponse.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-20
 */
@Value(staticConstructor = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SucursalesEnroladorResponse implements Serializable {
  private static final long serialVersionUID = 2186746497220077737L;

  @JsonProperty("sucursales")
  private List<String> sucursales;
}
