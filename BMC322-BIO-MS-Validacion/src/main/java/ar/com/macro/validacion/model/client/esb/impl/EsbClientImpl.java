package ar.com.macro.validacion.model.client.esb.impl;

import ar.com.macro.commons.esb.model.strategy.RespuestaStrategy;
import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.commons.utils.normalization.ReglaNormalizacionUtil;
import ar.com.macro.commons.utils.props.CatalogoDeServicios;
import ar.com.macro.commons.values.constants.error.ErrorCode;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import ar.com.macro.validacion.model.client.esb.EsbClient;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteRequest;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteResponse;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion.ConsultaNormalizacionIndividuo;
import ar.com.macro.validacion.model.client.esb.rest.dto.consulta.normalizacion.NormalizarIndividuoRequest;
import ar.com.macro.validacion.model.client.esb.rest.dto.utils.ClientesConstantes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EsbClientImpl extends ServicioESB implements EsbClient {

    private final ReglaNormalizacionUtil reglaNormalizacionUtil;
    private final ObjectMapper objectMapper;

    public EsbClientImpl(RestTemplate restTemplate, CatalogoDeServicios catalogoDeServicios, Map<String,
            RespuestaStrategy> estrategiaMap,ReglaNormalizacionUtil reglaNormalizacionUtil,ObjectMapper objectMapper) {

        super(restTemplate, catalogoDeServicios, estrategiaMap);
        this.reglaNormalizacionUtil=reglaNormalizacionUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public ConsultaGeneralClienteResponse consultarCliente(ConsultaGeneralClienteRequest consultaGeneralClienteRequest) {

        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add(ClientesConstantes.NOMBRE_PARAMETRO_CONSULTA_GENERAL_CLIENTE_DOCUMENTO, consultaGeneralClienteRequest.getDocumento());

        ResponseEntity<String> response = ejecutarGetAlESB(getCatalogoDeServicios().getServicioConsultaGeneralCliente(), parametersMap, configurarCabeceraServicio());
        return procesarRespuesta(response, ConsultaGeneralClienteResponse.class);
    }

    @Override
    public ConsultaNormalizacionIndividuo normalizarIndividuoV2(NormalizarIndividuoRequest normalizarIndividuoRequest) {
        MultiValueMap<String, String> parametersMap = construirParametrosDniNormalizacionIndividuo(normalizarIndividuoRequest.getDni());
        agregarParametroMapa(parametersMap, ClientesConstantes.APELLIDO, normalizarIndividuoRequest.getApellido());
        agregarParametroMapa(parametersMap, ClientesConstantes.SEXO, normalizarIndividuoRequest.getSexo());
        return consumirServicioNormalizacionIndividuo(parametersMap);
    }

    /**
     * Crea los parametros con el dni para el consumo del servicio de normalizar individuo
     * @param numeroDocumento
     * @return parametersMap
     */
    private MultiValueMap<String, String> construirParametrosDniNormalizacionIndividuo(String numeroDocumento) {
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add(ClientesConstantes.NOMBRE_PARAMETRO_NORMALIZACION_INDIVIDUO_NUMERODOCUMENTO, numeroDocumento);
        return parametersMap;
    }

    /**
     * Consume y procesa la respuesta del servicio de normalizacion de individuo
     * @param parametersMap
     * @return consultaNormalizacionIndividuo
     */
    private ConsultaNormalizacionIndividuo consumirServicioNormalizacionIndividuo(MultiValueMap<String, String> parametersMap) {
        Map<String, String> headers = configurarCabeceraServicio();
        ResponseEntity<String> response = ejecutarGetAlESB(getCatalogoDeServicios().getServicioMerlinConsultaNormalizacionIndividuo(), parametersMap, headers);
        ConsultaNormalizacionIndividuo respuesta = procesarRespuesta(response, ConsultaNormalizacionIndividuo.class);
        if(respuesta == null || CollectionUtils.isEmpty(respuesta.getIndividuos())) {
            throw new InformacionNoEncontradaException(ErrorCode.CODIGO_ERROR_NORMALIZACION_RESPUESTA_VACIA, ErrorMessage.ERROR_NORMALIZACION_RESPUESTA_VACIA.mensaje());
        }
        return respuesta;
    }

    private <T> MultiValueMap<String, String> crearParametrosRequestGet(T genericObject) {
        @SuppressWarnings("unchecked")
        Map<String, String> parametros = objectMapper.convertValue(genericObject, Map.class);
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametros.forEach(parametersMap::add);
        return parametersMap;
    }

}
