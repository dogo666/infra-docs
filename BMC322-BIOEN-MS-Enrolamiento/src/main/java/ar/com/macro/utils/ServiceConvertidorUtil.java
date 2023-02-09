package ar.com.macro.utils;

import ar.com.macro.exceptions.ConvertirStringAJsonException;
import ar.com.macro.exceptions.DaonEngineException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static ar.com.macro.constant.Errores.ERROR_CONVERTIR_JSON_A_OBJETO;

@AllArgsConstructor
@Slf4j
public class ServiceConvertidorUtil {

    private static ObjectMapper mapObject = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertirObjetoEnMapa(Object object){
        return mapObject.convertValue(object, Map.class);
    }

    public static <T> T convertirStringJsonEnObjeto(String json, Class<T> valueType) {
        try {
            return mapObject.readValue(json,valueType);
        } catch (ConvertirStringAJsonException | JsonProcessingException e) {
            log.error(ERROR_CONVERTIR_JSON_A_OBJETO.getMensaje(), e);
            throw new DaonEngineException(ERROR_CONVERTIR_JSON_A_OBJETO.getCodigo(),ERROR_CONVERTIR_JSON_A_OBJETO.getMensaje());
        }
    }
}
