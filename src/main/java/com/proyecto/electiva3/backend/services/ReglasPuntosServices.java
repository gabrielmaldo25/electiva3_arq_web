package com.proyecto.electiva3.backend.services;

import com.proyecto.electiva3.backend.model.DTO.ReglasPuntosDTO;
import com.proyecto.electiva3.backend.model.ReglasPuntos;
import com.proyecto.electiva3.backend.repository.ReglasPuntosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglasPuntosServices {
    @Autowired
    private ReglasPuntosRepository reglasRepository;

    public void convertToDTO(ReglasPuntos objeto, ReglasPuntosDTO objetoDTO) {
        objeto.setLimiteInferior(objetoDTO.getLimiteInferior());
        objeto.setLimiteSuperior(objetoDTO.getLimiteSuperior());
        objeto.setMonto(objetoDTO.getMonto());
    }

    public ReglasPuntos create(ReglasPuntos objeto) {
        return reglasRepository.save(objeto);
    }

    public ReglasPuntos update(ReglasPuntos objeto) {
        return reglasRepository.save(objeto);
    }

    public void delete(ReglasPuntos objeto) {
        reglasRepository.delete(objeto);
    }

    public List<ReglasPuntos> findAll() {
        return reglasRepository.findAll();
    }

    public ReglasPuntos findById(Long id) {
        return reglasRepository.findByIdRegla(id);
    }
}
