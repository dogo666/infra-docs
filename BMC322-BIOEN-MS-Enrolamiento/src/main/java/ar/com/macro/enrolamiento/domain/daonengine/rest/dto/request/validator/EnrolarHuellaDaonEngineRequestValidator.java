package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.validator;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.atributos.EnrolarHuellaDaonEngineRequestAtributos;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class EnrolarHuellaDaonEngineRequestValidator implements ConstraintValidator<EnrolarHuellaDaonEngineRequestAtributos, EnrolarHuellaDaonEngineRequest> {

    public void initialize(EnrolarHuellaDaonEngineRequestAtributos constraintAnnotation){
    }

    public boolean isValid(EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest, ConstraintValidatorContext context){
        if(
            isEmpty(enrolarHuellaDaonEngineRequest.getIdusuario()) ||
            isEmpty(enrolarHuellaDaonEngineRequest.getTipodocumento()) ||
            isEmpty(enrolarHuellaDaonEngineRequest.getNumerodocumento()) ||
            isEmpty(enrolarHuellaDaonEngineRequest.getGenero())
        ){
            return false;
        }
        return true;
    }
}
