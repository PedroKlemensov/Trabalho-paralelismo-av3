package Q3;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Gerente {

    List<Double> temperatures = new ArrayList<>();
    List<Double> humidities = new ArrayList<>();
    List<Double> pressures = new ArrayList<>();


    public void start() {
        Scanner scanner = new Scanner(System.in);

        do {
            double[] valoresAnteriores = pegarDados();

            double auxT = valoresAnteriores[0];
            double auxH = valoresAnteriores[1];
            double auxP = valoresAnteriores[2];
            temperatures.add(auxP);
            humidities.add(auxT);
            pressures.add(auxH);


            double[] novosValores = pegarDados();
            temperatures.add(novosValores[0]);
            humidities.add(novosValores[1]);
            pressures.add(novosValores[2]);
            System.out.println("Comparando valores...");

            System.out.println(auxT +" "+ novosValores[0]);
            if (novosValores[0] > auxT) {
                System.out.println("A temperatura aumentou.");
            } else if (novosValores[0] < auxT) {
                System.out.println("A temperatura diminuiu.");
            } else {
                System.out.println("A temperatura permaneceu igual.");
            }

            System.out.println(auxH +" "+ novosValores[1]);
            if (novosValores[1] > auxH) {
                System.out.println("A umidade aumentou.");
            } else if (novosValores[1] < auxH) {
                System.out.println("A umidade diminuiu.");
            } else {
                System.out.println("A umidade permaneceu igual.");
            }
            System.out.println(auxP +" "+ novosValores[2]);

            if (novosValores[2] > auxP) {
                System.out.println("A pressão aumentou.");
            } else if (novosValores[2] < auxP) {
                System.out.println("A pressão diminuiu.");
            } else {
                System.out.println("A pressão permaneceu igual.");
            }

            System.out.println("Deseja analisar novamente? (Digite '1' para continuar)");
        } while (scanner.nextLine().equalsIgnoreCase("1"));

        System.out.println("Programa encerrado.");
        printlog();
        scanner.close();
    }

    private void printlog() {
        System.out.println("Valores de Temperatura:");
        for (Double temperatura : temperatures) {
            System.out.print(temperatura+" ");
        }

        System.out.println("\nValores de Umidade:");
        for (Double umidade : humidities) {
            System.out.print(umidade+" ");
        }

        System.out.println("\nValores de Pressão:");
        for (Double pressao : pressures) {
            System.out.print(pressao+" ");
        }
    }


    public double[] pegarDados () {
        double[] valores = new double[3];
        try {
            String serverUrl = "https://belmondojr.dev/sensores.php";
            valores = pegarDados(serverUrl);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return valores;
    }

    private static double[] pegarDados(String url) throws Exception {
        String jsonFromApi = sendGetRequest(url);
        return extrairValores(jsonFromApi);
    }

    private static String sendGetRequest(String url) throws Exception {
        URL serverUrl = new URL(url);
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

            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

    private static double[] extrairValores(String json) {
        json = json.replaceAll("[{}\"]", "");

        String[] partes = json.split(",");

        double temperature = Double.parseDouble(partes[0].split(":")[1]);
        double humidity = Double.parseDouble(partes[1].split(":")[1]);
        double pressure = Double.parseDouble(partes[2].split(":")[1]);

        return new double[]{temperature, humidity, pressure};
    }
}
