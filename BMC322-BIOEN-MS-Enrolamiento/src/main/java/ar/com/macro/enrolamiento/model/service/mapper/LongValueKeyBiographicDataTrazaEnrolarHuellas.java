package ar.com.macro.enrolamiento.model.service.mapper;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum LongValueKeyBiographicDataTrazaEnrolarHuellas {
    ClientDocumentStatus("documentovigente"),
    ClientRenaperFaceMatchScore("scorerenaper"),
    ClientFaceAcceptance("indrostrorenaper"),
    ClientFingerprintAcceptance("indhuelladaon");

    public final String label;

    public static Stream<LongValueKeyBiographicDataTrazaEnrolarHuellas> stream() {
        return Stream.of(LongValueKeyBiographicDataTrazaEnrolarHuellas.values());
    }
}
