package ar.com.macro.datosgenerales.model.scheduler;


import ar.com.macro.datosgenerales.model.repository.DepuradorRepository;
import ar.com.macro.datosgenerales.model.repository.TraceMsValidacionRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;

import static java.time.LocalDate.now;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "depurador.validacion", name = "enabled",havingValue="true")
public class DepuradorTrazaValidacionScheduler {

    private final TraceMsValidacionRepositorio traceMsValidacionRepositorio;

    @Value("${depurador.validacion.diasPrevios:30}")
    private Integer cantidadDiasPrevios;

    @Value("${depurador.validacion.cron}")
    private String cron;

    @PostConstruct
    void init(){
        log.info("cron {} cantidadDiasPrevios {}", cron, cantidadDiasPrevios);
    }

    @Transactional
    @Scheduled( cron = "${depurador.validacion.cron}")
    public void depurarTrazaValidacion() {
        try{
            LocalDate fechaDepuracion = now().minusDays(cantidadDiasPrevios);
            log.info( "delete TRACEMSVALIDACION : cantidadDiasPrevios: {}, fechaDepuracion: {} ", cantidadDiasPrevios, fechaDepuracion);
            traceMsValidacionRepositorio.deleteDatosTraceMsValidacion(Date.valueOf(fechaDepuracion));
        }catch(Exception ex){
            log.error("Depurar Validacion : ", ex);
        }
    }

}




