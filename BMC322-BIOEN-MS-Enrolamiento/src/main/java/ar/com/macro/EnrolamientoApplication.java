package ar.com.macro;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.google.common.base.Charsets;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import ar.com.macro.commons.components.adaptadorinterceptorweb.AdaptadorInterceptorWeb;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableRetry
@EnableFeignClients
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
public class EnrolamientoApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(EnrolamientoApplication.class, args);
    log.info("Microservicio Enrolamiento iniciado...");
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(
        new Locale.Builder().setLanguage("es").setRegion("AR").build());
    return sessionLocaleResolver;
  }

  @Override
  @Bean
  public Validator getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
    resourceBundleMessageSource.setBasenames("messages");
    resourceBundleMessageSource.setDefaultEncoding(Charsets.UTF_8.name());
    resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    return resourceBundleMessageSource;
  }
}
