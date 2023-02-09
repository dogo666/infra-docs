package ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.atributos;

import ar.com.macro.constant.Constantes;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.validator.GuardarEnroladorRequestValidator;
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
    String message() default Constantes.RESPUESTA_MIDDLEWARE;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
