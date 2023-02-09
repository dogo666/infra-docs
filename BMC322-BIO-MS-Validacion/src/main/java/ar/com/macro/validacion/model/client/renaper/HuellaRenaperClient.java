package ar.com.macro.validacion.model.client.renaper;

import ar.com.macro.validacion.model.client.renaper.dto.soap.ConsultaPorTCN;
import ar.com.macro.validacion.model.client.renaper.dto.soap.ConsultaPorTCNResponse;
import ar.com.macro.validacion.model.client.renaper.dto.soap.GenerarTransaccion;
import ar.com.macro.validacion.model.client.renaper.dto.soap.GenerarTransaccionResponse;

public interface HuellaRenaperClient {

	GenerarTransaccionResponse generarTransaccion(GenerarTransaccion generarTransaccion);
	
	ConsultaPorTCNResponse consultarPorTCN(ConsultaPorTCN consultaPorTCN);
}
