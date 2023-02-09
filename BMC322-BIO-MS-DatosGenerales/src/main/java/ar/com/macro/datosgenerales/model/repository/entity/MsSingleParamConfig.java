package ar.com.macro.datosgenerales.model.repository.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mssingleparamconfig")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MsSingleParamConfig implements Serializable {

	private static final long serialVersionUID = -931550468513784474L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id")
    @NotNull
	private String aplicacionId;

    @Column(name = "microservice_id")
    @NotNull
	private String microservicioId;

    @Column(name = "name")
    @NotNull
	private String nombre;
    
    @Column(name = "value")
    @NotNull
	private String valor;

    @Column(name = "creationdate")
    @NotNull
	private LocalDateTime fechaCreacion;
    
    @Column(name = "modificationdate")
    @NotNull
	private LocalDateTime fechaModificacion;
}
