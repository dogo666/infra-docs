package ar.com.macro.validacion.domain.identityx.rest.dto.request.validator;

import ar.com.macro.validacion.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.atributos.CrearEvaluacionPerfilRequestAtributos;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class CrearEvaluacionPerfilRequestValidator implements ConstraintValidator<CrearEvaluacionPerfilRequestAtributos, CrearEvaluacionPerfilRequest> {

    public void initialize(CrearEvaluacionPerfilRequestAtributos constraintAnnotation){
    }

    public boolean isValid(CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest, ConstraintValidatorContext context){
        return !isEmpty(crearEvaluacionPerfilRequest.getIdusuario()) && !isEmpty(crearEvaluacionPerfilRequest.getIdcheck());
    }
}
