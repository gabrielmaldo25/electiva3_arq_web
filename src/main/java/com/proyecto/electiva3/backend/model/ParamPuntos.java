package com.proyecto.electiva3.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "parametrizacion_venc_puntos")
public class ParamPuntos {
    @Id
    @Column(name = "idParamPunto", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParamPunto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private LocalDate fechaFin;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;
}
