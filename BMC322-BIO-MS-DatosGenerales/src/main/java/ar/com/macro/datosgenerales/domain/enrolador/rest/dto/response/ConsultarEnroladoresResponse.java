package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ConsultarEnroladoresResponse.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ConsultarEnroladoresResponse implements Serializable {

  private static final long serialVersionUID = 6065701276975229762L;

  @JsonProperty("enroladores")
  private List<ConsultarEnroladorResponse> enroladores;
}
