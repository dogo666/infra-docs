package ar.com.macro.validacion.model.client.datapower.impl;

import ar.com.macro.commons.exceptions.ComunicacionFallidaException;
import ar.com.macro.commons.exceptions.InformacionNoEncontradaException;
import ar.com.macro.commons.utils.business.RespuestaHttp;
import ar.com.macro.commons.utils.converter.ConvertidorUtil;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import ar.com.macro.constant.Errores;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Component("enrolamientoServicioDataPower")
@Slf4j
@RequiredArgsConstructor
public class ServicioDataPower {

    protected final RestTemplate restTemplate;

    /**
    * Metodos publicos de entrada
    */

    /**
     * Da acceso a al uso del metodo Get para DataPower
     * @param servicio, parameters, headers
     * @return ResponseEntity
     */
    public ResponseEntity<String> ejecutarGetDataPower(String servicio, MultiValueMap<String, String> parameters, Map<String, String> headers) {
        ResponseEntity responseEntity = null;

        try {
            responseEntity = ((ServicioDataPower) AopContext.currentProxy()).get(servicio, parameters, headers);
        } catch (ClassCastException | IllegalStateException var6) {
            responseEntity = this.get(servicio, parameters, headers);
        }

        return responseEntity;
    }

    /**
     * Metodos de Invocación al servicio
     */

    public ResponseEntity<String> get(String servicio, MultiValueMap<String, String> parameters, Map<String, String> headers) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(servicio).queryParams(parameters).build();
        HttpHeaders httpHeaders = this.generarCabeceras(headers);
        return this.invocarServicio(uriComponents.toString(), HttpMethod.GET, httpHeaders != null ? new HttpEntity(httpHeaders) : new HttpEntity(new HttpHeaders()));
    }

    private ResponseEntity<String> invocarServicio(String uri, HttpMethod method, HttpEntity<?> entidadHttp) {
        RespuestaHttp respuestaHttp = null;
        ResponseEntity<String> response = null;
        Exception ex = null;
        long start = 0L;
        long duration = 0L;

        ResponseEntity var12;
        try {
            start = System.currentTimeMillis();
            MDC.put("DataPowerEndpointDuration", "");
            log.info("Request DataPower url: {}, Request DataPower body: {}, Request DataPower headers: {}", uri, this.getCuerpoComoCadena(entidadHttp), entidadHttp.getHeaders());
            response = this.restTemplate.exchange(uri, method, entidadHttp, String.class);
            duration = System.currentTimeMillis() - start;
            log.info("Response DataPower url: {}, Response DataPower duracion: {} ms, {}", uri, duration, duration);
            if (response == null || response.getStatusCode() == null) {
                throw new ComunicacionFallidaException(Errores.DATAPOWER_NO_DISPONIBLE.getCodigo(),Errores.DATAPOWER_NO_DISPONIBLE.getMensaje());
            }

            respuestaHttp = RespuestaHttp.builder().httpCodigo(response.getStatusCodeValue()).httpDescripcion(response.getStatusCode().getReasonPhrase()).build();
            log.info("Response DataPower headers: {}", response.getHeaders());
            ResponseEntity var11 = response;
            return var11;
        } catch (HttpClientErrorException cee) {
            ex = cee;
            duration = System.currentTimeMillis() - start;
            log.info("Response DataPower HttpClientErrorException url: {}, duracion: {} ms, {}", uri, duration, duration);
            throw new InformacionNoEncontradaException(Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getCodigo(), Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getMensaje(), cee);
        } catch (HttpServerErrorException var23) {
            ex = var23;
            log.error(var23.getMessage());
            duration = System.currentTimeMillis() - start;
            log.info("Response DataPower HttpServerErrorException url: {}, duracion: {} ms, {}", uri, duration, duration);
            respuestaHttp = RespuestaHttp.builder().httpCodigo(var23.getRawStatusCode()).httpDescripcion(var23.getStatusText()).build();
            var12 = new ResponseEntity(var23.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResourceAccessException var24) {
            ex = var24;
            duration = System.currentTimeMillis() - start;
            log.info("Response DataPower ResourceAccessException url: {}, duracion: {} ms, {}", uri, duration, duration);
            throw new ComunicacionFallidaException(Errores.DATAPOWER_NO_DISPONIBLE.getCodigo(), var24.getMessage(), var24);
        } catch (Exception e) {
            throw new ComunicacionFallidaException(Errores.DATAPOWER_NO_DISPONIBLE.getCodigo(), Errores.DATAPOWER_NO_DISPONIBLE.getMensaje());
        } finally {
            MDC.put("DataPowerEndpointDuration", "" + duration);
        }

        return var12;
    }

    private HttpHeaders generarCabeceras(Map<String, String> cabeceras) {
        if (cabeceras != null && !cabeceras.isEmpty()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            cabeceras.entrySet().parallelStream().forEach((entry) -> {
                httpHeaders.add(entry.getKey(), entry.getValue());
            });
            return httpHeaders;
        } else {
            return null;
        }
    }

    private String getCuerpoComoCadena(HttpEntity<?> entidadHttp) {
        String cuerpoRequest = "";
        if (entidadHttp.getBody() != null) {
            cuerpoRequest = Stream.of(entidadHttp.getBody().toString().split("\"")).map((elemento) -> {
                return ConvertidorUtil.reconocerBase64(elemento) ? "base64" : elemento;
            }).collect(Collectors.joining("\""));
        }

        return cuerpoRequest;
    }

    /**
     * Metodos utilitarios del servicio
     */

    public Map<String, String> configurarCabeceraServicio() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HeaderConstants.HEADER_X_AUTHORIZATION_IMPERSONATIONLEVEL, HeaderConstants.HEADER_X_AUTHORIZATION_IMPERSONATION_LEVEL_VALUE_SERVICE);
        return headers;
    }

    protected void agregarParametroMapa(MultiValueMap<String, String> map, String key, String value) {
        Assert.notNull(map, ErrorMessage.MAPA_PARAMETROS_ES_VACIO.mensaje());
        if (value != null && !value.isEmpty()) {
            map.add(key, value);
        }
    }

    public <T> T procesarRespuesta(ResponseEntity<String> response, Class<?> aClass) {
        log.info(response.getBody());
        try {
            if (response != null){
                return ConvertidorUtil.convertirJsonToClass(response.getBody(), aClass);
            } else {
                throw new ComunicacionFallidaException(Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getCodigo(),Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getMensaje());
            }
        } catch (Exception e) {
            throw new ComunicacionFallidaException(Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getCodigo(),Errores.DATAPOWER_INFORMACION_NO_ENCONTRADA.getMensaje());
        }
    }
}
