package ar.com.macro.datosgenerales.model.service.impl;

import static ar.com.macro.constant.Errores.ERROR_CONSULTAR_ENROLADORES;
import static ar.com.macro.constant.Errores.ERROR_ENROLADOR_NO_ENCONTRADO;
import static ar.com.macro.constant.Errores.ERROR_EN_LA_CONSULTA_ENROLADOR;
import static ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladoresRequest.hasValue;
import static ar.com.macro.datosgenerales.model.service.impl.EnrollerUserSpecification.emptySpecification;
import static ar.com.macro.datosgenerales.model.service.impl.EnrollerUserSpecification.inBranchOffice;
import static ar.com.macro.datosgenerales.model.service.impl.EnrollerUserSpecification.inStatus;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import java.beans.FeatureDescriptor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.google.common.primitives.Ints;
import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.constant.Constantes;
import ar.com.macro.constant.Errores;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladorRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladoresRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.GuardarEnroladorRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladorResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladoresResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.GuardarEnroladorResponse;
import ar.com.macro.datosgenerales.model.repository.EnrollerUserRepositorio;
import ar.com.macro.datosgenerales.model.repository.entity.EnrollerUser;
import ar.com.macro.datosgenerales.model.service.EnroladorService;
import ar.com.macro.exceptions.ConsultarEnroladoresException;
import ar.com.macro.exceptions.GuardarDatosEnroladorException;
import ar.com.macro.exceptions.ObtenerDatosEnroladorException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Service
public class EnroladorServiceImpl implements EnroladorService {

    private static final Predicate<String> EMPTY_VALUE = value -> nonNull(value) && isBlank(value);
    
    EnrollerUserRepositorio enrollerUserRepositorio;

    @Override
    public ConsultarEnroladorResponse consultarDatosEnrolador(final ConsultarEnroladorRequest request) {

    try {
      Optional<EnrollerUser> optUser =
          nonNull(request.getIdUsuario())
              ? enrollerUserRepositorio.findByUserId(request.getIdUsuario())
              : getEnrollerUserByDniAndGenero(request);

        return buildConsultarEnroladorResponse(
            optUser.orElseThrow(
                () ->
                    new ObtenerDatosEnroladorException(
                        ERROR_ENROLADOR_NO_ENCONTRADO.getCodigo(),
                        ERROR_ENROLADOR_NO_ENCONTRADO.getMensaje())));

      } catch (MacroException e) {
        throw e;
      } catch (Exception e) {
        log.error(ERROR_EN_LA_CONSULTA_ENROLADOR.getMensaje(), e);
       
        throw new ObtenerDatosEnroladorException(
            ERROR_EN_LA_CONSULTA_ENROLADOR.getCodigo(),
            ERROR_EN_LA_CONSULTA_ENROLADOR.getMensaje());
      }
    }

    /**
     * Consulta en la tabla EnrollerUser el usuario con tipo, dni y genero solicitados. En caso de
     * existir varios registros para el mismo usuario recu recupera el registro más reciente.
     *
     * @param request encapsula los datos del usuario que se quiere consultar.
     * @return el registro del usuario encontrado en base de datos, o un Optional.empty() en caso de
     *     no encontrarlo
     */
    private Optional<EnrollerUser> getEnrollerUserByDniAndGenero(final ConsultarEnroladorRequest request) {
      var response =
          enrollerUserRepositorio.getEnrollerUser(
              request.getTipoDocumento(),
              request.getNumeroDocumento(),
              request.getGenero(),
              PageRequest.of(0, 1, Sort.by(Direction.DESC, "creationDate")));
  
      return Optional.ofNullable(response.isEmpty() ? null : response.get(0));
    }

