package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.DTO.ParamPuntosDTO;
import com.proyecto.electiva3.backend.model.ParamPuntos;
import com.proyecto.electiva3.backend.services.ParamPuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parameters")
public class ParametrizacionController {
    @Autowired
    private ParamPuntosService paramPuntosService;

    @GetMapping
    public List<ParamPuntosDTO> findAll() {
        List<ParamPuntos> list = paramPuntosService.findAll();
        return list.stream().map(punto -> ParamPuntosDTO.instanciar(punto)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ParamPuntosDTO findById(@PathVariable Long id) {
        return ParamPuntosDTO.instanciar(paramPuntosService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParamPuntosDTO create(@RequestBody ParamPuntosDTO objetoDTO) {
        ParamPuntos objeto = new ParamPuntos();
        objetoDTO.setFechaFin(null);
        paramPuntosService.convertToDTO(objeto, objetoDTO);
        // por si existen varias parametrizaciones de puntos vigentes
        // se le agrega fechaFin para finaliar su vigencia y crear uno nuevo
        for(ParamPuntos param : paramPuntosService.getAllCurrentParam()) {
            param.setFechaFin(LocalDate.now());
            paramPuntosService.update(param);
        }
        return ParamPuntosDTO.instanciar(paramPuntosService.create(objeto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParamPuntosDTO update(@PathVariable Long id, @RequestBody ParamPuntosDTO objetoDTO) {
        ParamPuntos objeto = paramPuntosService.findById(id);
        paramPuntosService.convertToDTO(objeto, objetoDTO);
        return ParamPuntosDTO.instanciar(paramPuntosService.update(objeto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        ParamPuntos objeto = paramPuntosService.findById(id);
        paramPuntosService.delete(objeto);
    }
}
