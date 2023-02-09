package ar.com.macro.biometria.commons.trace.util;


import ar.com.macro.biometria.commons.trace.exception.ExceptionBuilder;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static ar.com.macro.biometria.commons.trace.constant.CodigoError.ERROR_DE_SISTEMA;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FunctionalDirectives {

    public static <T> T tryAndCatchAsSystemException(
            final CheckedFunction0<? extends T> supplier, final String errorMessage) {

        return Try.of(supplier)
                .getOrElseThrow(
                        (e) ->
                                ExceptionBuilder.builder()
                                        .message(errorMessage)
                                        .errorCode(ERROR_DE_SISTEMA)
                                        .cause(e)
                                        .build()
                                        .asSystemException());
    }

    public static void tryRunAndCatchAsSystemException(
            final CheckedRunnable runnable, final String errorMessage) {

        Try.run(runnable)
                .getOrElseThrow(
                        (e) ->
                                ExceptionBuilder.builder()
                                        .message(errorMessage)
                                        .errorCode(ERROR_DE_SISTEMA)
                                        .cause(e)
                                        .build()
                                        .asSystemException());
    }
}
