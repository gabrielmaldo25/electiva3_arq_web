package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.ReglasPuntos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReglasPuntosRepository extends JpaRepository<ReglasPuntos, Long> {
    public ReglasPuntos findByIdRegla(Long idRegla);
}
