package ar.com.macro.validacion.model.service.impl;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.constant.Errores.ERROR_CREAR_IDCHECK_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X;
import static ar.com.macro.constant.Errores.ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X;
import static ar.com.macro.constants.Constantes.X_OPERATION_ID_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.User;
import com.daon.identityx.rest.model.support.DataHolder;
import com.daon.identityx.rest.model.support.FaceData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.constant.Errores;
import ar.com.macro.constant.Validacion3DFL;
import ar.com.macro.constants.Constantes;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ConsultarVerificacionRostroPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.request.ValidarRostroVideoPersonaRequest;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ConsultarVerificacionRostroPersonaResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.CrearEvaluacionPerfilResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ImagenResponse;
import ar.com.macro.validacion.domain.identityx.rest.dto.response.ImagenResponse.TipoImagen;
import ar.com.macro.validacion.model.client.identityx.IdentityXClient;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Create3DFLVideoChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DniImageData;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Face;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetFacesResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetImagenResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetSensitiveDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Item;
import ar.com.macro.validacion.model.client.identityx.rest.dto.LivenessChallenge;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Results;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Submit3DFLVideoResponse;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.request.ValidarRostroRenaperRequest;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.response.ValidarRostroRenaperResponse;
import ar.com.macro.validacion.model.feign.enrolamiento.proxy.EnrolamientoProxyService;
import ar.com.macro.validacion.model.service.DatosGeneralesService;
import ar.com.macro.validacion.model.service.PerfilService;

@RunWith(SpringRunner.class)
public class PerfilServiceImplTest {

	private PerfilService perfilService;
	
	@Spy
	private ObjectMapper mapper;
	
	@Mock
	private IdentityXClient identityXClient;

	@Mock
	private DatosGeneralesService datosGeneralesService;

	@Mock
	private EnrolamientoProxyService enrolamientoProxyService;

	@Value("${identityx.client.referenceid.separador.value}")
	private String referenceIdSeparador;

	@Value("${daonengine.nombre.parametros.datos.generales}")
	private String nombreMicroServicioDatosGenerales;

	@Value("${identityx.nombre.parametros.configuracion.politica.evaluacion}")
	private String parametroConfigPoliticaEvaluacionIdentityX;

	@Value("${identityx.nombre.parametros.configuracion.politica.evaluacion.match}")
	private String parametroMatchCreacionEvaluacion;

	@Value("${identityx.nombre.parametros.configuracion.aplicacion}")
	private String parametroConfigAplicacionIdentityX;

	@Value("${identityx.registracion.id.sufijo}")
	private String sufijoIdRegistracion;

	@Value("${identityx.nombre.parametros.configuracion.politica}")
	private String parametroConfigPoliticaIdentityX;
	
	@Value("${identityx.nombre.parametros.configuracion.politica.3dfl}") 
	private String parametroConfigPolitica3DFLIdentityX;

	@Value("${identityx.authetication.id.sufijo}")
	private String sufijoIdAuthetication;

	@Value("${identityx.authetication.request.description}")
	private String autheticationRequestDescriptionTemplate;

	@Value("${identityx.nombre.parametros.configuracion.validacion.renaper.3dfl}")
	private String parametroConfigValidacionRenaper3DFLIdentityX;

	@Value("${identityx.nombre.parametros.configuracion.umbral.renaper.3dfl}")
	private String parametroConfigUmbralRenaper3DFLIdentityX;

	@Before
	public void init() {
		this.perfilService = new PerfilServiceImpl(
				identityXClient,
				datosGeneralesService,
				enrolamientoProxyService,
				referenceIdSeparador,
				nombreMicroServicioDatosGenerales,
				parametroConfigPoliticaEvaluacionIdentityX,
				parametroMatchCreacionEvaluacion,
				parametroConfigAplicacionIdentityX,
				sufijoIdRegistracion,
				parametroConfigPoliticaIdentityX,
				sufijoIdAuthetication,
				autheticationRequestDescriptionTemplate,
				parametroConfigPolitica3DFLIdentityX,
				parametroConfigValidacionRenaper3DFLIdentityX,
				parametroConfigUmbralRenaper3DFLIdentityX
				);
	}
	
