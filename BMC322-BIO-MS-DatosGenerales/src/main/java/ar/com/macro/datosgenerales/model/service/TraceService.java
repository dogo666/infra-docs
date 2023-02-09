package ar.com.macro.datosgenerales.model.service;

import ar.com.macro.datosgenerales.domain.traza.rest.dto.request.GuardarTrazaRequest;

public interface TraceService {
    void guardar(GuardarTrazaRequest guardarTrazaRequest);

    void guardarOutboundIdentityX(GuardarTrazaRequest trazaRequest);
}
