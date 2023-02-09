package ar.com.macro.validacion.model.client.datapower.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import ar.com.macro.commons.exceptions.AutenticacionFallidaException;
import ar.com.macro.commons.utils.auth.TokenAuthenticator;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataPowerInterceptor implements ClientHttpRequestInterceptor {
	
	@Autowired
	private TokenAuthenticator tokenAuthenticator;
	
	@Value("${app.applicationId}")
	private String applicationId;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		if (!isLoginRequest(request)) {
			if (tokenAuthenticator.isNotAuthenticated()) {
				tokenAuthenticator.authenticate();
			}
			if (tokenAuthenticator.isNotAuthenticated()) {
				log.info(ErrorMessage.ESB_AUTENTICACION_FALLIDA.mensaje());
				throw new AutenticacionFallidaException(ErrorMessage.ESB_AUTENTICACION_FALLIDA.mensaje());
			}
		}
		setHeader(request);
		ClientHttpResponse response = execution.execute(request, body);
		log.info("Http status request {}", response.getRawStatusCode());

		if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
			response = retryRequestIfTokenExpired(request, body, execution);
		}

		return response;
	}
	
	private ClientHttpResponse retryRequestIfTokenExpired(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {

		tokenAuthenticator.authenticate();
		if (tokenAuthenticator.isNotAuthenticated()) {
			log.info("retry failed to obtain a token from the security service");
			throw new AutenticacionFallidaException("failed to get a new token from the security service");
		}
		setHeader(request);
		return execution.execute(request, body);
	}

	public static boolean isLoginRequest(HttpRequest request) {
		List<String> loginRequestHeaders = request.getHeaders().get("Authorization");
		return loginRequestHeaders != null && !loginRequestHeaders.isEmpty() && loginRequestHeaders.get(0) != null;
	}	

	private void setHeader(HttpRequest request) {

		if (!request.getHeaders().containsKey(HeaderConstants.HEADER_X_AUTHORIZATION_TOKEN)) {
			request.getHeaders().add(HeaderConstants.HEADER_X_AUTHORIZATION_TOKEN,
					tokenAuthenticator.getCurrentToken());
		} else {
			request.getHeaders().set(HeaderConstants.HEADER_X_AUTHORIZATION_TOKEN,
					tokenAuthenticator.getCurrentToken());
		}

		if (!request.getHeaders().containsKey(HeaderConstants.HEADER_X_APPLICATION_IDENTITY)) {
			request.getHeaders().add(HeaderConstants.HEADER_X_APPLICATION_IDENTITY, applicationId);
		} else {
			request.getHeaders().set(HeaderConstants.HEADER_X_APPLICATION_IDENTITY, applicationId);
		}		
		
		if(!request.getMethod().equals(HttpMethod.GET)) {
			if (!request.getHeaders().containsKey(HeaderConstants.HEADER_CONTENT_TYPE)) {
				request.getHeaders().add(HeaderConstants.HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			} else {
				request.getHeaders().set(HeaderConstants.HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			}
		}
		
		if (!request.getHeaders().containsKey(HeaderConstants.HEADER_ACCEPT)) {
			request.getHeaders().add(HeaderConstants.HEADER_ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		} else {
			request.getHeaders().set(HeaderConstants.HEADER_ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		}
		
		if(request.getMethod().equals(HttpMethod.GET)) {
			request.getHeaders().remove("Content-Length");
		}
		log.info("Data Power Interceptor headers {}", request.getHeaders());
	}
	
}
