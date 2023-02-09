package ar.com.macro.config;

import ar.com.macro.validacion.model.client.datapower.impl.DataPowerInterceptor;
import ar.com.macro.validacion.model.client.renaper.impl.InterceptorRenaper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class DataPowerConfig {

    @Bean("dataPowerRestTemplate")
    public RestTemplate restTemplate(DataPowerInterceptor authInterceptor) {
        Netty4ClientHttpRequestFactory netty4ClientHttpRequestFactory = new Netty4ClientHttpRequestFactory();
        netty4ClientHttpRequestFactory.afterPropertiesSet();
        RestTemplate restTemplate = new RestTemplate(netty4ClientHttpRequestFactory);
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList();
        interceptors.add(authInterceptor);
        restTemplate.setInterceptors(interceptors);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
