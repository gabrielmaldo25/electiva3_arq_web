package com.proyecto.electiva3.backend.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReglasPuntosDTO {
    private Long idRegla;
    private Float limiteInferior;
    private Float limiteSuperior;
    private Float monto;
}
