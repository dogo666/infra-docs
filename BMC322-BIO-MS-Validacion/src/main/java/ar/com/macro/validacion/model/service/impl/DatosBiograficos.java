package ar.com.macro.validacion.model.service.impl;


import java.time.LocalDate;
import java.util.Optional;

import lombok.Data;

@Data
public class DatosBiograficos{
    Optional<LocalDate> fechaEnrolamientoRostro = Optional.empty();
    Optional<String> nombreEnroladorRostro = Optional.empty();
    Optional<LocalDate> fechaEnrolamientoHuella = Optional.empty();
    Optional<String> nombreEnroladorHuella = Optional.empty();
    Optional<Long> confidence = Optional.empty();
}