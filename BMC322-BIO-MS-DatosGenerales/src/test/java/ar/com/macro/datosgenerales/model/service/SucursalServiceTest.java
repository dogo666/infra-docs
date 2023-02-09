package ar.com.macro.datosgenerales.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarSucursalesRequest;
import ar.com.macro.datosgenerales.model.repository.EnrollerUserRepositorio;
import ar.com.macro.datosgenerales.model.service.impl.SucursalServiceImpl;
import ar.com.macro.exceptions.ConsultarSucursalesException;

@RunWith(SpringRunner.class)
public class SucursalServiceTest {

  @Spy protected ObjectMapper mapper;

  private SucursalServiceImpl sucursalServiceImpl;

  @Mock private EnrollerUserRepositorio enrollerUserRepositorio;

  @Before
  public void init() {
    this.sucursalServiceImpl = new SucursalServiceImpl(enrollerUserRepositorio);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void getSucursalesConEnroladorPendiente_Ok() throws Exception {
    var branchOffices = List.of("1", "2", "3");

    when(enrollerUserRepositorio.getBranchOfficeWithEnrollerUserInState(
            any(List.class), any(Sort.class)))
        .thenReturn(branchOffices);

    var res = this.sucursalServiceImpl.getSucursalesConEnroladorPendiente();
    Assert.assertNotNull(res);
    assertEquals(3, res.getSucursales().size());
  }

  @SuppressWarnings("unchecked")
  @Test(expected = ConsultarSucursalesException.class)
  public void getSucursalesConEnroladorPendiente_Error() throws Exception {
    when(enrollerUserRepositorio.getBranchOfficeWithEnrollerUserInState(
            any(List.class), any(Sort.class)))
        .thenThrow(ConsultarSucursalesException.class);

    this.sucursalServiceImpl.getSucursalesConEnroladorPendiente();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void getSucursalesConEnroladorEstados_Ok() throws Exception {
    var req = new ConsultarSucursalesRequest();
    req.setEstados(Arrays.asList("APROBADO", "RECHAZADO"));

    var branchOffices = List.of("1", "2", "3");

    when(enrollerUserRepositorio.getBranchOfficeWithEnrollerUserInState(
            any(List.class), any(Sort.class)))
        .thenReturn(branchOffices);

    var res = this.sucursalServiceImpl.getSucursalesConEnroladorEnEstados(req);
    Assert.assertNotNull(res);
    assertEquals(3, res.getSucursales().size());
  }

  @SuppressWarnings("unchecked")
  @Test(expected = ConsultarSucursalesException.class)
  public void getSucursalesConEnroladorEstados_Error() throws Exception {
    var req = new ConsultarSucursalesRequest();
    req.setEstados(Arrays.asList("APROBADO", "RECHAZADO"));

    when(enrollerUserRepositorio.getBranchOfficeWithEnrollerUserInState(
            any(List.class), any(Sort.class)))
        .thenThrow(ConsultarSucursalesException.class);

    this.sucursalServiceImpl.getSucursalesConEnroladorEnEstados(req);
  }

}
