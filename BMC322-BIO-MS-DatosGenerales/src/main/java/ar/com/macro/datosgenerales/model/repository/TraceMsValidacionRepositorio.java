package ar.com.macro.datosgenerales.model.repository;

import ar.com.macro.datosgenerales.model.repository.entity.TraceMsValidacion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface TraceMsValidacionRepositorio extends TraceRepositorio<TraceMsValidacion>{


    @Modifying
    @Query("DELETE FROM TraceMsValidacion t WHERE t.creationdate < :fecha")
    public void deleteDatosTraceMsValidacion( @Param("fecha") Date fechacreacion);


}