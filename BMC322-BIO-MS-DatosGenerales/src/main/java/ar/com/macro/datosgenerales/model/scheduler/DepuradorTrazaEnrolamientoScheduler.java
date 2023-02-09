package ar.com.macro.datosgenerales.model.scheduler;


import ar.com.macro.datosgenerales.model.repository.DepuradorRepository;
import ar.com.macro.datosgenerales.model.repository.TraceMsEnrolamientoRepositorio;
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
@ConditionalOnProperty(prefix = "depurador.enrolamiento", name = "enabled",havingValue="true")
public class DepuradorTrazaEnrolamientoScheduler {

    private final TraceMsEnrolamientoRepositorio traceMsEnrolamientoRepositorio;

    @Value("${depurador.enrolamiento.diasPrevios:30}")
    private Integer cantidadDiasPrevios;

    @Value("${depurador.enrolamiento.cron}")
    private String cron;

    @PostConstruct
    void init(){
        log.info("cron {} cantidadDiasPrevios {}", cron, cantidadDiasPrevios);
    }

    @Transactional
    @Scheduled( cron = "${depurador.enrolamiento.cron}")
    public void depurarTrazaEnrolamiento() {
        try{
            LocalDate fechaDepuracion = now().minusDays(cantidadDiasPrevios);
            log.info( "delete TRACEMSENROLAMIENTO : cantidadDiasPrevios: {}, fechaDepuracion: {} ", cantidadDiasPrevios, fechaDepuracion);
            traceMsEnrolamientoRepositorio.deleteDatosTraceMsEnrolamiento(Date.valueOf(fechaDepuracion));
        }catch(Exception ex){
            log.error("Depurar Enrolamiento : ", ex);
        }
    }

}




