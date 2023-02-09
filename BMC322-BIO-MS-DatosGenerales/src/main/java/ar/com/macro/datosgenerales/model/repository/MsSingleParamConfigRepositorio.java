package ar.com.macro.datosgenerales.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.macro.datosgenerales.model.repository.entity.MsSingleParamConfig;

public interface MsSingleParamConfigRepositorio extends JpaRepository<MsSingleParamConfig, Long> {

	Optional<List<MsSingleParamConfig>> findByAplicacionId(String aplicacionId);
	Optional<List<MsSingleParamConfig>> findByAplicacionIdAndMicroservicioId(String aplicacionId, String microservicio);
	Optional<List<MsSingleParamConfig>> findByAplicacionIdAndMicroservicioIdAndNombre(String aplicacionId, String microservicio, String nombre);
}
