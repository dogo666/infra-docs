package ar.com.macro.enrolamiento.domain.renaper.rest.dto.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarTcnHuellaEnRenaperResponse implements Serializable {

  private static final long serialVersionUID = 3305944036228706610L;

  private String tcnValue;
}