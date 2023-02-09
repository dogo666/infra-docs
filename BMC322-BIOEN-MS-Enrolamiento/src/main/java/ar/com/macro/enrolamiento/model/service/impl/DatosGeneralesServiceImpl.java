package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.constant.Errores.ERROR_CONSULTAR_ENROLADORES_FEIGN;
import static ar.com.macro.constant.Errores.ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE_FEIGN;
import static ar.com.macro.constant.Errores.ERROR_CONSULTAR_ENROLADOR_POR_DNI_FEIGN;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ar.com.macro.biometria.commons.trace.feing.dto.request.ConsultarEnroladorFeignRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.request.ConsultarEnroladoresFeignRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.request.GuardarEnroladorFeingRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.request.GuardarEnroladorListaFeingRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.request.ParametrosConfigRequest;
import ar.com.macro.biometria.commons.trace.feing.dto.response.ConsultarEnroladorFeingResponse;
import ar.com.macro.biometria.commons.trace.feing.dto.response.GuardarEnroladorFeingResponse;
import ar.com.macro.biometria.commons.trace.feing.dto.response.ParametrosConfigResponse;
import ar.com.macro.biometria.commons.trace.feing.proxy.DatosGeneralesTraceProxyService;
import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.utils.converter.ConvertidorUtil;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Ambientes;
import ar.com.macro.constant.Errores;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnroladorRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnroladoresRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarInfoEnroladorRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.GuardarEnroladorListaRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnroladorEstadoResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnroladorResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnroladoresResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.GuardarEnroladorResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.SucursalesEnroladorResponse;
import ar.com.macro.enrolamiento.model.feign.datosgenerales.dto.response.ConfiguracionAmbienteIdentityX;
import ar.com.macro.enrolamiento.model.feign.datosgenerales.dto.response.ConfiguracionAplicacionIdentityXDto;
import ar.com.macro.enrolamiento.model.feign.datosgenerales.dto.response.ConfiguracionPoliticaEvaluacionIdentityXDto;
import ar.com.macro.enrolamiento.model.feign.datosgenerales.dto.response.ConfiguracionPoliticaIdentityXDto;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.enrolamiento.model.service.predicate.DatosEnroladorPredicate;
import ar.com.macro.exceptions.ComunicacionFeingException;
import ar.com.macro.exceptions.GeneralException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatosGeneralesServiceImpl implements DatosGeneralesService {

	@Value("${api.macro.entorno.valor}")
    private String entorno;

    private DatosGeneralesTraceProxyService datosGeneralesProxyService;

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
			} catch (MacroException e) {
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
        } catch (MacroException e) {
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
        } catch (MacroException e) {
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
	public String obtenerConfiguracionPoliticaEvaluacionIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros) {

		ParametrosConfigRequest request = new ParametrosConfigRequest(nombreMicroservicio, nombreParametros);
		ResponseEntity<Respuesta<ParametrosConfigResponse>> datosGeneralesResponse = null;
		String aplicacion = null;
		try {
			datosGeneralesResponse = consumirBuscarConfiguracionCompuestaIniciativa(request, xOperationID);
		} catch (MacroException e) {
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

	@Override
	public Optional<GuardarEnroladorResponse> guardarDatosEnrolador(GuardarEnroladorListaRequest guardarEnroladorListaRequest, String xOperationId){
		var rs = guardarDatosFeingEnrolador(guardarEnroladorListaRequest,xOperationId);
		if(DatosEnroladorPredicate.guardarDatosFeingEnroladorPredicate(rs)){
			return Optional.of(GuardarEnroladorResponse.of(rs.get().getBody().getDatos().getStatus()));
		} else {
			log.error(Errores.ERROR_GUARDAR_DATOS_ENROLADOR_OBJETO_VACIO.getMensaje());
			throw new ComunicacionFeingException(Errores.ERROR_GUARDAR_DATOS_ENROLADOR_OBJETO_VACIO.getCodigo(), Errores.ERROR_GUARDAR_DATOS_ENROLADOR_OBJETO_VACIO.getMensaje());
		}
	}

	private Optional<ResponseEntity<Respuesta<GuardarEnroladorFeingResponse>>> guardarDatosFeingEnrolador(GuardarEnroladorListaRequest guardarEnroladorListaRequest, String xOperationId){
		try {

			GuardarEnroladorListaFeingRequest guardarEnroladorListaFeingRequest = new GuardarEnroladorListaFeingRequest();
			guardarEnroladorListaFeingRequest.setEnroladores(
					guardarEnroladorListaRequest
							.getEnroladores()
							.stream()
							.map((e)-> new GuardarEnroladorFeingRequest(
								e.getEmail(),
								e.getTipodocumento(),
								e.getNumerodocumento(),
								e.getGenero(),
								e.getSucursal(),
								e.getIdCobis(),
								e.getEstado(),
								e.getCantidadDeHuellas(),
								e.getRazon(),
								e.getSupervisor()))
							.collect(Collectors.toList())
			);

			return Optional.of(
					datosGeneralesProxyService.guardarDatosEnrolador(
							guardarEnroladorListaFeingRequest,
							xOperationId
					)
			);
		} catch (MacroException e) {
			log.error(e.getMessage(),e);
			throw new ComunicacionFeingException(Errores.ERROR_GUARDAR_DATOS_ENROLADOR_FEING.getCodigo(), Errores.ERROR_GUARDAR_DATOS_ENROLADOR_FEING.getMensaje());
		}
	}

    @Override
    public ConsultarEnroladoresResponse getUsuariosEnrolador(
        final ConsultarEnroladoresRequest consultarEnroladoresRequest, final String xOperationId) {
  
      try {
        var feignRequest =
            ConsultarEnroladoresFeignRequest.builder()
                .estados(consultarEnroladoresRequest.getEstados())
                .sucursales(consultarEnroladoresRequest.getSucursales())
                .build();
  
        var feignResponse =
            datosGeneralesProxyService.getUsuariosEnrolador(feignRequest, xOperationId);

        var enroladores =
            feignResponse
                .getBody()
                .getDatos()
                .getEnroladores()
                .stream()
                .map(this::buildConsultarEnroladorResponse)
                .collect(Collectors.toList());
  
        return ConsultarEnroladoresResponse.of(enroladores);

      } catch (Exception e) {
        log.error(e.getMessage(), e);
        throw new ComunicacionFeingException(
            ERROR_CONSULTAR_ENROLADORES_FEIGN.getCodigo(),
            ERROR_CONSULTAR_ENROLADORES_FEIGN.getMensaje());
      }
    }

    private ConsultarEnroladorResponse buildConsultarEnroladorResponse(
        final ConsultarEnroladorFeingResponse response) {
  
      return ConsultarEnroladorResponse.builder()
          .idUsuario(response.getIdUsuario())
          .tipoDocumento(response.getTipoDocumento())
          .numeroDocumento(response.getNumeroDocumento())
          .genero(response.getGenero())
          .sucursal(response.getSucursal())
          .idCobis(response.getIdCobis())
          .estado(response.getEstado())
          .cantidadDeHuellas(response.getCantidadDeHuellas())
          .razon(response.getRazon())
          .supervisor(response.getSupervisor())
          .build();
    }

    @Override
    public SucursalesEnroladorResponse getSucursalesConEnroladorPendiente(
        final String xOperationId) {
  
      try {
        var feignResponse =
            datosGeneralesProxyService.getSucursalesConEnroladorPendiente(xOperationId);
        
        return SucursalesEnroladorResponse.of(
            feignResponse.getBody().getDatos().getSucursales());
        
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        throw new ComunicacionFeingException(
            ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE_FEIGN.getCodigo(),
            ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE_FEIGN.getMensaje());
      }
    }

    @Override
    public ConsultarEnroladorEstadoResponse getEstadoEnrolador(
        ConsultarEnroladorRequest consultarEnroladorRequest, String xOperationId) {
      
      try {  
        var feignRequest =
            ConsultarEnroladorFeignRequest.builder()
                .idUsuario(consultarEnroladorRequest.getEmail())
                .build();
  
        var feignResponse = datosGeneralesProxyService.consultarEnrolador(feignRequest, xOperationId);
  
        return ConsultarEnroladorEstadoResponse.builder()
            .estado(feignResponse.getBody().getDatos().getEstado())
            .build();
  
      } catch (Exception e) {
        log.error(e.getMessage(), e);
       
        throw new ComunicacionFeingException(
            ERROR_CONSULTAR_ENROLADORES_FEIGN.getCodigo(),
            ERROR_CONSULTAR_ENROLADORES_FEIGN.getMensaje());
      }
    }

    @Override
    public ConsultarEnroladorResponse getDatosEnrolador(
        final ConsultarInfoEnroladorRequest request, final String xOperationId) {
  
      try {
        var feignRequest =
            ConsultarEnroladorFeignRequest.builder()
                .idUsuario(request.getEmail())
                .tipoDocumento(request.getTipodocumento())
                .numeroDocumento(request.getNumerodocumento())
                .genero(request.getGenero())
              .build();

        var feignResponse = datosGeneralesProxyService.consultarEnrolador(feignRequest, xOperationId);
        var body = feignResponse.getBody();
        
        if(body.hayDatos()) {
          return buildConsultarEnroladorResponse(body.getDatos());
          
        } else {
          throw new GeneralException(body.getError());
        }
  
      } catch (MacroException e) {
        log.error(e.getMessage(), e);
        throw e;
        
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        
        throw new ComunicacionFeingException(
            ERROR_CONSULTAR_ENROLADOR_POR_DNI_FEIGN.getCodigo(),
            ERROR_CONSULTAR_ENROLADOR_POR_DNI_FEIGN.getMensaje());
      }
    }
}
