package org.example.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import org.example.service.Animal;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class AnimalDAO {
    Dao<Animal, String> animalDao;
    AnimalDatabase animalDatabase;
    public AnimalDAO()  {
        animalDatabase = new AnimalDatabase();
        try {
            animalDao = DaoManager.createDao(animalDatabase.getConnectionSource(), Animal.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAnimal(Animal animal){
        try {
            animalDao.create(animal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAnimals(Request request, Response response){
        String type = request.queryParams("type");
        String category = request.queryParams("category");
        String sex = request.queryParams("sex");
        String sortBy = request.queryParams("sort_by");

        // filters and sort
        QueryBuilder<Animal, String> queryBuilder = animalDao.queryBuilder();
        try {
            if (type != null) {
                queryBuilder.where().eq("type", type);
            }
            if (category != null) {
                queryBuilder.where().eq("category", category);
            }
            if (sex != null) {
                queryBuilder.where().eq("sex", sex);
            }
            if (sortBy != null) {
                queryBuilder.orderBy(sortBy, true); // sort by specific field (true - ASC, false - DESC)
            }

            List<Animal> animals = queryBuilder.query();

            // Transform into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json;

            if(!animals.isEmpty()) {
                json = objectMapper.writeValueAsString(animals);
                if (!json.equals("[]"))
                    response.status(200); // OK
                else {
                    response.status(501);
                    json = "No animals found by given parameters";
                }
            }
            else{
                response.status(502);
                json = "No animals found in database";
            }
            return json;
        } catch (SQLException | com.fasterxml.jackson.core.JsonProcessingException e) {
            response.status(500); // Server error
            return "Error processing request";
        }
    }

    public void closeConnection(){
        animalDatabase.closeConnection();
    }


}
