package ar.com.macro.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * ImageType: tipo de la selfie enviada. Los tipos esperados son:
 *
 * ▪ SN: selfie con rostro neutral. (obligatoria)
 * ▪ SS: selfie con sonrisa. (opcional)
 * ▪ SCE: selfie con ojos cerrados. (opcional)
 * ▪ SBL: selfie con guiño izquierdo. (opcional)
 * ▪ SBR: selfie con guiño derecho.
 *
 */

@Getter
@AllArgsConstructor

public enum ImageType {
    SN,SS, SCE,SBL,SBR ;
    public static Stream<Status> stream() {
        return Stream.of(Status.values());
    }

}
