package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.model.DTO.ClienteDTO;
import com.proyecto.electiva3.backend.repository.ClienteRepository;
import com.proyecto.electiva3.backend.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> findAll() {
        return (List<Cliente>)clienteService.findAll();
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody ClienteDTO objetoDTO) {
        Cliente objeto = new Cliente();
        clienteService.convertToDTO(objeto, objetoDTO);
        return clienteService.create(objeto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente update(@PathVariable Long id, @RequestBody ClienteDTO objetoDTO) {
        Cliente objeto = clienteService.findById(id);
        clienteService.convertToDTO(objeto, objetoDTO);
        return clienteService.update(objeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Cliente objeto = clienteService.findById(id);
        clienteService.delete(objeto);
    }
}
