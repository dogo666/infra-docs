package ar.com.macro.validacion.model.service;

import java.util.Optional;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.ValidarHuellaDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.request.CompararHuellasDaonEngineRequest;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.ValidarHuellaDaonEngineResponse;
import ar.com.macro.validacion.domain.daonengine.rest.dto.response.CompararHuellasDaonEngineResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.EliminarPerfilUsuarioRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.LeerPdf417DniResponse;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.request.CrearHashRequest;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.validacion.domain.identidad.rest.dto.response.CrearHashResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ActualizarRostroRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarDatosDniRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.EnrolarRostroRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarDatosDniResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.BothSidesDocumentRequest;

public interface EnrolamientoService {

    String obtenerNombreMicroservicio();

    CrearPerfilResponse crearPerfilUsuario(CrearPerfilRequest crearPerfilRequest);

    void eliminarPerfilUsuario (EliminarPerfilUsuarioRequest eliminarPerfilUsuarioRequest);

    ConsultarIdentidadResponse consultarNormalizacionIndividuos(ConsultarIdentidadRequest consultarIdentidadRequest);

    void actualizarRostro(ActualizarRostroRequest actualizarRostroRequest);

    LeerPdf417DniResponse leerPdf417Dni(BothSidesDocumentRequest bothSidesDocumentRequest);

    void enrolarRostro(EnrolarRostroRequest enrolarRostroRequest, String xOperationID);

    ConsultarDatosDniResponse consultarDatosDni(ConsultarDatosDniRequest consultarDatosDniRequest);

    ConsultarEnrolamientoResponse consultarEnrolamiento(ConsultarEnrolamientoRequest consultarEnrolamientoRequest, String xOperationID);
    
    ValidarHuellaDaonEngineResponse validarHuellaDaon(ValidarHuellaDaonEngineRequest validarHuellaDaonEngineRequest, String xOperationID);

    Optional<CrearHashResponse> crearHashCon256(CrearHashRequest crearHashRequest);
    
    CompararHuellasDaonEngineResponse validarHuellasUnoaPocosDaon(CompararHuellasDaonEngineRequest request, String xOperationID);
}
