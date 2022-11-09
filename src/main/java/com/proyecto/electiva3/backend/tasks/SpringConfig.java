package com.proyecto.electiva3.backend.tasks;

import com.proyecto.electiva3.backend.model.BolsaPuntos;
import com.proyecto.electiva3.backend.repository.BolsaPuntosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
public class SpringConfig {

    @Autowired
    BolsaPuntosRepository bolsaPuntosRepository;

    // se ejecuta cada 2 min: PARA PRUEBAS
    //@Scheduled(cron = "0 * * ? * *")
    // la tarea se ejecuta al final de cada dia: 23:00:00
    @Scheduled(cron = "0 0 23 ? * * *")
    public void scheduleTaskUsingCronExpression() {
        List<BolsaPuntos> bolsas = bolsaPuntosRepository.findByFechaCaducidadLessThanEqualAndPuntosSaldoGreaterThan(LocalDate.now(), 0f);
        if(bolsas == null) return;

        for(BolsaPuntos bolsa : bolsas) {
            bolsa.setPuntosSaldo(0f);
            bolsaPuntosRepository.save(bolsa);
        }
    }
}