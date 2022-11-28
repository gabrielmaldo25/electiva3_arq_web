package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.ReglasPuntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglasPuntosRepository extends JpaRepository<ReglasPuntos, Long> {
    public ReglasPuntos findByIdRegla(Long idRegla);
    public ReglasPuntos findTopByLimiteSuperiorGreaterThanEqualAndLimiteInferiorLessThanEqualAndDestino(Float limiteInferior, Float limiteSuperior, String destino);
    public ReglasPuntos findTopByDestino(String destino);
}
