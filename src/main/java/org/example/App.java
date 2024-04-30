package org.example;

import org.example.service.AnimalController;
import javax.servlet.MultipartConfigElement;
import java.util.logging.Logger;

import static spark.Spark.*;

public class App {
    public static void main(String[] args)  {

        Logger LOG = Logger.getLogger(App.class.getSimpleName());


        get("/files/uploads", (req, res) ->
                "<form method='post' enctype='multipart/form-data'>"
                        + "    <input type='file' name='uploaded_file' accept='.csv, .xml'>"
                        + "    <button>Upload file</button>"
                        + "</form>"
        );

        post("/files/uploads", (request, response) -> {
            try {
                // Отримуємо тип файлу, який було завантажено
                request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
                String contentType = request.raw().getPart("uploaded_file").getContentType();
                LOG.info("Content type: " +  contentType);

                // Перевіряємо, чи містить запит CSV-файл
                if (contentType != null && contentType.contains("text/csv")) {
                    return AnimalController.handleCsv(request, response);
                }
                // Перевіряємо, чи містить запит XML-файл
                else if (contentType != null && contentType.contains("text/xml")) {
                    return AnimalController.handleXml(request, response);
                } else {
                    response.status(500);
                    return "Unsupported file format. Please upload a CSV or XML file.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(501);
                return "Error uploading file.";
            }
        });

        get("/animals", AnimalController::handleGet);
        get("/swagger", (request, response) -> App.class.getResourceAsStream("/swagger/index.html"));
        get("/", (request, response) ->
                "<h1>Animal api</h1>"+
                "<p><a href=\"http://localhost:4567/animals\">Get list of all animals</a></p>"+
                "<p><a href=\"http://localhost:4567/files/uploads\">Upload csv or xml file</a></p>" +
                        "<p><a href=\"http://localhost:4567/swagger\">Swagger file documentation</a></p>");

    }
}