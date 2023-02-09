package ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString
public class ConsultaGeneralCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("o_d_primer_apellido")
	private String oDPrimerApellido;
	@JsonProperty("o_d_nombre")
	private String oDNombre;
	@JsonProperty("o_f_nacimiento")
	private String oFNacimiento;
	@JsonProperty("o_c_sexo")
	private String oCSexo;
	@JsonProperty("o_d_sexo")
	private String oDSexo;
	@JsonProperty("o_c_estado_civil")
	private String oCEstadoCivil;
	@JsonProperty("o_d_estado_civil")
	private String oDEstadoCivil;
	@JsonProperty("o_n_impuesto")
	private String oNImpuesto;
	@JsonProperty("o_d_impuesto")
	private String oDImpuesto;
	@JsonProperty("o_c_tipo_persona")
	private String oCTipoPersona;
	@JsonProperty("o_d_tipo_persona")
	private String oDTipoPersona;
	@JsonProperty("o_f_inicio_actividades")
	private String oFInicioActividades;
	@JsonProperty("o_c_tipo_compania")
	private String oCTipoCompania;
	@JsonProperty("o_d_tipo_compania")
	private String oDTipoCompania;
	@JsonProperty("o_c_pais")
	private Integer oCPais;
	@JsonProperty("o_d_pais")
	private String oDPais;
	@JsonProperty("o_c_residencia")
	private String oCResidencia;
	@JsonProperty("o_d_residencia")
	private String oDResidencia;
	@JsonProperty("o_c_pais_residencia")
	private String oCPaisResidencia;
	@JsonProperty("o_d_pais_residencia")
	private String oDPaisResidencia;
	@JsonProperty("o_c_sector")
	private String oCSector;
	@JsonProperty("o_d_sector")
	private String oDSector;
	@JsonProperty("o_c_ttributa")
	private String oCTtributa;
	@JsonProperty("o_d_ttributa")
	private String oDTtributa;
	@JsonProperty("o_n_ced_ruc")
	private String oNCedRuc;
	@JsonProperty("o_c_ttitular")
	private String oCTtitular;
	@JsonProperty("o_d_ttitular")
	private String oDTtitular;
	@JsonProperty("o_c_actividad")
	private String oCActividad;
	@JsonProperty("o_d_actividad")
	private String oDActividad;
	@JsonProperty("o_c_categoria_impuesto")
	private String oCCategoriaImpuesto;
	@JsonProperty("o_d_grupo")
	private String oDGrupo;
	@JsonProperty("o_d_comentario")
	private String oDComentario;
	@JsonProperty("o_c_iva")
	private String oCIva;
	@JsonProperty("o_d_iva")
	private String oDIva;
	@JsonProperty("o_i_ivaexen")
	private Double oIIvaexen;
	@JsonProperty("o_f_ivaexen")
	private String oFIvaexen;
	@JsonProperty("o_i_ivareduc")
	private Double oIIvareduc;
	@JsonProperty("o_f_ivareduc")
	private String oFIvareduc;
	@JsonProperty("o_c_alcanzo")
	private String oCAlcanzo;
	@JsonProperty("o_c_retencion")
	private String oCRetencion;
	@JsonProperty("o_c_dgi")
	private String oCDgi;
	@JsonProperty("o_d_dgi")
	private String oDDgi;
	@JsonProperty("o_f_dgi")
	private String oFDgi;
	@JsonProperty("o_i_dgiexen")
	private Double oIDgiexen;
	@JsonProperty("o_c_oficial")
	private Integer oCOficial;
	@JsonProperty("o_f_creacion")
	private String oFCreacion;
	@JsonProperty("o_f_modificacion")
	private String oFModificacion;
	@JsonProperty("o_c_mala_referencia")
	private String oCMalaReferencia;
	@JsonProperty("o_c_grupo_clasificacion")
	private String oCGrupoClasificacion;
	@JsonProperty("o_d_oficial")
	private String oDOficial;
	@JsonProperty("o_c_tipo_identificacion")
	private String oCTipoIdentificacion;
	@JsonProperty("o_d_tipo_identificacion")
	private String oDTipoIdentificacion;
	@JsonProperty("o_n_numero_identificacion")
	private String oNNumeroIdentificacion;
	@JsonProperty("o_c_prov_pertenencia")
	private String oCProvPertenencia;
	@JsonProperty("o_d_prov_pertenencia")
	private String oDProvPertenencia;
	@JsonProperty("o_s_identificacion")
	private Integer oSIdentificacion;
	@JsonProperty("o_s_telefono")
	private Integer oSTelefono;
	@JsonProperty("o_s_direccion_electronica")
	private Integer oSDireccionElectronica;
	@JsonProperty("o_s_direccion")
	private Integer oSDireccion;
	@JsonProperty("o_c_subtipo")
	private String oCSubtipo;
	@JsonProperty("o_d_direccion")
	private String oDDireccion;
	@JsonProperty("o_n_direccion")
	private Integer oNDireccion;
	@JsonProperty("o_n_direccion_piso")
	private String oNDireccionPiso;
	@JsonProperty("o_n_direccion_depto")
	private String oNDireccionDepto;
	@JsonProperty("o_n_direccion_codigopostal")
	private String oNDireccionCodigopostal;
	@JsonProperty("o_c_direccion_ciudad")
	private Integer oCDireccionCiudad;
	@JsonProperty("o_d_direccion_ciudad")
	private String oDDireccionCiudad;
	@JsonProperty("o_c_direccion_provincia")
	private Integer oCDireccionProvincia;
	@JsonProperty("o_d_direccion_provincia")
	private String oDDireccionProvincia;
	@JsonProperty("o_c_direccion_pais")
	private Integer oCDireccionPais;
	@JsonProperty("o_d_direccion_pais")
	private String oDDireccionPais;
	@JsonProperty("o_c_direccion_tipo")
	private String oCDireccionTipo;
	@JsonProperty("o_d_direccion_tipo")
	private String oDDireccionTipo;
	@JsonProperty("o_c_oficina")
	private Integer oCOficina;
	@JsonProperty("o_d_oficina")
	private String oDOficina;
	@JsonProperty("o_c_tipo_telefono")
	private String oCTipoTelefono;
	@JsonProperty("o_d_tipo_telefono")
	private String oDTipoTelefono;
	@JsonProperty("o_n_telefono")
	private String oNTelefono;
	@JsonProperty("o_n_telefono_interno")
	private String oNTelefonoInterno;
	@JsonProperty("o_n_telefono_ddi")
	private String oNTelefonoDdi;
	@JsonProperty("o_n_telefono_ddn")
	private String oNTelefonoDdn;
	@JsonProperty("o_c_direccion_electronica")
	private String oCDireccionElectronica;
	@JsonProperty("o_d_direccion_electronica")
	private String oDDireccionElectronica;
	@JsonProperty("o_m_es_cliente")
	private String oMEsCliente;
	@JsonProperty("o_c_banca")
	private String oCBanca;
	@JsonProperty("o_d_banca")
	private String oDBanca;
	@JsonProperty("o_m_ref_ext_total")
	private String oMRefExtTotal;
	@JsonProperty("o_m_ref_ext_parcial")
	private String oMRefExtParcial;
	@JsonProperty("o_c_oficial_cobro")
	private String oCOficialCobro;
	@JsonProperty("o_d_oficial_cobro")
	private String oDOficialCobro;
	@JsonProperty("o_f_verificacion")
	private String oFVerificacion;
	@JsonProperty("o_d_fecha_nac_str")
	private String oDFechaNacStr;
	@JsonProperty("o_d_fecha_inic_act_str")
	private String oDFechaInicActStr;
	@JsonProperty("o_d_fecha_iva_str")
	private String oDFechaIvaStr;
	@JsonProperty("o_d_fecha_reduc_iva_str")
	private String oDFechaReducIvaStr;
	@JsonProperty("o_d_fecha_dgi_str")
	private String oDFechaDgiStr;
	@JsonProperty("o_d_fecha_creacion_str")
	private String oDFechaCreacionStr;
	@JsonProperty("o_d_fecha_modificacion_str")
	private String oDFechaModificacionStr;
	@JsonProperty("o_d_fecha_verificacion_str")
	private String oDFechaVerificacionStr;
	@JsonProperty("o_k_impedimentos_totales")
	private Integer oKImpedimentosTotales;
	@JsonProperty("o_k_parciales_bloqueo")
	private Integer oKParcialesBloqueo;
	@JsonProperty("o_k_parciales_warning")
	private Integer oKParcialesWarning;
	@JsonProperty("o_k_parciales_con_autorizacion")
	private Integer oKParcialesConAutorizacion;
	@JsonProperty("o_m_valor_semaforo")
	private Integer oMValorSemaforo;
	@JsonProperty("o_u_login_verificacion")
	private String oULoginVerificacion;
	@JsonProperty("o_n_personal")
	private String oNPersonal;
	@JsonProperty("o_k_total_empleados")
	private String oKTotalEmpleados;
	@JsonProperty("o_i_ingreso_anual")
	private String oIIngresoAnual;
	@JsonProperty("o_c_afjp")
	private String oCAfjp;
	@JsonProperty("o_d_afjp")
	private String oDAfjp;
	@JsonProperty("o_c_pais_swift")
	private String oCPaisSwift;
	@JsonProperty("o_d_pais_swift")
	private String oDPaisSwift;
	@JsonProperty("o_c_tipo_cliente_mca")
	private String oCTipoClienteMca;
	@JsonProperty("o_d_tipo_cliente_mca")
	private String oDTipoClienteMca;
	@JsonProperty("o_c_clasificacion")
	private String oCClasificacion;
	@JsonProperty("o_c_lugar_nac")
	private String oCLugarNac;
	@JsonProperty("o_d_lugar_nac")
	private String oDLugarNac;
	@JsonProperty("o_c_segmento")
	private String oCSegmento;
	@JsonProperty("o_d_segmento")
	private String oDSegmento;
	@JsonProperty("o_c_subsegmento")
	private String oCSubsegmento;
	@JsonProperty("o_d_subsegmento")
	private String oDSubsegmento;
	@JsonProperty("o_c_canal_captacion")
	private String oCCanalCaptacion;
	@JsonProperty("o_d_canal_captacion")
	private String oDCanalCaptacion;
	@JsonProperty("o_c_categoria")
	private String oCCategoria;
	@JsonProperty("o_d_categoria")
	private String oDCategoria;
	@JsonProperty("o_c_conyuge")
	private String oCConyuge;
	@JsonProperty("o_d_conyuge")
	private String oDConyuge;
	@JsonProperty("o_c_rep_legal")
	private String oCRepLegal;
	@JsonProperty("o_d_rep_legal")
	private String oDRepLegal;
	@JsonProperty("o_f_rep_legal_inivig")
	private String oFRepLegalInivig;
	@JsonProperty("o_f_rep_legal_finvig")
	private String oFRepLegalFinvig;
	@JsonProperty("o_n_banca_nueva")
	private String oNBancaNueva;
	@JsonProperty("o_n_eventual")
	private Integer oNEventual;
	@JsonProperty("o_d_nomlar")
	private String oDNomlar;
	@JsonProperty("o_c_ente")
	private Integer oCEnte;
	@JsonProperty("o_c_actividad_ext")
	private String oCActividadExt;
	@JsonProperty("o_d_en_actividad_ext")
	private String oDEnActividadExt;
	@JsonProperty("o_m_fatca")
	private String oMFatca;
	@JsonProperty("o_d_tin")
	private String oDTin;
	@JsonProperty("o_m_sujeto_obligado")
	private String oMSujetoObligado;
	@JsonProperty("o_c_sujeto_obligado")
	private String oCSujetoObligado;
	@JsonProperty("o_m_presento_ddjj")
	private String oMPresentoDdjj;
	@JsonProperty("o_m_pep_estado")
	private String oMPepEstado;
	@JsonProperty("o_d_pep_cargo")
	private String oDPepCargo;
	
}
