package ar.com.macro.enrolamiento.domain.identidad.rest.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

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
