package model;

import java.io.Serializable;

public class Transaccion implements Serializable {

    private static final long serialVersionUID = -760149887136496294L;

    private double valorIngresado;
    private double tasa;
    private String tipoTransaccion; // "USD_A_COP" o "COP_A_USD"
    private String resultado;       // resultado como String (para mostrar)

    // Constructores
    public Transaccion() {}

    public Transaccion(double valorIngresado, double tasa, String tipoTransaccion) {
        this.valorIngresado = valorIngresado;
        this.tasa = tasa;
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * Realiza la conversión según el tipo.
     * @return resultado numérico de la conversión
     */
    public double convertir() {
        if ("USD_A_COP".equals(this.tipoTransaccion)) {
            return valorIngresado * tasa;
        } else if ("COP_A_USD".equals(this.tipoTransaccion)) {
            return valorIngresado / tasa;
        } else {
            return 0;
        }
    }

    // Getters y setters
    public double getValorIngresado() { return valorIngresado; }
    public void setValorIngresado(double valorIngresado) { this.valorIngresado = valorIngresado; }

    public double getTasa() { return tasa; }
    public void setTasa(double tasa) { this.tasa = tasa; }

    public String getTipoTransaccion() { return tipoTransaccion; }
    public void setTipoTransaccion(String tipoTransaccion) { this.tipoTransaccion = tipoTransaccion; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}