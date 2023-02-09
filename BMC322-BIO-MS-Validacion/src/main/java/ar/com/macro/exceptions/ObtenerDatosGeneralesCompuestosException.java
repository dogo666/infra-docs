package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

public class ObtenerDatosGeneralesCompuestosException extends MacroException {
    private static final long serialVersionUID = 1082041564612225173L;
    public ObtenerDatosGeneralesCompuestosException(Integer codigo, String msg) {
        super(codigo,msg);
    }
}
