package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.validator;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarInfoEnroladorRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos.ConsultarEnrolamientoRequestAtributos;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos.ConsultarInfoEnroladorRequestAtributos;

public class ConsultarInfoEnroladorRequestValidator
    implements ConstraintValidator<
        ConsultarInfoEnroladorRequestAtributos, ConsultarInfoEnroladorRequest> {

  public void initialize(ConsultarEnrolamientoRequestAtributos constraintAnnotation) {}

  public boolean isValid(
      ConsultarInfoEnroladorRequest request, ConstraintValidatorContext context) {
    return isNotEmpty(request.getEmail()) || isNotEmpty(request.getNumerodocumento());
  }
}
