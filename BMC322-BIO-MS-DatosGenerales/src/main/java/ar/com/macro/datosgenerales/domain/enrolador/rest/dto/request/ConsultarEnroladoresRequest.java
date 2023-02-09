package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request;

import static java.util.Objects.nonNull;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * ConsultarEnroladoresRequest.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-19
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultarEnroladoresRequest {

  @JsonProperty("estados")
  List<String> estados;

  @JsonProperty("sucursales")
  List<String> sucursales;

  public static boolean hasValue(final List<String> value) {
    return nonNull(value) && !value.isEmpty();
  }
}
