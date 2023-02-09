package ar.com.macro.validacion.model.service.mapper;

import ar.com.macro.validacion.model.service.dto.ManosServiceDto;
import ar.com.macro.validacion.model.service.dto.TrazasActualizarHuellaDto;
import ar.com.macro.validacion.model.service.dto.TrazasEnrolarHuellaDto;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.utils.ServiceConvertidorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import static ar.com.macro.constant.Errores.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TraduccionHuellasDaonARenaperMapper {
    public List<Integer> traducirDedosDaonARenaper(List<Integer> fingers, ManosServiceDto manosServiceDtoDaon, ManosServiceDto manosServiceDtoRenaper) {
        List<Integer> fingersRenaper = new ArrayList<>();
        try {
            Map<String, Object> mapObjDaon = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoDaon);
            Map<String, Object> mapObjRenaper = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoRenaper);
            fingers.stream().forEach(finger -> {
                mapObjDaon.entrySet().stream().forEach(e -> {
                    if (e.getValue() == finger) {
                        fingersRenaper.add((Integer) mapObjRenaper.get(e.getKey()));
                    }
                });
            });
            return fingersRenaper;
        } catch (NullPointerException | IllegalArgumentException e) {
            log.error(ERROR_AL_TRADUCIR_DATOS_DAON_A_RENAPER_CONSULTA.getMensaje(),e);
            throw new DaonEngineException(ERROR_AL_TRADUCIR_DATOS_DAON_A_RENAPER_CONSULTA.getCodigo(),ERROR_AL_TRADUCIR_DATOS_DAON_A_RENAPER_CONSULTA.getMensaje());
        }
    }
    
    public List<Integer> traducirDedosRenaperADaon(List<Integer> fingers, ManosServiceDto manosServiceDtoDaon, ManosServiceDto manosServiceDtoRenaper) {
        List<Integer> fingersDaon = new ArrayList<>();
        try {
            Map<String, Object> mapObjDaon = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoDaon);
            Map<String, Object> mapObjRenaper = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoRenaper);
            fingers.stream().forEach(finger -> {
            	mapObjRenaper.entrySet().stream().forEach(e -> {
                    if (e.getValue() == finger) {
                        fingersDaon.add((Integer) mapObjDaon.get(e.getKey()));
                    }
                });
            });
            return fingersDaon;
        } catch (NullPointerException | IllegalArgumentException e) {
            log.error(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON.getMensaje(),e);
            throw new DaonEngineException(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON.getCodigo(), ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON.getMensaje());
        }
    }
}
