package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

public class DaonEngineException extends MacroException {
    private static final long serialVersionUID = -2472947870984411742L;
    public DaonEngineException(Integer codigo, String msg) {
        super(codigo, msg);
    }
}
