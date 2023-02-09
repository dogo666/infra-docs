package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.constant.Errores;

public class CrearHashException extends MacroException {
    public CrearHashException(Integer codigo, String msg) {
        super(codigo, msg);
    }

    public CrearHashException(Errores e) {
        super(e.getCodigo(), e.getMensaje());
    }
}
