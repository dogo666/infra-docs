package ar.com.macro.enrolamiento.model.client.renaper.impl;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InterceptorRenaper implements ClientHttpRequestInterceptor {

    public static final String APPLICATION_JSON = "application/json";

    public InterceptorRenaper() {
    }

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        this.setHeader(request);
        ClientHttpResponse response = execution.execute(request, body);
        log.info("Http status request {}", response.getRawStatusCode());
        if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
            response = this.retryRequestIfTokenExpired(request, body, execution);
        }
        return response;
    }

    private ClientHttpResponse retryRequestIfTokenExpired(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        this.setHeader(request);
        return execution.execute(request, body);
    }

    private void setHeader(HttpRequest request) {

        if (!request.getHeaders().containsKey(CONTENT_TYPE)) {
            request.getHeaders().add(CONTENT_TYPE, APPLICATION_JSON);
        } else {
            request.getHeaders().set(CONTENT_TYPE, APPLICATION_JSON);
        }

        if (!request.getHeaders().containsKey(ACCEPT)) {
            request.getHeaders().add(ACCEPT, APPLICATION_JSON);
        } else {
            request.getHeaders().set(ACCEPT, APPLICATION_JSON);
        }

    }
}