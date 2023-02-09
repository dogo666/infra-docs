package ar.com.macro.validacion.domain.fido.rest.controller;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.constant.Errores.*;
import static ar.com.macro.constants.Constantes.*;
import static com.daon.identityx.rest.model.def.AuthenticationRequestStatusEnum.COMPLETED_SUCCESSFUL;
import static java.util.Objects.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.macro.validacion.domain.fido.rest.dto.response.*;
import com.daon.identityx.rest.model.def.AuthenticatorStatusEnum;
import com.daon.identityx.rest.model.def.RegistrationChallengeStatusEnum;
import com.daon.identityx.rest.model.pojo.Authenticator;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MvcResult;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import ar.com.macro.ContextoTest;
import ar.com.macro.commons.components.applicationid.interceptor.ApiInterceptor;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import ar.com.macro.validacion.model.service.dto.FIDORegChallenge;
import ar.com.macro.validacion.model.service.dto.FIDORegChallengeAndId;

public class FidoControllerTest extends ContextoTest {

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
            return new FidoControllerTest.ApiInterceptorConfiguration.MockApiInterceptor();
        }
    }

	@Test
	public void crearRegistracionFidoExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		FIDORegChallengeAndId regChallengeAndId = FIDORegChallengeAndId.builder().registrationChallenge(new RegistrationChallenge()).build();
		when(fidoService.crearRegistracion(any(),any(), any())).thenReturn(regChallengeAndId);
		MvcResult result = ejecutarPostConHeader(URL_CREAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is2xxSuccessful(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void crearRegistracionFidoErrorUsuarioIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.crearRegistracion(any(),any(), any())).
				thenThrow(new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(URL_CREAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void crearRegistracionFidoErrorIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.crearRegistracion(any(),any(), any())).
				thenThrow(new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X));
		MvcResult result = ejecutarPostConHeader(URL_CREAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void crearRegistracionFidoErrorCrearRegistracionConPoliticaIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.crearRegistracion(any(),any(), any())).
				thenThrow(new IdentityXException(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY));
		MvcResult result = ejecutarPostConHeader(URL_CREAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_CREAR_REGISTRATION_CHALLENGE_POLICY.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void crearRegistracionFidoErrorCrearRegistracionConUsuarioBloqueadoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.crearRegistracion(any(),any(), any())).
				thenThrow(new IdentityXException(ERROR_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO));
		MvcResult result = ejecutarPostConHeader(URL_CREAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_CREAR_REGISTRATION_USUARIO_BLOQUEADO.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void procesarRegistracionFidoExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_PROCESAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		FIDORegChallenge regChallenge= new FIDORegChallenge();
		when(fidoService.processRegistrationResponse(any(),any(), any())).thenReturn(regChallenge);
		MvcResult result = ejecutarPostConHeader(URL_PROCESAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is2xxSuccessful(), X_OPERATION_ID_VALUE);
		Respuesta<ProcesarRegistracionResponse> response = obtenerRespuesta(result, ProcesarRegistracionResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void procesarRegistracionFidoErrorProcesarRegistracion() throws Exception {
		String jsonRequest = retrieveBody(JSON_PROCESAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.processRegistrationResponse(any(),any(), any())).
				thenThrow(new IdentityXException(ERROR_FIDO_PROCESAR_REGISTRATION_CHALLENGE));
		MvcResult result = ejecutarPostConHeader(URL_PROCESAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_PROCESAR_REGISTRATION_CHALLENGE.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void procesarRegistracionFidoErrorProcesarGuardandoRegistracion() throws Exception {
		String jsonRequest = retrieveBody(JSON_PROCESAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.processRegistrationResponse(any(),any(), any())).
				thenThrow(new IdentityXException(ERROR_FIDO_GUARDANDO_REGISTRATION_CHALLENGE));
		MvcResult result = ejecutarPostConHeader(URL_PROCESAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_GUARDANDO_REGISTRATION_CHALLENGE.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void procesarRegistracionFidoErrorProcesarBuscandoRegistracion() throws Exception {
		String jsonRequest = retrieveBody(JSON_PROCESAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.processRegistrationResponse(any(),any(), any())).
				thenThrow(new IdentityXException(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE));
		MvcResult result = ejecutarPostConHeader(URL_PROCESAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_BUSCANDO_REGISTRATION_CHALLENGE.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void procesarRegistracionFidoErrorProcesarRegistracionUserInvalido() throws Exception {
		String jsonRequest = retrieveBody(JSON_PROCESAR_REGISTRACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.processRegistrationResponse(any(),any(), any())).
				thenThrow(new IdentityXException(ERROR_FIDO_USER_REGISTRATION_CHALLENGE));
		MvcResult result = ejecutarPostConHeader(URL_PROCESAR_REGISTRACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearRegistracionResponse> response = obtenerRespuesta(result, CrearRegistracionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_USER_REGISTRATION_CHALLENGE.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void crearAutenticacionFidoExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST);
		AuthenticationRequest authenticationRequest= new AuthenticationRequest();
		when(fidoService.crearAuthenticationRequest(any(), any())).thenReturn(authenticationRequest);
		MvcResult result = ejecutarPostConHeader(URL_CREAR_AUTENTICACION_FIDO_IDENTITYX,jsonRequest,status().is2xxSuccessful(), X_OPERATION_ID_VALUE);
		Respuesta<CrearAutenticacionResponse> response = obtenerRespuesta(result, CrearAutenticacionResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void crearAutenticacionFidoErrorIdentityXUserInvalido() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.crearAuthenticationRequest(any(), any())).thenThrow(new IdentityXException(ERROR_FIDO_USER_BLOQUEADO));
		MvcResult result = ejecutarPostConHeader(URL_CREAR_AUTENTICACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearAutenticacionResponse> response = obtenerRespuesta(result, CrearAutenticacionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_USER_BLOQUEADO.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void crearAutenticacionFidoErrorIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_CREAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.crearAuthenticationRequest(any(), any())).thenThrow(new IdentityXException(ERROR_FIDO_CREAR_AUTENTICACION));
		MvcResult result = ejecutarPostConHeader(URL_CREAR_AUTENTICACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<CrearAutenticacionResponse> response = obtenerRespuesta(result, CrearAutenticacionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_CREAR_AUTENTICACION.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarAutenticacionFidoExitosoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_VALIDAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST);
		AuthenticationRequest authenticationRequest= new AuthenticationRequest();
		when(fidoService.validarAuthenticationRequest(any(), any())).thenReturn(authenticationRequest);
		MvcResult result = ejecutarPostConHeader(URL_VALIDAR_AUTENTICACION_FIDO_IDENTITYX,jsonRequest,status().is2xxSuccessful(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarAutenticacionResponse> response = obtenerRespuesta(result, ValidarAutenticacionResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void validarAutenticacionFidoErrorIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_VALIDAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.validarAuthenticationRequest(any(), any())).thenThrow(new IdentityXException(ERROR_FIDO_NO_AUTENTICACION_REQUEST));
		MvcResult result = ejecutarPostConHeader(URL_VALIDAR_AUTENTICACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarAutenticacionResponse> response = obtenerRespuesta(result, ValidarAutenticacionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_NO_AUTENTICACION_REQUEST.getCodigo(),response.getError().getCodigo());
	}

	@Test
	public void validarAutenticacionFidoErrorGuardandoIdentityX() throws Exception {
		String jsonRequest = retrieveBody(JSON_VALIDAR_AUTENTICACION_FIDO_IDENTITYX_REQUEST);
		when(fidoService.validarAuthenticationRequest(any(), any())).thenThrow(new IdentityXException(ERROR_FIDO_GUARDANDO_AUTENTICACION_REQUEST));
		MvcResult result = ejecutarPostConHeader(URL_VALIDAR_AUTENTICACION_FIDO_IDENTITYX,jsonRequest,status().is4xxClientError(), X_OPERATION_ID_VALUE);
		Respuesta<ValidarAutenticacionResponse> response = obtenerRespuesta(result, ValidarAutenticacionResponse.class);
		assertNotNull(response);
		assertNotNull(response.getError());
		assertEquals(ERROR_FIDO_GUARDANDO_AUTENTICACION_REQUEST.getCodigo(),response.getError().getCodigo());
	}

    @Test
    public void autenticacionRequestFidoExitosoIdentityX() throws Exception {
      var jsonRequest = retrieveBody(JSON_GET_AUTENTICACION_REQUEST_FIDO_IDENTITYX_REQUEST);
  
      var authenticationRequest = new AuthenticationRequest();
      authenticationRequest.setStatus(COMPLETED_SUCCESSFUL);
  
      when(fidoService.getAuthenticationRequestByUser(anyString(), anyString()))
          .thenReturn(authenticationRequest);
  
      var result =
          ejecutarPostConHeader(
              URL_AUTENTICACION_CONSULTAR_FIDO_IDENTITYX,
              jsonRequest,
              status().is2xxSuccessful(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<GetAutenticationResponse> response =
          obtenerRespuesta(result, GetAutenticationResponse.class);
  
      assertNotNull(response);
      assertNull(response.getError());
    }
  
    @Test
    public void autenticacionRequestFidoErrorNoAutenticacionEncontrada() throws Exception {
      var jsonRequest = retrieveBody(JSON_GET_AUTENTICACION_REQUEST_FIDO_IDENTITYX_REQUEST);
  
      var authenticationRequest = new AuthenticationRequest();
      authenticationRequest.setStatus(COMPLETED_SUCCESSFUL);
  
      when(fidoService.getAuthenticationRequestByUser(anyString(), anyString()))
          .thenThrow(new IdentityXException(ERROR_FIDO_NO_AUTENTICACION_REQUEST));
  
      var result =
          ejecutarPostConHeader(
              URL_AUTENTICACION_CONSULTAR_FIDO_IDENTITYX,
              jsonRequest,
              status().is4xxClientError(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<GetAutenticationResponse> response =
          obtenerRespuesta(result, GetAutenticationResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getError());
      assertEquals(ERROR_FIDO_NO_AUTENTICACION_REQUEST.getCodigo(), response.getError().getCodigo());
    }
  
    @Test
    public void autenticacionRequestFidoErrorInesperadoIdentityX() throws Exception {
      var jsonRequest = retrieveBody(JSON_GET_AUTENTICACION_REQUEST_FIDO_IDENTITYX_REQUEST);
  
      var authenticationRequest = new AuthenticationRequest();
      authenticationRequest.setStatus(COMPLETED_SUCCESSFUL);
  
      when(fidoService.getAuthenticationRequestByUser(anyString(), anyString()))
          .thenThrow(new IdentityXException(ERROR_GET_AUTHENTICATION_REQUEST));
  
      var result =
          ejecutarPostConHeader(
              URL_AUTENTICACION_CONSULTAR_FIDO_IDENTITYX,
              jsonRequest,
              status().is4xxClientError(),
              X_OPERATION_ID_VALUE);
  
      Respuesta<GetAutenticationResponse> response =
          obtenerRespuesta(result, GetAutenticationResponse.class);
  
      assertNotNull(response);
      assertNotNull(response.getError());
      assertEquals(ERROR_GET_AUTHENTICATION_REQUEST.getCodigo(), response.getError().getCodigo());
    }

	@Test
	public void autenticadoresRequestFidoExitosoIdentityX() throws Exception {
		var jsonRequest = retrieveBody(JSON_GET_AUTENTICADORES_REQUEST_FIDO_IDENTITYX_REQUEST);

		when(fidoService.getAutenticadores(anyString()))
				.thenReturn(getAutenticadores());

		var result =
				ejecutarPostConHeader(
						URL_AUTENTICADORES_CONSULTAR_FIDO_IDENTITYX,
						jsonRequest,
						status().is2xxSuccessful(),
						X_OPERATION_ID_VALUE);

		Respuesta<GetAutenticadoresResponse> response =
				obtenerRespuesta(result, GetAutenticadoresResponse.class);

		assertNotNull(response);
		assertNull(response.getError());
	}

	@Test
	public void autenticadoresRequestFidoErrorIdentityX() throws Exception {
		var jsonRequest = retrieveBody(JSON_GET_AUTENTICADORES_REQUEST_FIDO_IDENTITYX_REQUEST);

		when(fidoService.getAutenticadores(anyString()))
				.thenThrow(new IdentityXException(ERROR_FIDO_OBTENIENDO_AUTENTICADORES));

		var result =
				ejecutarPostConHeader(
						URL_AUTENTICADORES_CONSULTAR_FIDO_IDENTITYX,
						jsonRequest,
						status().is4xxClientError(),
						X_OPERATION_ID_VALUE);

		Respuesta<GetAutenticadoresResponse> response =
				obtenerRespuesta(result, GetAutenticadoresResponse.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertNotNull(response.getError().getCodigo());
		assertEquals(ERROR_FIDO_OBTENIENDO_AUTENTICADORES.getCodigo().longValue(),
					response.getError().getCodigo().longValue());
	}


	private Authenticator[] getAutenticadores(){
		return new Authenticator[] {getAuthenticator()};
	}

	private Authenticator getAuthenticator(){
		Authenticator authenticator = new Authenticator();
		authenticator.setId("QTAzrD5rFMrGgBWWwwntmn9bSw");
		authenticator.setAuthenticatorId("MDhjMDQwODg4MTQzNDZiZGJmMTNiN2RhYmIwY2Q4MTI");
		authenticator.setStatus(AuthenticatorStatusEnum.ARCHIVED);
		authenticator.setLogicalName("S21 de Andres");
		authenticator.setMake("samsung");
		authenticator.setModel("SM-G991B");
		authenticator.setDeviceCorrelationId("7290b0d7bf823ff7");
		authenticator.setAuthenticatorAttestationId("D409#8201");
		return authenticator;
	}

	@Test
	public void autenticadorArchivarRequestFidoExitosoIdentityX() throws Exception {
		var jsonRequest = retrieveBody(JSON_ARCHIVAR_AUTENTICADOR_REQUEST_FIDO_IDENTITYX_REQUEST);

		when(fidoService.archivarAutenticador(anyString()))
				.thenReturn(getAuthenticator());

		var result =
				ejecutarPostConHeader(
						URL_AUTENTICADOR_ARCHIVAR_FIDO_IDENTITYX,
						jsonRequest,
						status().is2xxSuccessful(),
						X_OPERATION_ID_VALUE);

		Respuesta<ArchivarAutenticadorResponse> response =
				obtenerRespuesta(result, ArchivarAutenticadorResponse.class);

		assertNotNull(response);
		assertNull(response.getError());
		assertEquals(AuthenticatorStatusEnum.ARCHIVED.name(),response.getDatos().getStatus());
	}

	@Test
	public void autenticadorArchivarRequestFidoErrorIdentityX() throws Exception {
		var jsonRequest = retrieveBody(JSON_ARCHIVAR_AUTENTICADOR_REQUEST_FIDO_IDENTITYX_REQUEST);

		when(fidoService.archivarAutenticador(anyString()))
				.thenThrow(new IdentityXException(ERROR_FIDO_ARCHIVANDO_AUTENTICADOR));

		var result =
				ejecutarPostConHeader(
						URL_AUTENTICADOR_ARCHIVAR_FIDO_IDENTITYX,
						jsonRequest,
						status().is4xxClientError(),
						X_OPERATION_ID_VALUE);

		Respuesta<GetAutenticadoresResponse> response =
				obtenerRespuesta(result, GetAutenticadoresResponse.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertNotNull(response.getError().getCodigo());
		assertEquals(ERROR_FIDO_ARCHIVANDO_AUTENTICADOR.getCodigo().longValue(),
				response.getError().getCodigo().longValue());
	}

	@Test
	public void consultarRegistracionRequestFidoExitosoIdentityX() throws Exception {
		var jsonRequest = retrieveBody(JSON_CONSULTAR_REGISTRACION_REQUEST_FIDO_IDENTITYX_REQUEST);

		when(fidoService.consultarRegistracion(anyString()))
				.thenReturn(getRegistracion());

		var result =
				ejecutarPostConHeader(
						URL_CONSULTAR_REGISTRACION_FIDO_IDENTITYX,
						jsonRequest,
						status().is2xxSuccessful(),
						X_OPERATION_ID_VALUE);

		Respuesta<ConsultarRegistracionResponse> response =
				obtenerRespuesta(result, ConsultarRegistracionResponse.class);
		assertNotNull(response);
		assertNull(response.getError());
		assertEquals(RegistrationChallengeStatusEnum.COMPLETED_SUCCESSFUL.name(),response.getDatos().getEstado());
	}

	@Test
	public void consultarRegistracionRequestFidoErrorIdentityX() throws Exception {
		var jsonRequest = retrieveBody(JSON_CONSULTAR_REGISTRACION_REQUEST_FIDO_IDENTITYX_REQUEST);

		when(fidoService.consultarRegistracion(anyString()))
				.thenThrow(new IdentityXException(ERROR_FIDO_CONSULTAR_REGISTRATION_CHALLENGE));

		var result =
				ejecutarPostConHeader(
						URL_CONSULTAR_REGISTRACION_FIDO_IDENTITYX,
						jsonRequest,
						status().is4xxClientError(),
						X_OPERATION_ID_VALUE);

		Respuesta<ConsultarRegistracionResponse> response =
				obtenerRespuesta(result, ConsultarRegistracionResponse.class);

		assertNotNull(response);
		assertNotNull(response.getError());
		assertNotNull(response.getError().getCodigo());
		assertEquals(ERROR_FIDO_CONSULTAR_REGISTRATION_CHALLENGE.getCodigo().longValue(),
				response.getError().getCodigo().longValue());
	}


	private FIDORegChallengeAndId getRegistracion(){
		RegistrationChallenge registrationChallenge = new RegistrationChallenge();
		registrationChallenge.setStatus(RegistrationChallengeStatusEnum.COMPLETED_SUCCESSFUL);
		FIDORegChallengeAndId fidoRegChallengeAndId = new FIDORegChallengeAndId();
		fidoRegChallengeAndId.setRegistrationChallenge(registrationChallenge);
		return fidoRegChallengeAndId;
	}

}
