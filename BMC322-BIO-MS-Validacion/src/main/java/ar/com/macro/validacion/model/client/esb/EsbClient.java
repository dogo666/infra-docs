package ar.com.macro.validacion.model.client.esb;

import ar.com.macro.commons.exceptions.BadRequestException;
import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteRequest;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteResponse;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion.ConsultaNormalizacionIndividuo;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion.NormalizarIndividuoRequest;

public interface EsbClient {

    /**
     * Consulta en el BE (ESB macro) de un servicio para la normalizacion de un individuo.
     * @param normalizarIndividuoRequest parametros dni, sexo o apellido para la consulta.
     * @return {@link ConsultaNormalizacionIndividuo} Objeto que contiene la respuesta del servicio.
     */
    ConsultaNormalizacionIndividuo normalizarIndividuoV2(NormalizarIndividuoRequest normalizarIndividuoRequest);

    /**
     * Retorna informacion general de un cliente
     * @param  consultaGeneralClienteRequest
     * @return consultaGeneralClienteResponse
     * @throws InformacionNoEncontradaException
     * @throws ComunicacionFallidaException
     * @throws BadRequestException
     */
    ConsultaGeneralClienteResponse consultarCliente(ConsultaGeneralClienteRequest consultaGeneralClienteRequest);

}
