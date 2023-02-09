package ar.com.macro.enrolamiento.model.client.activedirectory.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * GraphApiUserResultModel.
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "GraphApiUserResultModel", description = "GraphApiUserResultModel")
public class GraphApiUserResultModel {

  @JsonProperty("@odata.count")
  int count;

  @JsonProperty("value")
  List<GraphApiUserModel> users;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @FieldDefaults(level = AccessLevel.PRIVATE)
  @Schema(name = "GraphApiUserModel", description = "GraphApiUserModel")
  static class GraphApiUserModel {
   
    @JsonProperty("mail")
    String mail;
  }
}
