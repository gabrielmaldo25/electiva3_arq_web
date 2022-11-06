package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.DTO.ReglasPuntosDTO;
import com.proyecto.electiva3.backend.model.ReglasPuntos;
import com.proyecto.electiva3.backend.services.ReglasPuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reglas")
public class ReglasPuntosController {
    @Autowired
    private ReglasPuntosService reglasService;

    @GetMapping
    public List<ReglasPuntos> findAll() {
        return (List<ReglasPuntos>)reglasService.findAll();
    }

    @GetMapping("/{id}")
    public ReglasPuntos findById(@PathVariable Long id) {
        return reglasService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReglasPuntos create(@RequestBody ReglasPuntosDTO objetoDTO) {
        ReglasPuntos objeto = new ReglasPuntos();
        reglasService.convertToDTO(objeto, objetoDTO);
        return reglasService.create(objeto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReglasPuntos update(@PathVariable Long id, @RequestBody ReglasPuntosDTO objetoDTO) {
        ReglasPuntos objeto = reglasService.findById(id);
        reglasService.convertToDTO(objeto, objetoDTO);
        return reglasService.update(objeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        ReglasPuntos objeto = reglasService.findById(id);
        reglasService.delete(objeto);
    }
}
