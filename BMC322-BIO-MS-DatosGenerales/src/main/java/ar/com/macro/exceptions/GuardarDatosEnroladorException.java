package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

public class GuardarDatosEnroladorException extends MacroException {
    public GuardarDatosEnroladorException(Integer codigo, String msg){
        super(codigo,msg);
    }
}
