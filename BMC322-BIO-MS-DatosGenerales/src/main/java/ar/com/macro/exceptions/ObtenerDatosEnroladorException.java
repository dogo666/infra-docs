package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

public class ObtenerDatosEnroladorException extends MacroException {
    public ObtenerDatosEnroladorException(Integer codigo, String msg){
        super(codigo,msg);
    }
}
