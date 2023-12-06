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
                String valor = emviar();
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

    public String emviar(){
        String somaTotal="";
        try {
            String serverUrl = "https://belmondojr.dev/compra.php?";
             somaTotal = verificar (serverUrl, produtos, valores);


        } catch (Exception e) {
            e.printStackTrace();

        }

        return somaTotal;
    }

    private static String verificar(String url, List<String> produtos, List<String> valores) throws Exception {
        StringBuilder urlWithParams = new StringBuilder(url);
        for (int i = 0; i < produtos.size(); i++) {
            urlWithParams.append("&products[]=").append(URLEncoder.encode(produtos.get(i), "UTF-8"));
            urlWithParams.append("&values[]=").append(valores.get(i));
        }

        URL serverUrl = new URL(urlWithParams.toString());
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        String totalAmount ="" ;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }


            totalAmount = response.toString();
        }
        connection.disconnect();

        return totalAmount;
    }

}
