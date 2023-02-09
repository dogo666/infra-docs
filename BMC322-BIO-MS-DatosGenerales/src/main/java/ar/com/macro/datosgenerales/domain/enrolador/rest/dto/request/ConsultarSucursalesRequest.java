package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultarSucursalesRequest {
    @JsonProperty("estados")
    @NotNull
    private List<String> estados;

}
