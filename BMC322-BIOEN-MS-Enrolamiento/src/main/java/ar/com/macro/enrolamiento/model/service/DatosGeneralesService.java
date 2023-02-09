package ar.com.macro.enrolamiento.model.service;

import java.util.Optional;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnroladorRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnroladoresRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarInfoEnroladorRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.GuardarEnroladorListaRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.*;
import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;

public interface DatosGeneralesService {
  
  ConfiguracionHuellasDto obtenerDatosGeneralesCompuestos(
      String xOperationID, String nombreMicroservicio, String nombreParametros);

  String obtenerConfiguracionAplicacionIdentityX(
      String xOperationID, String nombreMicroservicio, String nombreParametros);

  String obtenerConfiguracionPoliticaIdentityX(
      String xOperationID, String nombreMicroservicio, String nombreParametros);

  String obtenerConfiguracionPoliticaEvaluacionIdentityX(
      String xOperationID, String nombreMicroservicio, String nombreParametros);

  Optional<GuardarEnroladorResponse> guardarDatosEnrolador(
      GuardarEnroladorListaRequest guardarEnroladorListaRequest, String xOperationId);

  ConsultarEnroladoresResponse getUsuariosEnrolador(
      ConsultarEnroladoresRequest consultarEnroladoresRequest, String xOperationId);

  SucursalesEnroladorResponse getSucursalesConEnroladorPendiente(String xOperationId);

  ConsultarEnroladorEstadoResponse getEstadoEnrolador(
      ConsultarEnroladorRequest consultarEnroladorRequest, String xOperationId);

  ConsultarEnroladorResponse getDatosEnrolador(
      ConsultarInfoEnroladorRequest request, String xOperationId);
}
