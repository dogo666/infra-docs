package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.validator.ConsultarEnrolamientoRequestValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConsultarEnrolamientoRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsultarEnrolamientoRequestAtributos {
    String message() default "{api.validacion.ConsultarEnrolamientoRequestValidator.validar.atributos.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
