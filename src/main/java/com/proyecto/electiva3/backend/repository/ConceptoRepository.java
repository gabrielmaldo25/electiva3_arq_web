package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptoRepository extends JpaRepository<Concepto, Long> {
    public Concepto findByIdConcepto(Long idConcepto);
}
