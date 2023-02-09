package ar.com.macro.enrolamiento.model.service.mapper;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.identidad.rest.dto.request.ConsultarIdentidadRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EnrolarHuellasDaonEngineToEsbMapper {
    @Mapping(source = "numerodocumento", target = "dni")
    @Mapping(source = "genero", target = "sexo")
    ConsultarIdentidadRequest mapErolarHuellaDaonEngineToEsb(EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest);

    @Mapping(source = "numerodocumento", target = "dni")
    @Mapping(source = "genero", target = "sexo")
    ConsultarIdentidadRequest mapActualizarHuellaDaonEngineToEsb(ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest);

}
