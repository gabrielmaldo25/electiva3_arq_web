package com.proyecto.electiva3.backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuntosDTO {
    private String fechaInicio;
    private String fechaFin;
    private Integer duracion;
}
