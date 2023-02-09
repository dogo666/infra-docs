package ar.com.macro.config;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ar.com.macro.enrolamiento.model.client.renaper.impl.InterceptorRenaper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataPowerConfig {

    @Bean("dataPowerRestTemplate")
    public RestTemplate restTemplate(InterceptorRenaper authInterceptor) {
        Netty4ClientHttpRequestFactory netty4ClientHttpRequestFactory = new Netty4ClientHttpRequestFactory();
        netty4ClientHttpRequestFactory.afterPropertiesSet();
        RestTemplate restTemplate = new RestTemplate(netty4ClientHttpRequestFactory);
        var interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(authInterceptor);
        restTemplate.setInterceptors(interceptors);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
