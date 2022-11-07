package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.PuntosDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntosDetalleRepostitory extends JpaRepository<PuntosDetalle, Long> {
}
