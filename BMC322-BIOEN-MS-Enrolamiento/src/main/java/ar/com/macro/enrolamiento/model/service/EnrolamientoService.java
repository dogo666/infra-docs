package ar.com.macro.enrolamiento.model.service;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.EliminarPerfilUsuarioRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.LeerPdf417DniResponse;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ActualizarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ConsultarDatosDniRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.EnrolarRostroRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ConsultarDatosDniResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.EnrolarRostroResponse;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.cliente.ConsultaClienteAtributosResponse;
import ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.general.ConsultaGeneralClienteRequest;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.BothSidesDocumentRequest;

public interface EnrolamientoService {

    String obtenerNombreMicroservicio();

    CrearPerfilResponse crearPerfilUsuario(CrearPerfilRequest crearPerfilRequest);

    void eliminarPerfilUsuario (EliminarPerfilUsuarioRequest eliminarPerfilUsuarioRequest);

    ConsultarIdentidadResponse consultarNormalizacionIndividuos(ConsultarIdentidadRequest consultarIdentidadRequest);

    void actualizarRostro(ActualizarRostroRequest actualizarRostroRequest);

    LeerPdf417DniResponse leerPdf417Dni(BothSidesDocumentRequest bothSidesDocumentRequest);

    EnrolarRostroResponse enrolarRostro(EnrolarRostroRequest enrolarRostroRequest, String xOperationID);

    ConsultarDatosDniResponse consultarDatosDni(ConsultarDatosDniRequest consultarDatosDniRequest);

    ConsultarEnrolamientoResponse consultarEnrolamiento(ConsultarEnrolamientoRequest consultarEnrolamientoRequest, String xOperationID);

    ConsultaClienteAtributosResponse consultarCliente(ConsultaGeneralClienteRequest consultaGeneralClienteRequest);
}
