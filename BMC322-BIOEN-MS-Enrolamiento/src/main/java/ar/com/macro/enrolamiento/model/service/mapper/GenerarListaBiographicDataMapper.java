package ar.com.macro.enrolamiento.model.service.mapper;

import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.BiographicData;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.LongItem;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.StringItem;
import ar.com.macro.enrolamiento.model.service.dto.TrazasDto;
import ar.com.macro.exceptions.DaonEngineException;
import ar.com.macro.utils.ServiceConvertidorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;

import static ar.com.macro.constant.Errores.*;

@Slf4j
@Component
public class GenerarListaBiographicDataMapper {

    private final String PIPE = "|";

    public BiographicData generarListaBiographicData(BiographicData biographicData, Object object){
        try {
            Map<String, Object> mapObjDaonEnrolarHuella = ServiceConvertidorUtil.convertirObjetoEnMapa(object);
            mapObjDaonEnrolarHuella.entrySet().stream().filter(e -> e.getValue() != null).forEach(e -> {
                StringValueKeyBiographicDataTrazaEnrolarHuellas.stream().forEach(f -> {
                    if(f.label.equals(e.getKey())){
                        StringItem stringItem = new StringItem();
                        stringItem.setName(f.name());
                        stringItem.setValue(String.valueOf(e.getValue()));
                        biographicData.getStringItem().add(stringItem);
                    }
                });
                LongValueKeyBiographicDataTrazaEnrolarHuellas.stream().forEach(f -> {
                    if(f.label.equals(e.getKey())){
                        LongItem longItem = new LongItem();
                        longItem.setName(f.name());
                        longItem.setValue(Long.valueOf((String) e.getValue()));
                        biographicData.getLongItem().add(longItem);
                    }
                });
            });
            return biographicData;
        } catch (DaonEngineException e) {
            log.error(ERROR_GENERANDO_LA_LISTA_BIOGRAFIC_DATA.getMensaje(),e);
            throw new DaonEngineException(ERROR_GENERANDO_LA_LISTA_BIOGRAFIC_DATA.getCodigo(),ERROR_GENERANDO_LA_LISTA_BIOGRAFIC_DATA.getMensaje());
        }
    }

    public BiographicData agregarItemsHuellasEnrolar(BiographicData biographicData, TrazasDto trazasDto){
        try {
            trazasDto.getHuellas().stream().forEach(e -> {
                StringItem stringItem = new StringItem();
                stringItem.setName(String.valueOf(e.getIdentificador()));
                stringItem.setValue(e.getScorerenaper().concat(PIPE).concat(e.getIndhuellahitrenaper()).concat(PIPE).concat(e.getIndhuellaexcepcion()));
                biographicData.getStringItem().add(stringItem);
            });
            return biographicData;
        } catch (DaonEngineException e) {
            log.error(ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ENROLAR.getMensaje(),e);
            throw new DaonEngineException(ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ENROLAR.getCodigo(),ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ENROLAR.getMensaje());
        }
    }

    public BiographicData agregarItemsHuellasActualizar(BiographicData biographicData, TrazasDto trazasActualizarHuellaDto){
        try {
            trazasActualizarHuellaDto.getHuellas().stream().forEach(e -> {
                StringItem stringItem = new StringItem();
                stringItem.setName(String.valueOf(e.getIdentificador()));
                stringItem.setValue(e.getScorerenaper().concat(PIPE).concat(e.getIndhuellahitrenaper()).concat(PIPE).concat(e.getIndhuellaexcepcion()));
                biographicData.getStringItem().add(stringItem);
            });
            return biographicData;
        } catch (DaonEngineException e) {
            log.error(ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ACTUALIZACION.getMensaje(),e);
            throw new DaonEngineException(ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ACTUALIZACION.getCodigo(),ERROR_AGREGANDO_ITEMS_DE_HUELLAS_EN_ACTUALIZACION.getMensaje());
        }
    }

}
