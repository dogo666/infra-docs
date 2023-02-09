package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.constant.Errores;

public class ComunicacionFeingException extends MacroException {
    public ComunicacionFeingException(Integer codigo, String msg) {
        super(codigo,msg);
    }

    public ComunicacionFeingException(Errores error) {
        super(error.getCodigo(),error.getMensaje());
    }
}
