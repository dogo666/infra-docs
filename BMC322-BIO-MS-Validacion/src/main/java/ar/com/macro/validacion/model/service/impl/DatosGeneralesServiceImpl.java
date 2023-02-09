package ar.com.macro.validacion.model.service.impl;

import ar.com.macro.biometria.commons.trace.feing.dto.request.ParametrosConfigRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.response.ParametrosConfigResponse;
import ar.com.macro.biometria.commons.trace.feing.proxy.DatosGeneralesTraceProxyService;
import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.utils.converter.ConvertidorUtil;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Ambientes;
import ar.com.macro.constant.Errores;
import ar.com.macro.validacion.model.feign.datosgenerales.dto.response.*;
import ar.com.macro.validacion.model.service.DatosGeneralesService;
import ar.com.macro.validacion.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Slf4j
@Service
public class DatosGeneralesServiceImpl implements DatosGeneralesService {

	@Value("${api.macro.entorno.valor}")
    private String entorno;

    private final DatosGeneralesTraceProxyService datosGeneralesProxyService;

    public DatosGeneralesServiceImpl(DatosGeneralesTraceProxyService datosGeneralesProxyService){
        this.datosGeneralesProxyService = datosGeneralesProxyService;
    }

	@Override
	public ConfiguracionHuellasDto obtenerDatosGeneralesCompuestos(String xOperationID, String nombreMicroservicio, String nombreParametros) {
		ConfiguracionHuellasDto configuracionHuellasDto = null;
		ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio,nombreParametros);
		ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
		try {
			datosGeneralesResponse = datosGeneralesProxyService.obtenerDatosGeneralesCompuestos(request, xOperationID);
		} catch (MacroException e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(),Errores.ERROR_DATOS_GENERALES.getMensaje());
		}
		if(Objects.nonNull(datosGeneralesResponse) && !datosGeneralesResponse.getBody().getDatos().getParametros().isEmpty()){
			try {
				configuracionHuellasDto = ConvertidorUtil.json2Class(datosGeneralesResponse.getBody().getDatos().getParametros().get(0).getValor(), ConfiguracionHuellasDto.class);
			} catch (Exception e) {
				throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_CONVERTIR_JSON_A_OBJETO.getCodigo(), Errores.ERROR_CONVERTIR_JSON_A_OBJETO.getMensaje());
			}
		} else {
			throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_APLICACION_NO_ENCONTRADA.getCodigo(), Errores.ERROR_APLICACION_NO_ENCONTRADA.getMensaje());
		}
		return configuracionHuellasDto;
	}

	@Override
	public String obtenerConfiguracionAplicacionIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros) {

        ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio, nombreParametros);
        ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
        String aplicacion = null;
        try {
            datosGeneralesResponse = consumirBuscarConfiguracionCompuestaIniciativa(request, xOperationID);
        } catch ( MacroException e) {
            log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
            throw new ObtenerDatosGeneralesCompuestosException(e.getCodigo(), e.getMessage());
        } catch ( Exception e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje());
		}
        if(Objects.nonNull(datosGeneralesResponse) && !datosGeneralesResponse.getBody().getDatos().getParametros().isEmpty()) {
        	ConfiguracionAplicacionIdentityXDto configuracionAplicacionIdentityXDto = ConvertidorUtil.json2Class(
        			datosGeneralesResponse.getBody().getDatos().getParametros().get(0).getValor(), ConfiguracionAplicacionIdentityXDto.class);
        	ConfiguracionAmbienteIdentityX configuracionAplicacionIdentityX = configuracionAplicacionIdentityXDto.getConfiguracionAplicacionIdentityX();
        	aplicacion = buscarValorPorEntorno(configuracionAplicacionIdentityX);
        } else {
        	throw new IdentityXException(Errores.ERROR_APLICACION_NO_ENCONTRADA.getCodigo(), Errores.ERROR_APLICACION_NO_ENCONTRADA.getMensaje());
        }
        return aplicacion;
	}

	@Override
	public String obtenerConfiguracionPoliticaIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros) {

        ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio, nombreParametros);
        ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
        String aplicacion = null;
        try {
            datosGeneralesResponse = consumirBuscarConfiguracionCompuestaIniciativa(request, xOperationID);
		} catch ( MacroException e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(e.getCodigo(), e.getMessage());
		} catch (Exception e) {
            log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
            throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje());
        }
        if(Objects.nonNull(datosGeneralesResponse) && !datosGeneralesResponse.getBody().getDatos().getParametros().isEmpty()) {
        	ConfiguracionPoliticaIdentityXDto configuracionPoliticaIdentityXDto = ConvertidorUtil.json2Class(
        			datosGeneralesResponse.getBody().getDatos().getParametros().get(0).getValor(), ConfiguracionPoliticaIdentityXDto.class);
        	ConfiguracionAmbienteIdentityX configuracionPoliticaIdentityX = configuracionPoliticaIdentityXDto.getConfiguracionPoliticaIdentityX();
        	aplicacion = buscarValorPorEntorno(configuracionPoliticaIdentityX);
        } else {
        	throw new IdentityXException(Errores.ERROR_POLITICA_NO_ENCONTRADA.getCodigo(), Errores.ERROR_POLITICA_NO_ENCONTRADA.getMensaje());
        }
		log.debug("obtenerConfiguracionPoliticaIdentityX xOperationID: {} nombreMicroservicio : {} nombreParametros: {} politicaId : {}", xOperationID, nombreMicroservicio, nombreParametros, aplicacion);
        return aplicacion;
	}


	@Override
	public String obtenerConfiguracionValidacionRenaper3dflIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros) {

		ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio, nombreParametros);
		ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
		String aplicacion = null;
		try {
			datosGeneralesResponse = consumirBuscarConfiguracionCompuestaIniciativa(request, xOperationID);
		} catch ( MacroException e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(e.getCodigo(), e.getMessage());
		} catch (Exception e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje());
		}
		if(Objects.nonNull(datosGeneralesResponse) && !datosGeneralesResponse.getBody().getDatos().getParametros().isEmpty()) {
			ConfiguracionValidacionRenaper3dflIdentityX configuracionPoliticaIdentityXDto = ConvertidorUtil.json2Class(
					datosGeneralesResponse.getBody().getDatos().getParametros().get(0).getValor(), ConfiguracionValidacionRenaper3dflIdentityX.class);
			ConfiguracionAmbienteIdentityX configuracionPoliticaIdentityX = configuracionPoliticaIdentityXDto.getConfiguracionPoliticaIdentityX();
			aplicacion = buscarValorPorEntorno(configuracionPoliticaIdentityX);
		} else {
			throw new IdentityXException(Errores.ERROR_VALIDACION_RENAPER_3DFL_NO_ENCONTRADA.getCodigo(), Errores.ERROR_VALIDACION_RENAPER_3DFL_NO_ENCONTRADA.getMensaje());
		}
		log.debug("obtenerConfiguracionValidacionRenaperIdentityX xOperationID: {} nombreMicroservicio : {} nombreParametros: {} politicaId : {}", xOperationID, nombreMicroservicio, nombreParametros, aplicacion);
		return aplicacion;
	}

	@Override
	public Double obtenerConfiguracionUmbralPoliticaDaonEngine(String xOperationID, String nombreMicroservicio, String nombreParametros) {

		ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio, nombreParametros);
		ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
		String umbralPolitica = null;
		try {
			datosGeneralesResponse = consumirBuscarConfiguracionCompuestaIniciativa(request, xOperationID);
		} catch ( MacroException e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(e.getCodigo(), e.getMessage());
		} catch (Exception e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje());
		}
		if(Objects.nonNull(datosGeneralesResponse) && !datosGeneralesResponse.getBody().getDatos().getParametros().isEmpty()) {
			ConfiguracionUmbralPoliticaDaonEngineDto configuracionUmbralPoliticaDaonEngineDto = ConvertidorUtil.json2Class(
					datosGeneralesResponse.getBody().getDatos().getParametros().get(0).getValor(), ConfiguracionUmbralPoliticaDaonEngineDto.class);

			ConfiguracionAmbienteIdentityX configuracionPoliticaIdentityX = configuracionUmbralPoliticaDaonEngineDto.getConfiguracionPoliticaIdentityX();
			umbralPolitica = buscarValorPorEntorno(configuracionPoliticaIdentityX);
		} else {
			throw new IdentityXException(Errores.ERROR_UMBRAL_POLITICA_DAON_ENGINE_NO_ENCONTRADA.getCodigo(), Errores.ERROR_UMBRAL_POLITICA_DAON_ENGINE_NO_ENCONTRADA.getMensaje());
		}
		log.debug("obtenerConfiguracionUmbralPoliticaDaonEngine xOperationID: {} nombreMicroservicio : {} nombreParametros: {} politicaId : {}", xOperationID, nombreMicroservicio, nombreParametros, umbralPolitica);

		return Double.valueOf(umbralPolitica);
	}

	@Override
	public Integer obtenerConfiguracionUmbralRenaper3dflIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros) {

		ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio, nombreParametros);
		ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
		String umbralPolitica = null;
		try {
			datosGeneralesResponse = consumirBuscarConfiguracionCompuestaIniciativa(request, xOperationID);
		} catch ( MacroException e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(e.getCodigo(), e.getMessage());
		} catch (Exception e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje());
		}
		if(Objects.nonNull(datosGeneralesResponse) && !datosGeneralesResponse.getBody().getDatos().getParametros().isEmpty()) {
			ConfiguracionUmbralRenaper3dflIdentityXDto configuracionUmbralPoliticaDaonEngineDto = ConvertidorUtil.json2Class(
					datosGeneralesResponse.getBody().getDatos().getParametros().get(0).getValor(), ConfiguracionUmbralRenaper3dflIdentityXDto.class);

			ConfiguracionAmbienteIdentityX configuracionPoliticaIdentityX = configuracionUmbralPoliticaDaonEngineDto.getConfiguracionPoliticaIdentityX();
			umbralPolitica = buscarValorPorEntorno(configuracionPoliticaIdentityX);
		} else {
			throw new IdentityXException(Errores.ERROR_UMBRAL_RENAPER_3DFL_NO_ENCONTRADA.getCodigo(), Errores.ERROR_UMBRAL_RENAPER_3DFL_NO_ENCONTRADA.getMensaje());
		}
		log.debug("obtenerConfiguracionUmbralRenaper3DFL xOperationID: {} nombreMicroservicio : {} nombreParametros: {} politicaId : {}", xOperationID, nombreMicroservicio, nombreParametros, umbralPolitica);

		return Integer.valueOf(umbralPolitica);
	}



	@Override
	public String obtenerConfiguracionPoliticaEvaluacionIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros) {

		ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio, nombreParametros);
		ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
		String aplicacion = null;
		try {
			datosGeneralesResponse = consumirBuscarConfiguracionCompuestaIniciativa(request, xOperationID);
		} catch ( MacroException e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(e.getCodigo(), e.getMessage());
		} catch (Exception e) {
			log.error(Errores.ERROR_DATOS_GENERALES.getMensaje(), e);
			throw new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje());
		}
		if(Objects.nonNull(datosGeneralesResponse) && !datosGeneralesResponse.getBody().getDatos().getParametros().isEmpty()) {
			ConfiguracionPoliticaEvaluacionIdentityXDto configuracionPoliticaEvaluacionIdentityXDto = ConvertidorUtil.json2Class(
					datosGeneralesResponse.getBody().getDatos().getParametros().get(0).getValor(), ConfiguracionPoliticaEvaluacionIdentityXDto.class);
			ConfiguracionAmbienteIdentityX configuracionPoliticaIdentityX = configuracionPoliticaEvaluacionIdentityXDto.getConfiguracionPoliticaIdentityX();
			aplicacion = buscarValorPorEntorno(configuracionPoliticaIdentityX);
		} else {
			throw new IdentityXException(Errores.ERROR_POLITICA_EVALUACION_NO_ENCONTRADA.getCodigo(), Errores.ERROR_POLITICA_EVALUACION_NO_ENCONTRADA.getMensaje());
		}
		return aplicacion;
	}

	private String buscarValorPorEntorno(ConfiguracionAmbienteIdentityX configuracionAmbienteIdentityX) {
		String valor = null;
		Optional<Ambientes> optAmbienteActual = Ambientes.obtenerAmbiente(entorno);
		if(optAmbienteActual.isPresent()) {

			switch (optAmbienteActual.get()) {
	    	case LOCAL:
				valor = configuracionAmbienteIdentityX.getLocal();
				break;
			case DEV:
				valor = configuracionAmbienteIdentityX.getDev();
				break;
			case STAGE:
				valor = configuracionAmbienteIdentityX.getStg();
				break;
			case LAB:
				valor = configuracionAmbienteIdentityX.getLab();
				break;
			case TEST:
				valor = configuracionAmbienteIdentityX.getTest();
				break;
			case PREPROD1:
				valor = configuracionAmbienteIdentityX.getPreProd1();
				break;
			case PREPROD2:
				valor = configuracionAmbienteIdentityX.getPreProd2();
				break;
			case PROD1:
				valor = configuracionAmbienteIdentityX.getProd1();
				break;
			case PROD2:
				valor = configuracionAmbienteIdentityX.getProd2();
				break;
			default:
				break;
			}
		}

		return valor;
	}

	private ResponseEntity<Respuesta<ParametrosConfigResponse>> consumirBuscarConfiguracionCompuestaIniciativa(ParametrosConfigRequest request, String xOperationID) {
		return datosGeneralesProxyService.obtenerDatosGeneralesCompuestos(request, xOperationID);
	}
}
