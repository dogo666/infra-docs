package ar.com.macro.validacion.domain.identidad.rest.dto.request;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class CrearHashRequestValidator implements ConstraintValidator<CrearHashAtributos, CrearHashRequest> {

    public void initialize(CrearHashAtributos crearHashAtributos) {
    }

    public boolean isValid(CrearHashRequest crearHashRequest, ConstraintValidatorContext context) {
        return !isEmpty(crearHashRequest.getIdentificador());
    }
}