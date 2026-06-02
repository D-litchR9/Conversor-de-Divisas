package service;

import model.Transaccion;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DivisaService {

    private static final String API_URL_DOLAR = "https://co.dolarapi.com/v1/trm";

    /**
     * Obtiene la tasa de cambio actual (USD a COP) desde la API.
     * @return tasa (ej. 3800.50)
     */
    public double obtenerTasaUSD_COP() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL_DOLAR))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());
            double valorDolar = rootNode.get("valor").asDouble();
            System.out.println("Tasa USD/COP obtenida: " + valorDolar);
            return valorDolar;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener la tasa. Usando valor por defecto: 3800");
            return 3800; // valor por defecto
        }
    }

    /**
     * Registra una transacción usando la tasa real de la API.
     * @param valorIngresado monto a convertir
     * @param tipoTransaccion "USD_A_COP" o "COP_A_USD"
     * @return Transaccion con los datos y el resultado ya calculado (en su campo resultado)
     */
    public Transaccion registrarTransaccion(double valorIngresado, String tipoTransaccion) {
        double tasa = obtenerTasaUSD_COP(); // misma tasa para ambas conversiones
        Transaccion nuevaTransaccion = new Transaccion(valorIngresado, tasa, tipoTransaccion);
        double resultadoCalculado = nuevaTransaccion.convertir();
        nuevaTransaccion.setResultado(String.valueOf(resultadoCalculado));
        return nuevaTransaccion;
    }
}