package ar.com.macro.validacion.model.client.esb.impl;


import ar.com.macro.commons.constants.ErrorCode;
import ar.com.macro.commons.esb.model.strategy.ProcesadorRespuesta;
import ar.com.macro.commons.esb.model.strategy.RespuestaStrategy;
import ar.com.macro.commons.exceptions.ComunicacionFallidaException;

import ar.com.macro.commons.utils.business.RespuestaHttp;
import ar.com.macro.commons.utils.components.ResponseHandlerRequestClient;
import ar.com.macro.commons.utils.converter.ConvertidorUtil;
import ar.com.macro.commons.utils.props.CatalogoDeServicios;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.commons.values.constants.error.ErrorMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Primary
@Component("validacionServicioESB")
@Slf4j
@RequiredArgsConstructor
public class ServicioESB {

    protected final RestTemplate restTemplate;

    protected final CatalogoDeServicios catalogoDeServicios;

    protected final Map<String, RespuestaStrategy> estrategiaMap;

    @PostConstruct
    public void initBean() {
        this.restTemplate.setErrorHandler(new ResponseHandlerRequestClient());
    }

    public ResponseEntity<String> get(String servicio, MultiValueMap<String, String> parameters, Map<String, String> headers) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(servicio).queryParams(parameters).build();
        HttpHeaders httpHeaders = this.generarCabeceras(headers);
        return this.invocarServicio(uriComponents.toString(), HttpMethod.GET, httpHeaders != null ? new HttpEntity(httpHeaders) : new HttpEntity(new HttpHeaders()));
    }

    public ResponseEntity<String> post(String servicio, String body, Map<String, String> headers) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(servicio);
        HttpHeaders httpHeaders = this.generarCabeceras(headers);
        return this.invocarServicio(uriComponentsBuilder.build().toString(), HttpMethod.POST, httpHeaders == null ? new HttpEntity(body) : new HttpEntity(body, httpHeaders));
    }

    public ResponseEntity<String> put(String servicio, String body, Map<String, String> headers) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(servicio);
        HttpHeaders httpHeaders = this.generarCabeceras(headers);
        return this.invocarServicio(uriComponentsBuilder.build().toString(), HttpMethod.PUT, httpHeaders == null ? new HttpEntity(body) : new HttpEntity(body, httpHeaders));
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

    private ResponseEntity<String> invocarServicio(String uri, HttpMethod method, HttpEntity<?> entidadHttp) {
        RespuestaHttp respuestaHttp = null;
        ResponseEntity<String> response = null;
        Exception ex = null;
        long start = 0L;
        long duration = 0L;

        ResponseEntity var12;
        try {
            start = System.currentTimeMillis();
            MDC.put("ESBEndpointDuration", "");
            log.info("Request ESB url: {}, Request ESB body: {}, Request ESB headers: {}", uri, this.getCuerpoComoCadena(entidadHttp), entidadHttp.getHeaders());
            response = this.restTemplate.exchange(uri, method, entidadHttp, String.class);
            duration = System.currentTimeMillis() - start;
            log.info("Response ESB url: {}, Response ESB duracion: {} ms, {}", uri, duration, duration);
            if (response == null || response.getStatusCode() == null) {
                throw new ComunicacionFallidaException(ErrorCode.CODIGO_GENERAL_ESB_NO_DISPONIBLE, ErrorMessage.ESB_NO_DISPONIBLE.mensaje());
            }

            respuestaHttp = RespuestaHttp.builder().httpCodigo(response.getStatusCodeValue()).httpDescripcion(response.getStatusCode().getReasonPhrase()).build();
            log.info("Response ESB headers: {}", response.getHeaders());
            ResponseEntity var11 = response;
            return var11;
        } catch (HttpServerErrorException var23) {
            ex = var23;
            duration = System.currentTimeMillis() - start;
            log.info("Response ESB HttpServerErrorException url: {}, duracion: {} ms, {}", uri, duration, duration);
            respuestaHttp = RespuestaHttp.builder().httpCodigo(var23.getRawStatusCode()).httpDescripcion(var23.getStatusText()).build();
            var12 = new ResponseEntity(var23.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResourceAccessException var24) {
            ex = var24;
            duration = System.currentTimeMillis() - start;
            log.info("Response ESB ResourceAccessException url: {}, duracion: {} ms, {}", uri, duration, duration);
            throw new ComunicacionFallidaException(ErrorCode.CODIGO_GENERAL_ESB_NO_DISPONIBLE, var24.getMessage(), var24);
        } finally {
            MDC.put("ESBEndpointDuration", "" + duration);
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

    protected void agregarParametroMapa(MultiValueMap<String, String> map, String key, String value) {
        Assert.notNull(map, ErrorMessage.MAPA_PARAMETROS_ES_VACIO.mensaje());
        if (value != null && !value.isEmpty()) {
            map.add(key, value);
        }

    }

    public Map<String, String> configurarCabeceraServicio() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HeaderConstants.HEADER_X_AUTHORIZATION_IMPERSONATIONLEVEL, HeaderConstants.HEADER_X_AUTHORIZATION_IMPERSONATION_LEVEL_VALUE_SERVICE);
        return headers;
    }

    public Map<String, String> headerApplication() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HeaderConstants.HEADER_X_AUTHORIZATION_IMPERSONATIONLEVEL, HeaderConstants.HEADER_X_AUTHORIZATION_IMPERSONATION_LEVEL_VALUE_APPLICATION);
        headers.put(HeaderConstants.HEADER_X_APPLICATION_USER_PRINCIPAL_NAME,catalogoDeServicios.getUsuarioNivelImpersonalizacionApplication());
        return headers;
    }

    public CatalogoDeServicios getCatalogoDeServicios() {
        return this.catalogoDeServicios;
    }

    private ProcesadorRespuesta procesarRespuestaConsumoServicio(ResponseEntity<String> respuesta) {
        return respuesta != null ? new ProcesadorRespuesta(this.estrategiaMap.get(String.valueOf(respuesta.getStatusCodeValue()))) : new ProcesadorRespuesta(this.estrategiaMap.get("NUll"));
    }

    public <T> T procesarRespuesta(ResponseEntity<String> response, Class<?> aClass) {
        ProcesadorRespuesta procesadorRespuesta = this.procesarRespuestaConsumoServicio(response);
        return procesadorRespuesta.validarRespuestaConsumoServicioESB(response, aClass);
    }

    protected String procesarEstadoRespuesta(ResponseEntity<String> response) {
        ProcesadorRespuesta procesadorRespuesta = this.procesarRespuestaConsumoServicio(response);
        return procesadorRespuesta.validaEstadoRespuesta(response);
    }

    public ResponseEntity<String> ejecutarPostAlESB(String servicio, String parameters, Map<String, String> headers) {
        ResponseEntity responseEntity = null;

        try {
            responseEntity = ((ServicioESB)AopContext.currentProxy()).post(servicio, parameters, headers);
        } catch (ClassCastException | IllegalStateException var6) {
            responseEntity = this.post(servicio, parameters, headers);
        }

        return responseEntity;
    }

    public ResponseEntity<String> ejecutarGetAlESB(String servicio, MultiValueMap<String, String> parameters, Map<String, String> headers) {
        ResponseEntity responseEntity = null;

        try {
            responseEntity = ((ServicioESB)AopContext.currentProxy()).get(servicio, parameters, headers);
        } catch (ClassCastException | IllegalStateException var6) {
            responseEntity = this.get(servicio, parameters, headers);
        }

        return responseEntity;
    }

    public ResponseEntity<String> ejecutarPutAlESB(String servicio, String body, Map<String, String> headers) {
        ResponseEntity responseEntity = null;

        try {
            responseEntity = ((ServicioESB)AopContext.currentProxy()).put(servicio, body, headers);
        } catch (ClassCastException | IllegalStateException var6) {
            responseEntity = this.put(servicio, body, headers);
        }

        return responseEntity;
    }
}