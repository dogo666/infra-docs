package ar.com.macro.datosgenerales.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.macro.datosgenerales.model.repository.entity.MsCompositeParamConfig;


public interface MsCompositeParamConfigRepositorio extends JpaRepository<MsCompositeParamConfig, Long> {

	Optional<List<MsCompositeParamConfig>> findByAplicacionId(String aplicacionId);
	Optional<List<MsCompositeParamConfig>> findByAplicacionIdAndMicroservicioId(String aplicacionId, String microservicio);
	Optional<List<MsCompositeParamConfig>> findByAplicacionIdAndMicroservicioIdAndNombre(String aplicacionId, String microservicio, String nombre);
}
