package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DocumentCollection.
 *
 * @author globant
 * @version 1.0
 * @since 2022-26-07
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentCollection {
  Item[] items;
  
  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Item {
    Date captured;
    ClientDocumentCapture clientCapture;
  }
}
