package com.proyecto.electiva3.backend.model.DTO;


import com.proyecto.electiva3.backend.model.Concepto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConceptoDTO {
    private Long idConcepto;
    private String descripcion;
    private Float puntos;

    public static ConceptoDTO instanciar(Concepto concepto) {
        if(concepto == null) return null;
        ConceptoDTO objeto = new ConceptoDTO();
        objeto.idConcepto = concepto.getIdConcepto();
        objeto.descripcion = concepto.getDescripcion();
        objeto.puntos = concepto.getPuntos();
        return objeto;
    }
}
