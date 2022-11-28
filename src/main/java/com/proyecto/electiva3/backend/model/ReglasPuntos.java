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
    @Column(name = "idRegla", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegla;

    @Column(name = "limite_inferior", nullable = true, columnDefinition = "NUMERIC(12,2)")
    private Float limiteInferior;

    @Column(name = "limite_superior", nullable = true, columnDefinition = "NUMERIC(12,2)")
    private Float limiteSuperior;

    @Column(name = "monto", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Float monto;

    @Column(name = "validezDias", nullable = false)
    private Integer validezDias;

    @Column(name = "destino", nullable = false)
    private String destino;
}