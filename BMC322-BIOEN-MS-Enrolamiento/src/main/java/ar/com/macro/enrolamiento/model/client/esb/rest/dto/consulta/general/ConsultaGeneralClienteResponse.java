package ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * Objeto response del servicio Consulta General Cliente
 * @author juan.hoyos
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaGeneralClienteResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Valid
	@JsonProperty("elements")
	private List<ConsultaGeneralCliente> consultaGeneralClienteList;

	public List<ConsultaGeneralCliente> getConsultaGeneralClienteList() {
		return consultaGeneralClienteList;
	}

	public void setConsultaGeneralClienteList(List<ConsultaGeneralCliente> consultaGeneralClienteList) {
		this.consultaGeneralClienteList = consultaGeneralClienteList;
	}
	
}
