package ar.com.macro.validacion.domain.fido.rest.dto.request;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetAutenticationRequest {

  @Schema(description = "Id Usuario de Identity-X", required = true)
  @NotNull(message = "{api.validacion.no.blank.message}")
  @JsonProperty("idusuario")
  String idUsuario;
  
  @Schema(description = "Fido Authentication Request Id de Identity-X", required = true)
  @NotNull(message = "{api.validacion.no.blank.message}")
  @JsonProperty("authenticationrequestid")
  String authenticationRequestId;
}
