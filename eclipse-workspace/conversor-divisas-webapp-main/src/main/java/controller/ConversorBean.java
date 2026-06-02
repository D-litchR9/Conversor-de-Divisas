package controller;

import service.DivisaService;
import model.Transaccion;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("conversorBean")
@ViewScoped
public class ConversorBean implements Serializable {

    private static final long serialVersionUID = -1354805372699217573L;

    private DivisaService divisaService;
    private static List<Transaccion> historial = new ArrayList<>();

    private Transaccion transaccion;
    private double resultado;

    @PostConstruct
    public void init() {
        divisaService = new DivisaService();
        transaccion = new Transaccion();
    }

    public void realizarConversion() {
        double monto = transaccion.getValorIngresado();
        String tipo = transaccion.getTipoTransaccion();

        if (tipo == null || tipo.isEmpty() || monto <= 0) {
            resultado = 0;
            return;
        }

        Transaccion nuevaTransaccion = divisaService.registrarTransaccion(monto, tipo);
        this.resultado = Double.parseDouble(nuevaTransaccion.getResultado());
        historial.add(nuevaTransaccion);
        transaccion.setTasa(nuevaTransaccion.getTasa());
    }

    // Getters y setters
    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    public static List<Transaccion> getHistorial() {
        return historial;
    }

    public static void setHistorial(List<Transaccion> historial) {
        ConversorBean.historial = historial;
    }
}