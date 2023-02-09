package ar.com.macro.validacion.model.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrazasActualizarHuellaDto {
    private String sucursal;
    private String ip;
    private String idenrolador;
    private String idsupervisor;
    private String documentovigente;
    private String canal;
    private String scorerenaper;
    private String indrostrorenaper;
    private String indhuelladaon;
    private List<HuellasActualizarHuellaDto> huellas;
}
