package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    public Cliente findByIdCliente(Long idCliente);
}
