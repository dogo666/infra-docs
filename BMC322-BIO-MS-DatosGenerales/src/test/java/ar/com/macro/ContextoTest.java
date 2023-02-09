package ar.com.macro;

import ar.com.macro.commons.utils.rest.dto.Respuesta;
import ar.com.macro.datosgenerales.model.service.EnroladorService;
import ar.com.macro.datosgenerales.model.service.OperacionService;
import ar.com.macro.datosgenerales.model.service.ParametrosConfigService;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
    protected ParametrosConfigService parametrosConfigService;

    @MockBean
    protected OperacionService operacionService;

    @MockBean
    protected EnroladorService enroladorService;
    
    protected MvcResult ejecutarRequest(HttpMethod metodoHttp, String URI, String request, ResultMatcher statusRespuestaEsperado) throws Exception {

        return this.mockMvc.perform(request(metodoHttp, URI)
                .content(request).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(statusRespuestaEsperado)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
    
    protected MvcResult ejecutarRequestConCustomHeaders(HttpMethod metodoHttp, String URI, 
          ResultMatcher statusRespuestaEsperado, HttpHeaders headers) throws Exception {
      return this.mockMvc.perform(request(metodoHttp, URI)
              .headers(headers)            
              .contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(statusRespuestaEsperado)
              .andReturn();
    }

    protected <T> T obtenerRespuesta(MvcResult result, @SuppressWarnings("rawtypes") Class claseRespuesta) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(), mapper.getTypeFactory().constructParametricType(Respuesta.class, claseRespuesta));
    }
}
