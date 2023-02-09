package ar.com.macro.commons.utils.auth;

import ar.com.macro.commons.constants.ErrorCode;
import ar.com.macro.commons.exceptions.AutenticacionFallidaException;
import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.utils.props.CatalogoDeServicios;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Component
public class TokenAuthenticator {
    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticator.class);
    private String currentToken;
    @Value("${authentication.service.username}")
    private String username;
    @Value("${authentication.service.password}")
    private String password;

    @Value("${authentication.service.info:false}")
    private Boolean info;

    @Autowired
    private CatalogoDeServicios catalogoDeServicios;

    public TokenAuthenticator() {
    }

    public void authenticate() {
        HttpHeaders headers = this.createHttpHeadersAutenticacion();
        RestTemplate restTemplate = this.obtenerRestTemplateBean();

        try {
            ResponseEntity<String> response = restTemplate.exchange(this.catalogoDeServicios.getUrlESBSecurity(), HttpMethod.GET, new HttpEntity(headers), String.class, new Object[0]);
            if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
                throw new AutenticacionFallidaException(ErrorMessage.ESB_AUTENTICACION_FALLIDA.mensaje());
            } else {
                if (HttpStatus.NO_CONTENT.equals(response.getStatusCode())) {
                    HttpHeaders responseHeaders = response.getHeaders();
                    this.currentToken = responseHeaders.get("X-Authorization-token").isEmpty() ? null : (String)responseHeaders.get("X-Authorization-token").get(0);
                }

            }
        } catch (ResourceAccessException var5) {
            log.error("{}", var5);
            throw new ComunicacionFallidaException(ErrorCode.CODIGO_GENERAL_ESB_NO_DISPONIBLE, ErrorMessage.ESB_NO_DISPONIBLE.mensaje());
        }
    }

    public RestTemplate obtenerRestTemplateBean() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder(new RestTemplateCustomizer[0]);
        return restTemplateBuilder.build();
    }

    public boolean isNotAuthenticated() {
        return this.currentToken == null;
    }

    public String getCurrentToken() {
        return this.currentToken;
    }

    private HttpHeaders createHttpHeadersAutenticacion() {
        String notEncoded = this.username + ":" + this.password;
        String encodedAuth = BaseEncoding.base64().encode(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic " + encodedAuth);

        if(info)
            log.info("headers : {} ", headers);

        return headers;
    }
}