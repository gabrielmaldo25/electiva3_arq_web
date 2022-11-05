package com.proyecto.electiva3.backend.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConceptoDTO {
    private Long idCliente;
    private String descripcion;
    private Float puntos;
}
