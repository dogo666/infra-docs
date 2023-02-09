package ar.com.macro.validacion.domain.identityx.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.constant.Errores.ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_APLICACION_NO_ENCONTRADA;
import static ar.com.macro.constant.Errores.ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_CREAR_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_GET_SENSITIVE_DATA_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_POLITICA_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;
import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.RegistracionNoEncontradoIdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarVerificacionRostroPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroVideoPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ValidarRostroVideoPersonaResponse.RostroResponse;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.response.ValidarRostroRenaperResponse;

public class IdentityXControllerTest extends ContextoTest {

	@TestConfiguration
    static class ApiInterceptorConfiguration {
        static class MockApiInterceptor extends ApiInterceptor {
            MockApiInterceptor() {
                super();
            }

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                return true;
            }
        }

        @Bean
        public ApiInterceptor apiInterceptor() {
            return new IdentityXControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

	@Test
	public void validarRostroExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroVideoPersona(any(),any())).thenReturn(new ValidarRostroVideoPersonaResponse());
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is2xxSuccessful(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void validarRostroErrorObteniendoUsuarioIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroVideoErrorObteniendoUsuarioIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoConfiguracionAplicacionID() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(Errores.ERROR_APLICACION_NO_ENCONTRADA));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_APLICACION_NO_ENCONTRADA.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoRegistracion() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new RegistracionNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoAplicacionIndentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_OBTENER_APLICACION_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_APLICACION_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorObteniendoPoliticaIndentityX() throws Exception {String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_POLITICA_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorCrearAutenticacionIndentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_CREAR_AUTENTICACION_REQUEST_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarRostroErrorActualizarAutenticacionIndentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.validarRostroPersona(any(),any())).thenThrow(new IdentityXException(ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ValidarRostroPersonaResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_ACTUALIZAR_AUTENTICACION_REQUEST_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void consultarVerificacionRostroExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.consultarVerificacionRostro(any(),any())).thenReturn(new ConsultarVerificacionRostroPersonaResponse());
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX, jsonRequest,status().is2xxSuccessful(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ConsultarVerificacionRostroPersonaResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void consultarVerificacionRostroError409IdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_SIN_IDVERIFICACION_REQUEST);
		when(perfilService.consultarVerificacionRostro(any(),any())).thenReturn(new ConsultarVerificacionRostroPersonaResponse());
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ConsultarVerificacionRostroPersonaResponse.class);
		assertNotNull(response.getError());
	}

	@Test
	public void consultarVerificacionRostroErrorIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_REQUEST);
		when(perfilService.consultarVerificacionRostro(any(),any())).thenThrow(new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroPersonaResponse> response = obtenerRespuesta(result, ConsultarVerificacionRostroPersonaResponse.class);
		assertNotNull(response.getError());
	}
	
	@Test
	public void consultarValidarRostroVideoPersonaExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
		when(perfilService.validarRostroVideoPersona(any(),any())).thenReturn(new ValidarRostroVideoPersonaResponse(1, new RostroResponse(new ValidarRostroRenaperResponse(1, "", 90, true))));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_VIDEO_IDENTITYX, jsonRequest,status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroVideoPersonaResponse> response = obtenerRespuesta(result, ValidarRostroVideoPersonaResponse.class);
		
		assertNotNull(response.getDatos());
		assertNotNull(response.getDatos().getStatus());
		assertNotNull(response.getDatos().getRostro());
		assertNotNull(response.getDatos().getRostro().getConfidence());
		assertNotNull(response.getDatos().getRostro().getMatch());
		assertNull(response.getError());
	}
	
	@Test
    public void consultarValidarRostroVideoPersonaExitosoIdentityXSinReNaPer() throws Exception {
        String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
        when(perfilService.validarRostroVideoPersona(any(),any())).thenReturn(new ValidarRostroVideoPersonaResponse(1, null));
        MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_VIDEO_IDENTITYX, jsonRequest,status().isOk(), Constantes.X_OPERATION_ID_VALUE);
        Respuesta<ValidarRostroVideoPersonaResponse> response = obtenerRespuesta(result, ValidarRostroVideoPersonaResponse.class);
        
        assertNotNull(response.getDatos());
        assertNotNull(response.getDatos().getStatus());
        assertNull(response.getDatos().getRostro());
        assertNull(response.getError());
    }
	
	@Test
	public void consultarValidarRostroVideoPersonaCrearUsuarioErrorIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_SIN_IDUSUARIO_REQUEST);
		when(perfilService.validarRostroVideoPersona(any(),any())).thenThrow(new IdentityXException(ERROR_CREAR_USUARIO_IDENTITY_X.getCodigo(), ERROR_CREAR_USUARIO_IDENTITY_X.getMensaje()));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_VIDEO_IDENTITYX, jsonRequest,status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroVideoPersonaResponse> response = obtenerRespuesta(result, ValidarRostroVideoPersonaResponse.class);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}
	
	@Test
	public void consultarValidarRostroVideoPersonaSinVideoErrorIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_SIN_VIDEO_REQUEST);
		when(perfilService.validarRostroVideoPersona(any(),any())).thenReturn(new ValidarRostroVideoPersonaResponse());
		MvcResult result = ejecutarPostConHeader(Constantes.URL_VALIDAR_ROSTRO_VIDEO_IDENTITYX, jsonRequest,status().isBadRequest(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ValidarRostroVideoPersonaResponse> response = obtenerRespuesta(result, ValidarRostroVideoPersonaResponse.class);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
	}

	@Test
	public void consultarPersonaOKIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
		when(perfilService.consultarPersona(any(),any())).thenReturn(new ConsultarPersonaResponse());
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_PERSONA_IDENTITYX, jsonRequest,status().isOk(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarPersonaResponse> response = obtenerRespuesta(result, ConsultarPersonaResponse.class);
		assertNotNull(response.getDatos());
		assertNull(response.getError());
	}

	@Test
	public void consultarPersonaErrorUsuarioNoEncontradoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
		when(perfilService.consultarPersona(any(),any()))
				.thenThrow(new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_PERSONA_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarPersonaResponse> response = obtenerRespuesta(result, ConsultarPersonaResponse.class);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void consultarPersonaErrorObteniendoRostroIdentityX() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
		when(perfilService.consultarPersona(any(),any()))
				.thenThrow(new IdentityXException(ERROR_GET_SENSITIVE_DATA_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(Constantes.URL_CONSULTAR_PERSONA_IDENTITYX, jsonRequest,status().is4xxClientError(), Constantes.X_OPERATION_ID_VALUE);
		Respuesta<ConsultarPersonaResponse> response = obtenerRespuesta(result, ConsultarPersonaResponse.class);
		assertNull(response.getDatos());
		assertNotNull(response.getError());
		assertEquals(ERROR_GET_SENSITIVE_DATA_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

}
