package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.ParamPuntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParamPuntosRepository extends JpaRepository<ParamPuntos, Long> {
    public ParamPuntos findByIdParamPunto(Long idParamPunto);
    public List<ParamPuntos> findByFechaFinIsNull();
}
