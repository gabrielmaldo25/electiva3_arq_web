package com.proyecto.electiva3.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bolsa_puntos")
public class BolsaPuntos {
    @Id
    @Column(name = "idBolsa", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBolsa;

    @Column(name = "fecha_asig")
    private LocalDate fechaAsig;

    @Column(name = "fecha_caducidad")
    private LocalDate fechaCaducidad;

    @Column(name = "puntos", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Float puntos;

    @Column(name = "puntos_usados", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Float puntosUsados;

    @Column(name = "puntos_saldo", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Float puntosSaldo;

    @Column(name = "monto_operacion", nullable = false, columnDefinition = "NUMERIC(12,2)")
    private Float montoOperacion;

    // relationship with Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
    private Cliente cliente;

    // relationship with PuntosDetalle
    @OneToMany(mappedBy = "bolsaPuntos")
    private Set<PuntosDetalle> puntosDetalles = new HashSet<>();
}
