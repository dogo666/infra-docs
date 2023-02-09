package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.validator;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos.ConsultarEnrolamientoRequestAtributos;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ConsultarEnrolamientoRequestValidator implements ConstraintValidator<ConsultarEnrolamientoRequestAtributos, ConsultarEnrolamientoRequest> {

    public void initialize(ConsultarEnrolamientoRequestAtributos constraintAnnotation) {
    }

    public boolean isValid(ConsultarEnrolamientoRequest consultarIdentidadRequest, ConstraintValidatorContext context) {
        return !isEmpty(consultarIdentidadRequest.getIdCliente());
    }
}
