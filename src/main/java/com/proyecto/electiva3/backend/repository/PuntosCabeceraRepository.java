package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.PuntosCabecera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntosCabeceraRepository extends JpaRepository<PuntosCabecera, Long> {
}
