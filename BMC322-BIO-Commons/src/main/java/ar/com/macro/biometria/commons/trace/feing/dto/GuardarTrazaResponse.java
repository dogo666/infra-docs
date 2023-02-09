package ar.com.macro.biometria.commons.trace.feing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuardarTrazaResponse implements Serializable {

    private static final long serialVersionUID = -6305873857020062374L;

    private String success;
}
