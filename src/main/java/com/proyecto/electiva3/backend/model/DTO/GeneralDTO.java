package com.proyecto.electiva3.backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralDTO {
    private Long idCliente;
    private Long idConcepto;
    private Float monto;
}
