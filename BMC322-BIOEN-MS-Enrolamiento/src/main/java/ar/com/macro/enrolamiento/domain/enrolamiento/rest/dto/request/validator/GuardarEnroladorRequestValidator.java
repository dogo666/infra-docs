package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.validator;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.GuardarEnroladorRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos.GuardarEnroladorRequestAtributos;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class GuardarEnroladorRequestValidator implements ConstraintValidator<GuardarEnroladorRequestAtributos, GuardarEnroladorRequest> {

    public void initialize(GuardarEnroladorRequestAtributos constraintAnnotation) {
    }

    public boolean isValid(GuardarEnroladorRequest guardarEnroladorRequest, ConstraintValidatorContext context) {
        return  !isEmpty(guardarEnroladorRequest.getEmail()) &&
                !isEmpty(guardarEnroladorRequest.getTipodocumento()) &&
                !isEmpty(guardarEnroladorRequest.getNumerodocumento()) &&
                !isEmpty(guardarEnroladorRequest.getGenero()) &&
                !isEmpty(guardarEnroladorRequest.getSucursal()) &&
                !isEmpty(guardarEnroladorRequest.getIdCobis());
    }
}
