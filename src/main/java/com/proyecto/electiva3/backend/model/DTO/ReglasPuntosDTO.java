package com.proyecto.electiva3.backend.model.DTO;


import com.proyecto.electiva3.backend.model.ReglasPuntos;
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

    public void convertir(ReglasPuntos reglasPuntos) {
        this.idRegla = reglasPuntos.getIdRegla();
        this.limiteInferior = reglasPuntos.getLimiteInferior();
        this.limiteSuperior = reglasPuntos.getLimiteSuperior();
        this.monto = reglasPuntos.getMonto();
    }
}
