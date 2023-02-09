package ar.com.macro.datosgenerales.domain.operacion.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrearOperationIdResponse implements Serializable {

  private static final long serialVersionUID = 1L;
  private String idoperacion;
}
