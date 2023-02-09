package ar.com.macro.validacion.model.client.esb.rest.dto.consulta.cliente;

import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralCliente;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ConsultaClienteAtributosResponse implements Serializable {

	private static final long serialVersionUID = 937746439577246871L;
	
	private Integer clienteId;
	
	private String segmento;
	
	private String segmentoDescripcion;
	
	private String subsegmento;
	
	private String subsegmentoDescripcion;
	
	private String categoria;
	
	private String categoriaDescripcion;
	
	private String apellido;
	
	private String nombre;
	
	private String fechaNacimiento;
	
	private String codigoGenero;
	
	private String descripcionGenero;
	
	private String codigoEstadoCivil;
	
	private String descripcionEstadoCivil;
	
	private String codigoTipoPersona;
	
	private String descripcionTipoPersona;
	
	private String codigoSector;
	
	private String descripcionSector;
	
	private String codigoDocumentoTributario;
	
	private String descripcionDocumentoTributario;
	
	private String nroDocumentoTributario;
	
	private Integer codigoEjecutivoAsignado ;
	
	private String fechaCreacion;
	
	private String fechaModificacion;
	
	private String esMalaReferencia;
	
	private String descripcionEjecutivoAsignado;
	
	private String tipoDocumentoIdentidad;
	
	private String descripcionDocumentoIdentidad;
	
	private String nroDocumentoIdentidad;
	
	private Integer codigoSucursal;
	
	private String descripcionSucursal;
	
	private String codigoTipoTelefono;
	
	private String descripcionTipoTelefono;
	
	private String nroTelefono;
	
	private String ddiNroTelefono;
	
	private String ddnNroTelefono;
	
	private String tipoDireccionElectronica;
	
	private String direccionElectronica;
	
	private String codigoBanca;
	
	private String descripcionBanca;
	
	private String esLeyFatca;
	
	private String esSujetoObligado;
	
	private String esPep;
	
	private Integer provincia;
	
	private String descripcionProvincia;
	
	public ConsultaClienteAtributosResponse(ConsultaGeneralCliente consultaGeneralCliente) {
		this.clienteId = consultaGeneralCliente.getOCEnte();
		this.segmento = consultaGeneralCliente.getOCSegmento();
		this.segmentoDescripcion = consultaGeneralCliente.getODSegmento();
		this.subsegmento = consultaGeneralCliente.getOCSubsegmento();
		this.subsegmentoDescripcion = consultaGeneralCliente.getODSubsegmento();
		this.categoria = consultaGeneralCliente.getOCCategoria();
		this.categoriaDescripcion = consultaGeneralCliente.getODCategoria();
		this.apellido = consultaGeneralCliente.getODPrimerApellido();
		this.nombre = consultaGeneralCliente.getODNombre();
		this.fechaNacimiento = consultaGeneralCliente.getOFNacimiento();
		this.codigoGenero = consultaGeneralCliente.getOCSexo();
		this.descripcionGenero = consultaGeneralCliente.getODSexo();
		this.codigoEstadoCivil = consultaGeneralCliente.getOCEstadoCivil();
		this.descripcionEstadoCivil = consultaGeneralCliente.getODEstadoCivil();
		this.codigoTipoPersona = consultaGeneralCliente.getOCTipoPersona();
		this.descripcionTipoPersona = consultaGeneralCliente.getODTipoPersona();
		this.codigoSector = consultaGeneralCliente.getOCSector();
		this.descripcionSector = consultaGeneralCliente.getODSector();
		this.codigoDocumentoTributario = consultaGeneralCliente.getOCTtributa();
		this.descripcionDocumentoTributario = consultaGeneralCliente.getODTtributa();
		this.nroDocumentoTributario = consultaGeneralCliente.getONCedRuc();
		this.codigoEjecutivoAsignado = consultaGeneralCliente.getOCOficial();
		this.fechaCreacion = consultaGeneralCliente.getOFCreacion();
		this.fechaModificacion = consultaGeneralCliente.getOFModificacion();
		this.esMalaReferencia = consultaGeneralCliente.getOCMalaReferencia();
		this.descripcionEjecutivoAsignado = consultaGeneralCliente.getODOficial();
		this.tipoDocumentoIdentidad = consultaGeneralCliente.getOCTipoIdentificacion();
		this.descripcionDocumentoIdentidad = consultaGeneralCliente.getODTipoIdentificacion();
		this.nroDocumentoIdentidad = consultaGeneralCliente.getONNumeroIdentificacion();
		this.codigoSucursal = consultaGeneralCliente.getOCOficina();
		this.descripcionSucursal = consultaGeneralCliente.getODOficina();
		this.codigoTipoTelefono = consultaGeneralCliente.getOCTipoTelefono();
		this.descripcionTipoTelefono = consultaGeneralCliente.getODTipoTelefono();
		this.nroTelefono = consultaGeneralCliente.getONTelefono();
		this.ddiNroTelefono = consultaGeneralCliente.getONTelefonoDdi();
		this.ddnNroTelefono = consultaGeneralCliente.getONTelefonoDdn();
		this.tipoDireccionElectronica = consultaGeneralCliente.getOCDireccionElectronica();
		this.direccionElectronica = consultaGeneralCliente.getODDireccionElectronica();
		this.codigoBanca = consultaGeneralCliente.getOCBanca();
		this.descripcionBanca = consultaGeneralCliente.getODBanca();
		this.esLeyFatca = consultaGeneralCliente.getOMFatca();
		this.esSujetoObligado = consultaGeneralCliente.getOMSujetoObligado();
		this.esPep = consultaGeneralCliente.getOMPepEstado();
		this.provincia = consultaGeneralCliente.getOCDireccionProvincia();
		this.descripcionProvincia = consultaGeneralCliente.getODDireccionProvincia();
	}

}
