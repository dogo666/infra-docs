package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import ar.com.macro.validacion.model.client.identityx.rest.dto.CodigoBarrasResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultarDatosDniResponse implements Serializable {

    private CodigoBarrasResponse codigobarras;
}
