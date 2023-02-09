package ar.com.macro.enrolamiento.domain.identityx.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolarRostroResponse implements Serializable {
  private static final long serialVersionUID = 6372111201430649666L;
 
  private String status;
}
