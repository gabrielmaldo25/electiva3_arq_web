package com.proyecto.electiva3.backend.services;

import com.proyecto.electiva3.backend.model.DTO.ParamPuntosDTO;
import com.proyecto.electiva3.backend.model.ParamPuntos;
import com.proyecto.electiva3.backend.repository.ParamPuntosRepository;
import com.proyecto.electiva3.backend.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParamPuntosService {
    @Autowired
    private ParamPuntosRepository paramPuntosRepository;

    public void convertToDTO(ParamPuntos objeto, ParamPuntosDTO objetoDTO) {
        objeto.setFechaInicio(GeneralUtils.getDateFromString(objetoDTO.getFechaInicio()));
        objeto.setFechaFin(GeneralUtils.getDateFromString(objetoDTO.getFechaFin()));
        objeto.setDuracion(objetoDTO.getDuracion());
    }

    public ParamPuntos create(ParamPuntos objeto) throws Exception {
        objeto.setFechaFin(null);
        objeto.setFechaInicio(LocalDate.now());
        // por si existen varias parametrizaciones de puntos vigentes
        // se le agrega fechaFin para finaliar su vigencia y crear uno nuevo
        //        for(ParamPuntos param : this.getAllCurrentParam()) {
        //            param.setFechaFin(LocalDate.now());
        //            this.update(param);
        //        }
        throw new Exception("La funcionalidad de create Parametrizacion ha quedado en desuso.");

        //return paramPuntosRepository.save(objeto);
    }

    public ParamPuntos update(ParamPuntos objeto) {
        return paramPuntosRepository.save(objeto);
    }

    public void delete(ParamPuntos objeto) {
        paramPuntosRepository.delete(objeto);
    }

    public List<ParamPuntos> findAll() {
        return paramPuntosRepository.findAll();
    }

    public ParamPuntos findById(Long id) {
        return paramPuntosRepository.findByIdParamPunto(id);
    }

    /* retorna la primera parametrizaciones de puntos que este vigente */
    public ParamPuntos getCurrentParam() {
        List<ParamPuntos> paramPuntos = paramPuntosRepository.findByFechaFinIsNull();
        return (paramPuntos != null)? paramPuntos.get(0) : null;
    }

    /* retorna todas las parametrizaciones de puntos que esten vigente
    *  (fechaFin igual null)
    * */
    public List<ParamPuntos> getAllCurrentParam() {
        return paramPuntosRepository.findByFechaFinIsNull();
    }
}
