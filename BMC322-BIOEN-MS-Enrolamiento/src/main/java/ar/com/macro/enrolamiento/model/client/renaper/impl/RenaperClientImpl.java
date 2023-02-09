package ar.com.macro.enrolamiento.model.client.renaper.impl;

import ar.com.macro.commons.esb.model.strategy.RespuestaStrategy;
import ar.com.macro.commons.utils.converter.ConvertidorUtil;
import ar.com.macro.commons.utils.props.CatalogoDeServicios;
import ar.com.macro.constant.Errores;
import ar.com.macro.enrolamiento.model.client.esb.impl.ServicioESB;
import ar.com.macro.enrolamiento.model.client.renaper.RenaperClient;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarDNIRenaperClientRequest;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarRostroRenaperClientRequest;
import ar.com.macro.enrolamiento.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;
import ar.com.macro.exceptions.RenaperException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static ar.com.macro.enrolamiento.model.client.esb.rest.dto.utils.ClientesConstantes.CERO;

@Slf4j
@Service
public class RenaperClientImpl extends ServicioESB implements RenaperClient {

    @Value("#{${api.servicios.renaper.ws.dni.header}}")
    private Map<String, String> headersRenaperValidacionPorDNI;

    @Value("#{${api.servicios.renaper.ws.rostro.header}}")
    private Map<String, String> headersRenaperValidacionPorRostro;

    @Value("${api.macro.identificacion.renaper.service.dni.url}")
    private String urlServicioRenaperDNI;

    @Value("${api.macro.identificacion.renaper.service.rostro.url}")
    private String urlServicioRenaperRostro;

    public RenaperClientImpl(
            @Qualifier("restTemplateRenaper") RestTemplate restTemplate, CatalogoDeServicios catalogoDeServicios, Map<String, RespuestaStrategy> estrategiaMap) {
        super(restTemplate, catalogoDeServicios, estrategiaMap);
    }

    @Override
    public Optional<ValidarDNIRenaperClientResponse> validarDni(ValidarDNIRenaperClientRequest validarDNIRenaperClientRequest) {
        return Optional.of(consumirServicioRenaper(urlServicioRenaperDNI, validarDNIRenaperClientRequest,  headersRenaperValidacionPorDNI, ValidarDNIRenaperClientResponse.class));
    }

    public  Optional<ValidarRostroRenaperClientResponse> validarRostro(ValidarRostroRenaperClientRequest validarRostroRenaperClientRequest) {

        Optional<ValidarRostroRenaperClientResponse>  response = Optional.empty();

        response =  Optional.of(consumirServicioRenaper(
                urlServicioRenaperRostro,
                validarRostroRenaperClientRequest,
                headersRenaperValidacionPorRostro,
                ValidarRostroRenaperClientResponse.class));

        if(CERO == response.get().getConfidence()){
            log.error(Errores.ERROR_RENAPER_ERROR_VALIDACION.getMensaje());
            throw new RenaperException(Errores.ERROR_RENAPER_ERROR_VALIDACION.getCodigo(), Errores.ERROR_RENAPER_ERROR_VALIDACION.getMensaje());
        }
        return response;
    }

    private <T> T consumirServicioRenaper(String servicioRenaperAConsumir, Object request, Map<String, String> headersRenaper, Class<T> claseRespuesta) {
        try{
            String requestRenaper = ConvertidorUtil.object2Json(request);
            ResponseEntity<String> respuestaESB = ejecutarPostAlESB(servicioRenaperAConsumir, requestRenaper, headersRenaper);
            return procesarRespuesta(respuestaESB, claseRespuesta);
        } catch (Exception e) {
            log.error(Errores.ERROR_RENAPER_ERROR_VALIDACION.getMensaje(), e);
            throw new RenaperException(Errores.ERROR_RENAPER_ERROR_VALIDACION.getCodigo(), Errores.ERROR_RENAPER_ERROR_VALIDACION.getMensaje());
        }
    }

}
