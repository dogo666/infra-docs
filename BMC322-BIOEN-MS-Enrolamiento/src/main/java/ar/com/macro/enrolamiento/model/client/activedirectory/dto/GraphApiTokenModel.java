package ar.com.macro.enrolamiento.model.client.activedirectory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * GraphApiTokenModel.
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "GraphApiTokenModel", description = "GraphApiTokenModel")
public class GraphApiTokenModel {

  @JsonProperty("token_type")
  String tokenType;

  @JsonProperty("expires_in")
  String expiresIn;

  @JsonProperty("ext_expires_in")
  String extExpiresIn;

  @JsonProperty("access_token")
  String accessToken;
  
}
