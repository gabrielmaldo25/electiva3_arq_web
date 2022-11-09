package com.proyecto.electiva3.backend.services;

import com.proyecto.electiva3.backend.model.Concepto;
import com.proyecto.electiva3.backend.model.DTO.ConceptoDTO;
import com.proyecto.electiva3.backend.repository.ConceptoRepository;
import com.proyecto.electiva3.backend.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConceptoService {

    @Autowired
    private ConceptoRepository conceptoRepository;

    public void convertToDTO(Concepto objeto, ConceptoDTO objetoDTO) {
        objeto.setDescripcion(objetoDTO.getDescripcion());
        objeto.setPuntos(objetoDTO.getPuntos());
    }

    public Concepto create(Concepto objeto) {
        return conceptoRepository.save(objeto);
    }

    public Concepto update(Concepto objeto) {
        return conceptoRepository.save(objeto);
    }

    public void delete(Concepto objeto) {
        conceptoRepository.delete(objeto);
    }

    public List<Concepto> findAll() {
        return conceptoRepository.findAll();
    }

    public Concepto findById(Long id) {
        return conceptoRepository.findByIdConcepto(id);
    }

    public List<Concepto> filterConcepto(String concepto) {
        if(concepto == null) return null;
        concepto = "%" + concepto + "%";
        return conceptoRepository.findByDescripcionIgnoreCase(concepto);
    }
}
