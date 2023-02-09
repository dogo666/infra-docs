package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultarEnroladorResponse implements Serializable {
    private static final long serialVersionUID = -6955583165425997681L;

    @JsonProperty("idusuario")
    String idUsuario;

    @JsonProperty("tipodocumento")
    String tipoDocumento;

    @JsonProperty("numerodocumento")
    String numeroDocumento;

    @JsonProperty("genero")
    String genero ;

    @JsonProperty("sucursal")
    String sucursal;

    @JsonProperty("idcobis")
    String idCobis;
    
    @JsonProperty("estado")
    String estado;

    @JsonProperty("cantidaddehuellas")
    Integer cantidadDeHuellas;

    @JsonProperty("razon")
    String razon;

    @JsonProperty("supervisor")
    String supervisor;

}
