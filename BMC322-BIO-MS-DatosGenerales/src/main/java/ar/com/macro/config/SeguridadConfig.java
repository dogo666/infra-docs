package ar.com.macro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SeguridadConfig extends WebSecurityConfigurerAdapter {

  @Value("${macro.spring.serviceid}")
  private String idServicio;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/" + idServicio + "/**", "/datosgenerales/*/**", "/secure/datosgenerales/*/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.headers()
        .contentTypeOptions()
        .and()
        .httpStrictTransportSecurity()
        .includeSubDomains(false)
        .and()
        .xssProtection()
        .block(true)
        .and()
        .frameOptions()
        .deny()
        .and()
        .headers()
        .referrerPolicy();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(
            "/v3/api-docs/**",
            "/configuration/**",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**");
  }
}
