package ar.com.macro.datosgenerales.model.repository;

import ar.com.macro.datosgenerales.model.repository.entity.TraceIdentityX;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TraceIdentityXRepositorio extends TraceRepositorio<TraceIdentityX>{

    @Modifying
    @Query("DELETE FROM TraceIdentityX t WHERE t.creationdate < :fecha")
    public void deleteDatosTraceOutbound( @Param("fecha") Date fechacreacion);


}