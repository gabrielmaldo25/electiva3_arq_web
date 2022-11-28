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
    private Integer validezDias;
    private String destino;

    public static ReglasPuntosDTO instanciar(ReglasPuntos reglasPuntos) {
        if(reglasPuntos == null) return null;
        ReglasPuntosDTO objeto = new ReglasPuntosDTO();
        objeto.idRegla = reglasPuntos.getIdRegla();
        objeto.limiteInferior = reglasPuntos.getLimiteInferior();
        objeto.limiteSuperior = reglasPuntos.getLimiteSuperior();
        objeto.monto = reglasPuntos.getMonto();
        objeto.validezDias = reglasPuntos.getValidezDias();
        objeto.destino = reglasPuntos.getDestino();
        return objeto;
    }
}
