package com.proyecto.electiva3.backend.model.DTO;

import com.proyecto.electiva3.backend.model.ParamPuntos;
import com.proyecto.electiva3.backend.util.GeneralUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParamPuntosDTO {
    private Long idParamPunto;
    private String fechaInicio;
    private String fechaFin;
    private Integer duracion;

    public static ParamPuntosDTO instanciar(ParamPuntos punto) {
        if(punto == null) return null;
        ParamPuntosDTO objeto = new ParamPuntosDTO();
        objeto.idParamPunto = punto.getIdParamPunto();
        objeto.fechaInicio = GeneralUtils.getStringFromDate(punto.getFechaInicio());
        objeto.fechaFin = GeneralUtils.getStringFromDate(punto.getFechaFin());
        objeto.duracion = punto.getDuracion();
        return objeto;
    }
}
