package com.proyecto.electiva3.backend.services;

import com.proyecto.electiva3.backend.model.*;
import com.proyecto.electiva3.backend.repository.BolsaPuntosRepository;
import com.proyecto.electiva3.backend.repository.PuntosCabeceraRepository;
import com.proyecto.electiva3.backend.repository.PuntosDetalleRepostitory;
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

    @Autowired
    private PuntosCabeceraRepository puntosCabeceraRepository;

    @Autowired
    private PuntosDetalleRepostitory puntosDetalleRepostitory;

    /* Registrar puntos a un cliente */
    public void registrarPuntos(Long idCliente, Float monto) throws Exception {
        Cliente cliente = clienteService.findById(idCliente);
        ParamPuntos parametrizacionPuntos = paramPuntosService.getCurrentParam();

        if(cliente == null) {
            throw new Exception("El cliente no se encuentra en la base de datos.");
        }

        Float cantPuntos = calcularPuntos(monto);

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
    public void usarPuntos(Long idCliente, Long idConcepto) throws Exception    {
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

        // si existe suficientes puntos, descontar de las bolsas y registrar
        // en puntos_cabecera y puntos_detalle
        puntosConcepto = concepto.getPuntos();

        PuntosCabecera puntosCab = new PuntosCabecera();
        puntosCab.setCliente(cliente);
        puntosCab.setConcepto(concepto);
        puntosCab.setFecha(LocalDate.now());
        puntosCab.setPuntajeUtilizado(puntosConcepto);
        puntosCabeceraRepository.save(puntosCab);

        for(BolsaPuntos bolsa : bolsaPuntos) {
            if(bolsa.getPuntosSaldo() == 0) continue;

            PuntosDetalle puntosDet = new PuntosDetalle();
            puntosDet.setBolsaPuntos(bolsa);
            puntosDet.setPuntosCabecera(puntosCab);

            if(bolsa.getPuntosSaldo() >= puntosConcepto) {
                bolsa.setPuntosUsados(bolsa.getPuntosUsados() + puntosConcepto);
                bolsa.setPuntosSaldo(bolsa.getPuntosSaldo() - puntosConcepto);
                puntosDet.setPuntajeUtilizado(puntosConcepto); // registrar puntos utilizados
                bolsaPuntosRepository.save(bolsa);
                puntosCab.getPuntosDetalles().add(puntosDet);
                puntosDetalleRepostitory.save(puntosDet);
                break;
            } else {
                puntosConcepto -= bolsa.getPuntosSaldo();
                puntosDet.setPuntajeUtilizado(bolsa.getPuntosSaldo()); // registrar puntos utilizados
                bolsa.setPuntosUsados(bolsa.getPuntosUsados() + bolsa.getPuntosSaldo());
                bolsa.setPuntosSaldo(0f);
                bolsaPuntosRepository.save(bolsa);
            }
            puntosCab.getPuntosDetalles().add(puntosDet);
            puntosDetalleRepostitory.save(puntosDet);
        }
        puntosCabeceraRepository.save(puntosCab);
        System.out.println("Puntos utilizados exitosamente");

        return;
    }

    public Float calcularPuntos(Float monto) throws Exception {
        ReglasPuntos regla = reglasPuntosRepository.findTopByLimiteSuperiorGreaterThanEqualAndLimiteInferiorLessThanEqual(monto, monto);
        ParamPuntos parametrizacionPuntos = paramPuntosService.getCurrentParam();

        if(regla == null) {
            regla = reglasPuntosRepository.findTopByLimiteSuperiorGreaterThanEqualAndLimiteInferiorLessThanEqual(null, null);
        }

        if (regla == null) {
            System.out.printf(">>> RESUL: No existe regla para este monto. <<<");
            return 0f;
        }

        if(parametrizacionPuntos == null) {
            System.out.println(">>> RESUL: No hay puntos parametrizados disponibles. <<<");
            return 0f;
        }

        Float cantPuntos = monto / regla.getMonto();
        if(cantPuntos < 0) cantPuntos = 0f;

        return cantPuntos;
    }

}
