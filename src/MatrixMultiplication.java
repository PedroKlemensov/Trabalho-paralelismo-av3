import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MatrixMultiplication {
    public static void main(String[] args) {

        try{
            //MATRIZES
            int [][] matrixA = {{1,2,3},{4,5,6}};
            int[][] matrixB = {{7,8}, {9,10}, {11,12}};

            //URL DO SERVIDOR
            String phpURL = "https://belmondojr.dev/proc_paralelo.php";

            //converte as matrizes em formato json
            String jsonMatrixA = convertMatrixToJson(matrixA);
            //transforma mas matrizes para serem parâmetros GET
            String encodedMatrixA = URLEncoder.encode(jsonMatrixA, "UTF-8");

            String jsonMatrixB = convertMatrixToJson(matrixB);
            String encodedMatrixB = URLEncoder.encode(jsonMatrixB, "UTF-8");

            String fullURL = phpURL + "?matrixA=" + encodedMatrixA
                    + "&matrixB=" + encodedMatrixB;



            //cria conexão
            URL url = new URL(fullURL);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //ler do servidor
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader
                            (connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while((line = reader.readLine( ) ) != null){
                response.append(line);
            }
            reader.close();

            System.out.println("Resposta: " + response.toString());

            connection.disconnect();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // Converte uma matriz em formato JSON
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