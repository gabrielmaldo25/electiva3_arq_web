package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.model.DTO.ReglasPuntosDTO;
import com.proyecto.electiva3.backend.model.ParamPuntos;
import com.proyecto.electiva3.backend.model.ReglasPuntos;
import com.proyecto.electiva3.backend.services.ParamPuntosService;
import com.proyecto.electiva3.backend.services.ReglasPuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/reglas")
public class ReglasPuntosController {
    @Autowired
    private ReglasPuntosService reglasService;

    @Autowired
    private ParamPuntosService paramPuntosService;

    @GetMapping
    public List<ReglasPuntosDTO> findAll() {
        List<ReglasPuntos> list = reglasService.findAll();
        List<ReglasPuntosDTO> reglas = list.stream().map(regla -> ReglasPuntosDTO.instanciar(regla)).collect(Collectors.toList());
        return reglas;
    }

    @GetMapping("/{id}")
    public ReglasPuntosDTO findById(@PathVariable Long id) {
        return ReglasPuntosDTO.instanciar(reglasService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReglasPuntosDTO create(@RequestBody ReglasPuntosDTO objetoDTO) {
        ReglasPuntos objeto = new ReglasPuntos();
        reglasService.convertToDTO(objeto, objetoDTO);

        // si existe campo validezDiaz crear parametrizacion
        //        if(objetoDTO.getValidezDias() != null && objetoDTO.getValidezDias().intValue() > 0) {
        //            ParamPuntos parametro = new ParamPuntos();
        //            parametro.setDuracion(objetoDTO.getValidezDias());
        //            paramPuntosService.create(parametro);
        //        }

        return ReglasPuntosDTO.instanciar(reglasService.create(objeto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReglasPuntosDTO update(@PathVariable Long id, @RequestBody ReglasPuntosDTO objetoDTO) {
        ReglasPuntos objeto = reglasService.findById(id);
        reglasService.convertToDTO(objeto, objetoDTO);
        return ReglasPuntosDTO.instanciar(reglasService.update(objeto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        ReglasPuntos objeto = reglasService.findById(id);
        reglasService.delete(objeto);
    }
}
