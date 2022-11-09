package com.proyecto.electiva3.backend.repository;

import com.proyecto.electiva3.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    public Cliente findByIdCliente(Long idCliente);
    public List<Cliente> findByNombreIgnoreCase(String nombre);
    public List<Cliente> findByApellidoIgnoreCase(String apellido);
    public List<Cliente> findByNombreIgnoreCaseOrApellidoIgnoreCase(String nombre, String apellido);
    public List<Cliente> findByFechaNac(LocalDate fechaNac);
}
