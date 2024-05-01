import org.example.service.Animal;
import org.example.service.IncorrectAnimalCostException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnimalTest {
    @Test
    public void testEvaluateCategory() {
        // Test cases for different cost ranges
        assertCategoryForCost(1, 0);   // cost = 0 should be in category 1
        assertCategoryForCost(1, 20);  // cost = 20 should be in category 1
        assertCategoryForCost(2, 21);  // cost = 21 should be in category 2
        assertCategoryForCost(2, 41);  // cost = 41 should be in category 2
        assertCategoryForCost(3, 42);  // cost = 42 should be in category 3
        assertCategoryForCost(3, 61);  // cost = 61 should be in category 3
        assertCategoryForCost(4, 62);  // cost = 62 should be in category 4
        assertCategoryForCost(4, 100); // cost = 100 should be in category 4

        // Test case for cost less than 0
        assertThrows(IncorrectAnimalCostException.class, () -> {
            Animal animal = new Animal();
            animal.setCost(-1);
            animal.evaluateCategory(); // cost less than 0 should throw exception
        });

    }

    private void assertCategoryForCost(int expectedCategory, int cost) {
        Animal animal = new Animal();
        animal.setCost(cost);
        animal.evaluateCategory();
        assertEquals(expectedCategory, animal.getCategory());
    }
}
