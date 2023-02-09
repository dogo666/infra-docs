package ar.com.macro.config;

import ar.com.macro.commons.exceptions.AutenticacionFallidaException;
import ar.com.macro.commons.utils.auth.TokenAuthenticator;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;




import ar.com.macro.commons.exceptions.AutenticacionFallidaException;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthInterceptorRenaper implements ClientHttpRequestInterceptor {

    @Value("${app.applicationId}")
    private String applicationId;

    public AuthInterceptorRenaper() {
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

    public String getBody(ClientHttpResponse response) {
        String theString = null;

        try {
            theString = IOUtils.toString(response.getBody(), Charset.defaultCharset());
        } catch (IOException var4) {
            log.error("{}", var4);
        }

        return theString;
    }

    private ClientHttpResponse retryRequestIfTokenExpired(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        this.setHeader(request);
        return execution.execute(request, body);

    }

    public static boolean isLoginRequest(HttpRequest request) {
        List<String> loginRequestHeaders = request.getHeaders().get("Authorization");
        return loginRequestHeaders != null && !loginRequestHeaders.isEmpty() && loginRequestHeaders.get(0) != null;
    }

    private void setHeader(HttpRequest request) {

        if (!request.getHeaders().containsKey("X-Application-identity")) {
            request.getHeaders().add("X-Application-identity", this.applicationId);
        } else {
            request.getHeaders().set("X-Application-identity", this.applicationId);
        }

        if (!request.getHeaders().containsKey("Content-Type")) {
            request.getHeaders().add("Content-Type", "application/json");
        } else {
            request.getHeaders().set("Content-Type", "application/json");
        }

        if (!request.getHeaders().containsKey("Accept")) {
            request.getHeaders().add("Accept", "application/json");
        } else {
            request.getHeaders().set("Accept", "application/json");
        }

    }
}