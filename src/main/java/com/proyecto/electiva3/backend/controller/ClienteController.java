package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.model.DTO.ClienteDTO;
import com.proyecto.electiva3.backend.repository.ClienteRepository;
import com.proyecto.electiva3.backend.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> findAll() {
        List<Cliente> list = (List<Cliente>)clienteService.findAll();
        return list.stream().map(cliente -> ClienteDTO.instanciar(cliente)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClienteDTO findById(@PathVariable Long id) {
        ClienteDTO objeto = ClienteDTO.instanciar(clienteService.findById(id));
        return objeto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO create(@RequestBody ClienteDTO objetoDTO) {
        Cliente objeto = new Cliente();
        clienteService.convertToDTO(objeto, objetoDTO);
        objetoDTO = ClienteDTO.instanciar(clienteService.create(objeto));
        return objetoDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO update(@PathVariable Long id, @RequestBody ClienteDTO objetoDTO) {
        Cliente objeto = clienteService.findById(id);
        clienteService.convertToDTO(objeto, objetoDTO);
        objetoDTO = ClienteDTO.instanciar(clienteService.update(objeto));
        return objetoDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Cliente objeto = clienteService.findById(id);
        clienteService.delete(objeto);
    }
}