package ar.com.macro.datosgenerales.model.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DepuradorRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String DELETE_TRACEMSENROLAMIENTO_POR_FECHA = "DELETE FROM tracemsenrolamiento  WHERE creationdate < ? ";

    private static final String DELETE_TRACEMSVALIDACION_POR_FECHA = "DELETE FROM tracemsvalidacion  WHERE creationdate < ? ";

    public void deleteDatosTraceMsValidacion(LocalDate fechacreacion) {
        jdbcTemplate.update(DELETE_TRACEMSVALIDACION_POR_FECHA,fechacreacion);
    }

    public void deleteDatosTraceMsEnrolamiento(LocalDate fechacreacion) {
        jdbcTemplate.update(DELETE_TRACEMSENROLAMIENTO_POR_FECHA,fechacreacion);
    }
}