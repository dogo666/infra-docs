package ar.com.macro.config;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

@Configuration
@ConditionalOnExpression("${daonengine.mtls.actived:false}")
public class DaonClientMTLSConfig {

    @Value("${daonengine.mtls-auth.keystore}")
    private String keyStore;

    @Value("${daonengine.mtls-auth.keystore.trust}")
    private String trustStore;

    @Value("${daonengine.mtls-auth.keystore.pass}")
    private String password;

    @Value("${daonengine.mtls-auth.keystore.trust.pass}")
    private String passwordTrustStore;

    @Bean("mtlsMessageSender")
    public HttpComponentsMessageSender mtlsMessageSender() throws Exception {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setHttpClient(httpClient());

        return httpComponentsMessageSender;
    }

    public HttpClient httpClient() throws Exception {
        return HttpClientBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory())
                .addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
    }

    public SSLConnectionSocketFactory sslConnectionSocketFactory() throws Exception {
        return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
    }

    public SSLContext sslContext() throws Exception {
        Resource keyStoreResource =  new FileSystemResource("".concat("").concat(keyStore));
        Resource trustStoreResource =  new FileSystemResource("".concat("").concat(trustStore));
        return SSLContextBuilder.create()
                .loadKeyMaterial(keyStoreResource.getFile(), password.toCharArray(),password.toCharArray())
                .loadTrustMaterial(trustStoreResource.getFile(), passwordTrustStore.toCharArray()).build();
    }

}

