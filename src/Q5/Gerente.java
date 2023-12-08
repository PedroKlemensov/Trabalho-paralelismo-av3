package Q5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class Gerente {
    public void start (){
        emviar();
    }



    public void emviar(){
        try {
            String serverUrl = "https://belmondojr.dev/ordenacao.php";
            Random random = new Random();
            Scanner scanner = new Scanner(System.in);

            String method = "bubbleSort";
            System.out.println("digite a quantidade de funcionarios (aquanta ate bem ate 315)");
            int n = scanner.nextInt();
            String[] vector = new String[n];

            for (int i = 0; i < vector.length; i++) {
                vector[i] = Integer.toString(random.nextInt(1, 120));
            }

            String urlWithParams = serverUrl + "?method=" + method;
            for (String value : vector) {
                urlWithParams += "&vector[]=" + value;
            }
            sendSensorData(urlWithParams);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendSensorData(String url) throws Exception {


        URL serverUrl = new URL(url.toString());
        System.out.println(serverUrl);
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.println(response.toString());
        }

        connection.disconnect();
    }

}
