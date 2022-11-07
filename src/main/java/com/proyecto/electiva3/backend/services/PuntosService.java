package com.proyecto.electiva3.backend.services;

import com.proyecto.electiva3.backend.model.*;
import com.proyecto.electiva3.backend.repository.BolsaPuntosRepository;
import com.proyecto.electiva3.backend.repository.ReglasPuntosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PuntosService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ConceptoService conceptoService;

    @Autowired
    private ReglasPuntosRepository reglasPuntosRepository;

    @Autowired
    private BolsaPuntosRepository bolsaPuntosRepository;

    @Autowired
    private ParamPuntosService paramPuntosService;

    /* Registrar puntos a un cliente */
    public void registrarPuntos(Long idCliente, Float monto) throws Exception {
        Cliente cliente = clienteService.findById(idCliente);
        ReglasPuntos regla = reglasPuntosRepository.findTopByLimiteSuperiorGreaterThanEqualAndLimiteInferiorLessThanEqual(monto, monto);
        ParamPuntos parametrizacionPuntos = paramPuntosService.getCurrentParam();

        if(cliente == null) {
            throw new Exception("El cliente no se encuentra en la base de datos.");
        }

        if(regla == null) {
            regla = reglasPuntosRepository.findTopByLimiteSuperiorGreaterThanEqualAndLimiteInferiorLessThanEqual(null, null);
        }

        if (regla == null) {
            throw new Exception("No existe regla para este monto.");
        }

        if(parametrizacionPuntos == null) {
            throw new Exception("No hay puntos parametrizados disponibles.");
        }

        Float cantPuntos = monto / regla.getMonto();
        if(cantPuntos < 0) cantPuntos = 0f;

        BolsaPuntos bolsaPuntos = new BolsaPuntos();
        bolsaPuntos.setCliente(cliente);
        // asigna la fecha actual
        bolsaPuntos.setFechaAsig(LocalDate.now());
        // asigna la fecha actual + los dias de duracion del punto
        bolsaPuntos.setFechaCaducidad(LocalDate.now().plusDays(parametrizacionPuntos.getDuracion()));
        bolsaPuntos.setPuntos(cantPuntos);
        bolsaPuntos.setPuntosUsados(0f);
        bolsaPuntos.setPuntosSaldo(cantPuntos);
        bolsaPuntos.setMontoOperacion(monto);
        bolsaPuntosRepository.save(bolsaPuntos);
        System.out.println("Puntos registrados correctamente.");

        return;
    }

    /* Usar puntos disponibles */
    public void usarPuntos(Long idCliente, Long idConcepto) throws Exception {
        Cliente cliente = clienteService.findById(idCliente);
        Concepto concepto = conceptoService.findById(idConcepto);
        List<BolsaPuntos> bolsaPuntos = new ArrayList<>();
        Float puntosDisp = 0f;
        Float puntosConcepto = 0f;

        if(cliente == null) {
            throw new Exception("El cliente no se encuentra en la base de datos.");
        }

        if(concepto == null) {
            throw new Exception("El concepto no se encuentra en la base de datos.");
        }

        bolsaPuntos = bolsaPuntosRepository.findByClienteOrderByFechaCaducidad(cliente);
        for (BolsaPuntos bolsa : bolsaPuntos) {
            puntosDisp += bolsa.getPuntosSaldo();
        }

        if(puntosDisp == 0f || puntosDisp < concepto.getPuntos()) {
            throw new Exception("El cliente no cuenta con suficientes puntos.");
        }

        // si existe suficientes puntos, descontar de las bolsas
        puntosConcepto = concepto.getPuntos();
        for(BolsaPuntos bolsa : bolsaPuntos) {
            if(bolsa.getPuntosSaldo() > puntosConcepto) {
                bolsa.setPuntosUsados(bolsa.getPuntosUsados() + puntosConcepto);
                bolsa.setPuntosSaldo(bolsa.getPuntosSaldo() - puntosConcepto);
                bolsaPuntosRepository.save(bolsa);
                break;
            } else {
                puntosConcepto -= bolsa.getPuntosSaldo();
                bolsa.setPuntosUsados(bolsa.getPuntosUsados() + bolsa.getPuntosSaldo());
                bolsa.setPuntosSaldo(0f);
                bolsaPuntosRepository.delete(bolsa);
            }
        }

        System.out.println("Puntos utilizados exitosamente");

        return;
    }

}
