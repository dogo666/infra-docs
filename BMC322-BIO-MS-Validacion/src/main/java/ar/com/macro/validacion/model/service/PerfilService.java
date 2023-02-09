package ar.com.macro.validacion.model.service;

import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.AgregarDniPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.*;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.AgregarDniPerfilResponse;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.*;
import com.daon.identityx.rest.model.pojo.User;

public interface PerfilService {

	CrearPerfilResponse crearPerfilUsuario(CrearPerfilRequest crearPerfilRequest);

	AgregarDniPerfilResponse agregarDniPerfilUsuario(AgregarDniPerfilRequest agregarDniPerfilRequest);

	ValidarRostroPersonaResponse validarRostroPersona (ValidarRostroPersonaRequest validarRostroPersonaRequest, String xOperationID);

	CrearEvaluacionPerfilResponse crearEvaluacion(CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest, String xOperationID);

	ConsultarVerificacionRostroPersonaResponse consultarVerificacionRostro (ConsultarVerificacionRostroPersonaRequest consultarVerificacionRostroPersonaRequest, String xOperationID);

	ValidarRostroVideoPersonaResponse validarRostroVideoPersona(ValidarRostroVideoPersonaRequest validarRostro3DFLPersonaRequest, String xOperationID);

	User obtenerUsuario(String idusuario);

	ConsultarPersonaResponse consultarPersona(ConsultarPersonaRequest consultarPersonaRequest, String xOperationID);

	String generarReferenceId(User user);

}
