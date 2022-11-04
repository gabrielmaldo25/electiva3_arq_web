package com.proyecto.electiva3.backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String nroDocumento;
    private String email;
    private String telefono;
    private String fechaNac;
}
