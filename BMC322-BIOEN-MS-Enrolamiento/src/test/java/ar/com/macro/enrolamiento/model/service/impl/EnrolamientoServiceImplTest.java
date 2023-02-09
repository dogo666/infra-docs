package ar.com.macro.enrolamiento.model.service.impl;

import static ar.com.macro.commons.utils.test.TestUtil.retrieveBody;
import static ar.com.macro.constant.Errores.*;
import static ar.com.macro.constant.Errores.ERROR_OBTENER_USUARIO_IDENTITY_X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ar.com.macro.commons.utils.normalization.ReglaNormalizacionUtil;
import ar.com.macro.constant.Errores;
import ar.com.macro.constants.Constantes;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.ConsultarEnrolamientoRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.ConsultarEnrolamientoResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.ConsultarDatosDniRequest;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.response.ConsultarDatosDniResponse;
import ar.com.macro.enrolamiento.model.client.daonengine.DaonEngineClient;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.*;
import ar.com.macro.enrolamiento.model.client.datapower.DataPowerClient;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.BothSidesDocumentRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.request.EliminarPerfilUsuarioRequest;
import ar.com.macro.enrolamiento.domain.enrolamiento.rest.dto.response.LeerPdf417DniResponse;
import ar.com.macro.enrolamiento.domain.identityx.rest.dto.request.EnrolarRostroRequest;
import ar.com.macro.enrolamiento.model.client.esb.EsbClient;
import ar.com.macro.enrolamiento.model.client.identityx.IdentityXClient;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.CreateEmptyIdCheckResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.GetOcrProcessingDataResponse;
import ar.com.macro.enrolamiento.model.client.identityx.rest.dto.SubmitBothSidesDocumentResponse;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.EnrolamientoService;
import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.enrolamiento.model.service.dto.Mano;
import ar.com.macro.enrolamiento.model.service.dto.ManosServiceDto;
import ar.com.macro.enrolamiento.model.service.mapper.ConfiguracionHuellasMapper;
import ar.com.macro.enrolamiento.model.service.mapper.EnrolamientoServiceMapper;
import ar.com.macro.enrolamiento.model.service.mapper.TraduccionHuellasDaonARenaperMapper;
import ar.com.macro.exceptions.ConsultarDatosDniIdentityXException;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.exceptions.UsuarioNoEncontradoIdentityXException;
import com.daon.identityx.rest.model.pojo.Application;
import com.daon.identityx.rest.model.pojo.Policy;
import com.daon.identityx.rest.model.pojo.Registration;
import com.daon.identityx.rest.model.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.identityx.clientSDK.collections.DataSampleCollection;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EnrolamientoServiceImplTest {

	@Spy
	private ObjectMapper mapper;

	private Gson gson;

	private EnrolamientoService enrolamientoService;

	@Value("${macro.microservicio.nombre}")
	private String nombreMicroservicio;

	@Value("${daonengine.nombre.parametros.datos.generales:macro-enrolamiento}")
	private String nombreMicroServicioDatosGenerales;

	@Value("${identityx.ocr.indice.sexo:12}")
	private String ocrSexo;

	@Value("${identityx.ocr.indice.numero.documento:2}")
	private String ocrNumeroDocumento;

	@Value("${identityx.ocr.indice.nombre.apellidos:25}")
	private String ocrNombreApellidos;

	@Value("${identityx.ocr.indice.apellidos:8}")
	private String ocrApellidos;

	@Value("${identityx.ocr.indice.nombres:9}")
	private String ocrNombres;

	@Value("${identityx.ocr.indice.numerotramite:7,193}")
	private String ocrNumeroTramite;

	@Mock
	private IdentityXClient identityXClient;

	@Mock
	private EsbClient esbClient;

	@Mock
	private ReglaNormalizacionUtil reglaNormalizacionUtil;

	@Mock
	private DatosGeneralesService datosGeneralesService;

	@Mock
	private EnrolamientoServiceMapper enrolamientoServiceMapper;

	@Mock
	private DataPowerClient dataPowerClient;

	@Mock
	private DaonEngineClient daonEngineClient;

	@Mock
	private TraduccionHuellasDaonARenaperMapper traduccionHuellasDaonARenaperMapper;

	@Mock
	private ConfiguracionHuellasMapper configuracionHuellasMapper;

	@Value("${daonengine.nombre.parametros.daon}")
	private String nombreParametrosDaon;

	@Value("${daonengine.nombre.parametros.renaper}")
	private String nombreParametrosRenaper;

	@Value("${huellaDaonEngineService.xOperationID}")
	private String xOperationID;

	@Value("${daonengine.useridentifier}")
	private String applicationUserIdentifier;

	@Value("${daonengine.domainidentifier}")
	private String defaultDomainIdentifier;

	@Value("${daonengine.identityidentifier}")
	private String pathIdentityIdentifier;

	@Value("${identityx.ocr.indice.tiempoespera:2000}")
	private int tiempoEsperaGetDni;

	@Value("${identityx.ocr.indice.intentosmaximos:3}")
	private int intentosMaximosGetDni;

	@Before
	public void init() {
		gson = new GsonBuilder().create();
		this.enrolamientoService = new EnrolamientoServiceImpl(
				nombreMicroservicio,
				ocrSexo,
				ocrNumeroDocumento,
				ocrNombreApellidos,
				ocrApellidos,
				ocrNombres,
				ocrNumeroTramite,
				nombreMicroServicioDatosGenerales,
				identityXClient,
				esbClient,
				reglaNormalizacionUtil,
				datosGeneralesService,
				enrolamientoServiceMapper,
				dataPowerClient,
				daonEngineClient,
				traduccionHuellasDaonARenaperMapper,
				configuracionHuellasMapper,
				nombreParametrosDaon,
				nombreParametrosRenaper,
				applicationUserIdentifier,
				defaultDomainIdentifier,
				pathIdentityIdentifier,
				tiempoEsperaGetDni,
				intentosMaximosGetDni);
	}

	@Test
	public void debeEliminarPerfilUsuarioExito() throws Exception, JsonProcessingException {

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String jsonRequest = retrieveBody(Constantes.JSON_ELIMINAR_PERFIL_USUARIO_REQUEST);
		EliminarPerfilUsuarioRequest eliminarPerfilUsuarioRequest = mapper.readValue(jsonRequest,
				EliminarPerfilUsuarioRequest.class);

		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");

		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);

		enrolamientoService.eliminarPerfilUsuario(eliminarPerfilUsuarioRequest);

		verify(identityXClient).eliminarPerfilUsuario(usuario);
	}

	@Test(expected = IdentityXException.class)
	public void debeEliminarPerfilUsuarioIdentityXException() throws Exception, JsonProcessingException {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String jsonRequest = retrieveBody(Constantes.JSON_ELIMINAR_PERFIL_USUARIO_REQUEST);
		EliminarPerfilUsuarioRequest eliminarPerfilUsuarioRequest = mapper.readValue(jsonRequest,
				EliminarPerfilUsuarioRequest.class);

		User usuario = new User();
		usuario.setUserId("abc");
		usuario.setId("QTAzEQAqVF64VRA__azO8o5S-g");

		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(usuario);

		doThrow(
				new IdentityXException(Constantes.ERROR_ELIMINAR_PERFIL_USUARIO_CODIGO,
						Constantes.ERROR_ELIMINAR_PERFIL_USUARIO_MENSAJE))
				.when(identityXClient).eliminarPerfilUsuario(usuario);

		enrolamientoService.eliminarPerfilUsuario(eliminarPerfilUsuarioRequest);

	}

	@Test
	public void debeLeerPdf417DniExito() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_LEER_DNI_PERSONA_REQUEST_VALIDO);
		BothSidesDocumentRequest bothSidesDocumentRequest = mapper.readValue(jsonRequest,
				BothSidesDocumentRequest.class);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseCrearPerfilUsuarioIdentityX = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.crearPerfilUsuario(any(User.class))).thenReturn(responseCrearPerfilUsuarioIdentityX);

		String responseCrearIdCheck = retrieveBody(Constantes.JSON_CREAR_ID_CHECK_IDENTITY_X_RESPONSE);
		CreateEmptyIdCheckResponse crearIdCheckResponse = gson.fromJson(responseCrearIdCheck,
				CreateEmptyIdCheckResponse.class);
		when(identityXClient.crearIdCheck(any(User.class))).thenReturn(Optional.of(crearIdCheckResponse));

		String responseEnviarFrenteyDorsoDni = retrieveBody(Constantes.JSON_ENVIAR_FRENTE_Y_DORSO_IDENTITY_X_RESPONSE);
		SubmitBothSidesDocumentResponse enviarFrenteyDorsoDniResponse = gson.fromJson(responseEnviarFrenteyDorsoDni,
				SubmitBothSidesDocumentResponse.class);
		when(identityXClient.enviarFrenteyDorsoDni(any(), any(), any()))
				.thenReturn(Optional.of(enviarFrenteyDorsoDniResponse));

		String responseObtenerDatosProcesamientoOCR = retrieveBody(
				Constantes.JSON_OBTENER_DATOS_OCR_IDENTITY_X_RESPONSE);
		GetOcrProcessingDataResponse obtenerDatosProcesamientoOCRResponse = mapper
				.readValue(responseObtenerDatosProcesamientoOCR, GetOcrProcessingDataResponse.class);
		when(identityXClient.obtenerDatosProcesamientoOCR(any(), any(), any()))
				.thenReturn(Optional.of(obtenerDatosProcesamientoOCRResponse));

		LeerPdf417DniResponse leerPdf417DniResponse = enrolamientoService.leerPdf417Dni(bothSidesDocumentRequest);
		assertEquals(obtenerDatosProcesamientoOCRResponse.getBarcode().get(Constantes.IDENTITYX_OCR_NUMERO_DOCUMENTO)
				.getValue(), leerPdf417DniResponse.getCodigobarras().getNumero());
		assertEquals(obtenerDatosProcesamientoOCRResponse.getBarcode().get(Constantes.IDENTITYX_OCR_NOMBRE_Y_APELLIDOS)
				.getValue(), leerPdf417DniResponse.getCodigobarras().getNombrecompleto());
	}

	@Test(expected = IdentityXException.class)
	public void debeLeerPdf417DniCrearIdCheckException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_LEER_DNI_PERSONA_REQUEST_VALIDO);
		BothSidesDocumentRequest bothSidesDocumentRequest = mapper.readValue(jsonRequest,
				BothSidesDocumentRequest.class);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseCrearPerfilUsuarioIdentityX = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.crearPerfilUsuario(any(User.class))).thenReturn(responseCrearPerfilUsuarioIdentityX);

		when(identityXClient.crearIdCheck(any(User.class))).thenThrow(
				new IdentityXException(Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getCodigo(),
						Errores.ERROR_CREAR_IDCHECK_IDENTITY_X.getMensaje()));

		enrolamientoService.leerPdf417Dni(bothSidesDocumentRequest);
	}

	@Test(expected = IdentityXException.class)
	public void debeLeerPdf417DniEnviarDocumentoException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_LEER_DNI_PERSONA_REQUEST_VALIDO);
		BothSidesDocumentRequest bothSidesDocumentRequest = mapper.readValue(jsonRequest,
				BothSidesDocumentRequest.class);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseCrearPerfilUsuarioIdentityX = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.crearPerfilUsuario(any(User.class))).thenReturn(responseCrearPerfilUsuarioIdentityX);
		when(identityXClient.crearIdCheck(any(User.class))).thenThrow(
				new IdentityXException(Errores.ERROR_ENVIAR_DOCUMENTO_IDENTITY_X.getCodigo(),
						Errores.ERROR_ENVIAR_DOCUMENTO_IDENTITY_X.getMensaje()));

		enrolamientoService.leerPdf417Dni(bothSidesDocumentRequest);
	}

	@Test(expected = IdentityXException.class)
	public void debeLeerPdf417DniObtenerDatosOcrException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_LEER_DNI_PERSONA_REQUEST_VALIDO);
		BothSidesDocumentRequest bothSidesDocumentRequest = mapper.readValue(jsonRequest,
				BothSidesDocumentRequest.class);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseCrearPerfilUsuarioIdentityX = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.crearPerfilUsuario(any(User.class))).thenReturn(responseCrearPerfilUsuarioIdentityX);

		String responseCrearIdCheck = retrieveBody(Constantes.JSON_CREAR_ID_CHECK_IDENTITY_X_RESPONSE);
		CreateEmptyIdCheckResponse crearIdCheckResponse = gson.fromJson(responseCrearIdCheck,
				CreateEmptyIdCheckResponse.class);
		when(identityXClient.crearIdCheck(any(User.class))).thenReturn(Optional.of(crearIdCheckResponse));

		String responseEnviarFrenteyDorsoDni = retrieveBody(Constantes.JSON_ENVIAR_FRENTE_Y_DORSO_IDENTITY_X_RESPONSE);
		SubmitBothSidesDocumentResponse enviarFrenteyDorsoDniResponse = gson.fromJson(responseEnviarFrenteyDorsoDni,
				SubmitBothSidesDocumentResponse.class);
		when(identityXClient.enviarFrenteyDorsoDni(any(), any(), any()))
				.thenReturn(Optional.of(enviarFrenteyDorsoDniResponse));

		when(identityXClient.obtenerDatosProcesamientoOCR(any(), any(), any())).thenThrow(
				new IdentityXException(Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getCodigo(),
						Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getMensaje()));

		enrolamientoService.leerPdf417Dni(bothSidesDocumentRequest);
	}

	@Test
	public void debeConsultarDatosDniExito() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);
		ConsultarDatosDniRequest consultarDatosDniRequest = mapper.readValue(jsonRequest,
				ConsultarDatosDniRequest.class);

		String obtenerRegistroUsuarioResponse = retrieveBody(Constantes.JSON_OBTENER_REGISTRO_USUARIO_RESPONSE);
		User usuario = mapper.readValue(obtenerRegistroUsuarioResponse, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);

		String responseObtenerDatosProcesamientoOCR = retrieveBody(
				Constantes.JSON_OBTENER_DATOS_OCR_IDENTITY_X_RESPONSE);
		GetOcrProcessingDataResponse obtenerDatosProcesamientoOCRResponse = mapper
				.readValue(responseObtenerDatosProcesamientoOCR, GetOcrProcessingDataResponse.class);
		when(identityXClient.obtenerDatosProcesamientoOCR(any(String.class), any(String.class), any(String.class)))
				.thenReturn(Optional.of(obtenerDatosProcesamientoOCRResponse));

		ConsultarDatosDniResponse consultarDatosDniResponse = enrolamientoService
				.consultarDatosDni(consultarDatosDniRequest);
		assertEquals(obtenerDatosProcesamientoOCRResponse.getBarcode().get(Constantes.IDENTITYX_OCR_NUMERO_DOCUMENTO)
				.getValue(), consultarDatosDniResponse.getCodigobarras().getNumero());
		assertEquals(obtenerDatosProcesamientoOCRResponse.getBarcode().get(Constantes.IDENTITYX_OCR_NOMBRE_Y_APELLIDOS)
				.getValue(), consultarDatosDniResponse.getCodigobarras().getNombrecompleto());
	}

	@Test
	public void debeConsultarDatosDniMrzExito() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);
		ConsultarDatosDniRequest consultarDatosDniRequest = mapper.readValue(jsonRequest,
				ConsultarDatosDniRequest.class);

		String obtenerRegistroUsuarioResponse = retrieveBody(Constantes.JSON_OBTENER_REGISTRO_USUARIO_RESPONSE);
		User usuario = mapper.readValue(obtenerRegistroUsuarioResponse, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);

		String responseObtenerDatosProcesamientoOCR = retrieveBody(
				Constantes.JSON_OBTENER_DATOS_OCR_MRZ_IDENTITY_X_RESPONSE);
		GetOcrProcessingDataResponse obtenerDatosProcesamientoOCRResponse = mapper
				.readValue(responseObtenerDatosProcesamientoOCR, GetOcrProcessingDataResponse.class);
		when(identityXClient.obtenerDatosProcesamientoOCR(any(String.class), any(String.class), any(String.class)))
				.thenReturn(Optional.of(obtenerDatosProcesamientoOCRResponse));

		ConsultarDatosDniResponse consultarDatosDniResponse = enrolamientoService
				.consultarDatosDni(consultarDatosDniRequest);
		assertEquals(
				obtenerDatosProcesamientoOCRResponse.getMrz().get(Constantes.IDENTITYX_OCR_NUMERO_DOCUMENTO).getValue(),
				consultarDatosDniResponse.getCodigobarras().getNumero());
		assertEquals(obtenerDatosProcesamientoOCRResponse.getMrz().get(Constantes.IDENTITYX_OCR_NOMBRE_Y_APELLIDOS)
				.getValue(), consultarDatosDniResponse.getCodigobarras().getNombrecompleto());
	}

	@Test(expected = IdentityXException.class)
	public void debeConsultarDatosDniObtenerRegistroUsuarioException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);
		ConsultarDatosDniRequest consultarDatosDniRequest = mapper.readValue(jsonRequest,
				ConsultarDatosDniRequest.class);

		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenThrow(
				new IdentityXException(Errores.ERROR_OBTENER_USUARIO_IDENTITY_X.getCodigo(),
						Errores.ERROR_OBTENER_USUARIO_IDENTITY_X.getMensaje()));

		enrolamientoService.consultarDatosDni(consultarDatosDniRequest);
	}

	@Test(expected = ConsultarDatosDniIdentityXException.class)
	public void debeConsultarDatosDniObtenerDatosOcrException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_DATOS_DNI_REQUEST);
		ConsultarDatosDniRequest consultarDatosDniRequest = mapper.readValue(jsonRequest,
				ConsultarDatosDniRequest.class);

		String obtenerRegistroUsuarioResponse = retrieveBody(Constantes.JSON_OBTENER_REGISTRO_USUARIO_RESPONSE);
		User usuario = mapper.readValue(obtenerRegistroUsuarioResponse, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(usuario);

		when(identityXClient.obtenerDatosProcesamientoOCR(any(String.class), any(String.class), any(String.class)))
				.thenThrow(
						new IdentityXException(Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getCodigo(),
								Errores.ERROR_OBTENER_DATOS_OCR_IDENTITY_X.getMensaje()));

		enrolamientoService.consultarDatosDni(consultarDatosDniRequest);
	}

	@Test(expected = IdentityXException.class)
	public void debeEnrolarRostroBusquedaUsuarioException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenThrow(
				new IdentityXException(ERROR_BUSQUEDA_PERFIL_IDENTITY_X));

		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Test(expected = IdentityXException.class)
	public void debeEnrolarRostroAgregarRostroException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		String responseCrearPerfilUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseUsuario = mapper.readValue(responseCrearPerfilUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(responseUsuario);

		when(identityXClient.agregarImagenRostroaPerfilUsuario(any(User.class), any(String.class))).thenThrow(
				new IdentityXException(ERROR_AGREGAR_IMAGEN_ROSTRO_A_PERFIL_USUARIO_IDENTITY_X));

		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Ignore
	@Test(expected = IdentityXException.class)
	public void debeEnrolarRostroAplicacionNoEncontradaException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		String responseJsonUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseUsuario = mapper.readValue(responseJsonUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(responseUsuario);

		DataSampleCollection dsc = new DataSampleCollection();
		when(identityXClient.agregarImagenRostroaPerfilUsuario(any(User.class), any(String.class))).thenReturn(dsc);

		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(eq(Constantes.X_OPERATION_ID_VALUE),
				eq(nombreMicroServicioDatosGenerales), any())).thenThrow(
						new IdentityXException(ERROR_APLICACION_NO_ENCONTRADA));

		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Ignore
	@Test(expected = IdentityXException.class)
	public void debeEnrolarRostroErrorAplicacionIdentityXException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		String responseJsonUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseUsuario = mapper.readValue(responseJsonUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(responseUsuario);

		DataSampleCollection dsc = new DataSampleCollection();
		when(identityXClient.agregarImagenRostroaPerfilUsuario(any(User.class), any(String.class))).thenReturn(dsc);

		String aplicacionId = "appId";
		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(any(String.class), any(String.class),
				any(String.class))).thenReturn(aplicacionId);

		when(identityXClient.obtenerRegistroAplicacion(any(String.class))).thenThrow(
				new IdentityXException(ERROR_OBTENER_APLICACION_IDENTITY_X));

		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Ignore
	@Test(expected = IdentityXException.class)
	public void debeEnrolarRostroPoliticaNoEncontradaException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		String responseJsonUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseUsuario = mapper.readValue(responseJsonUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(responseUsuario);

		DataSampleCollection dsc = new DataSampleCollection();
		when(identityXClient.agregarImagenRostroaPerfilUsuario(any(User.class), any(String.class))).thenReturn(dsc);

		String aplicacionId = "appId";
		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(any(String.class), any(String.class),
				any(String.class))).thenReturn(aplicacionId);

		Application app = new Application();
		when(identityXClient.obtenerRegistroAplicacion(any(String.class))).thenReturn(Optional.of(app));

		when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(eq(Constantes.X_OPERATION_ID_VALUE),
				eq(nombreMicroServicioDatosGenerales), any())).thenThrow(
						new IdentityXException(ERROR_POLITICA_NO_ENCONTRADA));

		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Ignore
	@Test(expected = IdentityXException.class)
	public void debeEnrolarRostroErrorPoliticaIdentityXException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		String responseJsonUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseUsuario = mapper.readValue(responseJsonUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(responseUsuario);

		DataSampleCollection dsc = new DataSampleCollection();
		when(identityXClient.agregarImagenRostroaPerfilUsuario(any(User.class), any(String.class))).thenReturn(dsc);

		String aplicacionId = "appId";
		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(any(String.class), any(String.class),
				any(String.class))).thenReturn(aplicacionId);

		Application app = new Application();
		app.setHref("appHref");
		when(identityXClient.obtenerRegistroAplicacion(any(String.class))).thenReturn(Optional.of(app));

		String politicaId = "policyId";
		when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(eq(Constantes.X_OPERATION_ID_VALUE),
				eq(nombreMicroServicioDatosGenerales), any())).thenReturn(politicaId);

		when(identityXClient.obtenerRegistroPolitica(any(String.class), any(String.class))).thenThrow(
				new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X));

		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Ignore
	@Test(expected = IdentityXException.class)
	public void debeEnrolarRostroErrorRegistracionException() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		String responseJsonUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseUsuario = mapper.readValue(responseJsonUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(responseUsuario);

		DataSampleCollection dsc = new DataSampleCollection();
		when(identityXClient.agregarImagenRostroaPerfilUsuario(any(User.class), any(String.class))).thenReturn(dsc);

		String aplicacionId = "appId";
		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(any(String.class), any(String.class),
				any(String.class))).thenReturn(aplicacionId);

		Application app = new Application();
		app.setHref("appHref");
		when(identityXClient.obtenerRegistroAplicacion(any(String.class))).thenReturn(Optional.of(app));

		String politicaId = "policyId";
		when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(eq(Constantes.X_OPERATION_ID_VALUE),
				eq(nombreMicroServicioDatosGenerales), any())).thenReturn(politicaId);

		Policy policy = new Policy();
		when(identityXClient.obtenerRegistroPolitica(any(String.class), any(String.class)))
				.thenReturn(Optional.of(policy));

		when(identityXClient.crearRegistracion(any(String.class), any(User.class), any(Application.class))).thenThrow(
				new IdentityXException(ERROR_OBTENER_POLITICA_IDENTITY_X));
		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Test
	public void debeEnrolarRostroExito() throws Exception, JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_ENROLAR_ROSTRO_IDENTITYX_REQUEST);
		EnrolarRostroRequest enrolarRostroRequest = mapper.readValue(jsonRequest, EnrolarRostroRequest.class);

		String responseJsonUsuario = retrieveBody(Constantes.JSON_CREAR_PERFIL_USUARIO_IDENTITY_X_RESPONSE);
		User responseUsuario = mapper.readValue(responseJsonUsuario, User.class);
		when(identityXClient.obtenerRegistroUsuario(any(String.class))).thenReturn(responseUsuario);

		DataSampleCollection dsc = new DataSampleCollection();
		when(identityXClient.agregarImagenRostroaPerfilUsuario(any(User.class), any(String.class))).thenReturn(dsc);

		String aplicacionId = "appId";
		when(datosGeneralesService.obtenerConfiguracionAplicacionIdentityX(any(String.class), any(String.class),
				any(String.class))).thenReturn(aplicacionId);

		Application app = new Application();
		app.setHref("appHref");
		when(identityXClient.obtenerRegistroAplicacion(any(String.class))).thenReturn(Optional.of(app));

		String politicaId = "policyId";
		when(datosGeneralesService.obtenerConfiguracionPoliticaIdentityX(eq(Constantes.X_OPERATION_ID_VALUE),
				eq(nombreMicroServicioDatosGenerales), any())).thenReturn(politicaId);

		Policy policy = new Policy();
		when(identityXClient.obtenerRegistroPolitica(any(String.class), any(String.class)))
				.thenReturn(Optional.of(policy));

		when(identityXClient.crearRegistracion(any(String.class), any(User.class), any(Application.class)))
				.thenReturn(new Registration());
		enrolamientoService.enrolarRostro(enrolarRostroRequest, Constantes.X_OPERATION_ID_VALUE);
	}

	@Test
	public void consultarEnrolamientoOk() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_REQUEST);
		ConsultarEnrolamientoRequest consultarEnrolamientoRequest = mapper.readValue(jsonRequest,
				ConsultarEnrolamientoRequest.class);
		User user = mock(User.class);
		List<Integer> listInt = Collections.emptyList();
		ObjectFactory objectFactory = new ObjectFactory();
		GetIdentityResponse getIdentityResponse = objectFactory.createGetIdentityResponse();
		GetIdentityResponse.ResponseData responseData = objectFactory.createGetIdentityResponseResponseData();
		Identity identity = objectFactory.createIdentity();
		BiometricDataElement biometricDataElement = objectFactory.createBiometricDataElement();
		identity.getBiometricData().add(biometricDataElement);
		responseData.setIdentity(identity);
		getIdentityResponse.setResponseData(responseData);
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(user);
		when(daonEngineClient.obtenerIdentidad(any(GetIdentityRequest.class)))
				.thenReturn(Optional.of(getIdentityResponse));
		ConfiguracionHuellasDto configuracionHuellasDto = new ConfiguracionHuellasDto();
		Mano mano = new Mano();
		mano.setAnular(12);
		mano.setAnular(11);
		configuracionHuellasDto.setManoDerecha(mano);
		configuracionHuellasDto.setManoIzquierda(mano);
		when(datosGeneralesService.obtenerDatosGeneralesCompuestos(anyString(), anyString(), anyString()))
				.thenReturn(configuracionHuellasDto);
		ManosServiceDto manosServiceDto = new ManosServiceDto();
		manosServiceDto.setMD_ANULAR(12);
		manosServiceDto.setMD_MAYOR(13);
		when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class))).thenReturn(manosServiceDto);
		when(traduccionHuellasDaonARenaperMapper.traducirDedosDaonARenaper(anyList(), any(ManosServiceDto.class),
				any(ManosServiceDto.class))).thenReturn(listInt);
		ConsultarEnrolamientoResponse consultarEnrolamientoResponse = enrolamientoService
				.consultarEnrolamiento(consultarEnrolamientoRequest, xOperationID);
		assertNotNull(consultarEnrolamientoResponse);
	}

	@Test
	public void consultarEnrolamientoOkVacioBiometricData() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_REQUEST);
		ConsultarEnrolamientoRequest consultarEnrolamientoRequest = mapper.readValue(jsonRequest,
				ConsultarEnrolamientoRequest.class);
		User user = mock(User.class);
		List<Integer> listInt = Collections.emptyList();
		ObjectFactory objectFactory = new ObjectFactory();
		GetIdentityResponse getIdentityResponse = objectFactory.createGetIdentityResponse();
		GetIdentityResponse.ResponseData responseData = objectFactory.createGetIdentityResponseResponseData();
		Identity identity = objectFactory.createIdentity();
		BiometricDataElement biometricDataElement = objectFactory.createBiometricDataElement();
		biometricDataElement.setUsageQualifier(12);
		identity.getBiometricData().add(biometricDataElement);
		responseData.setIdentity(identity);
		getIdentityResponse.setResponseData(responseData);
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(user);
		when(daonEngineClient.obtenerIdentidad(any(GetIdentityRequest.class)))
				.thenReturn(Optional.of(getIdentityResponse));
		ConfiguracionHuellasDto configuracionHuellasDto = new ConfiguracionHuellasDto();
		Mano mano = new Mano();
		mano.setAnular(12);
		mano.setAnular(11);
		configuracionHuellasDto.setManoDerecha(mano);
		configuracionHuellasDto.setManoIzquierda(mano);
		when(datosGeneralesService.obtenerDatosGeneralesCompuestos(anyString(), anyString(), anyString()))
				.thenReturn(configuracionHuellasDto);
		ManosServiceDto manosServiceDto = new ManosServiceDto();
		manosServiceDto.setMD_ANULAR(12);
		manosServiceDto.setMD_MAYOR(13);
		when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class))).thenReturn(manosServiceDto);
		when(traduccionHuellasDaonARenaperMapper.traducirDedosDaonARenaper(anyList(), any(ManosServiceDto.class),
				any(ManosServiceDto.class))).thenReturn(listInt);
		ConsultarEnrolamientoResponse consultarEnrolamientoResponse = enrolamientoService
				.consultarEnrolamiento(consultarEnrolamientoRequest, xOperationID);
		assertNotNull(consultarEnrolamientoResponse);
		assertEquals(0,
				consultarEnrolamientoResponse.getConsultarEnrolamientoDaonEngineResponse().getCalificadores().size());
	}

	@Test
	public void consultarEnrolamientoOkVacioFaceEnrolled() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_REQUEST);
		ConsultarEnrolamientoRequest consultarEnrolamientoRequest = mapper.readValue(jsonRequest,
				ConsultarEnrolamientoRequest.class);
		List<Integer> listInt = Collections.emptyList();
		ObjectFactory objectFactory = new ObjectFactory();
		GetIdentityResponse getIdentityResponse = objectFactory.createGetIdentityResponse();
		GetIdentityResponse.ResponseData responseData = objectFactory.createGetIdentityResponseResponseData();
		Identity identity = objectFactory.createIdentity();
		BiometricDataElement biometricDataElement = objectFactory.createBiometricDataElement();
		biometricDataElement.setUsageQualifier(12);
		identity.getBiometricData().add(biometricDataElement);
		responseData.setIdentity(identity);
		getIdentityResponse.setResponseData(responseData);
		when(identityXClient.obtenerRegistroUsuario(anyString()))
				.thenThrow(new UsuarioNoEncontradoIdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		when(daonEngineClient.obtenerIdentidad(any(GetIdentityRequest.class)))
				.thenReturn(Optional.of(getIdentityResponse));
		ConfiguracionHuellasDto configuracionHuellasDto = new ConfiguracionHuellasDto();
		Mano mano = new Mano();
		mano.setAnular(12);
		mano.setAnular(11);
		configuracionHuellasDto.setManoDerecha(mano);
		configuracionHuellasDto.setManoIzquierda(mano);
		when(datosGeneralesService.obtenerDatosGeneralesCompuestos(anyString(), anyString(), anyString()))
				.thenReturn(configuracionHuellasDto);
		ManosServiceDto manosServiceDto = new ManosServiceDto();
		manosServiceDto.setMD_ANULAR(12);
		manosServiceDto.setMD_MAYOR(13);
		when(configuracionHuellasMapper.mapperEntity(any(ConfiguracionHuellasDto.class))).thenReturn(manosServiceDto);
		when(traduccionHuellasDaonARenaperMapper.traducirDedosDaonARenaper(anyList(), any(ManosServiceDto.class),
				any(ManosServiceDto.class))).thenReturn(listInt);
		ConsultarEnrolamientoResponse consultarEnrolamientoResponse = enrolamientoService
				.consultarEnrolamiento(consultarEnrolamientoRequest, xOperationID);
		assertNotNull(consultarEnrolamientoResponse);
		assertEquals(0, consultarEnrolamientoResponse.getConsultarEnrolamientoIdentityXResponse().getEnrolado());
	}

	@Test(expected = DaonEngineException.class)
	public void consultarEnrolamientoError() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_REQUEST);
		ConsultarEnrolamientoRequest consultarEnrolamientoRequest = mapper.readValue(jsonRequest,
				ConsultarEnrolamientoRequest.class);
		User user = mock(User.class);
		when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(user);
		when(daonEngineClient.obtenerIdentidad(any(GetIdentityRequest.class))).thenThrow(DaonEngineException.class);
		enrolamientoService.consultarEnrolamiento(consultarEnrolamientoRequest, xOperationID);
	}

	@Test(expected = IdentityXException.class)
	public void consultarEnrolamientoObtenerRegistroUsuarioError() throws JsonProcessingException {
		String jsonRequest = retrieveBody(Constantes.JSON_CONSULTAR_ENROLAMIENTO_REQUEST);
		ConsultarEnrolamientoRequest consultarEnrolamientoRequest = mapper.readValue(jsonRequest,
				ConsultarEnrolamientoRequest.class);
		when(identityXClient.obtenerRegistroUsuario(anyString()))
				.thenThrow(new IdentityXException(ERROR_OBTENER_USUARIO_IDENTITY_X));
		enrolamientoService.consultarEnrolamiento(consultarEnrolamientoRequest, xOperationID);
	}

}
