package ar.com.macro.validacion.model.client.renaper.impl;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import ar.com.macro.constants.Constantes;
import ar.com.macro.utils.RenaperUtils;
import ar.com.macro.validacion.model.client.renaper.HuellaRenaperClient;
import ar.com.macro.validacion.model.client.renaper.dto.soap.ConsultaPorTCN;
import ar.com.macro.validacion.model.client.renaper.dto.soap.ConsultaPorTCNResponse;
import ar.com.macro.validacion.model.client.renaper.dto.soap.Entrada;
import ar.com.macro.validacion.model.client.renaper.dto.soap.GenerarTransaccion;
import ar.com.macro.validacion.model.client.renaper.dto.soap.ObjectFactory;
import ar.com.macro.validacion.model.client.renaper.dto.soap.Salida;

@RunWith(SpringRunner.class)
public class HuellaRenaperClientImplTest {

	private HuellaRenaperClient huellaRenaperClient;
	
	@MockBean
	@Qualifier("renaperWebService")
	private WebServiceTemplate webServiceTemplate;
	
	private ObjectFactory factory;
	
	@Before
	public void init() {
		this.huellaRenaperClient = new HuellaRenaperClientImpl(webServiceTemplate);
		factory = new ObjectFactory();
	}

    @Test
    public void debeGenerarTransaccionExito() {
  
      try (MockedStatic<RenaperUtils> renaperUtils = Mockito.mockStatic(RenaperUtils.class)) {
        var generarTransaccionResponse = factory.createGenerarTransaccionResponse();
        generarTransaccionResponse.setTcn("2021070211574684 resultado14080");
  
        when(webServiceTemplate.marshalSendAndReceive(any(GenerarTransaccion.class)))
            .thenReturn(generarTransaccionResponse);
  
        renaperUtils
            .when(() -> RenaperUtils.imagenBase64To512x512(any(String.class)))
            .thenReturn("image");
  
        var respuesta = huellaRenaperClient.generarTransaccion(prepararGenerarTransaccion());
  
        assertNotNull(respuesta);
        assertEquals("2021070211574684 resultado14080", respuesta.getTcn());
      }
  }

    @Test
    public void debeGenerarTransaccionError1() {
  
      try (MockedStatic<RenaperUtils> renaperUtils = Mockito.mockStatic(RenaperUtils.class)) {
        var generarTransaccionResponse = factory.createGenerarTransaccionResponse();
        generarTransaccionResponse.setTcn("-1 resultado");
  
        when(webServiceTemplate.marshalSendAndReceive(any(GenerarTransaccion.class)))
            .thenReturn(generarTransaccionResponse);
  
        renaperUtils
            .when(() -> RenaperUtils.imagenBase64To512x512(any(String.class)))
            .thenReturn("image");
  
        var respuesta = huellaRenaperClient.generarTransaccion(prepararGenerarTransaccion());
  
        assertNotNull(respuesta);
        assertEquals("-1 resultado", respuesta.getTcn());
      }
    }
  
    @Test
    public void debeGenerarTransaccionError2() {
      try (MockedStatic<RenaperUtils> renaperUtils = Mockito.mockStatic(RenaperUtils.class)) {
  
        var generarTransaccionResponse = factory.createGenerarTransaccionResponse();
        generarTransaccionResponse.setTcn("-2 resultado");
  
        when(webServiceTemplate.marshalSendAndReceive(any(GenerarTransaccion.class)))
            .thenReturn(generarTransaccionResponse);
  
        renaperUtils
            .when(() -> RenaperUtils.imagenBase64To512x512(any(String.class)))
            .thenReturn("image");
  
        var respuesta = huellaRenaperClient.generarTransaccion(prepararGenerarTransaccion());
  
        assertNotNull(respuesta);
        assertEquals("-2 resultado", respuesta.getTcn());
      }
    }

