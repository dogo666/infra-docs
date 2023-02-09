package ar.com.macro.enrolamiento.model.service;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.GuardarTrazasDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.ActualizarHuellaDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.GuardarTrazasDaonEngineResponse;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.response.EnrolarHuellaDaonEngineResponse;

public interface HuellaDaonEngineService {

  EnrolarHuellaDaonEngineResponse enrolarHuellaDaonEngine(
      EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest, String xOperationID);

  ActualizarHuellaDaonEngineResponse actualizarHuellaDaonEngine(
      ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest, String xOperationId);

  GuardarTrazasDaonEngineResponse guardarTrazasDaonEngine(
      GuardarTrazasDaonEngineRequest request, String xOperationId);
}
