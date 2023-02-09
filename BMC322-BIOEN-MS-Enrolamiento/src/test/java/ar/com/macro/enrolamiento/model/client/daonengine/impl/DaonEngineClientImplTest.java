package ar.com.macro.enrolamiento.model.client.daonengine.impl;

import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.*;
import ar.com.macro.enrolamiento.model.client.daonengine.header.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DaonEngineClientImplTest {

	@Value("${daonengine.soapaction.getidentity}")
	private String soapActionGetIdentity;

	@Value("${daonengine.soapaction.createidentity}")
	private String soapActionCreateIdentity;

	@Value("${daonengine.soapaction.updateidentity}")
	private String soapActionUpdateIdentity;

	@MockBean
	@Qualifier("daonEngineWebServiceTemplate")
	private WebServiceTemplate webServiceTemplate;

	@Mock private GetIdentityRequest getIdentityRequest;

	@Mock private CreateIdentityRequest createIdentityRequest;

	@Mock private UpdateIdentityRequest updateIdentityRequest;

	private DaonEngineClientImpl daonEngineClient;

	@Before
	public void init() {
		this.daonEngineClient = new DaonEngineClientImpl(webServiceTemplate, soapActionGetIdentity, soapActionCreateIdentity, soapActionUpdateIdentity);
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
	public void crearIdentidadOk(){
		ObjectFactory objectFactory = new ObjectFactory();
		CreateIdentityResponse createIdentityResponse = objectFactory.createCreateIdentityResponse();
		ResponseStatus responseStatus = objectFactory.createResponseStatus();
		responseStatus.setReturnCode(0);
		createIdentityResponse.setResponseStatus(responseStatus);
		when((CreateIdentityResponse) webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(createIdentityResponse);
		CreateIdentityResponse crearIdentidad = daonEngineClient.crearIdentidad(createIdentityRequest);
		assertNotNull(crearIdentidad);
	}

	@Test(expected = DaonEngineException.class)
	public void crearIdentidadErrorEnvio(){
		when(webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenThrow(IllegalStateException.class);
		daonEngineClient.crearIdentidad(createIdentityRequest);
	}

	@Test(expected = DaonEngineException.class)
	public void crearIdentidadErrorStatus(){
		ObjectFactory objectFactory = new ObjectFactory();
		CreateIdentityResponse createIdentityResponse = objectFactory.createCreateIdentityResponse();
		ResponseStatus responseStatus = objectFactory.createResponseStatus();
		responseStatus.setReturnCode(223);
		createIdentityResponse.setResponseStatus(responseStatus);
		when(webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(createIdentityResponse);
		daonEngineClient.crearIdentidad(createIdentityRequest);
	}

	@Test
	public void actualizarIdentidadOk(){
		ObjectFactory objectFactory = new ObjectFactory();
		UpdateIdentityResponse updateIdentityResponse = objectFactory.createUpdateIdentityResponse();
		ResponseStatus responseStatus = objectFactory.createResponseStatus();
		responseStatus.setReturnCode(0);
		updateIdentityResponse.setResponseStatus(responseStatus);
		when((UpdateIdentityResponse) webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(updateIdentityResponse);
		UpdateIdentityResponse updateIdentity = daonEngineClient.actualizarIdentidad(updateIdentityRequest);
		assertNotNull(updateIdentity);
	}

	@Test(expected = DaonEngineException.class)
	public void actualizarIdentidadErrorEnvio(){
		when(webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenThrow(IllegalStateException.class);
		daonEngineClient.actualizarIdentidad(updateIdentityRequest);
	}

	@Test(expected = DaonEngineException.class)
	public void actualizarIdentidadErrorStatus(){
		ObjectFactory objectFactory = new ObjectFactory();
		UpdateIdentityResponse updateIdentityResponse = objectFactory.createUpdateIdentityResponse();
		ResponseStatus responseStatus = objectFactory.createResponseStatus();
		responseStatus.setReturnCode(221);
		updateIdentityResponse.setResponseStatus(responseStatus);
		when(webServiceTemplate.marshalSendAndReceive(any(Object.class),any(SoapRequestHeaderModifier.class))).thenReturn(updateIdentityResponse);
		daonEngineClient.actualizarIdentidad(updateIdentityRequest);
	}
}
