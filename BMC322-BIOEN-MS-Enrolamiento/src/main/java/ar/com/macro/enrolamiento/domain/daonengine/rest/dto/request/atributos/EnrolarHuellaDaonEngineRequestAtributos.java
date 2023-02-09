package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.atributos;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.validator.EnrolarHuellaDaonEngineRequestValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnrolarHuellaDaonEngineRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnrolarHuellaDaonEngineRequestAtributos {
    String message() default "{api.validacion.EnrolarHuellaDaonEngineRequestValidator.validar.atributos.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
