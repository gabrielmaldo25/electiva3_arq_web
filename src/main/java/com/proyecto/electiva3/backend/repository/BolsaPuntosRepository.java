package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.BolsaPuntos;
import com.proyecto.electiva3.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BolsaPuntosRepository extends JpaRepository<BolsaPuntos, Long> {
    public List<BolsaPuntos> findByClienteOrderByFechaCaducidad(Cliente cliente);
    public List<BolsaPuntos> findByCliente(Cliente cliente);
    public List<BolsaPuntos> findByPuntosBetween(Float min, Float max);
}
