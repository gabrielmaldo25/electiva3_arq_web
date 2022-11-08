package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.model.Concepto;
import com.proyecto.electiva3.backend.model.PuntosCabecera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PuntosCabeceraRepository extends JpaRepository<PuntosCabecera, Long> {
    public List<PuntosCabecera> findByConcepto(Concepto concepto);
    public List<PuntosCabecera> findByCliente(Cliente cliente);
    public List<PuntosCabecera> findByFecha(LocalDate fecha);
}
