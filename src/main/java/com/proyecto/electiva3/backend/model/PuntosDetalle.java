package com.proyecto.electiva3.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "puntos_detalle")
public class PuntosDetalle {
    @Id
    @Column(name = "idPuntosDet", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPuntosDet;

    @Column(name = "puntaje_utilizado", columnDefinition = "NUMERIC(12,2)")
    private Float puntajeUtilizado;

    // relationship with PuntosCabecera
    @ManyToOne
    @JoinColumn(name = "puntosCab_id", referencedColumnName = "idPuntosCab")
    private PuntosCabecera puntosCabecera;

    // relationship with BolsaPuntos
    @ManyToOne
    @JoinColumn(name = "bolsa_id", referencedColumnName = "idBolsa")
    private BolsaPuntos bolsaPuntos;
}
