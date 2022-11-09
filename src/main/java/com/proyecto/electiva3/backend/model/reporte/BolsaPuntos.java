package com.proyecto.electiva3.backend.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BolsaPuntos {
    private Long idBolsa;
    private LocalDate fechaAsig;
    private LocalDate fechaCaducidad;
    private Float montoOperacion;
    private Float puntos;
    private Float puntosSaldo;
    private Float puntosUsados;
    private String cliente;
}
