package ar.com.macro.validacion.model.service.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrazasEnrolarHuellaDto {
    private String sucursal;
    private String ip;
    private String idenrolador;
    private String idsupervisor;
    private String documentovigente;
    private String canal;
    private String scorerenaper;
    private String indrostrorenaper;
    private String indhuelladaon;
    private ArrayList<HuellasEnrolarHuellaDto> huellas;
}
