package ar.com.macro.biometria.commons.trace.aop;

import ar.com.macro.biometria.commons.trace.feing.dto.GuardarTrazaRequest;
import ar.com.macro.biometria.commons.trace.services.TraceBiometricsService;

import ar.com.macro.biometria.commons.trace.util.TraceBiometricsLoader;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Aspect
@Component
@AllArgsConstructor
@ConditionalOnExpression("${api.macro.biometrics.trace.enabled:false}")
public class TraceBiometricsAspect {

    private final TraceBiometricsService dataProvider;

    @Value("#{'${api.macro.biometrics.trace.binarios:}'.split(',')}")
    final List<String> binarios;

    @Around(
            "@annotation(ar.com.macro.biometria.commons.trace.annotations.TraceBiometricsAnnotation) && execution(public * *(..))")
    public Object intercept(final ProceedingJoinPoint joinPoint) throws Throwable {

        log.trace("aspect: iniciar registro datos traza");
        ResponseWrapper rw = ResponseWrapper.create();

        return Try.of(
                        () -> {
                            rw.setResponse((ResponseEntity<?>) joinPoint.proceed());
                            return rw.getResponse();
                        })
                .andFinally(
                        () -> {
                            save(joinPoint, rw.getResponse());
                        })
                .get();
    }

    @AfterReturning(
            pointcut = "@annotation(org.springframework.web.bind.annotation.ExceptionHandler) && args(x)",
            returning = "result")
    public void interceptOnExcepcion(final JoinPoint joinPoint, Exception x, Object result)
            throws Throwable {

        Try.run(
                        () -> {
                            GuardarTrazaRequest traza =
                                    TraceBiometricsLoader.create(joinPoint, (ResponseEntity<?>) result)
                                            .init()
                                            .addFechaActualizacion()
                                            .extractRequestHeadersData()
                                            .extractResponseBodyData(binarios)
                                            .extractResponseCode()
                                            .extractExceptionData(x)
                                            .load();

                            dataProvider.guardar(traza);
                        })
                .onFailure(
                        ex -> {
                            log.error("error actualizando traza", ex);
                        });
    }

    private void save(final ProceedingJoinPoint joinPoint, final ResponseEntity<?> response) {

        Try.run(
                        () -> {
                            GuardarTrazaRequest traza =
                                    TraceBiometricsLoader.create(joinPoint, response)
                                            .init()
                                            .addFechaCreacion()
                                            .extractRequestURIData()
                                            .extractRequestHeadersData()
                                            .extractRequestBodyData(binarios)
                                            .extractResponseCode()
                                            .load();

                            dataProvider.guardar(traza);
                        })
                .onFailure(
                        ex -> {
                            log.error("error guardando traza", ex);
                        });
    }

    @Getter
    @Setter
    @NoArgsConstructor(staticName = "create")
    public static class ResponseWrapper {
        private ResponseEntity<?> response;
    }
}
