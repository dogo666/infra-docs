package ar.com.macro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import ar.com.macro.commons.components.adaptadorinterceptorweb.AdaptadorInterceptorWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@EnableDiscoveryClient
@EnableEncryptableProperties
@SpringBootApplication
@ComponentScan(
    basePackages = {"ar.com.macro"},
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = AdaptadorInterceptorWeb.class),
      @ComponentScan.Filter(
          type = FilterType.ASPECTJ,
          pattern = "ar.com.macro.commons.components.executor.*")
    })
public class MacroBiometriaDatosGeneralesApplication {

  public static void main(String[] args) {
    SpringApplication.run(MacroBiometriaDatosGeneralesApplication.class, args);
    log.info("Microservicio Biometria Datos Generales iniciado...");
  }
}
