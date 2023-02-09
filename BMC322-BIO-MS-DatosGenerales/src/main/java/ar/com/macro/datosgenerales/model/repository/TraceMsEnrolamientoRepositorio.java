package ar.com.macro.datosgenerales.model.repository;

import ar.com.macro.datosgenerales.model.repository.entity.TraceMsEnrolamiento;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

public interface TraceMsEnrolamientoRepositorio extends TraceRepositorio<TraceMsEnrolamiento>{

    @Modifying
    @Query("DELETE FROM TraceMsEnrolamiento t WHERE t.creationdate < :fecha")
    public void deleteDatosTraceMsEnrolamiento( @Param("fecha") Date fechacreacion);

}