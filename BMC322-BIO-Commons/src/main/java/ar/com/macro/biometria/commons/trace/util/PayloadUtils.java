package ar.com.macro.biometria.commons.trace.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ar.com.macro.biometria.commons.trace.util.FunctionalDirectives.tryAndCatchAsSystemException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayloadUtils {

    private static final String BASE64 = "Base64";
    private static final String DATOS = "datos";

    private static final ObjectMapper OM = new ObjectMapper();

    public static String extraerDataSinBinarios(List<String> binarios,final Object requesbody) {
        JsonNode json = OM.convertValue(requesbody, JsonNode.class);
        removerBinarios(binarios, json);
        return json.toString();
    }

    public static String extraerDataSinBinarios(final List<String> binarios, final ResponseEntity<?> response) {

        JsonNode json = OM.convertValue(response, JsonNode.class);

        // reemplazo de binarios del proceso de trazabilidad
        JsonNode datos = json.findValue(DATOS);
        if (Objects.nonNull(datos)) {

            if (datos instanceof ArrayNode) {
                ((ArrayNode) datos).forEach(jn -> removerBinarios(binarios,jn));
            } else {
                removerBinarios(binarios,datos);
            }
        }

        return json.toString();
    }

    private static void removerBinarios(List<String> binarios , final JsonNode datos) {
        binarios
                .stream()
                .filter(b -> datos.findValue(b) != null)
                .forEach(
                        b -> {
                            change(datos, b, BASE64);
                        });
    }

    public static void change(JsonNode parent, String fieldName, String newValue) {
        if (parent.has(fieldName)) {
            ((ObjectNode) parent).put(fieldName, newValue);
        }
        for (JsonNode child : parent) {
            change(child, fieldName, newValue);
        }
    }

    public static <T> T unmarshal(final String raw, Class<T> tipo) {
        return tryAndCatchAsSystemException(() -> OM.readValue(raw, tipo), "error transformando json");
    }

    public static <T> T unmarshal(final byte[] raw, Class<T> tipo) {
        return tryAndCatchAsSystemException(() -> OM.readValue(raw, tipo), "error transformando json");
    }

    public static float[] toFloatArray(final List<Float> collection) {

        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        float[] array = new float[len];
        for (int i = 0; i < len; i++) {
            array[i] = (float) boxedArray[i];
        }
        return array;
    }
}
