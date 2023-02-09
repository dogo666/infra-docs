package ar.com.macro.datosgenerales.model.repository.entity;

import lombok.Data;

@Data
public abstract class TraceInbound extends Trace {
    String dni;
}
