package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * ConsultarEnroladoresResponse.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-19
 */
@Value(staticConstructor = "of")
public class ConsultarEnroladoresResponse implements Serializable {

  private static final long serialVersionUID = 6065701276975229762L;

  @JsonProperty("enroladores")
  private List<ConsultarEnroladorResponse> enroladores;
}
