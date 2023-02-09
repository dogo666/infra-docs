package ar.com.macro.enrolamiento.model.service.mapper;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.model.service.dto.ManosServiceDto;
import ar.com.macro.enrolamiento.model.service.dto.TrazasDto;
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

    public TrazasDto traducirDedosRenaperADaonTrazasEnrolar(TrazasDto trazasDto, ManosServiceDto manosServiceDtoDaon, ManosServiceDto manosServiceDtoRenaper){
        try {
            Map<String, Object> mapObjDaon = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoDaon);
            Map<String, Object> mapObjRenaper = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoRenaper);
            trazasDto.getHuellas().stream().forEach(finger -> {
                mapObjRenaper.entrySet().stream().forEach(e -> {
                    if((Integer) e.getValue() == finger.getIdentificador()){
                        finger.setIdentificador((Integer) mapObjDaon.get(e.getKey()));
                    }
                });
            });
            return trazasDto;
        } catch (DaonEngineException e) {
            log.error(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ENROLAR.getMensaje(),e);
            throw new DaonEngineException(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ENROLAR.getCodigo(),ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ENROLAR.getMensaje());
        }
    }

    public TrazasDto traducirDedosRenaperADaonTrazasActualizar(TrazasDto trazasActualizarHuellaDto, ManosServiceDto manosServiceDtoDaon, ManosServiceDto manosServiceDtoRenaper){
        try {
            Map<String, Object> mapObjDaon = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoDaon);
            Map<String, Object> mapObjRenaper = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoRenaper);
            trazasActualizarHuellaDto.getHuellas().stream().forEach(finger -> {
                mapObjRenaper.entrySet().stream().forEach(e -> {
                    if((Integer) e.getValue() == finger.getIdentificador()){
                        finger.setIdentificador((Integer) mapObjDaon.get(e.getKey()));
                    }
                });
            });
            return trazasActualizarHuellaDto;
        } catch (DaonEngineException e) {
            log.error(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ACTUALIZAR.getMensaje(),e);
            throw new DaonEngineException(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ACTUALIZAR.getCodigo(),ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ACTUALIZAR.getMensaje());
        }
    }

    public EnrolarHuellaDaonEngineRequest traducirDedosRenaperADaonHuellas(EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest, ManosServiceDto manosServiceDtoDaon, ManosServiceDto manosServiceDtoRenaper){
        try {
            Map<String, Object> mapObjDaon = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoDaon);
            Map<String, Object> mapObjRenaper = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoRenaper);
            enrolarHuellaDaonEngineRequest.getHuellas().stream().forEach(finger -> {
                mapObjRenaper.entrySet().stream().forEach(e -> {
                    if((Integer) e.getValue() == finger.getIdentificador()){
                        finger.setIdentificador((Integer) mapObjDaon.get(e.getKey()));
                    }
                });
            });
            return enrolarHuellaDaonEngineRequest;
        } catch (DaonEngineException e) {
            log.error(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ENROLAR.getMensaje(),e);
            throw new DaonEngineException(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ENROLAR.getCodigo(),ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ENROLAR.getMensaje());
        }
    }

    public ActualizarHuellaDaonEngineRequest traducirDedosRenaperADaonHuellasActualizar(ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest, ManosServiceDto manosServiceDtoDaon, ManosServiceDto manosServiceDtoRenaper){
        try {
            Map<String, Object> mapObjDaon = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoDaon);
            Map<String, Object> mapObjRenaper = ServiceConvertidorUtil.convertirObjetoEnMapa(manosServiceDtoRenaper);
            actualizarHuellaDaonEngineRequest.getHuellas().stream().forEach(finger -> {
                mapObjRenaper.entrySet().stream().forEach(e -> {
                    if((Integer) e.getValue() == finger.getIdentificador()){
                        finger.setIdentificador((Integer) mapObjDaon.get(e.getKey()));
                    }
                });
            });
            return actualizarHuellaDaonEngineRequest;
        } catch (NullPointerException | IllegalArgumentException e) {
            log.error(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ACTUALIZAR.getMensaje(),e);
            throw new DaonEngineException(ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ACTUALIZAR.getCodigo(),ERROR_AL_TRADUCIR_DATOS_RENAPER_A_DAON_ACTUALIZAR.getMensaje());
        }
    }

    public Integer getIdentificadorDaon(Integer identificador, Map<String, Object> mapObjDaon, Map<String, Object> mapObjRenaper){
        Map.Entry<String,Object> entry = mapObjRenaper.entrySet().stream()
                .filter(f -> f.getValue().equals(identificador))
                .findFirst()
                .get();
        return (Integer) mapObjDaon.get(entry.getKey());
    }
}
