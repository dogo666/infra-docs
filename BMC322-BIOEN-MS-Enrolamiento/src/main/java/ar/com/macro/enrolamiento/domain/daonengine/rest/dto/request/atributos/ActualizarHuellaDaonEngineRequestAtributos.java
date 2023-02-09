package ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.atributos;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.validator.ActualizarHuellaDaonEngineRequestValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ActualizarHuellaDaonEngineRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActualizarHuellaDaonEngineRequestAtributos {
    String message() default "{api.validacion.ActualizarHuellaDaonEngineRequestValidator.validar.atributos.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
