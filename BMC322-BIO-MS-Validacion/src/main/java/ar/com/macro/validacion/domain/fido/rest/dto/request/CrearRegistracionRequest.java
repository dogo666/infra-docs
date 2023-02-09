package ar.com.macro.validacion.domain.fido.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Value
public class CrearRegistracionRequest {

    @Schema(description = "Id Usuario de Identity-X", required = true)
    @NotNull(message = "{api.validacion.no.blank.message}")
    @JsonProperty("idusuario")
    String idUsuario;

    @Schema(description = "SponsorshipToken de Identity-X", required = true)
    @JsonProperty("sponsorshiptoken")
    String sponsorshipToken;

}
