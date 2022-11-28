package com.proyecto.electiva3.backend.tasks;

import com.proyecto.electiva3.backend.email.CorreoService;
import com.proyecto.electiva3.backend.model.BolsaPuntos;
import com.proyecto.electiva3.backend.model.ReglasPuntos;
import com.proyecto.electiva3.backend.repository.BolsaPuntosRepository;
import com.proyecto.electiva3.backend.services.ReglasPuntosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class SpringConfig {

    @Autowired
    BolsaPuntosRepository bolsaPuntosRepository;

    @Autowired
    CorreoService correoService;

    @Autowired
    ReglasPuntosService reglasPuntosService;

    // se ejecuta cada 2 min: PARA PRUEBAS
    //@Scheduled(cron = "0 2 * ? * *")
    // la tarea se ejecuta al final de cada dia: 23:00:00
    @Scheduled(cron = "0 0 23 ? * *")
    public void descontarPuntos() {
        List<BolsaPuntos> bolsas = bolsaPuntosRepository.findByFechaCaducidadLessThanEqualAndPuntosSaldoGreaterThan(LocalDate.now(), 0f);
        if(bolsas == null) return;

        for(BolsaPuntos bolsa : bolsas) {
            bolsa.setPuntosSaldo(0f);
            bolsaPuntosRepository.save(bolsa);
        }
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void notificarVenc() {
        ReglasPuntos regla = reglasPuntosService.findAlertRule();

        if (regla == null) {
            System.out.println("Aun no existe regla para alerta de notificacion.");
            return;
        }

        // Bolsas que vencen en X dias
        List<BolsaPuntos> bolsas = bolsaPuntosRepository.findByFechaCaducidadLessThanEqualAndPuntosSaldoGreaterThan(LocalDate.now().plusDays(regla.getValidezDias()), 0f);

        if(bolsas == null) return;

        for(BolsaPuntos bolsa : bolsas) {
            // calcular puntos
            Float puntos = 0f;
            List<BolsaPuntos> bolsaPuntos = bolsaPuntosRepository.findByCliente(bolsa.getCliente());
            // listar puntos
            if(bolsaPuntos != null) {
                for(BolsaPuntos bol : bolsaPuntos) {
                    puntos += bol.getPuntosSaldo();
                }
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("name", bolsa.getCliente().getNombre() + " " + bolsa.getCliente().getApellido());
            parametros.put("days", regla.getValidezDias());
            parametros.put("puntosAct", bolsa.getPuntosSaldo());
            parametros.put("puntosTotal", puntos);
            correoService.enviar_correo_ganador(bolsa.getCliente().getEmail(), parametros);
        }
    }
}