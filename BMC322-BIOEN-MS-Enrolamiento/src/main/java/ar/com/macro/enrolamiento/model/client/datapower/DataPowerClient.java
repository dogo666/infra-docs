package ar.com.macro.enrolamiento.model.client.datapower;

import ar.com.macro.commons.exceptions.BadRequestException;
import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.enrolamiento.model.client.datapower.dto.rest.dto.consulta.identificacion.ConsultaIdentificacionClienteResponse;

public interface DataPowerClient {

    /**
     * Retorna informacion general de un cliente en DataPower
     * @param  consultarIdentidadRequest
     * @return consultaIdentificacionClienteResponse
     * @throws InformacionNoEncontradaException
     * @throws ComunicacionFallidaException
     * @throws BadRequestException
     */
    ConsultaIdentificacionClienteResponse consultarIdentificacionCliente(ConsultarIdentidadRequest consultarIdentidadRequest);

}
