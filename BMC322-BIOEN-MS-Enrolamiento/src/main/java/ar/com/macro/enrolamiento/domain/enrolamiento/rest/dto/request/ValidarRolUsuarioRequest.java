package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ar.com.macro.commons.values.enums.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * ValidarRolUsuarioRequest.
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-18
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidarRolUsuarioRequest {

  @Schema(description = "Email del usuario.", required = true)
  @NotEmpty(message = "{api.validacion.ValidarRolUsuarioRequest.email.empty.message}")
  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{api.validacion.ValidarRolUsuarioRequest.email.alfa.pattern.message}")
  @JsonProperty(value = "email", required = true)
  String email;
  
  @Schema(description = "Lista de Roles para verificar si el usuario pertenece a alguno de ellos.", required = true)
  @NotEmpty(message = "{api.validacion.ValidarRolUsuarioRequest.roles.empty.message}")
  @JsonProperty(value = "roles", required = true)
  List<Rol> roles;
  
}
