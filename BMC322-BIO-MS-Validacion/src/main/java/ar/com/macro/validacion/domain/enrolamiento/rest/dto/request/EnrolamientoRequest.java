package ar.com.macro.validacion.domain.enrolamiento.rest.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnrolamientoRequest implements Serializable {

    private static final long serialVersionUID = 9146205858140661445L;

    @Schema(description = "DNI del cliente", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    @Pattern(regexp = "\\d{1,9}", message = "{api.validacion.validacion.dni.pattern.message}")
    private String dni;
}
