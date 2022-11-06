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
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "idCliente", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;

    @Column(name = "nro_documento", length = 15, nullable = false)
    private String nroDocumento;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "telefono", length = 20, nullable = false)
    private String telefono;

    @Column(name = "fecha_nac")
    private LocalDate fechaNac;

    // relacion con BolsaPuntos
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<BolsaPuntos> bolsas = new HashSet<>();
}
