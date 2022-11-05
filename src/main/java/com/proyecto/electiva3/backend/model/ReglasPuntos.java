package com.proyecto.electiva3.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reglas_puntos")
public class ReglasPuntos {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idRegla;

    @Column(name = "limite_inferior", nullable = true, columnDefinition = "NUMERIC(12,2)")
    Float limiteInferior;

    @Column(name = "limite_superior", nullable = true, columnDefinition = "NUMERIC(12,2)")
    Float limiteSuperior;

    @Column(name = "monto", nullable = false, columnDefinition = "NUMERIC(12,2)")
    Float monto;
}
