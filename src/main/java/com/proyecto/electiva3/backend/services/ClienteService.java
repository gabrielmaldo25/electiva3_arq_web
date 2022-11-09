package com.proyecto.electiva3.backend.services;

import com.proyecto.electiva3.backend.model.BolsaPuntos;
import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.model.DTO.ClienteDTO;
import com.proyecto.electiva3.backend.repository.BolsaPuntosRepository;
import com.proyecto.electiva3.backend.repository.ClienteRepository;
import com.proyecto.electiva3.backend.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BolsaPuntosRepository bolsaPuntosRepository;

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

    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> objetos = calcularPuntosXcliente(clientes);
        return objetos;
    }

    public Cliente findById(Long id) {
        return clienteRepository.findByIdCliente(id);
    }

    public List<Cliente> findByNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Cliente>  findByApellido(String apellido) {
        return clienteRepository.findByApellidoContainingIgnoreCase(apellido);
    }

    public List<Cliente>  findByCumpleanhos(LocalDate date) {
        return clienteRepository.findByFechaNac(date);
    }

    public List<Cliente> filterCliente(String cliente) {
        if(cliente == null) return null;
        return clienteRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(cliente, cliente);
    }

    public List<ClienteDTO> calcularPuntosXcliente(List<Cliente> clientes) {
        if(clientes == null) return null;

        List<ClienteDTO> objetos = new ArrayList<>();

        for(Cliente cli : clientes) {
            ClienteDTO obj = ClienteDTO.instanciar(cli);
            obj.setPuntos(0f);
            List<BolsaPuntos> bolsas = bolsaPuntosRepository.findByClienteAndPuntosSaldoGreaterThanOrderByFechaCaducidad(cli, 0f);
            if(bolsas != null) { // si el cliente tiene bolsas de puntos se calcula el total, sino es cero.
                for(BolsaPuntos bolsa : bolsas) {
                    obj.setPuntos(obj.getPuntos() + bolsa.getPuntosSaldo());
                }
            }
            objetos.add(obj);
        }
        return objetos;
    }
}