    @Override
    public GuardarEnroladorResponse guardarDatosEnrolador(List<GuardarEnroladorRequest> guardarEnroladorRequests) {
        try {

            for(GuardarEnroladorRequest enroladorRequest : guardarEnroladorRequests){

                Optional<EnrollerUser> existe = enrollerUserRepositorio.findByUserId(enroladorRequest.getEmail());

                if(!existe.isPresent()){
                    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                    Set<ConstraintViolation<GuardarEnroladorRequest>> violations = validator.validate(enroladorRequest);
                    if(!violations.isEmpty()){
                        throw  new ConstraintViolationException(violations);
                    }
                }

                var er = transformarAEnrollerUser(enroladorRequest);

                if(!existe.isPresent()){
                    er.setCreationDate(LocalDateTime.now());
                    enrollerUserRepositorio.save(er);
                } else {
                    er.setModificationDate(LocalDateTime.now());
                    
                    var enrollerUser = existe.get();
                    copyNonNullProperties(er, enrollerUser);
                    
                    if (EMPTY_VALUE.test(enroladorRequest.getCantidadDeHuellas())) {
                      enrollerUser.setNumfingerprints(null);
                    }
                    
                    enrollerUserRepositorio.save(enrollerUser);
                }
            }

        } catch(ConstraintViolationException ex){
            throw ex ;
        } catch (Exception e){
            log.error(Errores.ERROR_GUARDAR_ENROLADOR.getMensaje(), e);
            throw new GuardarDatosEnroladorException(Errores.ERROR_GUARDAR_ENROLADOR.getCodigo(), Errores.ERROR_GUARDAR_ENROLADOR.getMensaje());
        }

        return GuardarEnroladorResponse.of(Constantes.RESPUESTA_GUARDAR_ENROLADOR_OK);
    }

    @Override
    public ConsultarEnroladoresResponse getUsuariosEnrolador(final ConsultarEnroladoresRequest request) {
  
      try {
        var specification = emptySpecification();
  
        if (hasValue(request.getEstados())) {
          specification = specification.and(inStatus(request.getEstados()));
        }
        if (hasValue(request.getSucursales())) {
          specification = specification.and(inBranchOffice(request.getSucursales()));
        }

        var enroladores =
            enrollerUserRepositorio
                .findAll(specification)
                .stream()
                .map(this::buildConsultarEnroladorResponse)
                .sorted(
                    Comparator.comparing(
                            ConsultarEnroladorResponse::getSucursal,
                            (a, b) -> Integer.compare(a.length(), b.length()))
                        .thenComparing(
                            Comparator.comparing(
                                ConsultarEnroladorResponse::getSucursal, Comparator.naturalOrder()))
                        .thenComparing(
                            Comparator.comparing(
                                ConsultarEnroladorResponse::getEstado,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                        .thenComparing(
                            Comparator.comparing(
                                ConsultarEnroladorResponse::getIdUsuario, Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return ConsultarEnroladoresResponse.of(enroladores);

      } catch (Exception e) {
        log.error(ERROR_CONSULTAR_ENROLADORES.getMensaje(), e);
  
        throw new ConsultarEnroladoresException(
            ERROR_CONSULTAR_ENROLADORES.getCodigo(), ERROR_CONSULTAR_ENROLADORES.getMensaje());
      }
    }

    private ConsultarEnroladorResponse buildConsultarEnroladorResponse(
        final EnrollerUser enrollerUser) {
      
      return ConsultarEnroladorResponse.builder()
          .idUsuario(enrollerUser.getUserId())
          .tipoDocumento(enrollerUser.getDocumentType())
          .numeroDocumento(enrollerUser.getDocumentNumber())
          .genero(enrollerUser.getGender())
          .sucursal(enrollerUser.getBranchOffice())
          .idCobis(enrollerUser.getIdcobis())
          .estado(enrollerUser.getStatus())
          .cantidadDeHuellas(enrollerUser.getNumfingerprints())
          .razon(enrollerUser.getReason())
          .supervisor(enrollerUser.getSupervisor())
          .build();
    }

    private EnrollerUser transformarAEnrollerUser(GuardarEnroladorRequest request){
        return EnrollerUser
                .builder()
                .userId(request.getEmail())
                .documentType(request.getTipoDocumento())
                .documentNumber(request.getNumeroDocumento())
                .gender( request.getGenero())
                .branchOffice(request.getSucursal())
                .modificationDate(LocalDateTime.now())
                .idcobis(request.getIdCobis())
                .status(request.getEstado())
                .numfingerprints(isNotBlank(request.getCantidadDeHuellas()) ? Ints.tryParse(request.getCantidadDeHuellas()) : null)
                .reason(request.getRazon())
                .supervisor(request.getSupervisor())
                .build();
    }

    private static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays
                .stream(src.getPropertyDescriptors())
                .filter(pd -> isNull(src.getPropertyValue(pd.getName())))
                .map(FeatureDescriptor::getName)
                .toArray(String[]::new);
    }
}
