package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.validator;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.atributos.ActualizarHuellaDaonEngineRequestAtributos;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ActualizarHuellaDaonEngineRequestValidator implements ConstraintValidator<ActualizarHuellaDaonEngineRequestAtributos, ActualizarHuellaDaonEngineRequest> {

    public void initialize(ActualizarHuellaDaonEngineRequestAtributos constraintAnnotation){
    }

    public boolean isValid(ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest, ConstraintValidatorContext context){
        if(
                isEmpty(actualizarHuellaDaonEngineRequest.getIdusuario()) ||
                isEmpty(actualizarHuellaDaonEngineRequest.getTipodocumento()) ||
                isEmpty(actualizarHuellaDaonEngineRequest.getNumerodocumento()) ||
                isEmpty(actualizarHuellaDaonEngineRequest.getGenero())
        ){
            return false;
        }
        return true;
    }
}
