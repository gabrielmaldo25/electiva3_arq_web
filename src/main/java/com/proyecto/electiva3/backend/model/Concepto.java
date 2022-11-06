package com.proyecto.electiva3.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "concepto")
public class Concepto {
    @Id
    @Column(name = "idConcepto", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConcepto;

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    @Column(name = "puntos", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Float puntos;
}
