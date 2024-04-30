import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import org.example.dao.AnimalDAO;
import org.example.service.Animal;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AnimalDaoTest {

    @Test
    public void getAnimals_TypeQueryTest() throws SQLException, JsonProcessingException {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        Dao<Animal, String> animalDao = mock(Dao.class);
        QueryBuilder<Animal, String> queryBuilder = mock(QueryBuilder.class);
        Where<Animal, String> where = mock(Where.class);
        AnimalDAO animalDAO = new AnimalDAO();

        when(request.queryParams("type")).thenReturn("cat");
        when(animalDao.queryBuilder()).thenReturn(queryBuilder);
        when(queryBuilder.where()).thenReturn(where);

        Animal animal = new Animal();
        animal.setType("cat");
        animal.setId(1);
        animal.setName("Milo");
        animal.setSex("male");
        animal.setCategory(3);
        animal.setWeight(40);
        animal.setCost(51);
        when(queryBuilder.query()).thenReturn(List.of(animal));
        String expectedJson  = new ObjectMapper().writeValueAsString(animal);
        String result = animalDAO.getAnimals(request, response);
        assertTrue(result.contains(expectedJson));

    }

}
