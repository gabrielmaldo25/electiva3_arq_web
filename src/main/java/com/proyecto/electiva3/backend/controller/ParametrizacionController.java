package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.DTO.PuntosDTO;
import com.proyecto.electiva3.backend.model.ParamPuntos;
import com.proyecto.electiva3.backend.services.ParamPuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/parameters")
public class ParametrizacionController {
    @Autowired
    private ParamPuntosService paramPuntosService;

    @GetMapping
    public List<ParamPuntos> findAll() {
        return (List<ParamPuntos>)paramPuntosService.findAll();
    }

    @GetMapping("/{id}")
    public ParamPuntos findById(@PathVariable Long id) {
        return paramPuntosService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParamPuntos create(@RequestBody PuntosDTO objetoDTO) {
        ParamPuntos objeto = new ParamPuntos();
        objetoDTO.setFechaFin(null);
        paramPuntosService.convertToDTO(objeto, objetoDTO);
        // por si existen varias parametrizaciones de puntos vigentes
        // se le agrega fechaFin para finaliar su vigencia y crear uno nuevo
        for(ParamPuntos param : paramPuntosService.getAllCurrentParam()) {
            param.setFechaFin(LocalDate.now());
            paramPuntosService.update(param);
        }
        return paramPuntosService.create(objeto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParamPuntos update(@PathVariable Long id, @RequestBody PuntosDTO objetoDTO) {
        ParamPuntos objeto = paramPuntosService.findById(id);
        paramPuntosService.convertToDTO(objeto, objetoDTO);
        return paramPuntosService.update(objeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        ParamPuntos objeto = paramPuntosService.findById(id);
        paramPuntosService.delete(objeto);
    }
}
