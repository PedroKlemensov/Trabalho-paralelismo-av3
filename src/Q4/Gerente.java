package Q4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Scanner;

public class Gerente {
    public void start(){
        gerar();
    }

    public void gerar(){
        try{
            Scanner s = new Scanner(System.in);
            System.out.println("Digite o número que corresponderá às linhas da Matrix A e às colunas que a B terá.(Somente um número)");
            int n = s.nextInt();
            System.out.println("Agora, digite o número de colunas da Matrix A e o número de linhas que a B terá.(Somente um número)");
            int m = s.nextInt();
            s.close();
            Random random = new Random();

            int [][] matrixA = new int[m][n];
            int[][] matrixB = new int[n][m];

            for (int i= 0; i < matrixA.length;i++){
                for (int j = 0; j < matrixA[i].length; j++){
                    matrixA[i][j] = random.nextInt(1,501);
                }
            }

            System.out.println("Sua matrixA ficou assim");
            for (int i = 0; i < matrixA.length; i++) {
                for (int j = 0; j < matrixA[i].length; j++) {
                    System.out.print(matrixA[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

            for (int i= 0; i < matrixB.length;i++){
                for (int j = 0; j < matrixB[i].length; j++){
                    matrixB[i][j] = random.nextInt(1,501);
                }
            }
            System.out.println("Sua matrixB ficou assim");

            for (int i = 0; i < matrixB.length; i++) {
                for (int j = 0; j < matrixB[i].length; j++) {
                    System.out.print(matrixB[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

            String phpURL = "https://belmondojr.dev/proc_paralelo.php";

            String jsonMatrixA = convertMatrixToJson(matrixA);
            String encodedMatrixA = URLEncoder.encode(jsonMatrixA, "UTF-8");

            String jsonMatrixB = convertMatrixToJson(matrixB);
            String encodedMatrixB = URLEncoder.encode(jsonMatrixB, "UTF-8");

            String fullURL = phpURL + "?matrixA=" + encodedMatrixA
                    + "&matrixB=" + encodedMatrixB;

            URL url = new URL(fullURL);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader
                            (connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while((line = reader.readLine( ) ) != null){
                response.append(line);
            }
            reader.close();

            System.out.println(response.toString());

            String cleanJsonString = response.toString().replace("{", "")
                    .replace("}", "")
                    .replace("\"result\":[[", "")
                    .replace("]]", "");
            String[] rows = cleanJsonString.split("],");

            for (String row : rows) {
                String[] elements = row.split(",");
                for (String element : elements) {
                    System.out.print(element + " ");
                }
                System.out.println();
            }

            connection.disconnect();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static String convertMatrixToJson(int[][] matrix) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < matrix.length; i++) {
            json.append("[");
            for (int j = 0; j < matrix[i].length; j++) {
                json.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            if (i < matrix.length - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }


}
