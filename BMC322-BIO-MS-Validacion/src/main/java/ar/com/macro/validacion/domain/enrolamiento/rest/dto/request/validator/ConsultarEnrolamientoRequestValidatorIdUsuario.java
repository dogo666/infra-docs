package ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.validator;

public class ConsultarEnrolamientoRequestValidatorIdUsuario {

    public static Boolean validarRequest(String idu){
        return  idu.substring(0, 2).matches("[01|02|03]{2}") &&
                idu.substring(2, idu.length() - 1).matches("^[A-Za-z0-9]+$") &&
                idu.substring(idu.length() - 1).matches("[A-Z]{1}");
    }

}
