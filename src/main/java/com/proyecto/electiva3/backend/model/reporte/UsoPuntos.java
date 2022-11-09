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
public class UsoPuntos {
    private Long idPuntosCab;
    private LocalDate fechaOperacion;
    private String cliente;
    private String nroDocumento;
    private String concepto;
    private Float puntosUsados;
}
