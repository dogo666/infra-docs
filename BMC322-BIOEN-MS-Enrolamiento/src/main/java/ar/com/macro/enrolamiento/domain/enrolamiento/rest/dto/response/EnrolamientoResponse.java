package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrolamientoResponse implements Serializable {

    private static final long serialVersionUID = 8791119568394330417L;

    private String nombreMicroservicio;
}
