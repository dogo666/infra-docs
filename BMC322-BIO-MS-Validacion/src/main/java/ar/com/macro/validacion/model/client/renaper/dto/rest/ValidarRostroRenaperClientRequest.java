package ar.com.macro.validacion.model.client.renaper.dto.rest;

import ar.com.macro.commons.utils.business.RenaperSelfieRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ValidarRostroRenaperClientRequest {

    public final static String BASE64_FINGER_PRINT = "base64Fingerprint";

    private String number;

    private String gender;

    private List<RenaperSelfieRequest> selfieList;

    private String browserFingerprintData;
}
