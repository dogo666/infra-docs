package ar.com.macro.validacion.model.client.identityx.impl;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.daon.identityx.rest.model.pojo.Application;
import com.daon.identityx.rest.model.pojo.DataSample;
import com.daon.identityx.rest.model.pojo.Policy;
import com.daon.identityx.rest.model.pojo.Registration;
import com.daon.identityx.rest.model.pojo.User;
import com.daon.identityx.rest.model.support.DataSampleEvaluation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.identityx.clientSDK.TenantRepoFactory;
import com.identityx.clientSDK.collections.ApplicationCollection;
import com.identityx.clientSDK.collections.DataSampleCollection;
import com.identityx.clientSDK.collections.EnrollmentCollection;
import com.identityx.clientSDK.collections.PolicyCollection;
import com.identityx.clientSDK.exceptions.IdxRestException;
import com.identityx.clientSDK.queryHolders.ApplicationQueryHolder;
import com.identityx.clientSDK.queryHolders.PolicyQueryHolder;
import com.identityx.clientSDK.repositories.ApplicationRepository;
import com.identityx.clientSDK.repositories.PolicyRepository;
import com.identityx.clientSDK.repositories.RegistrationRepository;
import com.identityx.clientSDK.repositories.UserRepository;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.validacion.model.client.identityx.DigitalOnboardingServices;
import ar.com.macro.validacion.model.client.identityx.IdentityXClient;
import ar.com.macro.validacion.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.ClientDocumentCapture;
import ar.com.macro.validacion.model.client.identityx.rest.dto.Create3DFLVideoChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.CreateEvaluationChallengeResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.DocumentCollection;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetIdChecksResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.GetSensitiveDataResponse;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentRequest;
import ar.com.macro.validacion.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;

@RunWith(SpringRunner.class)
public class IdentityXClientImplTest {

	private IdentityXClient identityXClient;
	
	private Gson gson;
	
	@Value("${identityx.imagen.frente:FRONT}")
	private String tipoImagenFrente;
	
	@Value("${identityx.imagen.dorso:BACK}")
	private String tipoImagenDorso;
	
	private String formatoCapturaTarjeta;
	
	private String video3DFLSubtipo;
	private String video3DFLFormato;
	private Integer video3DFLChallengeInicio;
	private Integer video3DFLChallengeFin;

	private List<String> aplicacionesConValidacionJpg;
	
	@Spy
	private ObjectMapper mapper;
	
	@Mock
	private DigitalOnboardingServices digitalOnboardingServices;
	
	@MockBean
	private TenantRepoFactory tenantRepoFactory;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ApplicationRepository applicationRepository;
	
	@MockBean
	private PolicyRepository policyRepository;
	
	@MockBean
	private RegistrationRepository registrationRepository;
	
	@Before
	public void init() {
		gson = new GsonBuilder().create();
		identityXClient = new IdentityXClientImpl(tenantRepoFactory, digitalOnboardingServices, tipoImagenFrente, tipoImagenDorso, formatoCapturaTarjeta,
				video3DFLSubtipo, video3DFLFormato, video3DFLChallengeInicio, video3DFLChallengeFin,aplicacionesConValidacionJpg);
	}
	
