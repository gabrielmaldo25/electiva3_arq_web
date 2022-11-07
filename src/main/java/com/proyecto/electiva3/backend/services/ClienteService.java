package com.proyecto.electiva3.backend.services;

import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.model.DTO.ClienteDTO;
import com.proyecto.electiva3.backend.repository.ClienteRepository;
import com.proyecto.electiva3.backend.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void convertToDTO(Cliente objeto, ClienteDTO objetoDTO) {
        objeto.setNombre(objetoDTO.getNombre());
        objeto.setApellido(objetoDTO.getApellido());
        objeto.setNroDocumento(objetoDTO.getNroDocumento());
        objeto.setEmail(objetoDTO.getEmail());
        objeto.setTelefono(objetoDTO.getTelefono());
        objeto.setFechaNac(GeneralUtils.getDateFromString(objetoDTO.getFechaNac()));
    }

    public Cliente create(Cliente objeto) {
        return clienteRepository.save(objeto);
    }

    public Cliente update(Cliente objeto) {
        return clienteRepository.save(objeto);
    }

    public void delete(Cliente objeto) {
        clienteRepository.delete(objeto);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findByIdCliente(id);
    }
}