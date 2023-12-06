package Q2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gerente {
    List<String> produtos = new ArrayList<>();
    List<String> valores = new ArrayList<>();


    public void start(){
        anotar();
    }

    public void anotar(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("digite seus produtos mais precos (um por linha) apois anotar todos digite \"1\" ");
            String linha = scanner.nextLine();

            if (linha.equals("1")) {
                double valor = emviar();
                System.out.println("Obrigado por comprar conosco seu total foi de: "+valor);
                break;
            }

            String[] partes = linha.split(" ");

            if (partes.length >= 2) {
                produtos.add(partes[0]);
                valores.add(partes[1]);



            } else {
                System.out.println("Esqueceu de anotar corretamente");
            }
        }

        scanner.close();
        emviar();
    }

    public double emviar(){
        double somaTotal = Double.parseDouble(null);
        try {
            String serverUrl = "https://belmondojr.dev/compra.php?";
             somaTotal = verificar (serverUrl, produtos, valores);


        } catch (Exception e) {
            e.printStackTrace();

        }

        return somaTotal;
    }

    private static double verificar(String url, List<String> produtos, List<String> valores) throws Exception {
        // Constrói a URL com os parâmetros
        StringBuilder urlWithParams = new StringBuilder(url);
        for (int i = 0; i < produtos.size(); i++) {
            urlWithParams.append("&products[]=").append(URLEncoder.encode(produtos.get(i), "UTF-8"));
            urlWithParams.append("&amounts[]=").append(valores.get(i));
        }

        // Cria a conexão
        URL serverUrl = new URL(urlWithParams.toString());
        System.out.println(urlWithParams.toString());
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

        // Define o método de requisição
        connection.setRequestMethod("GET");

        // Obtém a resposta
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Lê a resposta
        double totalAmount = 0.0;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // Exibe a resposta do servidor
            System.out.println("Response Body:\n" + response.toString());

            // Obtém a soma total dos valores recebidos do servidor
            totalAmount = Double.parseDouble(response.toString());
        }

        // Fecha a conexão
        connection.disconnect();

        return totalAmount;
    }

}
