package ar.com.macro.enrolamiento.model.client.datapower.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import ar.com.macro.enrolamiento.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.enrolamiento.model.client.datapower.DataPowerClient;
import ar.com.macro.enrolamiento.model.client.datapower.dto.rest.dto.consulta.identificacion.ConsultaIdentificacionClienteResponse;
import ar.com.macro.enrolamiento.model.client.datapower.dto.rest.dto.utils.DataPowerConstantes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataPowerClientImpl extends ServicioDataPower implements DataPowerClient {

    private String tipoCliente;
    private String uriDataPower;

    public DataPowerClientImpl(
            @Qualifier("dataPowerRestTemplate") RestTemplate restTemplate,
            @Value("${datapower.uri}") String uriDataPower,
            @Value("${datapower.consulta.identidad.tipocliente}") String tipoCliente
    ) {
        super(restTemplate);
        this.uriDataPower = uriDataPower;
        this.tipoCliente = tipoCliente;
    }

    /**
     * Consulta la identificacion del cliente
     * @param consultarIdentidadRequest
     * @return consultaIdentificacionClienteResponse
     */
    @Override
    public ConsultaIdentificacionClienteResponse consultarIdentificacionCliente(ConsultarIdentidadRequest consultarIdentidadRequest){
        log.debug("Consultando identificacion del cliente con dni {} y sexo {}", 
                consultarIdentidadRequest.getDni(), consultarIdentidadRequest.getSexo());
        var parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add(DataPowerConstantes.NUMERO_DOCUMENTO_LABEL, consultarIdentidadRequest.getDni());
        agregarParametroMapa(parametersMap, DataPowerConstantes.GENERO_LABEL, consultarIdentidadRequest.getSexo());
        agregarParametroMapa(parametersMap, DataPowerConstantes.TIPO_CLIENTE_LABEL, tipoCliente);
        var response = ejecutarGetDataPower(uriDataPower, parametersMap, configurarCabeceraServicio());
        return procesarRespuesta(response, ConsultaIdentificacionClienteResponse.class);
    }
}
