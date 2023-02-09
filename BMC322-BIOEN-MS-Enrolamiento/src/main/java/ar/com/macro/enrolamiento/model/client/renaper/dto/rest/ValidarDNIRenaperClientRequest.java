package ar.com.macro.enrolamiento.model.client.renaper.dto.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidarDNIRenaperClientRequest {

    private String number;
    private String gender;
    private String order;
}
//