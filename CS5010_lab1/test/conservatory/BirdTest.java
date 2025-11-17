package conservatory;

import conservatory.subclass.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BirdTest {

    @Test
    public void testParrotCreation() {
        Bird parrot = new Parrot("African Grey", "intelligent bird", false,
                Set.of(Food.seeds, Food.nuts), 2, 150, "Hello World");

        assertEquals("Type should be African Grey", "African Grey", parrot.getType());
        assertEquals("Defining characteristic should match", "intelligent bird", parrot.getDefiningChar());
        assertFalse("Parrot should not be extinct", parrot.isExtinct());
        assertEquals("Should have 2 wings", 2, parrot.getWings());
        assertEquals("Diet should have 2 items", 2, parrot.getDiet().size());
        assertTrue("Should eat seeds", parrot.getDiet().contains(Food.seeds));
        assertTrue("Should eat nuts", parrot.getDiet().contains(Food.nuts));
    }

    @Test
    public void testParrotSpecificMethods() {
        Parrot parrot = new Parrot("Macaw", "colorful parrot", false,
                Set.of(Food.fruit), 2, 200, "Polly wants a cracker");

        assertEquals("Vocab size should be 200", "200", parrot.getVocabSize());
        assertEquals("Favorite saying should match", "Polly wants a cracker", parrot.getFavoriteSaying());
    }

    @Test
    public void testBirdOfPreyCreation() {
        Bird eagle = new BirdOfPrey("Bald Eagle", "majestic predator", false,
                Set.of(Food.fish, Food.other_birds), 2);

        assertEquals("Type should be Bald Eagle", "Bald Eagle", eagle.getType());
        assertTrue("Should eat fish", eagle.getDiet().contains(Food.fish));
        assertTrue("Should eat other birds", eagle.getDiet().contains(Food.other_birds));
        assertTrue("Should be identified as bird of prey", eagle.isBirdOfPrey());
        assertFalse("Should not be identified as waterfowl", eagle.isWaterfowl());
        assertFalse("Should not be identified as flightless", eagle.isFlightless());
    }

    @Test
    public void testFlightlessBirdCreation() {
        Bird penguin = new FlightlessBird("Emperor Penguin", "Antarctic bird", false,
                Set.of(Food.fish, Food.aquatic_invertebrates), 0);

        assertEquals("Type should be Emperor Penguin", "Emperor Penguin", penguin.getType());
        assertEquals("Should have 0 wings", 0, penguin.getWings());
        assertTrue("Should be identified as flightless", penguin.isFlightless());
        assertFalse("Should not be identified as bird of prey", penguin.isBirdOfPrey());
        assertFalse("Should not be identified as waterfowl", penguin.isWaterfowl());
    }

    @Test
    public void testWaterfowlCreation() {
        Bird duck = new Waterfowl("Mallard", "common duck", false,
                Set.of(Food.insects, Food.vegetation), 2, "Lake Michigan");

        assertEquals("Type should be Mallard", "Mallard", duck.getType());
        assertTrue("Should be identified as waterfowl", duck.isWaterfowl());
        assertFalse("Should not be identified as bird of prey", duck.isBirdOfPrey());
        assertFalse("Should not be identified as flightless", duck.isFlightless());
    }

    @Test
    public void testWaterfowlBodyOfWater() {
        Waterfowl swan = new Waterfowl("Mute Swan", "elegant waterfowl", false,
                Set.of(Food.vegetation, Food.aquatic_invertebrates), 2, "River Thames");

        assertEquals("Body of water should match", "River Thames", swan.getBodyOfWater());
    }

    @Test
    public void testOwlCreation() {
        Bird owl = new Owl("Great Horned Owl", "powerful owl", false,
                Set.of(Food.small_mammals, Food.other_birds), 2);

        assertEquals("Type should be Great Horned Owl", "Great Horned Owl", owl.getType());
        assertTrue("Should eat small mammals", owl.getDiet().contains(Food.small_mammals));
        assertFalse("Owl should not be bird of prey type", owl.isBirdOfPrey());
        assertFalse("Owl should not be waterfowl", owl.isWaterfowl());
        assertFalse("Owl should not be flightless", owl.isFlightless());
    }

    @Test
    public void testShoreBirdCreation() {
        Bird sandpiper = new ShoreBird("Sandpiper", "small wading bird", false,
                Set.of(Food.insects, Food.aquatic_invertebrates), 2, "Atlantic Coast");

        assertEquals("Type should be Sandpiper", "Sandpiper", sandpiper.getType());
        assertTrue("Should eat insects", sandpiper.getDiet().contains(Food.insects));
        assertFalse("Shore bird should not be waterfowl", sandpiper.isWaterfowl());
        assertFalse("Shore bird should not be bird of prey", sandpiper.isBirdOfPrey());
        assertFalse("Shore bird should not be flightless", sandpiper.isFlightless());
    }

    @Test
    public void testShoreBirdBodyOfWater() {
        ShoreBird plover = new ShoreBird("Plover", "beach bird", false,
                Set.of(Food.insects), 2, "Pacific Ocean");

        assertEquals("Body of water should match", "Pacific Ocean", plover.getBodyOfWater());
    }

    @Test
    public void testExtinctBird() {
        Bird dodo = new FlightlessBird("Dodo", "extinct flightless bird", true,
                Set.of(Food.fruit, Food.seeds), 0);

        assertTrue("Dodo should be extinct", dodo.isExtinct());
    }

    @Test
    public void testNonExtinctBird() {
        Bird robin = new Owl("American Robin", "common bird", false,
                Set.of(Food.insects, Food.berries), 2);

        assertFalse("Robin should not be extinct", robin.isExtinct());
    }

    @Test
    public void testBirdTypeChecks() {
        Bird eagle = new BirdOfPrey("Golden Eagle", "powerful hunter", false,
                Set.of(Food.small_mammals), 2);
        Bird duck = new Waterfowl("Wood Duck", "colorful duck", false,
                Set.of(Food.seeds), 2, "Pond");
        Bird kiwi = new FlightlessBird("Kiwi", "New Zealand bird", false,
                Set.of(Food.insects), 0);
        Bird parrot = new Parrot("Cockatiel", "small parrot", false,
                Set.of(Food.seeds), 2, 50, "Tweet");

        // Eagle checks
        assertTrue("Eagle should be bird of prey", eagle.isBirdOfPrey());
        assertFalse("Eagle should not be waterfowl", eagle.isWaterfowl());
        assertFalse("Eagle should not be flightless", eagle.isFlightless());

        // Duck checks
        assertFalse("Duck should not be bird of prey", duck.isBirdOfPrey());
        assertTrue("Duck should be waterfowl", duck.isWaterfowl());
        assertFalse("Duck should not be flightless", duck.isFlightless());

        // Kiwi checks
        assertFalse("Kiwi should not be bird of prey", kiwi.isBirdOfPrey());
        assertFalse("Kiwi should not be waterfowl", kiwi.isWaterfowl());
        assertTrue("Kiwi should be flightless", kiwi.isFlightless());

        // Parrot checks
        assertFalse("Parrot should not be bird of prey", parrot.isBirdOfPrey());
        assertFalse("Parrot should not be waterfowl", parrot.isWaterfowl());
        assertFalse("Parrot should not be flightless", parrot.isFlightless());
    }

    @Test
    public void testBirdDietVariety() {
        Set<Food> largeDiet = new HashSet<>();
        largeDiet.add(Food.seeds);
        largeDiet.add(Food.nuts);
        largeDiet.add(Food.fruit);
        largeDiet.add(Food.insects);
        largeDiet.add(Food.berries);

        Bird omnivore = new Parrot("Omnivorous Parrot", "eats everything", false,
                largeDiet, 2, 100, "I love food");

        assertEquals("Should have 5 food types", 5, omnivore.getDiet().size());
        assertTrue("Should eat seeds", omnivore.getDiet().contains(Food.seeds));
        assertTrue("Should eat nuts", omnivore.getDiet().contains(Food.nuts));
        assertTrue("Should eat fruit", omnivore.getDiet().contains(Food.fruit));
        assertTrue("Should eat insects", omnivore.getDiet().contains(Food.insects));
        assertTrue("Should eat berries", omnivore.getDiet().contains(Food.berries));
    }

    @Test
    public void testBirdWithSingleFoodDiet() {
        Bird specialist = new BirdOfPrey("Fish Eagle", "fish specialist", false,
                Set.of(Food.fish), 2);

        assertEquals("Should have only 1 food type", 1, specialist.getDiet().size());
        assertTrue("Should only eat fish", specialist.getDiet().contains(Food.fish));
    }

    @Test
    public void testBirdWithNoWings() {
        Bird ratite = new FlightlessBird("Cassowary", "large flightless", false,
                Set.of(Food.fruit, Food.vegetation), 0);

        assertEquals("Should have 0 wings", 0, ratite.getWings());
    }

    @Test
    public void testBirdWithTwoWings() {
        Bird sparrow = new Owl("Sparrow", "small bird", false,
                Set.of(Food.seeds, Food.insects), 2);

        assertEquals("Should have 2 wings", 2, sparrow.getWings());
    }

    @Test
    public void testMultipleBirdsOfSameType() {
        Bird parrot1 = new Parrot("African Grey", "first parrot", false,
                Set.of(Food.seeds), 2, 100, "Hello");
        Bird parrot2 = new Parrot("African Grey", "second parrot", false,
                Set.of(Food.nuts), 2, 150, "Goodbye");

        assertEquals("Both should have same type", parrot1.getType(), parrot2.getType());
        assertNotEquals("Should have different characteristics",
                parrot1.getDefiningChar(), parrot2.getDefiningChar());
    }
}