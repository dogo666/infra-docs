package ar.com.macro.enrolamiento.model.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrazasDto {
    String sucursal;
    String ip;
    String idenrolador;
    String idenroladorhuellas;
    String idsupervisor;
    String documentovigente;
    String canal;
    String scorerenaper;
    String indrostrorenaper;
    String indhuelladaon;
    String numerotramite = "";
    String dnivalido = "1";
    String rostrovalido = "1";
    String tipoenrolamiento = "0";
    List<TrazasHuellaDto> huellas;
}
