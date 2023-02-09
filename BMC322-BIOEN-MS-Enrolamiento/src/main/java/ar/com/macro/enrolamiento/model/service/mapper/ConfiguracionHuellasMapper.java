package ar.com.macro.enrolamiento.model.service.mapper;

import ar.com.macro.enrolamiento.model.service.dto.ConfiguracionHuellasDto;
import ar.com.macro.enrolamiento.model.service.dto.ManosServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ConfiguracionHuellasMapper {
    @Mapping(source = "manoDerecha.pulgar", target = "MD_PULGAR")
    @Mapping(source = "manoDerecha.indice", target = "MD_INDICE")
    @Mapping(source = "manoDerecha.mayor", target = "MD_MAYOR")
    @Mapping(source = "manoDerecha.anular", target = "MD_ANULAR")
    @Mapping(source = "manoDerecha.menique", target = "MD_MENIQUE")
    @Mapping(source = "manoIzquierda.pulgar", target = "MI_PULGAR")
    @Mapping(source = "manoIzquierda.indice", target = "MI_INDICE")
    @Mapping(source = "manoIzquierda.mayor", target = "MI_MAYOR")
    @Mapping(source = "manoIzquierda.anular", target = "MI_ANULAR")
    @Mapping(source = "manoIzquierda.menique", target = "MI_MENIQUE")
    ManosServiceDto mapperEntity(ConfiguracionHuellasDto configuracionHuellasDto);
}
