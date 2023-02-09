package ar.com.macro.validacion.model.service.mapper;

import ar.com.macro.validacion.domain.identidad.rest.dto.response.ConsultarIdentidadResponse;
import ar.com.macro.validacion.model.client.datapower.dto.rest.dto.consulta.identificacion.IdentificacionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EnrolamientoServiceMapper {

    @Mapping(source = "idCobis", target = "idCliente")
    @Mapping(source = "codigoTributario", target = "tipoDocumentoTributario")
    @Mapping(source = "numeroTributario", target = "nroDocumentoTributario")
    @Mapping(source = "codigoIdentificacion", target = "tipoDocumentoIdentidad")
    @Mapping(source = "numeroIdentificacion", target = "nroDocumentoIdentidad")
    @Mapping(source = "apellido", target = "apellido")
    @Mapping(source = "razonSocialNombre", target = "nombre")
    ConsultarIdentidadResponse mapConsultarNormalizacionIndividuos(IdentificacionResponse identificacionResponse);
}
