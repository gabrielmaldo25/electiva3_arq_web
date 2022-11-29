package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.email.CorreoService;
import com.proyecto.electiva3.backend.model.BolsaPuntos;
import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.model.Concepto;
import com.proyecto.electiva3.backend.model.DTO.ClienteDTO;
import com.proyecto.electiva3.backend.model.DTO.GeneralDTO;
import com.proyecto.electiva3.backend.model.ReglasPuntos;
import com.proyecto.electiva3.backend.repository.BolsaPuntosRepository;
import com.proyecto.electiva3.backend.services.ClienteService;
import com.proyecto.electiva3.backend.services.ConceptoService;
import com.proyecto.electiva3.backend.services.PuntosService;
import com.proyecto.electiva3.backend.services.ReglasPuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/puntos")
public class PuntosController {

    @Autowired
    private PuntosService puntosService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ConceptoService conceptoService;

    @Autowired
    private CorreoService correoService;

    @Autowired
    private BolsaPuntosRepository bolsaPuntosRepository;

    @Autowired
    private ReglasPuntosService reglasPuntosService;

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

    @GetMapping("/equivalente-puntos")
    public Float calcularPuntos(@RequestParam Float monto) throws Exception {
        Float puntos = puntosService.calcularPuntos(monto);
        return puntos;
    }

    @GetMapping("/participantes")
    public List<ClienteDTO> verParticipantes() throws Exception {
        List<ClienteDTO> clientes = clienteService.findAll();
        List<ClienteDTO> participantes = new ArrayList();
        ReglasPuntos regla = reglasPuntosService.findSorteoRule();

        if(regla == null)
            throw new Exception("No existe aun regla de sorteo.");
        // listar clientes
        for(ClienteDTO cli : clientes) {
            Cliente aux = new Cliente();
            Float puntos = 0f;

            clienteService.convertToDTO(aux, cli);
            List<BolsaPuntos> bolsaPuntos = bolsaPuntosRepository.findByCliente(aux);
            // listar puntos
            if(bolsaPuntos == null) continue;
            for(BolsaPuntos bol : bolsaPuntos) {
                puntos += bol.getPuntosSaldo();
            }

            // es participante?
            if(puntos >= regla.getLimiteInferior() && puntos <= regla.getLimiteSuperior()) {
                participantes.add(cli);
            }
        }

        return participantes;
    }

    @GetMapping("/sortear")
    public ClienteDTO sortear(@RequestParam Long idCliente, @RequestParam Long idConcepto) throws Exception {
        if(idCliente == null || idConcepto == null)
            throw new Exception("Error en la recepcion de parametros.");

        Cliente cliente = clienteService.findById(idCliente);
        Concepto concepto = conceptoService.findById(idConcepto);

        if(cliente == null || concepto == null)
            throw new Exception("No existe el cliente o el concepto.");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("name", cliente.getNombre() + " " + cliente.getApellido());
        String premio = "Concepto : " + concepto.getDescripcion() + ", Puntos : " + concepto.getPuntos();
        parametros.put("premio", premio);
        correoService.enviar_correo_ganador(cliente.getEmail(), parametros);

        return ClienteDTO.instanciar(cliente);
    }

}
