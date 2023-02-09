package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarPersonaResponse implements Serializable {
  private static final long serialVersionUID = 5977860680923195990L;
  
  @JsonProperty("imagenes")
  private List<ImagenResponse> imagenes = new ArrayList<>();
  
  public ConsultarPersonaResponse addImagen(ImagenResponse imagen) {
    getImagenes().add(imagen);
    return this;
  }
}
