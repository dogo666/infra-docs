package ar.com.macro.validacion.model.client.daonengine.impl;

import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.validacion.model.client.daonengine.SystemManagerClient;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityRequest;
import ar.com.macro.validacion.model.client.daonengine.dto.soap.GetIdentityResponse;
import ar.com.macro.validacion.model.client.daonengine.header.SoapRequestHeaderModifier;
import ar.com.macro.validacion.model.client.daonengine.mapper.CodigosDeRespuestaObtenerIdentidadDaonEngine;
import ar.com.macro.validacion.model.client.daonengine.mapper.CodigosDeRespuestaObtenerIdentidadVaciosDaonEngine;
import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.ListSummaryAuditsRequest;
import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.ListSummaryAuditsResponse;
import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.SummaryAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

import static ar.com.macro.constant.Errores.*;

@Slf4j
@Service
public class SystemManagerClientImpl implements SystemManagerClient {

    private final String soapActionGetIdentity;

    private final WebServiceTemplate webServiceTemplate;

    public SystemManagerClientImpl(@Value("${daonengine.systemmanagement.listsummaryaudits}") String soapActionGetIdentity,
                                   @Qualifier("daonEngineSystemManagementWebServiceTemplate")WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
        this.soapActionGetIdentity = soapActionGetIdentity;
    }

    @Override
    public Optional<ListSummaryAuditsResponse> obtenerListSummaryAuditsResponse(ListSummaryAuditsRequest listSummaryAuditsRequest){
        ListSummaryAuditsResponse listSummaryAuditsResponse;
        try {
            listSummaryAuditsResponse = (ListSummaryAuditsResponse) marshalSendAndReceive(listSummaryAuditsRequest,soapActionGetIdentity);
        } catch (DaonEngineException e) {
            log.error(ERROR_OBTENER_LIST_SUMMARY_AUDITS.getMensaje(), e);
            throw new DaonEngineException(ERROR_OBTENER_LIST_SUMMARY_AUDITS);
        }

        SummaryAudit summaryAudit = listSummaryAuditsResponse.getResponseData().getSummaryAudit().stream()
                .findFirst().orElseThrow(()->new DaonEngineException(ERROR_OBTENER_LIST_SUMMARY_AUDITS_NOT_MATCH));

        return Optional.of(listSummaryAuditsResponse);
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
