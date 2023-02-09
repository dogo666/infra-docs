package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * ImagenResponse.
 *
 * @author globant
 * @version 1.0
 * @since 2022-25-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImagenResponse implements Serializable {
  private static final long serialVersionUID = -7320172620446627513L;

  @JsonProperty("tipo")
  TipoImagen tipo;

  @JsonProperty("imagen")
  String imagen;

  public enum TipoImagen {
    SF, // Selfie Persona
    DF, // DNI Frente
    DD; // DNI Dorso
  }
}
