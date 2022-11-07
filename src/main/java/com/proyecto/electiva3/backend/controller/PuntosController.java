package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.model.Concepto;
import com.proyecto.electiva3.backend.model.DTO.GeneralDTO;
import com.proyecto.electiva3.backend.services.ClienteService;
import com.proyecto.electiva3.backend.services.ConceptoService;
import com.proyecto.electiva3.backend.services.PuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puntos")
public class PuntosController {

    @Autowired
    private PuntosService puntosService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ConceptoService conceptoService;

    @PostMapping("/pago")
    @ResponseStatus(HttpStatus.OK)
    public void registrarPuntos(@RequestBody GeneralDTO objetoDTO) throws Exception {
        Cliente cliente = clienteService.findById(objetoDTO.getIdCliente());

        if(cliente == null) {
            throw new Exception("El cliente no se encuentra en la base de datos.");
        }

        if(objetoDTO.getMonto() == null || objetoDTO.getMonto() <= 0.0) {
            throw new Exception("Monto para la operacion inválido.");
        }

        puntosService.registrarPuntos(objetoDTO.getIdCliente(), objetoDTO.getMonto());
    }

    @PostMapping("/canjear")
    @ResponseStatus(HttpStatus.OK)
    public void usarPuntos(@RequestBody GeneralDTO objetoDTO) throws Exception {
        Cliente cliente = clienteService.findById(objetoDTO.getIdCliente());
        Concepto concepto = conceptoService.findById(objetoDTO.getIdConcepto());

        if(cliente == null) {
            throw new Exception("El cliente no se encuentra en la base de datos.");
        }

        if(concepto == null) {
            throw new Exception("El Concepto no se encuentra en la base de datos.");
        }

        puntosService.usarPuntos(objetoDTO.getIdCliente(), objetoDTO.getIdConcepto());
    }

}
