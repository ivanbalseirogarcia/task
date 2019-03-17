package ivanbalseirogarcia.task;

import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TaskApplication {

    static JsonResponse jsonData = null;
    static DocJdbc docJdbc = new DocJdbc();

    public static void main(String[] args) {
        resetDB();
        jsonData = getDataFromRestService();
        if (jsonData != null) {
            writeCsv(jsonData.getResponse().getDocs());
            docJdbc.saveDocs(jsonData.getResponse().getDocs());
            // Retrieve from DB the previously inserted docs and prompt them to console
            System.out.println(docJdbc.getDocs());
        }
    }

    // Method to clear DB data from previous executions
    private static void resetDB(){
        docJdbc.clearDB();
    }

    //Method that consumes the rest service
    private static JsonResponse getDataFromRestService() {
        //Give the URL that it will get the data from
        final String uri = "http://api.plos.org/search?q=title:DNA";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, JsonResponse.class);
    }

    //Method to manually generate a csv file with a custom toString method and with a printed header at the top
    private static void writeCsv(List<Doc> docs) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("data.csv"));
            writer.write("ID" + ',' + "Journal" + ',' + "EISSN" + ',' + "Publication Date" + ',' + "Article Type" + ',' + "Author Display" + ',' + "Abstract" + ',' + "Title Display" + ',' + "Score" + "\n");
            for (Doc doc : docs) {
                writer.write(doc.toCSVString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
