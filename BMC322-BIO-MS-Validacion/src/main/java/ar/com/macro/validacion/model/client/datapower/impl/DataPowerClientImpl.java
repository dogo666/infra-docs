package ar.com.macro.validacion.model.client.datapower.impl;

import ar.com.macro.validacion.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.validacion.model.client.datapower.DataPowerClient;
import ar.com.macro.validacion.model.client.datapower.dto.rest.dto.consulta.identificacion.ConsultaIdentificacionClienteResponse;
import ar.com.macro.validacion.model.client.datapower.dto.rest.dto.utils.DataPowerConstantes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class DataPowerClientImpl extends ServicioDataPower implements DataPowerClient {

    private final String tipoCliente;
    private final String uriDataPower;
    
    private final static String TIPO_CLIENTE_VALOR_I = "I";

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
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add(DataPowerConstantes.NUMERO_DOCUMENTO_LABEL, consultarIdentidadRequest.getDni());
        agregarParametroMapa(parametersMap, DataPowerConstantes.GENERO_LABEL, consultarIdentidadRequest.getSexo());
        agregarParametroMapa(parametersMap, DataPowerConstantes.TIPO_CLIENTE_LABEL, TIPO_CLIENTE_VALOR_I);
        ResponseEntity<String> response = ejecutarGetDataPower(uriDataPower, parametersMap, configurarCabeceraServicio());
        return procesarRespuesta(response, ConsultaIdentificacionClienteResponse.class);
    }
}
