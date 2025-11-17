package conservatory;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FoodTest {

    @Test
    public void testAllFoodTypesExist() {
        // Verify all expected food types are defined
        List<Food> expectedFoods = Arrays.asList(
                Food.berries,
                Food.seeds,
                Food.fruit,
                Food.insects,
                Food.other_birds,
                Food.eggs,
                Food.small_mammals,
                Food.fish,
                Food.buds,
                Food.larvae,
                Food.aquatic_invertebrates,
                Food.nuts,
                Food.vegetation
        );

        assertEquals("Should have 13 food types", 13, expectedFoods.size());
    }

    @Test
    public void testFoodEnumValues() {
        Food[] foods = Food.values();
        assertEquals("Food enum should have 13 values", 13, foods.length);
    }

    @Test
    public void testFoodValueOf() {
        assertEquals("Should get berries", Food.berries, Food.valueOf("berries"));
        assertEquals("Should get seeds", Food.seeds, Food.valueOf("seeds"));
        assertEquals("Should get fruit", Food.fruit, Food.valueOf("fruit"));
        assertEquals("Should get insects", Food.insects, Food.valueOf("insects"));
        assertEquals("Should get other_birds", Food.other_birds, Food.valueOf("other_birds"));
        assertEquals("Should get eggs", Food.eggs, Food.valueOf("eggs"));
        assertEquals("Should get small_mammals", Food.small_mammals, Food.valueOf("small_mammals"));
        assertEquals("Should get fish", Food.fish, Food.valueOf("fish"));
        assertEquals("Should get buds", Food.buds, Food.valueOf("buds"));
        assertEquals("Should get larvae", Food.larvae, Food.valueOf("larvae"));
        assertEquals("Should get aquatic_invertebrates", Food.aquatic_invertebrates,
                Food.valueOf("aquatic_invertebrates"));
        assertEquals("Should get nuts", Food.nuts, Food.valueOf("nuts"));
        assertEquals("Should get vegetation", Food.vegetation, Food.valueOf("vegetation"));
    }

    @Test
    public void testFoodName() {
        assertEquals("Name should be berries", "berries", Food.berries.name());
        assertEquals("Name should be seeds", "seeds", Food.seeds.name());
        assertEquals("Name should be fish", "fish", Food.fish.name());
    }

    @Test
    public void testFoodOrdinal() {
        // Test that ordinals are unique and sequential
        Food[] foods = Food.values();
        for (int i = 0; i < foods.length; i++) {
            assertEquals("Ordinal should match position", i, foods[i].ordinal());
        }
    }

    @Test
    public void testFoodEquality() {
        Food food1 = Food.seeds;
        Food food2 = Food.seeds;
        Food food3 = Food.nuts;

        assertEquals("Same food types should be equal", food1, food2);
        assertNotEquals("Different food types should not be equal", food1, food3);
        assertSame("Enum values should be same instance", food1, food2);
    }

    @Test
    public void testFoodInSwitch() {
        Food food = Food.fish;
        String category = getFoodCategory(food);
        assertEquals("Fish should be animal protein", "Animal Protein", category);

        food = Food.seeds;
        category = getFoodCategory(food);
        assertEquals("Seeds should be plant matter", "Plant Matter", category);
    }

    // Helper method for testing switch statements with Food enum
    private String getFoodCategory(Food food) {
        return switch (food) {
            case fish, other_birds, small_mammals, eggs, aquatic_invertebrates, insects, larvae ->
                    "Animal Protein";
            case seeds, nuts, fruit, berries, vegetation, buds ->
                    "Plant Matter";
        };
    }

    @Test
    public void testFoodToString() {
        assertEquals("toString should return name", "berries", Food.berries.toString());
        assertEquals("toString should return name", "fish", Food.fish.toString());
        assertEquals("toString should return name", "aquatic_invertebrates",
                Food.aquatic_invertebrates.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFoodValueOf() {
        Food.valueOf("pizza"); // Should throw IllegalArgumentException
    }

    @Test(expected = NullPointerException.class)
    public void testNullFoodValueOf() {
        Food.valueOf(null); // Should throw NullPointerException
    }

    @Test
    public void testFoodComparison() {
        Food food1 = Food.berries;
        Food food2 = Food.seeds;

        // Enum compareTo uses ordinal values
        assertTrue("Comparison should use ordinals",
                food1.compareTo(food2) != 0);
    }
}