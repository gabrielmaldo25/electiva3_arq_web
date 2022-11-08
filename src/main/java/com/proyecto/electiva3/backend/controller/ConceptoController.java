package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.Concepto;
import com.proyecto.electiva3.backend.model.DTO.ConceptoDTO;
import com.proyecto.electiva3.backend.services.ConceptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conceptos")
public class ConceptoController {

    @Autowired
    private ConceptoService conceptoService;

    @GetMapping
    public List<ConceptoDTO> findAll() {
        List<Concepto> list = (List<Concepto>)conceptoService.findAll();
        return list.stream().map(concepto -> ConceptoDTO.instanciar(concepto)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ConceptoDTO findById(@PathVariable Long id) {
        return ConceptoDTO.instanciar(conceptoService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConceptoDTO create(@RequestBody ConceptoDTO objetoDTO) {
        Concepto objeto = new Concepto();
        conceptoService.convertToDTO(objeto, objetoDTO);
        return ConceptoDTO.instanciar(conceptoService.create(objeto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConceptoDTO update(@PathVariable Long id, @RequestBody ConceptoDTO objetoDTO) {
        Concepto objeto = conceptoService.findById(id);
        conceptoService.convertToDTO(objeto, objetoDTO);
        return ConceptoDTO.instanciar(conceptoService.update(objeto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Concepto objeto = conceptoService.findById(id);
        conceptoService.delete(objeto);
    }
}
