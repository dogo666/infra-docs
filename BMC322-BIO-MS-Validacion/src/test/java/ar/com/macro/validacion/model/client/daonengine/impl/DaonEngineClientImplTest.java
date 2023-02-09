package ar.com.macro.validacion.model.client.daonengine.impl;

import ar.com.macro.validacion.model.client.daonengine.dto.soap.*;
import ar.com.macro.validacion.model.client.daonengine.header.*;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyRequest;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResponse;
import ar.com.macro.validacion.model.client.daonengine.mapper.CodigosDeRespuestaVerificarIdentificacionDaonEngine;
import ar.com.macro.exceptions.DaonEngineException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DaonEngineClientImplTest {

	@Value("${daonengine.soapaction.getidentity}")
	private String soapActionGetIdentity;
	
	@Value("${daonengine.soapaction.identification.verify}")
	private String soapActionVerifyIdentification;

	@MockBean
	@Qualifier("daonEngineWebServiceTemplate")
	private WebServiceTemplate webServiceTemplate;
	
	@MockBean
	@Qualifier("daonEngineIdentificationWebServiceTemplate")
	private WebServiceTemplate identificationWebServiceTemplate;

	@Mock private GetIdentityRequest getIdentityRequest;
	@Mock private VerifyRequest verifyRequest;

	private DaonEngineClientImpl daonEngineClient;
	
	private ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.ObjectFactory identificationObjectFactory;

	@Before
	public void init() {
		this.daonEngineClient = new DaonEngineClientImpl(webServiceTemplate, soapActionGetIdentity, identificationWebServiceTemplate, soapActionVerifyIdentification);
		identificationObjectFactory = new ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.ObjectFactory();
	}

	@Test
	public void obtenerIdentidadOk(){
		ObjectFactory objectFactory = new ObjectFactory();
		GetIdentityResponse getIdentityResponse = objectFactory.createGetIdentityResponse();
		ResponseStatus responseStatus = objectFactory.createResponseStatus();
		responseStatus.setReturnCode(0);
		getIdentityResponse.setResponseStatus(responseStatus);
		when((GetIdentityResponse) webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(getIdentityResponse);
		Optional<GetIdentityResponse> optionalGetIdentityResponse = daonEngineClient.obtenerIdentidad(getIdentityRequest);
		assertNotNull(optionalGetIdentityResponse);
	}

	@Test(expected = DaonEngineException.class)
	public void obtenerIdentidadErrorEnvio(){
		when(webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenThrow(IllegalStateException.class);
		daonEngineClient.obtenerIdentidad(getIdentityRequest);
	}

	@Test(expected = DaonEngineException.class)
	public void obtenerIdentidadErrorStatus(){
		ObjectFactory objectFactory = new ObjectFactory();
		GetIdentityResponse getIdentityResponse = objectFactory.createGetIdentityResponse();
		ResponseStatus responseStatus = objectFactory.createResponseStatus();
		responseStatus.setReturnCode(221);
		getIdentityResponse.setResponseStatus(responseStatus);
		when(webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(getIdentityResponse);
		daonEngineClient.obtenerIdentidad(getIdentityRequest);
	}
	
	@Test
	public void validarHuellaOk(){
		
		VerifyResponse verifyResponse = identificationObjectFactory.createVerifyResponse();
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.ResponseStatus responseStatus = identificationObjectFactory.createResponseStatus();
		responseStatus.setReturnCode(0);
		verifyResponse.setResponseStatus(responseStatus);
		when((VerifyResponse) identificationWebServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(verifyResponse);
		Optional<VerifyResponse> optionalVerifyResponse = daonEngineClient.verificarIdentificacion(verifyRequest);
		assertNotNull(optionalVerifyResponse);
		assertTrue(optionalVerifyResponse.isPresent());
	}
	
	@Test
	public void validarHuellaIdentidadInexistente(){
		VerifyResponse verifyResponse = identificationObjectFactory.createVerifyResponse();
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.ResponseStatus responseStatus = identificationObjectFactory.createResponseStatus();
		responseStatus.setReturnCode(CodigosDeRespuestaVerificarIdentificacionDaonEngine.IDENTIDAD_INEXISTENTE.getCode());
		verifyResponse.setResponseStatus(responseStatus);
		when((VerifyResponse) identificationWebServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(verifyResponse);
		Optional<VerifyResponse> optionalVerifyResponse = daonEngineClient.verificarIdentificacion(verifyRequest);
		assertNotNull(optionalVerifyResponse);
		assertTrue(optionalVerifyResponse.isPresent());
		assertEquals(CodigosDeRespuestaVerificarIdentificacionDaonEngine.IDENTIDAD_INEXISTENTE.getCode(), optionalVerifyResponse.get().getResponseStatus().getReturnCode());
	}
	
	@Test(expected = DaonEngineException.class)
	public void validarHuellaErrorDesconocido(){
		VerifyResponse verifyResponse = identificationObjectFactory.createVerifyResponse();
		ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.ResponseStatus responseStatus = identificationObjectFactory.createResponseStatus();
		responseStatus.setReturnCode(9999);
		verifyResponse.setResponseStatus(responseStatus);
		when((VerifyResponse) identificationWebServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(verifyResponse);
		daonEngineClient.verificarIdentificacion(verifyRequest);
	}

}