	@Test
	public void debeCrearIdCheckExito() throws Exception {
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
		
		String responseCrearIdCheck = retrieveBody(Constantes.JSON_CREAR_ID_CHECK_IDENTITY_X_RESPONSE);		
		CreateEmptyIdCheckResponse crearIdCheckResponse = gson.fromJson(responseCrearIdCheck, CreateEmptyIdCheckResponse.class);

		when(digitalOnboardingServices.ejecutarCrearIdCheck(any(String.class), any(CreateEmptyIdCheckRequest.class))).thenReturn(Optional.of(crearIdCheckResponse));
		
		CreateEmptyIdCheckResponse respuesta = identityXClient.crearIdCheck(usuario).get();
		assertNotNull(respuesta);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeCrearIdCheckExcepcion() throws Exception {
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User usuario = mapper.readValue(responseCrearPerfilUsuario, User.class);

		when(digitalOnboardingServices.ejecutarCrearIdCheck(any(String.class), any(CreateEmptyIdCheckRequest.class))).thenThrow(
				new IdentityXException(Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getCodigo(), Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getMensaje()));
		
		identityXClient.crearIdCheck(usuario);
	}
	
	@Test
	public void debeEnviarFrenteyDorsoDniExito() throws Exception {
		
		String jsonRequest = retrieveBody(Constantes.JSON_LEER_DNI_PERSONA_REQUEST_VALIDO);
		BothSidesDocumentRequest bothSidesDocumentRequest = mapper.readValue(jsonRequest, BothSidesDocumentRequest.class);
		
		String responseEnviarFrenteyDorsoDni = retrieveBody(Constantes.JSON_ENVIAR_FRENTE_Y_DORSO_IDENTITY_X_RESPONSE);
		SubmitBothSidesDocumentResponse enviarFrenteyDorsoDniResponse = gson.fromJson(responseEnviarFrenteyDorsoDni, SubmitBothSidesDocumentResponse.class);
		when(digitalOnboardingServices.ejecutarEnviarDocumento(any(String.class), any(String.class), any(SubmitBothSidesDocumentRequest.class))).thenReturn(Optional.of(enviarFrenteyDorsoDniResponse));
		
		SubmitBothSidesDocumentResponse respuesta = identityXClient.enviarFrenteyDorsoDni("id", "idCheck", bothSidesDocumentRequest).get();
		assertNotNull(respuesta);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeEnviarFrenteyDorsoDniExcepcion() throws Exception {
		
		String jsonRequest = retrieveBody(Constantes.JSON_LEER_DNI_PERSONA_REQUEST_VALIDO);
		BothSidesDocumentRequest bothSidesDocumentRequest = mapper.readValue(jsonRequest, BothSidesDocumentRequest.class);
		
		when(digitalOnboardingServices.ejecutarEnviarDocumento(any(String.class), any(String.class), any(SubmitBothSidesDocumentRequest.class))).thenThrow(
				new IdentityXException(Errores.ERROR_ENVIAR_DOCUMENTO_IDENTITY_X.getCodigo(), Errores.ERROR_ENVIAR_DOCUMENTO_IDENTITY_X.getMensaje()));
		
		identityXClient.enviarFrenteyDorsoDni("id", "idCheck", bothSidesDocumentRequest);
	}
	
	@Test
	public void debeObtenerDatosProcesamientoOCRExito() throws Exception {
		
		String responseObtenerDatosProcesamientoOCR = retrieveBody(Constantes.JSON_OBTENER_DATOS_OCR_IDENTITY_X_RESPONSE);
		GetOcrProcessingDataResponse obtenerDatosProcesamientoOCRResponse = mapper.readValue(responseObtenerDatosProcesamientoOCR, GetOcrProcessingDataResponse.class);
		when(digitalOnboardingServices.ejecutarObtenerDatosOCR(any(String.class), any(String.class), any(String.class))).thenReturn(Optional.of(obtenerDatosProcesamientoOCRResponse));
		
		GetOcrProcessingDataResponse respuesta = identityXClient.obtenerDatosProcesamientoOCR("id", "idCheck", "idDocument").get();
		assertNotNull(respuesta);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeObtenerDatosProcesamientoOCRExcepcion() throws Exception {
		
		when(digitalOnboardingServices.ejecutarObtenerDatosOCR(any(String.class), any(String.class), any(String.class))).thenThrow(
				new IdentityXException(Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getCodigo(), Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getMensaje()));
		
		identityXClient.obtenerDatosProcesamientoOCR("id", "idCheck", "idDocument");
	}
	
	@Test(expected = IdentityXException.class)
	public void debeAgregarImagenRostroExcepcion() throws Exception {
		
		when(tenantRepoFactory.getUserRepo()).thenReturn(userRepository);
		when(userRepository.addData(any(User.class), any(DataSample.class))).thenThrow(new IdxRestException());		
		
		identityXClient.agregarImagenRostroaPerfilUsuario(new User(), "imagen");
	}
	
	@Test(expected = IdentityXException.class)
	public void debeAgregarImagenRostroErrorValidacionUsableExcepcion() throws Exception {
		
		when(tenantRepoFactory.getUserRepo()).thenReturn(userRepository);
		DataSampleCollection coleccion = new DataSampleCollection();
		DataSample dataSample = new DataSample();
		dataSample.setUsable(Boolean.FALSE);
		
		DataSampleEvaluation[] evaluaciones = new DataSampleEvaluation[1];
		DataSampleEvaluation evaluacion = new DataSampleEvaluation();
		evaluacion.setResultCode(0);
		evaluaciones[0] = evaluacion;
		dataSample.setEvaluations(evaluaciones);
		DataSample[] samples = new DataSample[1];
		samples[0] = dataSample;
		coleccion.setItems(samples);
		when(userRepository.addData(any(User.class), any(DataSample.class))).thenReturn(coleccion);		
		
		identityXClient.agregarImagenRostroaPerfilUsuario(new User(), "imagen");
	}
	
	@Test(expected = IdentityXException.class)
	public void debeAgregarImagenRostroErrorValidacionCodigoNoOkExcepcion() throws Exception {
		
		when(tenantRepoFactory.getUserRepo()).thenReturn(userRepository);
		DataSampleCollection coleccion = new DataSampleCollection();
		DataSample dataSample = new DataSample();
		dataSample.setUsable(Boolean.TRUE);
		
		DataSampleEvaluation[] evaluaciones = new DataSampleEvaluation[1];
		DataSampleEvaluation evaluacion = new DataSampleEvaluation();
		evaluacion.setResultCode(1);
		evaluaciones[0] = evaluacion;
		dataSample.setEvaluations(evaluaciones);
		DataSample[] samples = new DataSample[1];
		samples[0] = dataSample;
		coleccion.setItems(samples);
		when(userRepository.addData(any(User.class), any(DataSample.class))).thenReturn(coleccion);		
		
		identityXClient.agregarImagenRostroaPerfilUsuario(new User(), "imagen");
	}
	
	@Test
	public void debeAgregarImagenRostroExito() throws Exception {
		
		when(tenantRepoFactory.getUserRepo()).thenReturn(userRepository);
		DataSampleCollection coleccion = new DataSampleCollection();
		DataSample dataSample = new DataSample();
		dataSample.setUsable(Boolean.TRUE);
		
		DataSampleEvaluation[] evaluaciones = new DataSampleEvaluation[1];
		DataSampleEvaluation evaluacion = new DataSampleEvaluation();
		evaluacion.setResultCode(0);
		evaluaciones[0] = evaluacion;
		dataSample.setEvaluations(evaluaciones);
		DataSample[] samples = new DataSample[1];
		samples[0] = dataSample;
		coleccion.setItems(samples);
		when(userRepository.addData(any(User.class), any(DataSample.class))).thenReturn(coleccion);		
		
		DataSampleCollection coleccionResultado = identityXClient.agregarImagenRostroaPerfilUsuario(new User(), "imagen");
		assertEquals(1, coleccionResultado.getItems().length);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeObtenerRegistroAplicacionExcepcion() throws Exception {
		
		when(tenantRepoFactory.getApplicationRepo()).thenReturn(applicationRepository);
		when(applicationRepository.list(any(ApplicationQueryHolder.class))).thenThrow(new IdxRestException());		
		
		identityXClient.obtenerRegistroAplicacion("id");
	}
	
	@Test(expected = IdentityXException.class)
	public void debeObtenerRegistroAplicacionValidacionExcepcion() throws Exception {
		
		when(tenantRepoFactory.getApplicationRepo()).thenReturn(applicationRepository);
		Application aplicacion = new Application();
		aplicacion.setApplicationId("aplicacionId");
		ApplicationCollection coleccion = new ApplicationCollection();
		
		when(applicationRepository.list(any(ApplicationQueryHolder.class))).thenReturn(coleccion);		
		
		identityXClient.obtenerRegistroAplicacion("aplicacionId");
	}
	
	@Test
	public void debeObtenerRegistroAplicacionExito() throws Exception {
		
		when(tenantRepoFactory.getApplicationRepo()).thenReturn(applicationRepository);
		Application aplicacion = new Application();
		aplicacion.setApplicationId("aplicacionId");
		ApplicationCollection coleccion = new ApplicationCollection();
		Application[] aplicaciones = new Application[1];
		aplicaciones[0] = aplicacion;
		coleccion.setItems(aplicaciones);
		when(applicationRepository.list(any(ApplicationQueryHolder.class))).thenReturn(coleccion);		
		
		Optional<Application> optAplicacion = identityXClient.obtenerRegistroAplicacion("aplicacionId");
		assertEquals("aplicacionId", optAplicacion.get().getApplicationId());
	}
	
	@Test(expected = IdentityXException.class)
	public void debeObtenerRegistroPoliticaExcepcion() throws Exception {
		
		when(tenantRepoFactory.getPolicyRepo()).thenReturn(policyRepository);
		when(policyRepository.list(any(PolicyQueryHolder.class))).thenThrow(new IdxRestException());		
		
		identityXClient.obtenerRegistroPolitica("id", "appHref");
	}
	
	@Test(expected = IdentityXException.class)
	public void debeObtenerRegistroPoliticaValidacionExcepcion() throws Exception {
		
		when(tenantRepoFactory.getPolicyRepo()).thenReturn(policyRepository);
		Application aplicacion = new Application();
		aplicacion.setHref("appHref");
		PolicyCollection coleccion = new PolicyCollection();
		Policy politica = new Policy();
		politica.setPolicyId("policyId");
		politica.setApplication(aplicacion);
		Policy[] politicas = new Policy[1];
		politicas[0] = politica;
		coleccion.setItems(politicas);
		when(policyRepository.list(any(PolicyQueryHolder.class))).thenReturn(coleccion);		
		
		identityXClient.obtenerRegistroPolitica("policyId", "appHrefDistinto");
	}
	
	@Test
	public void debeObtenerRegistroPoliticaExito() throws Exception {
		
		when(tenantRepoFactory.getPolicyRepo()).thenReturn(policyRepository);
		Application aplicacion = new Application();
		aplicacion.setHref("appHref");
		PolicyCollection coleccion = new PolicyCollection();
		Policy politica = new Policy();
		politica.setPolicyId("policyId");
		politica.setApplication(aplicacion);
		Policy[] politicas = new Policy[1];
		politicas[0] = politica;
		coleccion.setItems(politicas);
		when(policyRepository.list(any(PolicyQueryHolder.class))).thenReturn(coleccion);		
		
		Optional<Policy> optPolitica = identityXClient.obtenerRegistroPolitica("policyId", "appHref");
		assertEquals("policyId", optPolitica.get().getPolicyId());
	}
	
	@Test(expected = IdentityXException.class)
	public void debeCrearRegistracionExcepcion() throws Exception {
		
		when(tenantRepoFactory.getRegistrationRepo()).thenReturn(registrationRepository);
		when(registrationRepository.create(any(Registration.class))).thenThrow(new IdxRestException());		
		
		identityXClient.crearRegistracion("registracionId", new User(), new Application());
	}
	
	@Test
	public void debeCrearRegistracionExito() throws Exception {
		
		when(tenantRepoFactory.getRegistrationRepo()).thenReturn(registrationRepository);
		when(registrationRepository.create(any(Registration.class))).thenReturn(new Registration());		
		
		identityXClient.crearRegistracion("registracionId", new User(), new Application());
	}

	@Test
	public void ejecutarCrearEvaluacion(){
		when(tenantRepoFactory.getPolicyRepo()).thenReturn(policyRepository);

	}
	
	@Test
	public void debeCrearVideo3DFLExito() throws Exception {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearVideoChallenge3DFL = retrieveBody(Constantes.JSON_CREAR_VIDEO_CHALLENGE_3DFL_IDENTITYX_RESPONSE);
		Create3DFLVideoChallengeResponse create3DFLVideoChallengeResponse = mapper.readValue(responseCrearVideoChallenge3DFL, Create3DFLVideoChallengeResponse.class);
		when(digitalOnboardingServices.ejecutar3DFLVideoChallenge(any(String.class), any(String.class), any(String.class))).thenReturn(Optional.of(create3DFLVideoChallengeResponse));
		
		Create3DFLVideoChallengeResponse respuesta = identityXClient.crearVideo3DFL("id", "idCheck", "livenessPolicyName").get();
		assertNotNull(respuesta);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeCrearVideo3DFLExcepcion() throws Exception {
		
		when(digitalOnboardingServices.ejecutar3DFLVideoChallenge(any(String.class), any(String.class), any(String.class))).thenThrow(
				new IdentityXException(Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X.getCodigo(), Errores.ERROR_3DFL_VIDEO_CHALLENGE_IDENTITY_X.getMensaje()));
		
		identityXClient.crearVideo3DFL("id", "idCheck", "livenessPolicyName");
	}
	
	@Test
	public void debeCrearEvaluacion3DFLExito() throws Exception {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearEvaluacion3DFL = retrieveBody(Constantes.JSON_CREAR_EVALUACION_3DFL_IDENTITYX_RESPONSE);
		CreateEvaluationChallengeResponse createEvaluationChallengeResponse = mapper.readValue(responseCrearEvaluacion3DFL, CreateEvaluationChallengeResponse.class);
		when(digitalOnboardingServices.ejecutarCrearEvaluacionChallenge(any(String.class), any(String.class), any(String.class))).thenReturn(Optional.of(createEvaluationChallengeResponse));
		
		CreateEvaluationChallengeResponse respuesta = identityXClient.crearEvaluacionChallenge("id", "idCheck", "evaluationPolicyName").get();
		assertNotNull(respuesta);
	}
	
	@Test(expected = IdentityXException.class)
	public void debeCrearEvaluacion3DFLExcepcion() throws Exception {
		
		when(digitalOnboardingServices.ejecutarCrearEvaluacionChallenge(any(String.class), any(String.class), any(String.class))).thenThrow(
				new IdentityXException(Errores.ERROR_CREAR_EVALUACION_IDENTITY_X.getCodigo(), Errores.ERROR_CREAR_EVALUACION_IDENTITY_X.getMensaje()));
		
		identityXClient.crearEvaluacionChallenge("id", "idCheck", "evaluationPolicyName");
	}

    @Test
    public void getDniImageExito() throws JsonMappingException, JsonProcessingException {
       var user =
          mapper.readValue(
              retrieveBody(Constantes.JSON_OBTENER_REGISTRO_USUARIO_RESPONSE), User.class);
      var enrollmentCollection =
          mapper.readValue(
              retrieveBody(Constantes.JSON_DNI_IMAGES_ENROLLMENTS), EnrollmentCollection.class);
      var sampleCollection =
          mapper.readValue(
              retrieveBody(Constantes.JSON_DNI_IMAGES_SAMPLES), DataSampleCollection.class);
      var idChecks =
          mapper.readValue(
              retrieveBody(Constantes.JSON_DNI_IMAGES_USER_ID_CHECKS), GetIdChecksResponse.class);
      var checkResponse =
          mapper.readValue(
              retrieveBody(Constantes.JSON_DNI_IMAGES_ID_CHECK), CreateEmptyIdCheckResponse.class);
      var documents =
          mapper.readValue(
              retrieveBody(Constantes.JSON_DNI_IMAGES_CLIENT_DOCUMENTS), DocumentCollection.class);
      var clientCapture =
          mapper.readValue(
              retrieveBody(Constantes.JSON_DNI_IMAGES_CLIENT_CAPTURE), ClientDocumentCapture.class);
      var sensitiveData =
          mapper.readValue(
              retrieveBody(Constantes.JSON_DNI_IMAGES_SENSITIVE_DATA),
              GetSensitiveDataResponse.class);
  
      when(digitalOnboardingServices.getEnrollments(anyString())).thenReturn(enrollmentCollection);
  
      when(digitalOnboardingServices.getSamples(anyString())).thenReturn(sampleCollection);
  
      when(digitalOnboardingServices.getIdchecksByUser(any(User.class), anyMap()))
          .thenReturn(Optional.of(idChecks));
  
      when(digitalOnboardingServices.getIdCheck(anyString())).thenReturn(checkResponse);
  
      when(digitalOnboardingServices.getDocuments(anyString())).thenReturn(documents);
  
      when(digitalOnboardingServices.getClientDocumentCapture(anyString())).thenReturn(clientCapture);
  
      when(digitalOnboardingServices.ejecutarGetSensitiveData(anyString()))
          .thenReturn(Optional.of(sensitiveData));
  
      var response = identityXClient.getDniImage(user);
  
      assertNotNull(response);
      assertTrue(response.isPresent());
      assertNotNull(response.get().getFrente());
      assertNotNull(response.get().getDorso());
    }

    @Test(expected = IdentityXException.class)
    public void getDniImageExcepcion() throws JsonMappingException, JsonProcessingException {
      var user =
          mapper.readValue(
              retrieveBody(Constantes.JSON_OBTENER_REGISTRO_USUARIO_RESPONSE), User.class);
  
      when(digitalOnboardingServices.getEnrollments(anyString()))
          .thenThrow(new IdentityXException(Errores.ERROR_GET_ENROLLMENTS_IDENTITY_X));
  
      identityXClient.getDniImage(user);
    }
}
