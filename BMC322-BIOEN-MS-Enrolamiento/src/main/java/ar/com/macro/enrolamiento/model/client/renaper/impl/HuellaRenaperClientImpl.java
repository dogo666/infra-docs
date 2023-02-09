package ar.com.macro.enrolamiento.model.client.renaper.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import ar.com.macro.constant.Errores;
import ar.com.macro.enrolamiento.model.client.renaper.HuellaRenaperClient;
import ar.com.macro.enrolamiento.model.client.renaper.dto.soap.ConsultaPorTCN;
import ar.com.macro.enrolamiento.model.client.renaper.dto.soap.ConsultaPorTCNResponse;
import ar.com.macro.enrolamiento.model.client.renaper.dto.soap.GenerarTransaccion;
import ar.com.macro.enrolamiento.model.client.renaper.dto.soap.GenerarTransaccionResponse;
import ar.com.macro.exceptions.RenaperException;
import ar.com.macro.utils.RenaperUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HuellaRenaperClientImpl implements HuellaRenaperClient {	

	private WebServiceTemplate webServiceTemplate;
	
	public HuellaRenaperClientImpl(@Qualifier("renaperWebService")WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}
	
	@Override
	public GenerarTransaccionResponse generarTransaccion(GenerarTransaccion generarTransaccion) {
				
		return (GenerarTransaccionResponse) webServiceTemplate.marshalSendAndReceive(redimensionarHuellas(generarTransaccion));
	}

	private GenerarTransaccion redimensionarHuellas(GenerarTransaccion generarTransaccion) {
		
		String nuevaHuella1;
		String nuevaHuella2;
		try {
			nuevaHuella1 = RenaperUtils.imagenBase64To512x512(generarTransaccion.getEntrada().getHuella1());
			nuevaHuella2 = RenaperUtils.imagenBase64To512x512(generarTransaccion.getEntrada().getHuella2());
			generarTransaccion.getEntrada().setHuella1(nuevaHuella1);
			generarTransaccion.getEntrada().setHuella2(nuevaHuella2);
		} catch (IOException e) {
			log.error("Error redimensionando huellas", e);
			throw new RenaperException(Errores.ERROR_GENERAR_TRANSACCION.getCodigo(), Errores.ERROR_GENERAR_TRANSACCION.getMensaje());
		}
		
		return generarTransaccion;
	}

	@Override
	public ConsultaPorTCNResponse consultarPorTCN(ConsultaPorTCN consultaPorTCN) {
		return (ConsultaPorTCNResponse) webServiceTemplate.marshalSendAndReceive(consultaPorTCN);
	}
	
}