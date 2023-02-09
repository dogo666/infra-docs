package ar.com.macro.validacion.model.service.impl;

import ar.com.macro.commons.values.constants.config.TraceConstants;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ConsultarValidacionHuellaPersonaRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ConsultarValidacionHuellaPersonaResponse;
import ar.com.macro.validacion.model.client.daonengine.SystemManagerClient;
import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.*;
import ar.com.macro.validacion.model.service.DatosGeneralesService;
import ar.com.macro.validacion.model.service.HuellaDaonEngineService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RefreshScope
@Slf4j
@Service
public class HuellaDaonEngineServiceImpl implements HuellaDaonEngineService {

    private final SystemManagerClient systemManagerClient;

    private final String applicationUserIdentifier;

    private final String defaultDomainIdentifier;

    private final String orderbyfield;

    private final Integer startoffset;

    private final Integer numbertoreturn;

    private final String returnsetorder;

    private final DatosGeneralesService datosGeneralesService;

    private final String nombreMicroServicioDatosGenerales;

    private final String nombreConfiguracionUmbralPoliticas;

    private final ObjectFactory objectFactory = new ObjectFactory();

    public HuellaDaonEngineServiceImpl(SystemManagerClient systemManagerClient,
                                       @Value("${daonengine.useridentifier}") String applicationUserIdentifier,
                                       @Value("${daonengine.domainidentifier:9002}") String defaultDomainIdentifier,
                                       @Value("${daonengine.systemmanagement.orderbyfield:CREATED_DTM}") String orderbyfield,
                                       @Value("${daonengine.systemmanagement.startoffset:0}") Integer startoffset,
                                       @Value("${daonengine.systemmanagement.numbertoreturn:100}") Integer numbertoreturn,
                                       @Value("${daonengine.systemmanagement.returnsetorder:ASC}") String returnsetorder,
                                       @Value("${daonengine.nombre.parametros.datos.generales}") String nombreMicroServicioDatosGenerales,
                                       @Value("${daonengine.nombre.parametros.configuracion.umbral.politica}") String nombreConfiguracionUmbralPoliticas,
                                       DatosGeneralesService datosGeneralesService) {

        this.systemManagerClient = systemManagerClient;
        this.applicationUserIdentifier = applicationUserIdentifier;
        this.defaultDomainIdentifier = defaultDomainIdentifier;
        this.orderbyfield = orderbyfield;
        this.startoffset = startoffset;
        this.numbertoreturn = numbertoreturn;
        this.returnsetorder = returnsetorder;
        this.datosGeneralesService = datosGeneralesService;
        this.nombreMicroServicioDatosGenerales = nombreMicroServicioDatosGenerales;
        this.nombreConfiguracionUmbralPoliticas = nombreConfiguracionUmbralPoliticas;

    }

    public ConsultarValidacionHuellaPersonaResponse consultarTrackingUID(ConsultarValidacionHuellaPersonaRequest enrolarHuellaDaonEngineRequest, String xOperationId) {

        String xApplicationId = MDC.get(TraceConstants.APPLICATION);

        ListSummaryAuditsRequest listSummaryAuditsRequest = objectFactory.createListSummaryAuditsRequest();
        GenericRequestParameters genericRequestParameters = objectFactory.createGenericRequestParameters();
        ReturnSetCriteria returnSetCriteria = objectFactory.createReturnSetCriteria();
        DomainIdentifier domainIdentifier = objectFactory.createDomainIdentifier();
        SummaryAuditSearchCriteria summaryAuditSearchCriteria = objectFactory.createSummaryAuditSearchCriteria();

        listSummaryAuditsRequest.setGenericRequestParameters(genericRequestParameters);
        listSummaryAuditsRequest.getGenericRequestParameters().setApplicationIdentifier(xApplicationId);
        listSummaryAuditsRequest.getGenericRequestParameters().setApplicationUserIdentifier(applicationUserIdentifier);

        returnSetCriteria.setStartOffset(startoffset);
        returnSetCriteria.setNumberToReturn(numbertoreturn);
        listSummaryAuditsRequest.setReturnSetCriteria(returnSetCriteria);

        listSummaryAuditsRequest.setReturnSetOrder(ReturnSetOrder.fromValue(returnsetorder));
        listSummaryAuditsRequest.getOrderByField().add(SummaryAuditOrderByField.fromValue(orderbyfield));

        domainIdentifier.setValue(defaultDomainIdentifier);
        listSummaryAuditsRequest.setDomainIdentifier(domainIdentifier);

        summaryAuditSearchCriteria.setRequestCorrelationId(enrolarHuellaDaonEngineRequest.getIdrequesttracking());
        listSummaryAuditsRequest.setSummaryAuditSelectionCriteria(summaryAuditSearchCriteria);

        Optional<ListSummaryAuditsResponse> listSummaryAuditsResponse = systemManagerClient.obtenerListSummaryAuditsResponse(listSummaryAuditsRequest);

        Double umbralPolitica = datosGeneralesService.obtenerConfiguracionUmbralPoliticaDaonEngine(xOperationId, nombreMicroServicioDatosGenerales, nombreConfiguracionUmbralPoliticas);

        return new ConsultarValidacionHuellaPersonaResponse(listSummaryAuditsResponse, umbralPolitica);
    }
}
