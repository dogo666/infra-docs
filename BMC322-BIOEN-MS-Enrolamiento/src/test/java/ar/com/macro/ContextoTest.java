package ar.com.macro;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.commons.values.constants.comm.HeaderConstants;
import ar.com.macro.enrolamiento.model.service.DatosGeneralesService;
import ar.com.macro.enrolamiento.model.service.EnrolamientoService;
import ar.com.macro.enrolamiento.model.service.HuellaDaonEngineService;
import ar.com.macro.enrolamiento.model.service.PerfilService;
import ar.com.macro.enrolamiento.model.service.RenaperService;

/**
 * Clase padre para los tests que carga un solo contexto de pruebas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class ContextoTest {

    @Autowired
    protected MockMvc mockMvc;

    @Spy
    protected ObjectMapper mapper;

    @MockBean
    protected EnrolamientoService enrolamientoService;

    @MockBean
    protected PerfilService perfilService;

    @MockBean
    protected HuellaDaonEngineService huellaDaonEngineService;

    @MockBean
    protected RenaperService renaperService;

    @MockBean
    protected DatosGeneralesService datosGeneralesService;

    protected MvcResult ejecutarRequest(HttpMethod metodoHttp, String URI, String request,
            ResultMatcher statusRespuestaEsperado) throws Exception {

        return this.mockMvc.perform(request(metodoHttp, URI)
                .content(request).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    protected MvcResult ejecutarRequestConParamsNoBodyOpId(HttpMethod metodoHttp, String URI,
            MultiValueMap<String, String> params,
            ResultMatcher statusRespuestaEsperado, String headerValue) throws Exception {

        return this.mockMvc.perform(request(metodoHttp, URI)
                .header(HeaderConstants.HEADER_X_OPERATION_ID, headerValue)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    protected MvcResult ejecutarRequestNoParamsNoBodyOpId(HttpMethod metodoHttp, String URI,
            ResultMatcher statusRespuestaEsperado, String headerValue) throws Exception {

        return this.mockMvc.perform(request(metodoHttp, URI)
                .header(HeaderConstants.HEADER_X_OPERATION_ID, headerValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andReturn();
    }

    protected MvcResult ejecutarRequestConCustomHeaders(HttpMethod metodoHttp, String URI, String request,
            ResultMatcher statusRespuestaEsperado, HttpHeaders headers) throws Exception {
        return this.mockMvc.perform(request(metodoHttp, URI)
                .headers(headers)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andReturn();
    }

    protected MvcResult ejecutarPostConHeader(String URI, String request, ResultMatcher statusRespuestaEsperado,
            String xOperationIdValue) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .header(HeaderConstants.HEADER_X_OPERATION_ID, xOperationIdValue)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andReturn();
    }

    protected MvcResult ejecutarPostConHeaders(String URI, String request, ResultMatcher statusRespuestaEsperado,
            String xOperationIdValue, String xApplicationIdValue) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderConstants.HEADER_X_OPERATION_ID, xOperationIdValue);
        headers.add(HeaderConstants.HEADER_X_APPLICATION_ID, xApplicationIdValue);
        return this.mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .headers(HttpHeaders.writableHttpHeaders(headers))
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andReturn();
    }

    protected MvcResult ejecutarPostConHeaderResponseEmpty(String URI, String request,
            ResultMatcher statusRespuestaEsperado, String xOperationIdValue) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .header(HeaderConstants.HEADER_X_OPERATION_ID, xOperationIdValue)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andReturn();
    }

    protected <T> T obtenerRespuesta(MvcResult result, Class<?> claseRespuesta) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(),
                mapper.getTypeFactory().constructParametricType(Respuesta.class, claseRespuesta));
    }
}