    @Test
    public void debeConsultarPorTCNExito() {
      var consultaPorTCN = factory.createConsultaPorTCN();
      consultaPorTCN.setTcn("2021061813420825");
  
      when(webServiceTemplate.marshalSendAndReceive(any(ConsultaPorTCN.class)))
          .thenReturn(prepararConsultaPorTCNResponse());
  
      var respuesta = huellaRenaperClient.consultarPorTCN(consultaPorTCN);
  
      assertNotNull(respuesta);
      assertEquals(Constantes.RENAPER_CONSULTA_TCN_HIT, respuesta.getSalida().getMatchType());
    }

	@Test
	public void debeConsultarPorTCNNoHit() {
		
		ConsultaPorTCN consultaPorTCN = factory.createConsultaPorTCN();
		consultaPorTCN.setTcn("2021061813420825");
		
		ConsultaPorTCNResponse consultaPorTCNResponse = prepararConsultaPorTCNResponse();
		consultaPorTCNResponse.getSalida().setMatchScore("0");
		consultaPorTCNResponse.getSalida().setMatchType(Constantes.RENAPER_CONSULTA_TCN_NOHIT);
		
		when(webServiceTemplate.marshalSendAndReceive(any(ConsultaPorTCN.class))).thenReturn(consultaPorTCNResponse);
		ConsultaPorTCNResponse respuesta = huellaRenaperClient.consultarPorTCN(consultaPorTCN);
		assertNotNull(respuesta);
		assertEquals(Constantes.RENAPER_CONSULTA_TCN_NOHIT, respuesta.getSalida().getMatchType());
	}
	
	@Test
	public void debeConsultarPorTCNError() {
		
		ConsultaPorTCN consultaPorTCN = factory.createConsultaPorTCN();
		consultaPorTCN.setTcn("2021061813420825");
		Salida salida = factory.createSalida();
		ConsultaPorTCNResponse consultaPorTCNResponse = factory.createConsultaPorTCNResponse();
		consultaPorTCNResponse.setSalida(salida);
		when(webServiceTemplate.marshalSendAndReceive(any(ConsultaPorTCN.class))).thenReturn(consultaPorTCNResponse);
		ConsultaPorTCNResponse respuesta = huellaRenaperClient.consultarPorTCN(consultaPorTCN);
		assertNotNull(respuesta);
		assertEquals(null, respuesta.getSalida().getMatchType());
	}
	
	private ConsultaPorTCNResponse prepararConsultaPorTCNResponse() {
		
		ConsultaPorTCNResponse consultaPorTCNResponse = factory.createConsultaPorTCNResponse();
		Salida salida = factory.createSalida();
		salida.setCodigo(0);
		salida.setDateOfTransaction("2020-12-24T15:17:55.440Z");
		salida.setMatchScore("9402");
		salida.setMatchType(Constantes.RENAPER_CONSULTA_TCN_HIT);
		salida.setMensaje(Constantes.RENAPER_CONSULTA_TCN_MENSAJE_OK);
		salida.setTransactionControlNumber("2020122412182687");
		consultaPorTCNResponse.setSalida(salida);
		return consultaPorTCNResponse;		
	}
	
	private GenerarTransaccion prepararGenerarTransaccion() {
		
		GenerarTransaccion generarTransaccion = factory.createGenerarTransaccion();
		Entrada entrada = factory.createEntrada();
		entrada.setDescripcionH1(Constantes.RENAPER_PULGAR_DERECHO);
		entrada.setDescripcionH2(Constantes.RENAPER_PULGAR_IZQUIERDO);
		entrada.setDni(28865789);
		entrada.setSexo(Constantes.SEXO_MASCULINO);
		
		String huella1 = retrieveBody(Constantes.RENAPER_HUELLA);
		String huella2 = retrieveBody(Constantes.RENAPER_HUELLA);
		entrada.setHuella1(huella1);
		entrada.setHuella2(huella2);
		generarTransaccion.setEntrada(entrada);
		return generarTransaccion;		
	}
}