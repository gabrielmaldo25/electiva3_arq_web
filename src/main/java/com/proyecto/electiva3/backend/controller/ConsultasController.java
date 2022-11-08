package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.*;
import com.proyecto.electiva3.backend.model.reporte.UsoPuntos;
import com.proyecto.electiva3.backend.repository.BolsaPuntosRepository;
import com.proyecto.electiva3.backend.repository.PuntosCabeceraRepository;
import com.proyecto.electiva3.backend.services.ClienteService;
import com.proyecto.electiva3.backend.services.ConceptoService;
import com.proyecto.electiva3.backend.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ConsultasController {

    @Autowired
    private PuntosCabeceraRepository puntosCabeceraRepository;

    @Autowired
    private ConceptoService conceptoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BolsaPuntosRepository bolsaPuntosRepository;

    @GetMapping("/uso-puntos")
    public List<UsoPuntos> reporteUsoPuntos(@RequestParam(required = false) Long concepto,
                                                 @RequestParam(required = false) String fecha,
                                                 @RequestParam(required = false) Long cliente) {
        List<PuntosCabecera> puntos = new ArrayList<>();
        List<UsoPuntos> list = new ArrayList<>();

        if(concepto != null) {
            Concepto objeto = conceptoService.findById(concepto);
            if(objeto != null) puntos = puntosCabeceraRepository.findByConcepto(objeto);
        } else if(fecha != null) {
            LocalDate date = GeneralUtils.getDateFromString(fecha);
            if(date != null) puntos = puntosCabeceraRepository.findByFecha(date);
        } else if(cliente != null) {
            Cliente objeto = clienteService.findById(cliente);
            if(objeto != null) puntos = puntosCabeceraRepository.findByCliente(objeto);
        }

        if(puntos != null) {
            for(PuntosCabecera punto : puntos) {
                String nombre = punto.getCliente().getNombre() + " " + punto.getCliente().getApellido();
                list.add(new UsoPuntos(punto.getFecha(), nombre, punto.getCliente().getNroDocumento(),
                        punto.getConcepto().getDescripcion(), punto.getPuntajeUtilizado()));
            }
        }

        return list;
    }

    @GetMapping("/bolsa-puntos")
    public List<com.proyecto.electiva3.backend.model.reporte.BolsaPuntos> reporteBolsaPuntos(@RequestParam(required = false) Long cliente,
                                                                                             @RequestParam(required = false) Float inferior,
                                                                                             @RequestParam(required = false) Float superior) {
        List<BolsaPuntos> bolsas = new ArrayList<>();
        List<com.proyecto.electiva3.backend.model.reporte.BolsaPuntos> list = new ArrayList<>();

        if(cliente != null) {
            Cliente objeto = clienteService.findById(cliente);
            if(objeto != null) bolsas = bolsaPuntosRepository.findByCliente(objeto);
        } else if(inferior != null && superior != null) {
            bolsas = bolsaPuntosRepository.findByPuntosBetween(inferior, superior);
        }

        if(bolsas != null) {
            for(BolsaPuntos bolsa : bolsas) {
                String nombre = bolsa.getCliente().getNombre() + " " + bolsa.getCliente().getApellido();
                list.add(new com.proyecto.electiva3.backend.model.reporte.BolsaPuntos(
                        bolsa.getFechaAsig(), bolsa.getFechaCaducidad(), bolsa.getMontoOperacion(),
                        bolsa.getPuntos(), bolsa.getPuntosSaldo(), bolsa.getPuntosUsados(), nombre
                ));
            }
        }

        return list;
    }

    @GetMapping("/venc-puntos/{cantDias}")
    public List<com.proyecto.electiva3.backend.model.reporte.BolsaPuntos> reporteVencPuntos(@PathVariable Integer cantDias) {
        List<BolsaPuntos> bolsas = new ArrayList<>();
        List<com.proyecto.electiva3.backend.model.reporte.BolsaPuntos> list = new ArrayList<>();

        if(cantDias != null) {
            bolsas = bolsaPuntosRepository.findByFechaCaducidadBetween(LocalDate.now(), LocalDate.now().plusDays(cantDias));
        }

        if(bolsas != null) {
            for(BolsaPuntos bolsa : bolsas) {
                String nombre = bolsa.getCliente().getNombre() + " " + bolsa.getCliente().getApellido();
                list.add(new com.proyecto.electiva3.backend.model.reporte.BolsaPuntos(
                        bolsa.getFechaAsig(), bolsa.getFechaCaducidad(), bolsa.getMontoOperacion(),
                        bolsa.getPuntos(), bolsa.getPuntosSaldo(), bolsa.getPuntosUsados(), nombre
                ));
            }
        }

        return list;
    }

    @GetMapping("/clientes")
    public List<Cliente> reporteClientes(@RequestParam(required = false) String nombre,
                                            @RequestParam(required = false) String apellido,
                                            @RequestParam(required = false) String fecha) {
        List<Cliente> list = new ArrayList<>();

        if(nombre != null) {
            list = clienteService.findByNombre(nombre);
        } else if(apellido != null) {
            list = clienteService.findByApellido(apellido);
        } else if(fecha != null) {
            LocalDate date = GeneralUtils.getDateFromString(fecha);
            if(date != null) list = clienteService.findByCumpleanhos(date);
        }

        if(list != null && !list.isEmpty()) {
            for(Cliente cliente : list) {
                cliente.setBolsas(null);
            }
        }

        return list;
    }

}
