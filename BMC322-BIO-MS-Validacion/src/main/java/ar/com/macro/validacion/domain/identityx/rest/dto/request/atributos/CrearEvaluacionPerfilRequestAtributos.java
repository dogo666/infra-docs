package ar.com.macro.validacion.domain.identityx.rest.dto.request.atributos;

import ar.com.macro.validacion.domain.identityx.rest.dto.request.validator.CrearEvaluacionPerfilRequestValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CrearEvaluacionPerfilRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrearEvaluacionPerfilRequestAtributos {
    String message() default "{api.validacion.CrearEvaluacionPerfilRequestValidator.validar.atributos.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
