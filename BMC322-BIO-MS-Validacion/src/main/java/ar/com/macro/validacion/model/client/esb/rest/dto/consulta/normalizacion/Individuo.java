package ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Individuo implements Serializable {

    private static final long serialVersionUID = -4324212113724151282L;

    private Integer tipoDocumento;

    private Integer numeroDocumento;

    private Integer tipoTributario;

    private Long numeroTributario;

    private String apellido;

    private String nombre;

    private String sexo;

    private String fechaNacimiento;

    private String marcaTributaria;

    public Individuo() {
        super();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("tipoDocumento", tipoDocumento)
                .append("numeroDocumento", numeroDocumento).append("tipoTributario", tipoTributario)
                .append("numeroTributario", numeroTributario).append("apellido", apellido).append("nombre", nombre)
                .append("sexo", sexo).append("fechaNacimiento", fechaNacimiento)
                .append("marcaTributaria", marcaTributaria).toString();
    }

}
