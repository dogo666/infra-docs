package ar.com.macro.biometria.commons.trace.util;

import ar.com.macro.biometria.commons.trace.feing.dto.GuardarTrazaRequest;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor(staticName = "create")
public class TraceBiometricsLoader {

    private final JoinPoint joinPoint;
    private final ResponseEntity<?> response;
    private HttpServletRequest request;
    private GuardarTrazaRequest.GuardarTrazaRequestBuilder traza;

    public TraceBiometricsLoader init() {
        request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        traza = GuardarTrazaRequest.builder();
        return this;
    }

    public TraceBiometricsLoader addFechaCreacion() {
        return this;
    }

    public TraceBiometricsLoader addFechaActualizacion() {
        return this;
    }

    public TraceBiometricsLoader extractRequestURIData() {
        traza.endpoint(request.getRequestURI());
        return this;
    }

    public TraceBiometricsLoader extractRequestHeadersData() {
        return this;
    }

  public TraceBiometricsLoader extractRequestBodyData(List<String> binarios) {
    traza.request(PayloadUtils.extraerDataSinBinarios(binarios,joinPoint.getArgs()[0]));
    return this;
  }

    public TraceBiometricsLoader extractResponseBodyData(final List<String> binarios) {

        if (Objects.nonNull(response)) {
            traza.response(PayloadUtils.extraerDataSinBinarios(binarios, response));
        }
        return this;
    }

    public TraceBiometricsLoader extractResponseCode() {

        if (Objects.nonNull(response)) {
            traza.codigohttp(response.getStatusCodeValue());
            traza.valorcodigohttp(response.getStatusCode().getReasonPhrase());
        }

        return this;
    }

    public TraceBiometricsLoader extractExceptionData(final Throwable e) {

        if (Objects.nonNull(e)) {
            traza.mensajeexcepcion(e.getMessage());
            traza.stacktrace(ExceptionUtils.getStackTrace(e));
        }
        return this;
    }

    public GuardarTrazaRequest load() {
        return traza.build();
    }
}
