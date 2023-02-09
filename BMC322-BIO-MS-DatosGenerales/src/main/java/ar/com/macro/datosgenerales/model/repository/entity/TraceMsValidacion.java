package ar.com.macro.datosgenerales.model.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tracemsvalidacion")
public class TraceMsValidacion extends TraceInbound {
    
}
