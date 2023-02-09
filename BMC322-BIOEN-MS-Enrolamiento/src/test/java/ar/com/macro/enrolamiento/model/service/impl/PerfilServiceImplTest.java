package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.constant.Errores.ERROR_REGISTRAR_USUARIO_APLICACION_IDENTITY_X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import com.daon.identityx.rest.model.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.CrearPerfilRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ValidarRolUsuarioRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.CrearPerfilResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.CrearEvaluacionPerfilRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.CrearEvaluacionPerfilResponse;
import ar.com.macro.enrolamiento.model.client.activedirectory.ActiveDirectoryService;
import ar.com.macro.enrolamiento.model.client.identityx.IdentityXClient;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEvaluationResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.Item;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.Results;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.PerfilService;
import ar.com.macro.exceptions.GeneralException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.ObtenerDatosGeneralesCompuestosException;

@RunWith(SpringRunner.class)
public class PerfilServiceImplTest {

	private static final String X_OPERATION_ID = "8025F117-B821-480D-99CD-799277614A24";

    private PerfilService perfilService;
	
	@Spy
	private ObjectMapper mapper;
	
	@Mock
	private IdentityXClient identityXClient;

	@Mock
	private DatosGeneralesService datosGeneralesService;
	
	@Mock
    private ActiveDirectoryService activeDirectoryService;
	
	@Mock
    private Environment env;

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

	@Value("${identityx.authetication.id.sufijo}")
	private String sufijoIdAuthetication;

	@Value("${identityx.authetication.request.description}")
	private String autheticationRequestDescriptionTemplate;

	@Before
	public void init() {
		this.perfilService = new PerfilServiceImpl(
				identityXClient,
				datosGeneralesService,
				activeDirectoryService,
				env,
				referenceIdSeparador,
				nombreMicroServicioDatosGenerales,
				parametroConfigPoliticaEvaluacionIdentityX,
				parametroMatchCreacionEvaluacion,
				parametroConfigAplicacionIdentityX,
				sufijoIdRegistracion,
				parametroConfigPoliticaIdentityX,
				sufijoIdAuthetication,
				autheticationRequestDescriptionTemplate);
	}
	
	@Test
	public void debeCrearPerfilUsuarioExito() throws Exception, JsonProcessingException {
		
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseCrearPerfilUsuarioIdentityX = mapper.readValue(responseCrearPerfilUsuario, User.class);
		
		String jsonRequest = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_REQUEST);
		CrearPerfilRequest crearPerfilUsuarioRequest = mapper.readValue(jsonRequest, CrearPerfilRequest.class);
		
		when(identityXClient.crearPerfilUsuario(any(User.class))).thenReturn(responseCrearPerfilUsuarioIdentityX);
		
		CrearPerfilResponse crearPerfilUsuarioResponse = perfilService.crearPerfilUsuario(crearPerfilUsuarioRequest);
		assertNotNull(crearPerfilUsuarioResponse);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeCrearPerfilUsuarioIdentityXException() throws Exception, JsonProcessingException {
		
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
		CrearEvaluacionPerfilResponse crearEvaluacionPerfilResponse = perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,X_OPERATION_ID);
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
		CrearEvaluacionPerfilResponse crearEvaluacionPerfilResponse = perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,X_OPERATION_ID);
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
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,X_OPERATION_ID);
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
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,X_OPERATION_ID);
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
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,X_OPERATION_ID);
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
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,X_OPERATION_ID);
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
		perfilService.crearEvaluacion(crearEvaluacionPerfilRequest,X_OPERATION_ID);
	}

    @Test
    public void validarRolUsuarioExito() throws JsonMappingException, JsonProcessingException {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_REQUEST);
      var validarRolRequest = mapper.readValue(jsonRequest, ValidarRolUsuarioRequest.class);
  
      when(activeDirectoryService.getAccessToken()).thenReturn("accessToken");
      
      when(env.getProperty(anyString())).thenReturn("");
  
      when(activeDirectoryService.isUserInGroup(anyString(), anyString(), anyString()))
          .thenReturn(true);
  
      var response = perfilService.validarRolUsuario(validarRolRequest, X_OPERATION_ID);
  
      assertNotNull(response);
      assertEquals(4, response.getRoles().size());
    }
    
    @Test
    public void validarRolUsuarioRespuestaSinRolesExito() throws JsonMappingException, JsonProcessingException {
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_REQUEST);
      var validarRolRequest = mapper.readValue(jsonRequest, ValidarRolUsuarioRequest.class);
  
      when(activeDirectoryService.getAccessToken()).thenReturn("accessToken");
      
      when(env.getProperty(anyString())).thenReturn("");
  
      when(activeDirectoryService.isUserInGroup(anyString(), anyString(), anyString()))
          .thenReturn(false);
  
      var response = perfilService.validarRolUsuario(validarRolRequest, X_OPERATION_ID);
  
      assertNotNull(response);
      assertEquals(0, response.getRoles().size());
    }

    @Test(expected = GeneralException.class)
    public void validarRolUsuarioAccessTokenException()
        throws JsonMappingException, JsonProcessingException {
      
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_REQUEST);
      var validarRolRequest = mapper.readValue(jsonRequest, ValidarRolUsuarioRequest.class);
  
      when(activeDirectoryService.getAccessToken())
          .thenThrow(new GeneralException(Errores.ERROR_VALIDANDO_USUARIO_GRUPO_AD));
  
      perfilService.validarRolUsuario(validarRolRequest, X_OPERATION_ID);
    }
  
    @Test(expected = GeneralException.class)
    public void validarRolUsuarioisUserInGroupException()
        throws JsonMappingException, JsonProcessingException {
      
      var jsonRequest = retrieveBody(Constantes.JSON_VALIDAR_ROL_USUARIO_REQUEST);
      var validarRolRequest = mapper.readValue(jsonRequest, ValidarRolUsuarioRequest.class);
  
      when(activeDirectoryService.getAccessToken()).thenReturn("accessToken");
  
      when(env.getProperty(anyString())).thenReturn("");
  
      when(activeDirectoryService.isUserInGroup(anyString(), anyString(), anyString()))
          .thenThrow(new GeneralException(Errores.ERROR_VALIDANDO_USUARIO_GRUPO_AD));
  
      perfilService.validarRolUsuario(validarRolRequest, X_OPERATION_ID);
    }
}
