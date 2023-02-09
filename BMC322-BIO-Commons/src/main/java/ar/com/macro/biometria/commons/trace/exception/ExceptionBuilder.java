package ar.com.macro.biometria.commons.trace.exception;

import ar.com.macro.biometria.commons.trace.constant.CodigoError;
import lombok.Builder;

@Builder
public final class ExceptionBuilder {

    private String message;
    private Throwable cause;
    private CodigoError errorCode;


    public SystemException asSystemException() {
        return new SystemException(message, cause, errorCode);
    }
}
