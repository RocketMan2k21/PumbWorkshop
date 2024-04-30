package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.dao.AnimalDAO;
import spark.Request;
import spark.Response;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AnimalController {
    static AnimalDAO animalDAO = new AnimalDAO();
    static Logger LOG = Logger.getLogger(AnimalController.class.getSimpleName());
    public static int handleCsv(Request request, Response response) {
        try (InputStream is = request.raw().getPart("uploaded_file").getInputStream()) {
            List<Animal> animals = CSVManager.readFromCSVRequest(is);

            checkAndAddAnimals(animals, response);
        }catch (ServletException | IOException e){
            e.printStackTrace();
        }

        return response.status();
    }

   public static int handleXml(Request request, Response response) {
       try (InputStream is = request.raw().getPart("uploaded_file").getInputStream()) {
           XmlMapper xmlMapper = new XmlMapper();
           List<Animal> animals = xmlMapper.readValue(is, new TypeReference<>() {
           });

           checkAndAddAnimals(animals, response);
       }catch (ServletException | IOException e){
           e.printStackTrace();
       }
       return response.status();
   }

   public static String handleGet(Request request, Response response){
       String body = animalDAO.getAnimals(request, response);
       if(body != null && !body.isEmpty()){
           response.status(200);
           return body;
       }else{
           response.status(500);
           return "There are no animals in database";
       }
   }

    public static void checkAndAddAnimals(List<Animal> animals, Response response) {
        List<Animal> validAnimals = new ArrayList<>();
        // Filter animals based on the checkAnimal method
        for (Animal animal : animals) {
            if (isValidAnimal(animal)) {
                animal.eveluateCategory();
                LOG.info(animal.toString());
                validAnimals.add(animal);
            }
        }

        if(validAnimals.isEmpty()){
            LOG.info("NO VALID ANIMALS!!");
            response.status(500);
        }else{
            validAnimals.forEach(animal -> animalDAO.addAnimal(animal));
            LOG.info("Animals added successfully to database");
            response.status(200);
        }

    }

    public static boolean isValidAnimal(Animal animal){
        return animal != null
                && animal.getName() != null
                && animal.getType() != null
                && animal.getSex() != null
                && !animal.getType().isEmpty()
                && !animal.getName().isEmpty()
                && !animal.getSex().isEmpty()
                && animal.getWeight() != 0
                && animal.getCost() != 0;

    }
}
