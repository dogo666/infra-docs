package ar.com.macro.validacion.domain.identityx.rest.dto.response;

import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static ar.com.macro.commons.values.constants.format.DatePatternsConstants.FECHA_HORA_FORMATO_YYYY_MM_DD_HH_MM_SS;
import static ar.com.macro.constant.Constantes.YES;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultarVerificacionRostroPersonaResponse implements Serializable {

    public static final String FECHA_HORA_FORMATO_YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private String idverificacion;
    private Integer completado;
    private Integer resultado;
    private String fechaexpiracion;
    private String fechaproceso;
    private String fechacreacion;
    private List<ValidarRostroPersonaIntento> intentos;


    public ConsultarVerificacionRostroPersonaResponse(final AuthenticationRequest verificationResponse) {
        this.idverificacion = verificationResponse.getId();
        this.completado = verificationResponse.getComplete() == true ? 1 : 0;
        this.resultado = YES.equals(verificationResponse.getVerificationResult()) ? 1 : 0;

        SimpleDateFormat formater = new SimpleDateFormat(FECHA_HORA_FORMATO_YYYY_MM_DD_T_HH_MM_SS_SSS);
        this.fechaexpiracion = formater.format(verificationResponse.getExpiration());
        this.fechacreacion = formater.format(verificationResponse.getCreated());
        this.fechaproceso = formater.format(verificationResponse.getProcessed());

        this.intentos = new ArrayList<>();
        if(Objects.nonNull(verificationResponse.getAuthenticationAttempts())){
            Arrays.stream(verificationResponse.getAuthenticationAttempts())
                    .forEach((i) -> this.intentos.add(new ValidarRostroPersonaIntento(i)));
        }

    }

}