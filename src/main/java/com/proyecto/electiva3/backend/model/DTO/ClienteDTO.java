package com.proyecto.electiva3.backend.model.DTO;

import com.proyecto.electiva3.backend.model.Cliente;
import com.proyecto.electiva3.backend.util.GeneralUtils;
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

    public static ClienteDTO instanciar(Cliente cliente) {
        if(cliente == null) return null;
        ClienteDTO objeto = new ClienteDTO();
        objeto.idCliente = cliente.getIdCliente();
        objeto.nombre = cliente.getNombre();
        objeto.apellido = cliente.getApellido();
        objeto.nroDocumento = cliente.getNroDocumento();
        objeto.email = cliente.getEmail();
        objeto.telefono = cliente.getTelefono();
        objeto.fechaNac = GeneralUtils.getStringFromDate(cliente.getFechaNac());
        return objeto;
    }
}
