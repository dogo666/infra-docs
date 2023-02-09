package ar.com.macro.config;

import ar.com.macro.validacion.model.client.renaper.impl.InterceptorRenaper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import ar.com.macro.validacion.model.client.renaper.soap.RenaperClientInterceptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableWs
@Configuration
public class RenaperConfig {

	@Value("${renaper.url}")
	private String renaperUrl;

	@Value("${renaper.certificado}")
	private String renaperCertificado;

	@Value("${renaper.clave}")
	private String renaperClave;


	@Bean("renaperMashaller")
	Jaxb2Marshaller renaperMashaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("ar.com.macro.validacion.model.client.renaper.dto.soap");

		return jaxb2Marshaller;
	}

	@Bean("renaperWebService")
	public WebServiceTemplate webServiceTemplate() throws Exception {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(renaperMashaller());
		webServiceTemplate.setUnmarshaller(renaperMashaller());
		webServiceTemplate.setDefaultUri(renaperUrl);
		webServiceTemplate.setMessageSender(renaperHttpComponentsMessageSender());
		ClientInterceptor[] interceptors = new ClientInterceptor[] {renaperInterceptor()};
		webServiceTemplate.setInterceptors(interceptors);

		return webServiceTemplate;
	}

	@Bean("renaperHttpComponentsMessageSender")
	public HttpComponentsMessageSender renaperHttpComponentsMessageSender() throws Exception {
		HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
		httpComponentsMessageSender.setHttpClient(httpClient());

		return httpComponentsMessageSender;
	}
	
	@Bean("renaperInterceptor")
	public RenaperClientInterceptor renaperInterceptor() {
		return new RenaperClientInterceptor();
	}


	public HttpClient httpClient() throws Exception {
		return HttpClientBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory())
				.addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor()).build();
	}

	public SSLConnectionSocketFactory sslConnectionSocketFactory()
			throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException,
			UnrecoverableKeyException, KeyManagementException {

		KeyStore clientStore = KeyStore.getInstance("PKCS12");
		clientStore.load(new FileInputStream(renaperCertificado), renaperClave.toCharArray());
		SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
		sslContextBuilder.useProtocol("TLSv1.2");
		sslContextBuilder.loadKeyMaterial(clientStore, renaperClave.toCharArray());
		sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());


		return sslConnectionSocketFactory;
	}

	@Bean("restTemplateRenaper")
	public RestTemplate restTemplate(InterceptorRenaper authInterceptor) {
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