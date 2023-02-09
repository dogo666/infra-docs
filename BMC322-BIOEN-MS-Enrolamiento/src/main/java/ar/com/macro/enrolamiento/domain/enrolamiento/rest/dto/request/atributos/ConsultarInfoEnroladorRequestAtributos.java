package ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.atributos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.validator.ConsultarInfoEnroladorRequestValidator;

@Constraint(validatedBy = ConsultarInfoEnroladorRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsultarInfoEnroladorRequestAtributos {
    String message() default "{api.validacion.ConsultarInfoEnroladorRequestValidator.validar.atributos.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
