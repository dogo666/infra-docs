package ar.com.macro.datosgenerales.model.service.impl;

import static ar.com.macro.constant.Errores.ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE;
import static ar.com.macro.datosgenerales.model.service.impl.EnrollerUserSpecification.BRANCH_OFFICE;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarSucursalesRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.SucursalesEnroladorResponse;
import ar.com.macro.datosgenerales.model.repository.EnrollerUserRepositorio;
import ar.com.macro.datosgenerales.model.repository.entity.EnrollerStatus;
import ar.com.macro.datosgenerales.model.service.SucursalService;
import ar.com.macro.exceptions.ConsultarSucursalesException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * SucursalServiceImpl.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-21
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Service
public class SucursalServiceImpl implements SucursalService {

  EnrollerUserRepositorio enrollerUserRepositorio;

  @Override
  public SucursalesEnroladorResponse getSucursalesConEnroladorPendiente() {
    var consultarSucursalesRequest = new ConsultarSucursalesRequest();
    consultarSucursalesRequest.setEstados(List.of(EnrollerStatus.PENDIENTE.name()));
    return getSucursalesConEnroladorEnEstados(consultarSucursalesRequest);
  }

  @Override
  public SucursalesEnroladorResponse getSucursalesConEnroladorEnEstados(
      ConsultarSucursalesRequest consultarSucursalesRequest) {

    try {
      var branchOffices = enrollerUserRepositorio.getBranchOfficeWithEnrollerUserInState(
          consultarSucursalesRequest.getEstados(),
          JpaSort.unsafe(Direction.ASC, "LENGTH(" + BRANCH_OFFICE + ")", BRANCH_OFFICE));

      return SucursalesEnroladorResponse.of(branchOffices);

    } catch (Exception e) {
      log.error(ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE.getMensaje(), e);

      throw new ConsultarSucursalesException(
          ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE.getCodigo(),
          ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE.getMensaje());
    }
  }
}
