package ar.com.macro.datosgenerales.model.service.impl;

import ar.com.macro.commons.utils.generator.IdentifierUtil;
import ar.com.macro.datosgenerales.domain.operacion.rest.dto.response.CrearOperationIdResponse;
import ar.com.macro.datosgenerales.model.service.OperacionService;
import org.springframework.stereotype.Service;

@Service
public class OperacionServiceImpl implements OperacionService {

    @Override
    public CrearOperationIdResponse crearOperationId() {
        return new CrearOperationIdResponse(IdentifierUtil.newUUIDCode().toUpperCase());
    }
}
