package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.Concepto;
import com.proyecto.electiva3.backend.model.DTO.ConceptoDTO;
import com.proyecto.electiva3.backend.services.ConceptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conceptos")
public class ConceptoController {

    @Autowired
    private ConceptoService conceptoService;

    @GetMapping
    public List<Concepto> findAll() {
        return (List<Concepto>)conceptoService.findAll();
    }

    @GetMapping("/{id}")
    public Concepto findById(@PathVariable Long id) {
        return conceptoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Concepto create(@RequestBody ConceptoDTO objetoDTO) {
        Concepto objeto = new Concepto();
        conceptoService.convertToDTO(objeto, objetoDTO);
        return conceptoService.create(objeto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Concepto update(@PathVariable Long id, @RequestBody ConceptoDTO objetoDTO) {
        Concepto objeto = conceptoService.findById(id);
        conceptoService.convertToDTO(objeto, objetoDTO);
        return conceptoService.update(objeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Concepto objeto = conceptoService.findById(id);
        conceptoService.delete(objeto);
    }
}
