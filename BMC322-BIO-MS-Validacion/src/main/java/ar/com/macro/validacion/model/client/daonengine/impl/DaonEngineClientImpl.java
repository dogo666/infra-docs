package ar.com.macro.validacion.model.client.daonengine.impl;

import ar.com.macro.validacion.model.client.daonengine.DaonEngineClient;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityRequest;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityResponse;
import ar.com.macro.validacion.model.client.daonengine.mapper.CodigosDeRespuestaObtenerIdentidadDaonEngine;
import ar.com.macro.validacion.model.client.daonengine.header.SoapRequestHeaderModifier;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyRequest;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResponse;
import ar.com.macro.validacion.model.client.daonengine.mapper.CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine;
import ar.com.macro.validacion.model.client.daonengine.mapper.CodigosDeRespuestaVerificarIdentificacionDaonEngine;
import ar.com.macro.commons.values.constants.text.CharConstants;
import ar.com.macro.exceptions.DaonEngineException;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

import static ar.com.macro.constant.Errores.*;

@Slf4j
@Service
public class DaonEngineClientImpl implements DaonEngineClient {

    private final String soapActionGetIdentity;

    private final WebServiceTemplate webServiceTemplate;
    
    private String soapActionVerifyIdentification;
    
    private WebServiceTemplate identificationWebServiceTemplate;

    public DaonEngineClientImpl(
            @Qualifier("daonEngineWebServiceTemplate")WebServiceTemplate webServiceTemplate,
            @Value("${daonengine.soapaction.getidentity}") String soapActionGetIdentity,
            @Qualifier("daonEngineIdentificationWebServiceTemplate")WebServiceTemplate identificationWebServiceTemplate,
            @Value("${daonengine.soapaction.identification.verify}")String soapActionVerifyIdentification
    ) {
        this.webServiceTemplate = webServiceTemplate;
        this.soapActionGetIdentity = soapActionGetIdentity;
        this.identificationWebServiceTemplate = identificationWebServiceTemplate;
        this.soapActionVerifyIdentification = soapActionVerifyIdentification;
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

    private Object marshalSendAndReceive(Object objectRequest,String soapAction){
        try{
            return webServiceTemplate.marshalSendAndReceive(objectRequest,new SoapRequestHeaderModifier(soapAction));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new DaonEngineException(e.hashCode(),e.getMessage());
        }

    }
    
    private Object identificacionMarshalSendAndReceive(Object objectRequest,String soapAction){
        try{
            return identificationWebServiceTemplate.marshalSendAndReceive(objectRequest,new SoapRequestHeaderModifier(soapAction));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new DaonEngineException(e.hashCode(),e.getMessage());
        }

    }

	@Override
	public Optional<VerifyResponse> verificarIdentificacion(VerifyRequest verifyRequest) {
		VerifyResponse verifyResponse = null;
		try {
			verifyResponse = (VerifyResponse) identificacionMarshalSendAndReceive(verifyRequest, soapActionVerifyIdentification);
        } catch (DaonEngineException e) {
            log.error(ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getMensaje(), e);
            throw new DaonEngineException(ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getCodigo(), ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getMensaje());
        }
        if(verifyResponse.getResponseStatus().getReturnCode() != 0){
            
        	var codigo = CodigosDeRespuestaVerificarIdentificacionDaonEngine.buscarPorCodigo(verifyResponse.getResponseStatus().getReturnCode());
        	log.error(codigo.getMessage());
        	if(codigo.code == CodigosDeRespuestaVerificarIdentificacionDaonEngine.CODIGO_DESCONOCIDO.getCode()) {
        		log.error(verifyResponse.getResponseStatus().getReturnCode() + CharConstants.CARACTER_ESPACIO_BLANCO + verifyResponse.getResponseStatus().getMessage());
            	throw new DaonEngineException(
            			ERROR_VERIFICAR_IDENTIFICACION_DAON_ENGINE.getCodigo(),
            			codigo.getMessage()
            			);
        	}        	
        }
        return Optional.of(verifyResponse);
	}

}
