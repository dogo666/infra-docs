package ar.com.macro.enrolamiento.model.service.mapper;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum StringValueKeyBiographicDataTrazaEnrolarHuellas {
    TipoDoc("tipodocumento"),
    DNI("numerodocumento"),
    Gender("genero"),
    ClientCUIL("nroDocumentoTributario"),
    ClientIdCobis("idCliente"),
    EnrolledDate("enrolledDate"),
    EnrolledBranch("sucursal"),
    EnrolledIP("ip"),
    EnrollerIdCobis("idenrolador"),
    SupervisorIdCobis("idsupervisor"),
    EnrolledCanal("canal");

    public final String label;

    public static Stream<StringValueKeyBiographicDataTrazaEnrolarHuellas> stream() {
        return Stream.of(StringValueKeyBiographicDataTrazaEnrolarHuellas.values());
    }
}
