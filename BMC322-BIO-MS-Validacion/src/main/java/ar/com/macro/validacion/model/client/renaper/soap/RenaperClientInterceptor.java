package ar.com.macro.validacion.model.client.renaper.soap;

import java.util.Objects;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import ar.com.macro.constant.Errores;
import ar.com.macro.exceptions.RenaperException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RenaperClientInterceptor implements ClientInterceptor {

	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
		
		if(Objects.nonNull(ex)) {
			log.error(ex.getMessage(), ex);
			throw new RenaperException(Errores.ERROR_RENAPER_GENERAL.getCodigo(), Errores.ERROR_RENAPER_GENERAL.getMensaje());
		}
	}

	
}
