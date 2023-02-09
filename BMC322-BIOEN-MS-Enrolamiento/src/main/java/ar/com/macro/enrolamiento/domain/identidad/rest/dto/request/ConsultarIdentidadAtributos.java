package ar.com.macro.enrolamiento.domain.identidad.rest.dto.request;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConsultarIdentidadRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsultarIdentidadAtributos {
    String message() default "{api.validacion.ConsultarIdentidadRequest.validar.atributos.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}