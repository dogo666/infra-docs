package ar.com.macro.validacion.model.service;

import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ConsultarValidacionHuellaPersonaRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ConsultarValidacionHuellaPersonaResponse;

public interface HuellaDaonEngineService {

    ConsultarValidacionHuellaPersonaResponse consultarTrackingUID(ConsultarValidacionHuellaPersonaRequest enrolarHuellaDaonEngineRequest, String xOperationId);
}
