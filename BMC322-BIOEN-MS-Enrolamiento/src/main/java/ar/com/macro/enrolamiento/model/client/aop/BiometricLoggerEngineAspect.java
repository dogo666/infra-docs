package ar.com.macro.enrolamiento.model.client.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import lombok.extern.slf4j.Slf4j;

/**
 * BiometricLoggerEngineAspect
 *
 * @author globant
 * @version 1.0
 * @since 2022-05-24
 */
//@Aspect
//@Service
@Slf4j
public class BiometricLoggerEngineAspect {

  @Around(
      value =
          "execution(* ar.com.macro.enrolamiento.model.client.identityx.IdentityXClient.*(..)) "
              + "|| execution(* ar.com.macro.enrolamiento.model.client.daonengine.DaonEngineClient.*(..)) "
              + "|| execution(* ar.com.macro.enrolamiento.model.service.DatosGeneralesService.*(..)) "
              + "|| execution(* ar.com.macro.enrolamiento.model.client.identityx.impl.DigitalOnboardingServicesImpl.*(..))")
  public Object beforeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    var start = System.currentTimeMillis();

    try {
      log.info("INICIA METODO: {}", joinPoint.getSignature());
      return joinPoint.proceed();

    } finally {
      log.info("FINALIZA METODO {}", joinPoint.getSignature());
      log.info("TIEMPO TOTAL: {} ms", (System.currentTimeMillis() - start));
    }
  }
}
