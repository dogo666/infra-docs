package ar.com.macro.validacion.domain.identidad.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Objeto response del servicio consultar identidad
 * @author francisco.ocon
 *
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultarIdentidadResponse implements Serializable {

	private static final long serialVersionUID = 1698937546501931478L;

	@JsonProperty("idcliente")
	Integer idCliente;

	@JsonProperty("tipodocumentotributario")
	String tipoDocumentoTributario;

	@JsonProperty("numerodocumentotributario")
	String nroDocumentoTributario;

	@JsonProperty("tipodocumentoidentidad")
	String tipoDocumentoIdentidad;

	@JsonProperty("numerodocumentoidentidad")
	String nroDocumentoIdentidad;

	@JsonProperty("apellido")
	String apellido;

	@JsonProperty("nombre")
	String nombre;

}
