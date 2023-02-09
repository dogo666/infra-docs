package ar.com.macro.validacion.model.client.renaper.dto.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidarDNIRenaperClientResponse implements Serializable {

    private static final String VIGENTE = "Vigente";

    private String code;
    private String message;
    private String person;
    private String valid;

    public boolean isValid() {
        return VIGENTE.equalsIgnoreCase(valid);
    }
}
