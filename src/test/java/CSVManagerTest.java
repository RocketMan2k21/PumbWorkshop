import org.example.service.Animal;
import org.example.service.CSVManager;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class CSVManagerTest {
    private static String CSV_FILE = "testData/animals_test.csv";

    @Test
    public void readCsv(){
        List<Animal> animalList = List.of(new Animal(0, "Rocky", "dog", "male", 18, 77),
                new Animal(0, "Sadie", "cat", "male", 26, 27));
        try {
            List<Animal> csvRequest = CSVManager.readFromCSVRequest(new FileInputStream(CSV_FILE));
            assertArrayEquals(csvRequest.toArray(), animalList.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
