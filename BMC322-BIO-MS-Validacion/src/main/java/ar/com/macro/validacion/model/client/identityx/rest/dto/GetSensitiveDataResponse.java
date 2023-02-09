package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSensitiveDataResponse implements Serializable {
  private static final long serialVersionUID = 2175735911195950510L;
  
  String href;
  String value;
}
