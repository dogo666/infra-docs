package ar.com.macro.enrolamiento.domain.identidad.rest.dto.request;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ConsultarIdentidadRequestValidator implements ConstraintValidator<ConsultarIdentidadAtributos, ConsultarIdentidadRequest> {

    public void initialize(ConsultarIdentidadAtributos constraintAnnotation) {
    }

    public boolean isValid(ConsultarIdentidadRequest consultarIdentidadRequest, ConstraintValidatorContext context) {
        if (isEmpty(consultarIdentidadRequest.getDni()) || isEmpty(consultarIdentidadRequest.getSexo())) {
            return false;
        }
        return true;
    }
}