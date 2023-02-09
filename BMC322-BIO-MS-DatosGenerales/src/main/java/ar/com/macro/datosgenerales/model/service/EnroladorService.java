package ar.com.macro.datosgenerales.model.service;

import java.util.List;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladorRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.ConsultarEnroladoresRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.request.GuardarEnroladorRequest;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladorResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.ConsultarEnroladoresResponse;
import ar.com.macro.datosgenerales.domain.enrolador.rest.dto.response.GuardarEnroladorResponse;

public interface EnroladorService {

    ConsultarEnroladorResponse consultarDatosEnrolador(ConsultarEnroladorRequest consultarEnroladorRequest);
    
    GuardarEnroladorResponse guardarDatosEnrolador(List<GuardarEnroladorRequest> guardarEnroladorRequest);

    ConsultarEnroladoresResponse getUsuariosEnrolador(ConsultarEnroladoresRequest consultarEnroladoresRequest);
    
}
