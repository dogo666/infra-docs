package ar.com.macro.enrolamiento.domain.identityx.rest.dto.response;

import java.io.Serializable;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CodigoBarrasResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultarDatosDniResponse implements Serializable {
  private static final long serialVersionUID = -6786210278255715357L;
  
  private CodigoBarrasResponse codigobarras;
}
