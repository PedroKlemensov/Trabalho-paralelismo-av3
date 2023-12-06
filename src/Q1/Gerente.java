package Q1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Gerente {


    public void start(){
        try {
            // URL do servidor de dados do sensor
            String serverUrl = "https://belmondojr.dev/comunicacao.php?";

            // Dados do sensor
            String[] sensors = {"altura", "temperatura", "pressao", "prosimidade"};

            // Envia os dados do sensor para o servidor
            enviar(serverUrl, sensors);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enviar(String url, String[] sensors) throws Exception {
        StringBuilder urlWithParams = new StringBuilder(url);
        for (String sensor : sensors) {
            urlWithParams.append("&sensors[]=").append(URLEncoder.encode(sensor, "UTF-8"));
        }
        URL serverUrl = new URL(urlWithParams.toString());
        System.out.println(serverUrl);
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.println("Response Body:\n" + response.toString());
        }

        connection.disconnect();
    }
}

