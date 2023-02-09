package ar.com.macro.biometria.commons.trace.exception;

import ar.com.macro.biometria.commons.trace.constant.CodigoError;
import ar.com.macro.commons.exceptions.MacroException;

public class SystemException extends MacroException {

    private static final long serialVersionUID = 1L;

    public SystemException(final String message, final Throwable cause, final CodigoError errorCode) {

        super(Integer.valueOf(errorCode.getCodigo()), message);
    }
}
