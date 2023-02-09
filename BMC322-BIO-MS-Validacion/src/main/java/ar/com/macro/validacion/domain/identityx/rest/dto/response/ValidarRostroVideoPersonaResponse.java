package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.response.ValidarRostroRenaperResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ValidarRostroVideoPersonaResponse implements Serializable {
  private static final long serialVersionUID = -5274683206594320204L;

  Integer status;
  RostroResponse rostro;

  @Getter
  @Setter
  @NoArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class RostroResponse implements Serializable {
    private static final long serialVersionUID = 6477563629260373801L;

    Integer confidence;
    Integer match;

    public RostroResponse(final ValidarRostroRenaperResponse response) {
      if (Objects.nonNull(response)) {
        this.confidence = response.getConfidence();
        
        if (response.getMatch() != null) {
          this.match = response.getMatch().booleanValue() ? 1 : 0;
        }
      }
    }
  }
}
