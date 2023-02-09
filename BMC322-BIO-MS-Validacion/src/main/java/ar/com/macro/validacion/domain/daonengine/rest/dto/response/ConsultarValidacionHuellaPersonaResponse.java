package ar.com.macro.validacion.domain.daonengine.rest.dto.response;

import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResult;
import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.ListSummaryAuditsResponse;
import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.SummaryAudit;
import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static ar.com.macro.constant.Errores.ERROR_OBTENER_LIST_SUMMARY_AUDITS_NOT_MATCH;
import static ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResult.MATCH;
import static ar.com.macro.validacion.model.client.daonengine.identification.dto.soap.VerifyResult.NO_MATCH;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarValidacionHuellaPersonaResponse implements Serializable {

    private static final long serialVersionUID = 3887389485761582993L;

    public static final String FECHA_HORA_FORMATO_YYYY_MM_DD_T_HH_MM_SS_XXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private String idrequesttracking;
    private String idusuario;
    private Double score;
    private Integer identificador;
    private VerifyResult resultado;
    private String fechacreacion;
    private Integer codigo;
    private String mensaje;

    public ConsultarValidacionHuellaPersonaResponse(Optional<ListSummaryAuditsResponse> listSummaryAuditsResponse, Double dFalseAcceptRate ) {
        ListSummaryAuditsResponse response = listSummaryAuditsResponse.get();
        this.idrequesttracking = response.getRequestTrackingUID();

        SummaryAudit summaryAudit = response.getResponseData().getSummaryAudit().stream()
                .findFirst().orElseThrow(()->new DaonEngineException(ERROR_OBTENER_LIST_SUMMARY_AUDITS_NOT_MATCH));

        this.idusuario = summaryAudit.getIdentityIdentifier().getValue();
        this.score = summaryAudit.getScore();
        this.identificador = summaryAudit.getMatchSummaryAudit().stream().findFirst().get().getUsageQualifier();
        this.resultado = score.compareTo(dFalseAcceptRate) < 0 ? MATCH : NO_MATCH;
        XMLGregorianCalendar date = summaryAudit.getCreatedDtm();
        SimpleDateFormat sdf = new SimpleDateFormat(FECHA_HORA_FORMATO_YYYY_MM_DD_T_HH_MM_SS_XXX);
        this.fechacreacion = sdf.format(date.toGregorianCalendar().getTime());
        this.codigo = summaryAudit.getReturnCode();
        this.mensaje = summaryAudit.getMessage();
    }



}
