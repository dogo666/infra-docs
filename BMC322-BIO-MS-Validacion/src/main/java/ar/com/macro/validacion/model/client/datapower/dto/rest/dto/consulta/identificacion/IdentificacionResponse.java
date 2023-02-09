package ar.com.macro.validacion.model.client.datapower.dto.rest.dto.consulta.identificacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdentificacionResponse {

    @JsonProperty("Apellido")
    String apellido;

    @JsonProperty("RazonSocialNombre")
    String razonSocialNombre;

    @JsonProperty("CodigoSexo")
    String codigoSexo;

    @JsonProperty("Sexo")
    String sexo;

    @JsonProperty("CodigoTributario")
    String codigoTributario;

    @JsonProperty("DescripcionTributario")
    String descripcionTributario;

    @JsonProperty("NumeroTributario")
    String numeroTributario;

    @JsonProperty("CodigoFuncionarioAsignado")
    String codigoFuncionarioAsignado;

    @JsonProperty("ApellidoNombreFuncionario")
    String apellidoNombreFuncionario;

    @JsonProperty("CodigoIdentificacion")
    String codigoIdentificacion;

    @JsonProperty("DescripcionIdentificacion")
    String descripcionIdentificacion;

    @JsonProperty("NumeroIdentificacion")
    String numeroIdentificacion;

    @JsonProperty("NumeroTelefono")
    String numeroTelefono;

    @JsonProperty("DDI")
    String ddi;

    @JsonProperty("DDN")
    String ddn;

    @JsonProperty("MailCliente")
    String mailCliente;

    @JsonProperty("CodigoBanca")
    String codigoBanca;

    @JsonProperty("Banca")
    String banca;

    @JsonProperty("CodigoSegmento")
    String codigoSegmento;

    @JsonProperty("Segmento")
    String segmento;

    @JsonProperty("CodigoSubsegmento")
    String codigoSubsegmento;

    @JsonProperty("Subsegmento")
    String subsegmento;

    @JsonProperty("CodigoCategoria")
    String codigoCategoria;

    @JsonProperty("Categoria")
    String categoria;

    @JsonProperty("IdCobis")
    String idCobis;

    @JsonProperty("FuncionarioAsignado")
    String funcionarioAsignado;

}
