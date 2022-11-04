package com.proyecto.electiva3.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCliente;

    @Column(name = "nombre", length = 50, nullable = false)
    String nombre;

    @Column(name = "apellido", length = 50, nullable = false)
    String apellido;

    @Column(name = "nro_documento", length = 15, nullable = false)
    String nroDocumento;

    @Column(name = "email", length = 50, nullable = false)
    String email;

    @Column(name = "telefono", length = 20, nullable = false)
    String telefono;

    @Column(name = "fecha_nac")
    LocalDate fechaNac;
}
