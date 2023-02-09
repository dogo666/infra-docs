package ar.com.macro.enrolamiento.model.client.datapower.dto.rest.dto.consulta.identificacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultaIdentificacionClienteResponse {

    @JsonProperty("elements")
    List<IdentificacionResponse> elements;

}