	@Test
	public void debeCrearPerfilUsuarioExito() throws Exception {
		
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseCrearPerfilUsuarioIdentityX = mapper.readValue(responseCrearPerfilUsuario, User.class);
		
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_REQUEST);
		CrearPerfilRequest crearPerfilUsuarioRequest = mapper.readValue(jsonRequest, CrearPerfilRequest.class);
		
		when(identityXClient.crearPerfilUsuario(any(User.class))).thenReturn(responseCrearPerfilUsuarioIdentityX);
		
		CrearPerfilResponse crearPerfilUsuarioResponse = perfilService.crearPerfilUsuario(crearPerfilUsuarioRequest);
		assertNotNull(crearPerfilUsuarioResponse);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeCrearPerfilUsuarioIdentityXException() throws Exception {
		
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_REQUEST);
		CrearPerfilRequest crearPerfilUsuarioRequest = mapper.readValue(jsonRequest, CrearPerfilRequest.class);
		
		when(identityXClient.crearPerfilUsuario(any(User.class))).thenThrow(
				new IdentityXException(Constantes.ERROR_CREAR_PERFIL_USUARIO_EXISTENTE_CODIGO, Constantes.ERROR_CREAR_PERFIL_USUARIO_EXISTENTE_MSG));
		
		perfilService.crearPerfilUsuario(crearPerfilUsuarioRequest);
	}

	@Test
	public void crearEvaluacionExitoMatch() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest = mapper.readValue(jsonRequest,CrearEvaluacionPerfilRequest.class);
		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaEvaluacionIdentityX(anyString(),anyString(),anyString())).thenReturn("1234");
		CreateEvaluationResponse createEvaluationResponse = new CreateEvaluationResponse();
		Results results = new Results();
		Item item = new Item();
		item.setResult(parametroMatchCreacionEvaluacion);
		results.setItems(new Item[] {item});
		createEvaluationResponse.setResults(results);
		Optional<CreateEvaluationResponse> optionalCreateEvaluationResponse = Optional.of(createEvaluationResponse);
		when(identityXClient.crearEvaluacion(anyString(),anyString(),anyString())).thenReturn(optionalCreateEvaluationResponse);
		CrearEvaluacionPerfilResponse crearEvaluacionPerfilResponse = perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,"789456123");
		int result = crearEvaluacionPerfilResponse.getResultado();
		assertNotNull(crearEvaluacionPerfilResponse);
		assertEquals(result, 1);
	}

	@Test
	public void crearEvaluacionExitoNoMatch() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest = mapper.readValue(jsonRequest,CrearEvaluacionPerfilRequest.class);
		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaEvaluacionIdentityX(anyString(),anyString(),anyString())).thenReturn("1234");
		CreateEvaluationResponse createEvaluationResponse = new CreateEvaluationResponse();
		Results results = new Results();
		Item item = new Item();
		item.setResult("NO_MATCH");
		results.setItems(new Item[] {item});
		createEvaluationResponse.setResults(results);
		Optional<CreateEvaluationResponse> optionalCreateEvaluationResponse = Optional.of(createEvaluationResponse);
		when(identityXClient.crearEvaluacion(anyString(),anyString(),anyString())).thenReturn(optionalCreateEvaluationResponse);
		CrearEvaluacionPerfilResponse crearEvaluacionPerfilResponse = perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,"789456123");
		int result = crearEvaluacionPerfilResponse.getResultado();
		assertNotNull(crearEvaluacionPerfilResponse);
		assertEquals(result, 0);
	}

	@Test(expected = IdentityXException.class)
	public void crearEvaluacionExeptionObtenerRegistro() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest = mapper.readValue(jsonRequest,CrearEvaluacionPerfilRequest.class);
		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenThrow(new IdentityXException(ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getCodigo(), ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X.getMensaje()));
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,"789456123");
	}

	@Test(expected = ObtenerDatosGeneralesCompuestosException.class)
	public void crearEvaluacionExeptionDatosGenerales() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest = mapper.readValue(jsonRequest,CrearEvaluacionPerfilRequest.class);
		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaEvaluacionIdentityX(anyString(),anyString(),anyString())).thenThrow(new ObtenerDatosGeneralesCompuestosException(Errores.ERROR_DATOS_GENERALES.getCodigo(), Errores.ERROR_DATOS_GENERALES.getMensaje()));
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,"789456123");
	}

	@Test(expected = IdentityXException.class)
	public void crearEvaluacionExeption() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest = mapper.readValue(jsonRequest,CrearEvaluacionPerfilRequest.class);
		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaEvaluacionIdentityX(anyString(),anyString(),anyString())).thenReturn("1234");
		when(identityXClient.crearEvaluacion(anyString(),anyString(),anyString())).thenThrow(new IdentityXException(Errores.ERROR_OBTENER_CREAR_EVALUACION_IDENTITY_X.getCodigo(), Errores.ERROR_OBTENER_CREAR_EVALUACION_IDENTITY_X.getMensaje()));
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,"789456123");
	}

	@Test(expected = IdentityXException.class)
	public void crearEvaluacionExceptionResponseNotPresent() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest = mapper.readValue(jsonRequest,CrearEvaluacionPerfilRequest.class);
		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaEvaluacionIdentityX(anyString(),anyString(),anyString())).thenReturn("1234");
		CreateEvaluationResponse createEvaluationResponse = new CreateEvaluationResponse();
		Results results = new Results();
		Item item = new Item();
		item.setResult("NO_MATCH");
		results.setItems(new Item[] {item});
		createEvaluationResponse.setResults(results);
		Optional<CreateEvaluationResponse> optionalCreateEvaluationResponse = Optional.empty();
		when(identityXClient.crearEvaluacion(anyString(),anyString(),anyString())).thenReturn(optionalCreateEvaluationResponse);
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,"789456123");
	}

	@Test(expected = IdentityXException.class)
	public void crearEvaluacionExceptionSizeItemsBad() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_EVALUACION_IDENTITYX_REQUEST);
		CrearEvaluacionPerfilRequest crearEvaluacionPerfilRequest = mapper.readValue(jsonRequest,CrearEvaluacionPerfilRequest.class);
		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaEvaluacionIdentityX(anyString(),anyString(),anyString())).thenReturn("1234");
		CreateEvaluationResponse createEvaluationResponse = new CreateEvaluationResponse();
		Results results = new Results();
		results.setItems(new Item[] {});
		createEvaluationResponse.setResults(results);
		Optional<CreateEvaluationResponse> optionalCreateEvaluationResponse = Optional.of(createEvaluationResponse);
		when(identityXClient.crearEvaluacion(anyString(),anyString(),anyString())).thenReturn(optionalCreateEvaluationResponse);
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,"789456123");
	}

	@Test
	public void debeConsultarVerificacionRostroExito() throws Exception {
		String requestConsultarVerificacionRostro = retrieveBody(Constantes.JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_REQUEST);
		ConsultarVerificacionRostroPersonaRequest consultarVerificacionRostroPersonaRequest = mapper.readValue(requestConsultarVerificacionRostro, ConsultarVerificacionRostroPersonaRequest.class);
		String responseConsultarVerificacionRostro = retrieveBody(Constantes.JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_RESPONSE);
		AuthenticationRequest authenticationRequest = mapper.readValue(responseConsultarVerificacionRostro, AuthenticationRequest.class);
		when(identityXClient.consultarVerificacionRostro(any(String.class))).thenReturn(Optional.of(authenticationRequest));
		ConsultarVerificacionRostroPersonaResponse response = perfilService.consultarVerificacionRostro(consultarVerificacionRostroPersonaRequest, X_OPERATION_ID_VALUE);
		assertNotNull(response);
	}

	@Test(expected = IdentityXException.class)
	public void debeConsultarVerificacionRostroError() throws Exception {
		String requestConsultarVerificacionRostro = retrieveBody(Constantes.JSON_CONSULTAR_VERIFICACION_ROSTRO_IDENTITYX_REQUEST);
		ConsultarVerificacionRostroPersonaRequest consultarVerificacionRostroPersonaRequest = mapper.readValue(requestConsultarVerificacionRostro, ConsultarVerificacionRostroPersonaRequest.class);
		when(identityXClient.consultarVerificacionRostro(any(String.class))).thenThrow( new IdentityXException(ERROR_VERIFICACION_AUTENTICACION_REQUEST_IDENTITY_X));
		perfilService.consultarVerificacionRostro(consultarVerificacionRostroPersonaRequest, X_OPERATION_ID_VALUE);
		fail();
	}
	
	@Test(expected = IdentityXException.class)
	public void debeValidarRostro3DFlPersonaUsuarioNoEncontradoError() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
		ValidarRostroVideoPersonaRequest validarRostro3DFLPersonaRequest = mapper.readValue(jsonRequest, ValidarRostroVideoPersonaRequest.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenThrow( new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		perfilService.validarRostroVideoPersona(validarRostro3DFLPersonaRequest, X_OPERATION_ID_VALUE);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeValidarRostro3DFlPersonaCrearIdCheckError() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_SIN_IDCHECK_REQUEST);
		ValidarRostroVideoPersonaRequest validarRostro3DFLPersonaRequest = mapper.readValue(jsonRequest, ValidarRostroVideoPersonaRequest.class);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);
		when(identityXClient.crearIdCheck(any(User.class), any(String.class))).thenThrow( new IdentityXException(ERROR_CREAR_IDCHECK_IDENTITY_X));
		
		perfilService.validarRostroVideoPersona(validarRostro3DFLPersonaRequest, X_OPERATION_ID_VALUE);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeValidarRostroViedoPersonaParametroNoEncontradoError() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
		ValidarRostroVideoPersonaRequest validarRostro3DFLPersonaRequest = mapper.readValue(jsonRequest, ValidarRostroVideoPersonaRequest.class);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(perfilService.obtenerUsuario(any(String.class))).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(any(String.class), any(String.class), any(String.class)))
			.thenThrow( new IdentityXException(Errores.ERROR_POLITICA_NO_ENCONTRADA));
		
		perfilService.validarRostroVideoPersona(validarRostro3DFLPersonaRequest, X_OPERATION_ID_VALUE);
	}

	@Test(expected = IdentityXException.class)
	public void debeValidarRostroViedoPersonaChallengeError() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
		ValidarRostroVideoPersonaRequest validarRostro3DFLPersonaRequest = mapper.readValue(jsonRequest, ValidarRostroVideoPersonaRequest.class);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(any(String.class), any(String.class), any(String.class)))
			.thenReturn("test");
		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(any(String.class), any(String.class), any(String.class)))
			.thenReturn("globantEvalutationtest");
		when(identityXClient.crearVideo3DFL(any(String.class), any(String.class), any(String.class)))
			.thenThrow( new IdentityXException(Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X));
		
		perfilService.validarRostroVideoPersona(validarRostro3DFLPersonaRequest, X_OPERATION_ID_VALUE);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeValidarRostroViedoPersonaChallengeVacioSinIdError() throws Exception {
		String jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
		ValidarRostroVideoPersonaRequest validarRostro3DFLPersonaRequest = mapper.readValue(jsonRequest, ValidarRostroVideoPersonaRequest.class);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);
		when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(any(String.class), any(String.class), any(String.class)))
			.thenReturn("test");
		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(any(String.class), any(String.class), any(String.class)))
			.thenReturn("globantEvalutationtest");
		

		Create3DFLVideoChallengeResponse videoChallengeResponse = new Create3DFLVideoChallengeResponse();
		videoChallengeResponse.setItems(new LivenessChallenge[]{});
		when(identityXClient.crearVideo3DFL(any(String.class), any(String.class), any(String.class)))
			.thenReturn(Optional.of(videoChallengeResponse));
		
		perfilService.validarRostroVideoPersona(validarRostro3DFLPersonaRequest, X_OPERATION_ID_VALUE);
	}

    @Test
    public void debeValidarRostroVideoPersonaExitoso() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
  
      var validarRostro3DFLPersonaRequest =
          mapper.readValue(jsonRequest, ValidarRostroVideoPersonaRequest.class);
  
      var responseCrearPerfilUsuario =
          retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
  
      var usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
  
      when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);
  
      when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(
              any(String.class), any(String.class), any(String.class)))
          .thenReturn("test");
  
      when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(
              any(String.class), any(String.class), any(String.class)))
          .thenReturn("globantEvalutationtest");
  
      var idCheckResponse = new CreateEmptyIdCheckResponse();
      idCheckResponse.setIdCheck("idCheck");
  
      when(identityXClient.crearIdCheckByUserAndLivenessPolicy(
              any(User.class), anyString(), anyString()))
          .thenReturn(Optional.of(idCheckResponse));
  
      var videoChallengeResponse = new Create3DFLVideoChallengeResponse();
      var livenessChallenge = new LivenessChallenge();
      livenessChallenge.setId("1122");
      videoChallengeResponse.setItems(new LivenessChallenge[] {livenessChallenge});
  
      when(identityXClient.getVideo3DFL(anyString(), anyString(), anyString()))
          .thenReturn(Optional.of(videoChallengeResponse));
  
      var submit3DFLVideoResponse = new Submit3DFLVideoResponse();
      submit3DFLVideoResponse.setProcessingStatus(
          Validacion3DFL.VALIDACION_VIDEO_STATUS_PROCESAMIENTO_PROCESSED.getValue());
  
      when(identityXClient.enviarVideo3DFL(anyString(), anyString(), anyString(), anyString()))
          .thenReturn(Optional.of(submit3DFLVideoResponse));
  
      when(datosGeneralesService.obtenerConfiguracionValidacionRenaper3dflIdentityX(
              anyString(), anyString(), anyString()))
          .thenReturn("true");
  
      var faces = new GetFacesResponse();
      var face = new Face();
      face.setId("Face1");
      faces.setItems(new Face[] {face});
      when(identityXClient.getFaces(any(User.class), anyString())).thenReturn(Optional.of(faces));
  
      when(identityXClient.getImagen(any(User.class), anyString(), anyString()))
          .thenReturn(Optional.of(new GetImagenResponse()));
  
      when(datosGeneralesService.obtenerConfiguracionUmbralRenaper3dflIdentityX(
              anyString(), anyString(), anyString()))
          .thenReturn(90);
  
      var validarRostroRenaperResponse = new ValidarRostroRenaperResponse();
      validarRostroRenaperResponse.setMatch(true);
      validarRostroRenaperResponse.setConfidence(99);
  
      when(enrolamientoProxyService.validarRostro(
              any(ValidarRostroRenaperRequest.class), anyString()))
          .thenReturn(ResponseEntity.ok(new Respuesta<>(validarRostroRenaperResponse)));
  
      var response =
          perfilService.validarRostroVideoPersona(
              validarRostro3DFLPersonaRequest, X_OPERATION_ID_VALUE);
      
      assertNotNull(response);
      assertNotNull(response.getStatus());
      assertNotNull(response.getRostro());
      assertNotNull(response.getRostro().getConfidence());
      assertNotNull(response.getRostro().getMatch());
    }
    
    @Test
    public void debeValidarRostroVideoPersonaSinReNaPerExitoso() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROSTRO_VIDEO_PERSONA_IDENTITYX_REQUEST);
  
      var validarRostro3DFLPersonaRequest =
          mapper.readValue(jsonRequest, ValidarRostroVideoPersonaRequest.class);
  
      var responseCrearPerfilUsuario =
          retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
  
      var usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
  
      when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);
  
      when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(
              any(String.class), any(String.class), any(String.class)))
          .thenReturn("test");
  
      when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(
              any(String.class), any(String.class), any(String.class)))
          .thenReturn("globantEvalutationtest");
  
      var idCheckResponse = new CreateEmptyIdCheckResponse();
      idCheckResponse.setIdCheck("idCheck");
  
      when(identityXClient.crearIdCheckByUserAndLivenessPolicy(
              any(User.class), anyString(), anyString()))
          .thenReturn(Optional.of(idCheckResponse));
  
      var videoChallengeResponse = new Create3DFLVideoChallengeResponse();
      var livenessChallenge = new LivenessChallenge();
      livenessChallenge.setId("1122");
      videoChallengeResponse.setItems(new LivenessChallenge[] {livenessChallenge});
  
      when(identityXClient.getVideo3DFL(anyString(), anyString(), anyString()))
          .thenReturn(Optional.of(videoChallengeResponse));
  
      var submit3DFLVideoResponse = new Submit3DFLVideoResponse();
      submit3DFLVideoResponse.setProcessingStatus(
          Validacion3DFL.VALIDACION_VIDEO_STATUS_PROCESAMIENTO_PROCESSED.getValue());
  
      when(identityXClient.enviarVideo3DFL(anyString(), anyString(), anyString(), anyString()))
          .thenReturn(Optional.of(submit3DFLVideoResponse));
  
      when(datosGeneralesService.obtenerConfiguracionValidacionRenaper3dflIdentityX(
              anyString(), anyString(), anyString()))
          .thenReturn("false");
  
      var faces = new GetFacesResponse();
      var face = new Face();
      face.setId("Face1");
      faces.setItems(new Face[] {face});
      when(identityXClient.getFaces(any(User.class), anyString())).thenReturn(Optional.of(faces));
  
      when(identityXClient.getImagen(any(User.class), anyString(), anyString()))
          .thenReturn(Optional.of(new GetImagenResponse()));
  
      when(datosGeneralesService.obtenerConfiguracionUmbralRenaper3dflIdentityX(
              anyString(), anyString(), anyString()))
          .thenReturn(90);
  
      var validarRostroRenaperResponse = new ValidarRostroRenaperResponse();
      validarRostroRenaperResponse.setMatch(true);
      validarRostroRenaperResponse.setConfidence(99);
  
      when(enrolamientoProxyService.validarRostro(
              any(ValidarRostroRenaperRequest.class), anyString()))
          .thenReturn(ResponseEntity.ok(new Respuesta<>(validarRostroRenaperResponse)));
  
      var response =
          perfilService.validarRostroVideoPersona(
              validarRostro3DFLPersonaRequest, X_OPERATION_ID_VALUE);
      
      assertNotNull(response);
      assertNotNull(response.getStatus());
      assertNull(response.getRostro());
    }

    @Test
    public void debeConsultarPersonaTodasLasImagenesExitoso() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
  
      var request = mapper.readValue(jsonRequest, ConsultarPersonaRequest.class);
      request.setIndicador("1");
      
      var responseCrearPerfilUsuario =
          retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
      var usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
      var face = new FaceData();
      var dataHolder = new DataHolder();
      dataHolder.setHref("");
      face.setSensitiveData(dataHolder);
      usuario.setFace(face);
  
      when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
      when(identityXClient.getUserByHref(usuario.getHref())).thenReturn(Optional.of(usuario));
  
      var sensitiveData = new GetSensitiveDataResponse();
      sensitiveData.setValue("selfie");
      when(identityXClient.getSensitiveData(anyString())).thenReturn(Optional.of(sensitiveData));
  
      var dniImage = new DniImageData();
      dniImage.setFrente("frente");
      dniImage.setDorso("dorso");
      when(identityXClient.getDniImage(any(User.class))).thenReturn(Optional.of(dniImage));
  
      var response = perfilService.consultarPersona(request, X_OPERATION_ID_VALUE);
  
      assertNotNull(response);
      List<ImagenResponse> imagenes = response.getImagenes();
      assertEquals(3, imagenes.size());
      assertTrue(imagenes.stream().filter(e -> e.getTipo() == TipoImagen.SF).findFirst().isPresent());
      assertTrue(imagenes.stream().filter(e -> e.getTipo() == TipoImagen.DF).findFirst().isPresent());
      assertTrue(imagenes.stream().filter(e -> e.getTipo() == TipoImagen.DD).findFirst().isPresent());
    }
  
    @Test
    public void debeConsultarPersonaSoloSelfieExitoso() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
  
      var request = mapper.readValue(jsonRequest, ConsultarPersonaRequest.class);
      request.setIndicador("2");
  
      var responseCrearPerfilUsuario =
          retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
      var usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
      var face = new FaceData();
      var dataHolder = new DataHolder();
      dataHolder.setHref("");
      face.setSensitiveData(dataHolder);
      usuario.setFace(face);
  
      when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
      when(identityXClient.getUserByHref(usuario.getHref())).thenReturn(Optional.of(usuario));
      
      var sensitiveData = new GetSensitiveDataResponse();
      sensitiveData.setValue("selfie");
      when(identityXClient.getSensitiveData(anyString())).thenReturn(Optional.of(sensitiveData));
      
      var response = perfilService.consultarPersona(request, X_OPERATION_ID_VALUE);
  
      assertNotNull(response);
      List<ImagenResponse> imagenes = response.getImagenes();
      assertEquals(1, imagenes.size());
      assertTrue(imagenes.stream().filter(e -> e.getTipo() == TipoImagen.SF).findFirst().isPresent());
    }
  
    @Test
    public void debeConsultarPersonaSoloDNIExitoso() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
  
      var request = mapper.readValue(jsonRequest, ConsultarPersonaRequest.class);
      request.setIndicador("3");
  
      var responseCrearPerfilUsuario =
          retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
      var usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
  
      when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);
      when(identityXClient.getUserByHref(usuario.getHref())).thenReturn(Optional.of(usuario));
  
      var dniImage = new DniImageData();
      dniImage.setFrente("frente");
      dniImage.setDorso("dorso");
      when(identityXClient.getDniImage(any(User.class))).thenReturn(Optional.of(dniImage));
  
      var response = perfilService.consultarPersona(request, X_OPERATION_ID_VALUE);
  
      assertNotNull(response);
      List<ImagenResponse> imagenes = response.getImagenes();
      assertEquals(2, imagenes.size());
      assertTrue(imagenes.stream().filter(e -> e.getTipo() == TipoImagen.DF).findFirst().isPresent());
      assertTrue(imagenes.stream().filter(e -> e.getTipo() == TipoImagen.DD).findFirst().isPresent());
    }

    @Test(expected = IdentityXException.class)
    public void debeConsultarPersonaIdxRestException() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
      var request = mapper.readValue(jsonRequest, ConsultarPersonaRequest.class);
  
      when(identityXClient.obtenerRegistroUsuario(anyString()))
          .thenThrow(new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
  
      perfilService.consultarPersona(request, X_OPERATION_ID_VALUE);
    }
    
    @Test(expected = IdentityXException.class)
    public void debeConsultarPersonaNullPointerException() throws Exception {
      var jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_PERSONA_IDENTITYX_REQUEST);
      var request = mapper.readValue(jsonRequest, ConsultarPersonaRequest.class);
  
      when(identityXClient.obtenerRegistroUsuario(anyString()))
          .thenThrow(new NullPointerException());
  
      perfilService.consultarPersona(request, X_OPERATION_ID_VALUE);
    }
}

