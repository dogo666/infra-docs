package ar.com.macro.enrolamiento.model.service;

import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.AgregarDniPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ValidarRolUsuarioRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.AgregarDniPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ValidarRolUsuarioResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ValidarRostroPersonaRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ValidarRostroPersonaResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.CrearEvaluacionPerfilResponse;

public interface PerfilService {

	CrearPerfilResponse crearPerfilUsuario(CrearPerfilRequest crearPerfilRequest);

	AgregarDniPerfilResponse agregarDniPerfilUsuario(AgregarDniPerfilRequest agregarDniPerfilRequest);

	ValidarRostroPersonaResponse validarRostroPersona (ValidarRostroPersonaRequest validarRostroPersonaRequest, String xOperationID);

	CrearEvaluacionPerfilResponse crearEvaluacion(CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest, String xOperationID);
	
	ValidarRolUsuarioResponse validarRolUsuario(ValidarRolUsuarioRequest request, String xOperationID);
}
