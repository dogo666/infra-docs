package ar.com.macro.datosgenerales.model.service;

import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarSucursalesRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.SucursalesEnroladorResponse;

/**
 * SucursalService.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-21
 */
public interface SucursalService {

  SucursalesEnroladorResponse getSucursalesConEnroladorPendiente();

  SucursalesEnroladorResponse getSucursalesConEnroladorEnEstados(ConsultarSucursalesRequest consultarSucursalesRequest);
}
