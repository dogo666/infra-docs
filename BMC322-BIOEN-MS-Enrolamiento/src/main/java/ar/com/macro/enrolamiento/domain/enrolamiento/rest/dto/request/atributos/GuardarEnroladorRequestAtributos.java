package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.validator.GuardarEnroladorRequestValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GuardarEnroladorRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GuardarEnroladorRequestAtributos {
    String message() default "{api.validacion.GuardarEnroladorRequestValidator.validar.atributos.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
