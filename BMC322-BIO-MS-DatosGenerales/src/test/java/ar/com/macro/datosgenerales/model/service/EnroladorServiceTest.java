package ar.com.macro.datosgenerales.model.service;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Ints;
import ar.com.macro.constants.Constantes;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladoresRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.GuardarEnroladorRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.GuardarEnroladorResponse;
import ar.com.macro.datosgenerales.model.repository.EnrollerUserRepositorio;
import ar.com.macro.datosgenerales.model.repository.entity.EnrollerUser;
import ar.com.macro.datosgenerales.model.service.impl.EnroladorServiceImpl;
import ar.com.macro.exceptions.ConsultarEnroladoresException;
import ar.com.macro.exceptions.GuardarDatosEnroladorException;

@RunWith(SpringRunner.class)
public class EnroladorServiceTest {

    @Spy
    protected ObjectMapper mapper;

    private EnroladorServiceImpl enroladorServiceImpl;

    @Mock
    private EnrollerUserRepositorio enrollerUserRepositorio;

    @Before
    public void init(){
        this.enroladorServiceImpl = new EnroladorServiceImpl(enrollerUserRepositorio);
    }

    @Test
    public void guardarDatosEnrolador_Ok() throws Exception {
        final String jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_CONSULTA_ENROLADOR);
        GuardarEnroladorRequest req = mapper.readValue(jsonRequest, GuardarEnroladorRequest.class);
        when(enrollerUserRepositorio.save(any(EnrollerUser.class)))
                .thenReturn(
                        EnrollerUser.of(
                                req.getEmail(),
                                req.getTipoDocumento(),
                                req.getNumeroDocumento(),
                                req.getGenero(),
                                req.getSucursal(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                req.getIdCobis(),
                                req.getEstado(),
                                (nonNull(req.getCantidadDeHuellas()) ? Ints.tryParse(req.getCantidadDeHuellas()) : null),
                                req.getRazon(),
                                req.getSupervisor()
                                )
                            );
        GuardarEnroladorResponse res = this.enroladorServiceImpl.guardarDatosEnrolador(List.of(req));
        Assert.assertNotNull(res);
    }

    @Test(expected = GuardarDatosEnroladorException.class)
    public void guardarDatosEnroladorExistente_Error() throws Exception{
        final String jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_DATOS_CONSULTA_ENROLADOR);
        GuardarEnroladorRequest req = mapper.readValue(jsonRequest, GuardarEnroladorRequest.class);
        when(enrollerUserRepositorio.save(any(EnrollerUser.class))).thenThrow(GuardarDatosEnroladorException.class);
        this.enroladorServiceImpl.guardarDatosEnrolador(List.of(req));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getUsuariosEnrolador_Ok() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_ENROLADORES_REQUEST);
      var req = mapper.readValue(jsonRequest, ConsultarEnroladoresRequest.class);
  
      var enrolledUsers =
          List.of(
              EnrollerUser.builder()
                  .userId("admin@admin.com")
                  .documentType("01")
                  .documentNumber("152489964")
                  .build());
  
      when(enrollerUserRepositorio.findAll(any(Specification.class)))
          .thenReturn(enrolledUsers);
  
      var res = this.enroladorServiceImpl.getUsuariosEnrolador(req);
      Assert.assertNotNull(res);
      assertEquals(1, res.getEnroladores().size());
      
      var enrolador = res.getEnroladores().get(0);
      assertEquals("admin@admin.com", enrolador.getIdUsuario());
      assertEquals("01", enrolador.getTipoDocumento());
      assertEquals("152489964", enrolador.getNumeroDocumento());
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ConsultarEnroladoresException.class)
    public void getUsuariosEnrolador_Error() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_BIOMETRIA_CONSULTAR_ENROLADORES_REQUEST);
      var req = mapper.readValue(jsonRequest, ConsultarEnroladoresRequest.class);
  
      when(enrollerUserRepositorio.findAll(any(Specification.class)))
          .thenThrow(ConsultarEnroladoresException.class);
  
      this.enroladorServiceImpl.getUsuariosEnrolador(req);
    }
}
