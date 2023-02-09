package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BothSidesDocumentRequest {

	String frente;

	String dorso;
}
