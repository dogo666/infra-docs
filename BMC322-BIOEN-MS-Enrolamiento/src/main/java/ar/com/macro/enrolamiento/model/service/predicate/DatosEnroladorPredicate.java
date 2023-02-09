package ar.com.macro.enrolamiento.model.service.predicate;

import ar.com.macro.biometria.commons.trace.feing.dto.response.GuardarEnroladorFeingResponse;
import ar.com.macro.commons.utils.rest.dto.Respuesta;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

public class DatosEnroladorPredicate {
    public static Boolean guardarDatosFeingEnroladorPredicate(Optional<ResponseEntity<Respuesta<GuardarEnroladorFeingResponse>>> ob){
        return (ob.isPresent() &&
                Objects.nonNull(ob.get().getBody()) &&
                Objects.nonNull(ob.get().getBody().getDatos()) &&
                Objects.nonNull(ob.get().getBody().getDatos().getStatus())) ? true : false;
    }
}
