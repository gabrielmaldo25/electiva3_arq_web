package com.proyecto.electiva3.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "puntos_cabecera")
public class PuntosCabecera {
    @Id
    @Column(name = "idPuntosCab", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPuntosCab;

    @Column(name = "puntaje_utilizado", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Float puntajeUtilizado;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    // relationship with Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
    private Cliente cliente;

    // relationship with Concepto
    @ManyToOne
    @JoinColumn(name = "concepto_id", referencedColumnName = "idConcepto")
    private Concepto concepto;

    // relationship with PuntosDetalle
    @OneToMany(mappedBy = "puntosCabecera",  cascade = CascadeType.ALL)
    private Set<PuntosDetalle> puntosDetalles = new HashSet<>();
}
