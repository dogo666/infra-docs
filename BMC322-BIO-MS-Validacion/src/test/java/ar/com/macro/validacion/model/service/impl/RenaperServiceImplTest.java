package ar.com.macro.validacion.model.service.impl;


import ar.com.macro.constants.Constantes;
import ar.com.macro.validacion.domain.renaper.rest.dto.request.ValidarHuellaEnRenaperRequest;
import ar.com.macro.validacion.domain.renaper.rest.dto.response.ValidarHuellaEnRenaperResponse;
import ar.com.macro.validacion.model.client.renaper.HuellaRenaperClient;
import ar.com.macro.validacion.model.client.renaper.RenaperClient;
import ar.com.macro.validacion.model.client.renaper.dto.soap.*;
import ar.com.macro.validacion.model.service.RenaperService;
import ar.com.macro.exceptions.RenaperException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class RenaperServiceImplTest {
	
	@Spy
	private ObjectMapper mapper;

	private RenaperService renaperService;

	@Mock
	private RenaperClient renaperClient;

	@Mock
	private HuellaRenaperClient huellaRenaperClient;

	@Before
	public void init() {
		this.renaperService = new RenaperServiceImpl(renaperClient,huellaRenaperClient);
	}
	


	@Test(expected = RenaperException.class)
	public void debeValidarHuellaEnRenaperGenerarTransaccionHuellaException() throws Exception {
		
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_REQUEST);
		ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest = mapper.readValue(jsonRequest, ValidarHuellaEnRenaperRequest.class);
		
		when(huellaRenaperClient.generarTransaccion(any(GenerarTransaccion.class))).thenThrow(
				new RenaperException(Constantes.ERROR_GENERAR_TRANSACCION_CODIGO, Constantes.ERROR_GENERAR_TRANSACCION_MENSAJE));

		renaperService.validarHuellaEnRenaper(validarHuellaEnRenaperRequest);
	}
	
	@Test(expected = RenaperException.class)
	public void debeValidarHuellaEnRenaperTCNInvalido1Exception() throws Exception {
		
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_REQUEST);
		ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest = mapper.readValue(jsonRequest, ValidarHuellaEnRenaperRequest.class);
		
		GenerarTransaccionResponse generarTransaccionResponse = new GenerarTransaccionResponse();
		generarTransaccionResponse.setTcn("-1 resultado");
		when(huellaRenaperClient.generarTransaccion(any(GenerarTransaccion.class))).thenReturn(generarTransaccionResponse);

		renaperService.validarHuellaEnRenaper(validarHuellaEnRenaperRequest);
	}
	
	@Test(expected = RenaperException.class)
	public void debeValidarHuellaEnRenaperTCNInvalido2Exception() throws Exception {
		
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_REQUEST);
		ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest = mapper.readValue(jsonRequest, ValidarHuellaEnRenaperRequest.class);
		
		GenerarTransaccionResponse generarTransaccionResponse = new GenerarTransaccionResponse();
		generarTransaccionResponse.setTcn("-2 resultado");
		when(huellaRenaperClient.generarTransaccion(any(GenerarTransaccion.class))).thenReturn(generarTransaccionResponse);

		renaperService.validarHuellaEnRenaper(validarHuellaEnRenaperRequest);
	}

	@Test
	public void debeValidarHuellaEnRenaperExito() throws Exception {

		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_HUELLA_RENAPER_REQUEST);
		ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest = mapper.readValue(jsonRequest, ValidarHuellaEnRenaperRequest.class);

		GenerarTransaccionResponse generarTransaccionResponse = new GenerarTransaccionResponse();
		generarTransaccionResponse.setTcn("2021070211574684 resultado14080");
		when(huellaRenaperClient.generarTransaccion(any(GenerarTransaccion.class))).thenReturn(generarTransaccionResponse);

		when(huellaRenaperClient.consultarPorTCN(any(ConsultaPorTCN.class))).thenReturn(prepararConsultaPorTCNResponse());

		ValidarHuellaEnRenaperResponse validarHuellaEnRenaperResponse = renaperService.validarHuellaEnRenaper(validarHuellaEnRenaperRequest);

		assertEquals("9402", validarHuellaEnRenaperResponse.getPuntaje());
		assertEquals(Constantes.RENAPER_CONSULTA_TCN_HIT, validarHuellaEnRenaperResponse.getResultado());
	}

	private ConsultaPorTCNResponse prepararConsultaPorTCNResponse() {
		ConsultaPorTCNResponse consultaPorTCNResponse = new ConsultaPorTCNResponse();
		Salida salida = new Salida();
		salida.setCodigo(0);
		salida.setDateOfTransaction("2020-12-24T15:17:55.440Z");
		salida.setMatchScore("9402");
		salida.setMatchType(Constantes.RENAPER_CONSULTA_TCN_HIT);
		salida.setMensaje(Constantes.RENAPER_CONSULTA_TCN_MENSAJE_OK);
		salida.setTransactionControlNumber("2020122412182687");
		consultaPorTCNResponse.setSalida(salida);
		return consultaPorTCNResponse;
	}
}
