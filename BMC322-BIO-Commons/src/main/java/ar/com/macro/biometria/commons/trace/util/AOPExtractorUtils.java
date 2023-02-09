package ar.com.macro.biometria.commons.trace.util;

import ar.com.macro.biometria.commons.trace.exception.ExceptionBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static ar.com.macro.biometria.commons.trace.constant.CodigoError.ERROR_DE_SISTEMA;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AOPExtractorUtils {

    @Autowired
    private ObjectMapper objectMapper;


    private static Map<String, Object> getParameters(
            final String[] parameterNames, final Object[] values) {

        HashMap parameters = new HashMap<String, Object>();

        for (int i = 0; i < parameterNames.length; i++) {
            String paramName = parameterNames[i];
            Object paramValue = values[i];
            final Object value = values[i];

            if (paramValue instanceof MultipartFile) {

                paramValue =
                        Try.of(() -> Base64.getEncoder().encodeToString(((MultipartFile) value).getBytes()))
                                .getOrElseThrow(
                                        (e) ->
                                                ExceptionBuilder.builder()
                                                        .message("error convirtiendo parametro a base64")
                                                        .errorCode(ERROR_DE_SISTEMA)
                                                        .cause(e)
                                                        .build()
                                                        .asSystemException());

            } else if (i == 0) {
                paramName = "body";
            }

            parameters.put(paramName, paramValue);
        }

        return parameters;
    }

    private static Map<String, String> getHttpServletRequestHeaders(
            final HttpServletRequest request) {

        Enumeration<String> names = request.getHeaderNames();
        HashMap<String, String> headers = new HashMap<String, String>();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = request.getHeader(name);
            headers.put(name, value);
        }

        return headers;
    }
}
