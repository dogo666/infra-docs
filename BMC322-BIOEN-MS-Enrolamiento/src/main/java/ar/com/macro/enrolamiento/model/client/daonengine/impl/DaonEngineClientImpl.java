package ar.com.macro.enrolamiento.model.client.daonengine.impl;

import ar.com.macro.enrolamiento.model.client.daonengine.DaonEngineClient;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GetIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.GetIdentityResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.mapper.CodigosDeRespuestaActualizardentidadDaonEngine;
import ar.com.macro.enrolamiento.model.client.daonengine.mapper.CodigosDeRespuestaCrearIdentidadDaonEngine;
import ar.com.macro.enrolamiento.model.client.daonengine.mapper.CodigosDeRespuestaObtenerIdentidadDaonEngine;
import ar.com.macro.enrolamiento.model.client.daonengine.header.SoapRequestHeaderModifier;
import ar.com.macro.enrolamiento.model.client.daonengine.mapper.CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine;
import ar.com.macro.exceptions.DaonEngineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

import static ar.com.macro.constant.Errores.*;

@Slf4j
@Service
public class DaonEngineClientImpl implements DaonEngineClient {

    private String soapActionGetIdentity;

    private String soapActionCreateIdentity;

    private String soapActionUpdateIdentity;

    private WebServiceTemplate webServiceTemplate;

    public DaonEngineClientImpl(
            @Qualifier("daonEngineWebServiceTemplate")WebServiceTemplate webServiceTemplate,
            @Value("${daonengine.soapaction.getidentity}") String soapActionGetIdentity,
            @Value("${daonengine.soapaction.createidentity}") String soapActionCreateIdentity,
            @Value("${daonengine.soapaction.updateidentity}")String soapActionUpdateIdentity
    ) {
        this.webServiceTemplate = webServiceTemplate;
        this.soapActionGetIdentity = soapActionGetIdentity;
        this.soapActionCreateIdentity = soapActionCreateIdentity;
        this.soapActionUpdateIdentity = soapActionUpdateIdentity;
    }

    @Override
    public Optional<GetIdentityResponse> obtenerIdentidad(GetIdentityRequest getIdentityRequest) {
        GetIdentityResponse getIdentityResponse = null;
        try {
            getIdentityResponse = (GetIdentityResponse) marshalSendAndReceive(getIdentityRequest,soapActionGetIdentity);
        } catch (DaonEngineException e) {
            log.error(ERROR_OBTENER_IDENTIDAD_DAON_ENGINE.getMensaje(), e);
            throw new DaonEngineException(ERROR_OBTENER_IDENTIDAD_DAON_ENGINE.getCodigo(),ERROR_OBTENER_IDENTIDAD_DAON_ENGINE.getMensaje());
        }
        if(getIdentityResponse.getResponseStatus().getReturnCode() != 0){
            if(CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine.buscarPorCodigo(getIdentityResponse.getResponseStatus().getReturnCode())){
                return Optional.empty();
            } else {
                var codigo = CodigosDeRespuestaObtenerIdentidadDaonEngine.buscarPorCodigo(getIdentityResponse.getResponseStatus().getReturnCode());
                log.error(codigo.getMessage());
                throw new DaonEngineException(
                        ERROR_OBTENER_IDENTIDAD_DAON_ENGINE.getCodigo(),
                        codigo.getMessage()
                );
            }
        }
        return Optional.of(getIdentityResponse);
    }

    @Override
    public CreateIdentityResponse crearIdentidad(CreateIdentityRequest createIdentityRequest) {
        CreateIdentityResponse createIdentityResponse = null;
        try {
            createIdentityResponse = (CreateIdentityResponse) marshalSendAndReceive(createIdentityRequest,soapActionCreateIdentity);
        } catch (DaonEngineException e) {
            log.error(ERROR_CREAR_IDENTIDAD_DAON_ENGINE.getMensaje(), e);
            throw new DaonEngineException(ERROR_CREAR_IDENTIDAD_DAON_ENGINE.getCodigo(),ERROR_CREAR_IDENTIDAD_DAON_ENGINE.getMensaje());
        }
        if(createIdentityResponse.getResponseStatus().getReturnCode() != 0){
            log.error(CodigosDeRespuestaCrearIdentidadDaonEngine.buscarPorCodigo(createIdentityResponse.getResponseStatus().getReturnCode()).get().message);
            throw new DaonEngineException(
                    ERROR_CREAR_IDENTIDAD_DAON_ENGINE.getCodigo(),
                    CodigosDeRespuestaCrearIdentidadDaonEngine.buscarPorCodigo(createIdentityResponse.getResponseStatus().getReturnCode()).get().message
            );
        }
        return createIdentityResponse;
    }

    @Override
    public UpdateIdentityResponse actualizarIdentidad(UpdateIdentityRequest updateIdentityRequest) {
        UpdateIdentityResponse updateIdentityResponse = null;
        try {
            updateIdentityResponse = (UpdateIdentityResponse) marshalSendAndReceive(updateIdentityRequest,soapActionUpdateIdentity);
        } catch (DaonEngineException e) {
            log.error(ERROR_ACTUALIZAR_IDENTIDAD_DAON_ENGINE.getMensaje(), e);
            throw new DaonEngineException(ERROR_ACTUALIZAR_IDENTIDAD_DAON_ENGINE.getCodigo(),ERROR_ACTUALIZAR_IDENTIDAD_DAON_ENGINE.getMensaje());
        }
        if(updateIdentityResponse.getResponseStatus().getReturnCode() != 0){
            var codigo = CodigosDeRespuestaActualizardentidadDaonEngine.buscarPorCodigo(updateIdentityResponse.getResponseStatus().getReturnCode());
            log.error(codigo.getMessage());
            throw new DaonEngineException(
                    ERROR_ACTUALIZAR_IDENTIDAD_DAON_ENGINE.getCodigo(),
                    codigo.getMessage()
            );
        }
        return updateIdentityResponse;
    }

    private Object marshalSendAndReceive(Object objectRequest,String soapAction){
        try{
            return webServiceTemplate.marshalSendAndReceive(objectRequest,new SoapRequestHeaderModifier(soapAction));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new DaonEngineException(e.hashCode(),e.getMessage());
        }

    }

}
