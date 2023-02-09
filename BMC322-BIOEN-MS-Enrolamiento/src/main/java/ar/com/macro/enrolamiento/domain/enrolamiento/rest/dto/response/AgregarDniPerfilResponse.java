package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response;

import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgregarDniPerfilResponse implements Serializable {

    private static final long serialVersionUID = 3272666122020697281L;

    private String idusuario;
    private String iddaon;
    private String idcheck;
    private String iddocumento;

}
