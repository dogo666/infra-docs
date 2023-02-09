package ar.com.macro.datosgenerales.model.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "tracemsenrolamiento")
public class TraceMsEnrolamiento extends TraceInbound {

}
