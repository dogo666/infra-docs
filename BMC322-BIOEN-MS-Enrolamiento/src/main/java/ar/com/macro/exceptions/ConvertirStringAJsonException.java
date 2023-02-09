package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

public class ConvertirStringAJsonException extends MacroException {
    private static final long serialVersionUID = -2472947870984411743L;
    public ConvertirStringAJsonException(Integer codigo, String msg) {
        super(codigo, msg);
    }
}
