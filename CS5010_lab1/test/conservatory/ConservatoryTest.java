package conservatory;

import conservatory.subclass.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ConservatoryTest {
    private Conservatory conservatory;
    private Bird parrot;
    private Bird owl;
    private Bird eagle;
    private Bird duck;
    private Bird penguin;
    private Bird extinctBird;
    private Bird hawk;

    @Before
    public void setUp() {
        // Create a conservatory with 5 aviaries for testing
        List<String> locations = Arrays.asList("North Wing", "South Wing", "East Wing", "West Wing", "Central Hall");
        conservatory = new Conservatory(locations);

        // Create various birds for testing
        parrot = new Parrot("African Grey", "intelligent parrot", false,
                Set.of(Food.seeds, Food.nuts, Food.fruit), 2, 100, "Hello!");

        owl = new Owl("Barn Owl", "nocturnal hunter", false,
                Set.of(Food.small_mammals, Food.insects), 2);

        eagle = new BirdOfPrey("Bald Eagle", "majestic predator", false,
                Set.of(Food.fish, Food.other_birds), 2);

        duck = new Waterfowl("Mallard Duck", "common waterfowl", false,
                Set.of(Food.insects, Food.aquatic_invertebrates), 2, "Lake Superior");

        penguin = new FlightlessBird("Emperor Penguin", "Antarctic bird", false,
                Set.of(Food.fish), 0);

        extinctBird = new Parrot("Carolina Parakeet", "extinct species", true,
                Set.of(Food.seeds), 2, 50, "Gone forever");

        hawk = new BirdOfPrey("Red-tailed Hawk", "common hawk", false,
                Set.of(Food.small_mammals), 2);
    }

    @Test
    public void testRescueNormalBird() {
        String location = conservatory.rescue(parrot);
        assertNotNull("Parrot should be rescued successfully", location);
        assertEquals("First bird should go to first aviary", "North Wing", location);
    }

    @Test
    public void testRescueExtinctBird() {
        String location = conservatory.rescue(extinctBird);
        assertNull("Extinct bird should not be rescued", location);
    }

    @Test
    public void testRescueMultipleBirds() {
        conservatory.rescue(parrot);
        String location2 = conservatory.rescue(owl);
        assertNotNull("Second bird should be rescued", location2);
    }

    @Test
    public void testBirdsOfPreySeparation() {
        conservatory.rescue(eagle);
        conservatory.rescue(hawk);
        conservatory.rescue(parrot);

        // Lookup where each bird is
        String eagleLocation = conservatory.lookup("Bald Eagle");
        String hawkLocation = conservatory.lookup("Red-tailed Hawk");
        String parrotLocation = conservatory.lookup("African Grey");

        // Birds of prey should be together, parrots separate
        assertEquals("Eagle and hawk should be in same aviary", eagleLocation, hawkLocation);
        assertNotEquals("Parrot should not be with birds of prey", parrotLocation, eagleLocation);
    }

    @Test
    public void testWaterfowlSeparation() {
        conservatory.rescue(parrot);
        conservatory.rescue(duck);

        String parrotLocation = conservatory.lookup("African Grey");
        String duckLocation = conservatory.lookup("Mallard Duck");

        assertNotEquals("Waterfowl should not mix with other birds", parrotLocation, duckLocation);
    }

    @Test
    public void testFlightlessBirdSeparation() {
        conservatory.rescue(parrot);
        conservatory.rescue(penguin);

        String parrotLocation = conservatory.lookup("African Grey");
        String penguinLocation = conservatory.lookup("Emperor Penguin");

        assertNotEquals("Flightless birds should not mix with other birds", parrotLocation, penguinLocation);
    }

    @Test
    public void testAviaryCapacity() {
        // Create 5 parrots to fill one aviary
        for (int i = 1; i <= 5; i++) {
            Bird p = new Parrot("Parrot" + i, "test parrot", false,
                    Set.of(Food.seeds), 2, 10, "Hi" + i);
            String location = conservatory.rescue(p);
            assertNotNull("Parrot " + i + " should be rescued", location);
            assertEquals("First 5 parrots should go to same aviary", "North Wing", location);

        }

        // 6th parrot should go to a different aviary
        Bird parrot6 = new Parrot("Parrot6", "test parrot", false,
                Set.of(Food.seeds), 2, 10, "Hi6");
        String location6 = conservatory.rescue(parrot6);
        assertNotNull("6th parrot should be rescued", location6);
        assertNotEquals("6th parrot should go to different aviary", "North Wing", location6);
    }

    @Test
    public void testLookupExistingBird() {
        conservatory.rescue(parrot);
        String location = conservatory.lookup("African Grey");
        assertNotNull("Should find rescued parrot", location);
    }

    @Test
    public void testLookupNonExistingBird() {
        String location = conservatory.lookup("Phoenix");
        assertNull("Should not find non-existing bird", location);
    }

    @Test
    public void testPrintSignEmptyAviary() {
        String sign = conservatory.printSign("North Wing");
        assertNotNull("Should return sign for empty aviary", sign);
        assertTrue("Sign should mention no birds", sign.contains("No birds"));
    }

    @Test
    public void testPrintSignWithBirds() {
        conservatory.rescue(parrot);
        String sign = conservatory.printSign("North Wing");

        assertNotNull("Should return sign", sign);
        assertTrue("Sign should contain bird type", sign.contains("African Grey"));
        assertTrue("Sign should contain defining characteristic", sign.contains("intelligent parrot"));
        assertTrue("Sign should contain vocabulary info for parrot", sign.contains("vocab"));
        assertTrue("Sign should contain favorite saying", sign.contains("Hello!"));
    }

    @Test
    public void testPrintSignWaterfowl() {
        conservatory.rescue(duck);
        String aviaryLocation = conservatory.lookup("Mallard Duck");
        String sign = conservatory.printSign(aviaryLocation);

        assertTrue("Sign should contain body of water", sign.contains("Lake Superior"));
    }

    @Test
    public void testPrintSignNonExistingAviary() {
        String sign = conservatory.printSign("Fantasy Land");
        assertNull("Should return null for non-existing aviary", sign);
    }

    @Test
    public void testGetMapEmpty() {
        Map<String, List<Bird>> map = conservatory.getMap();
        assertNotNull("Map should not be null", map);
        assertEquals("Map should have 5 aviaries", 5, map.size());

        // All aviaries should be empty
        for (List<Bird> birds : map.values()) {
            assertTrue("Each aviary should be empty initially", birds.isEmpty());
        }
    }

    @Test
    public void testGetMapWithBirds() {
        conservatory.rescue(parrot);
        conservatory.rescue(owl);
        conservatory.rescue(eagle);

        Map<String, List<Bird>> map = conservatory.getMap();
        System.out.println(map);

        int totalBirds = 0;
        for (List<Bird> birds : map.values()) {
            totalBirds += birds.size();
        }

        assertEquals("Map should show 3 birds total", 3, totalBirds);
    }

    @Test
    public void testGetIndexEmpty() {
        List<Map.Entry<String, String>> index = conservatory.getIndex();
        assertNotNull("Index should not be null", index);
        assertTrue("Index should be empty", index.isEmpty());
    }

    @Test
    public void testGetIndexWithBirds() {
        conservatory.rescue(parrot);
        conservatory.rescue(owl);
        conservatory.rescue(eagle);

        List<Map.Entry<String, String>> index = conservatory.getIndex();

        assertEquals("Index should have 3 entries", 3, index.size());
    }

    @Test
    public void testGetIndexAlphabetical() {
        // Rescue birds in non-alphabetical order
        conservatory.rescue(owl); // Barn Owl
        conservatory.rescue(parrot); // African Grey
        conservatory.rescue(eagle); // Bald Eagle

        List<Map.Entry<String, String>> index = conservatory.getIndex();

        // After sorting, should be: African Grey, Bald Eagle, Barn Owl
        assertEquals("First should be African Grey", "African Grey", index.get(0).getKey());
        assertEquals("Second should be Bald Eagle", "Bald Eagle", index.get(1).getKey());
        assertEquals("Third should be Barn Owl", "Barn Owl", index.get(2).getKey());
    }

    @Test
    public void testCalculateFoodNeedsEmpty() {
        Map<Food, Integer> foodNeeds = conservatory.calculateFoodNeeds();
        assertNotNull("Food needs map should not be null", foodNeeds);
        assertTrue("Food needs should be empty", foodNeeds.isEmpty());
    }

    @Test
    public void testCalculateFoodNeedsWithBirds() {
        conservatory.rescue(parrot); // eats seeds, nuts, fruit
        conservatory.rescue(owl); // eats small_mammals, insects

        Map<Food, Integer> foodNeeds = conservatory.calculateFoodNeeds();

        assertTrue("Should need seeds", foodNeeds.containsKey(Food.seeds));
        assertTrue("Should need nuts", foodNeeds.containsKey(Food.nuts));
        assertTrue("Should need fruit", foodNeeds.containsKey(Food.fruit));
        assertTrue("Should need small_mammals", foodNeeds.containsKey(Food.small_mammals));
        assertTrue("Should need insects", foodNeeds.containsKey(Food.insects));

        assertEquals("Should need seeds once", Integer.valueOf(1), foodNeeds.get(Food.seeds));
    }

    @Test
    public void testCalculateFoodNeedsMultipleBirdsSameFood() {
        // Both eat small_mammals
        conservatory.rescue(eagle); // eats fish, other_birds
        conservatory.rescue(hawk); // eats small_mammals
        Bird owl2 = new Owl("Great Horned Owl", "large owl", false,
                Set.of(Food.small_mammals), 2);
        conservatory.rescue(owl2); // also eats small_mammals

        Map<Food, Integer> foodNeeds = conservatory.calculateFoodNeeds();

        assertEquals("Should need small_mammals twice", Integer.valueOf(2), foodNeeds.get(Food.small_mammals));
    }

    @Test
    public void testMaxConservatoryCapacity() {
        // Create a conservatory with max aviaries
        List<String> locations = new ArrayList<>();
        for (int i = 1; i <= Conservatory.MAX_AVIARIES; i++) {
            locations.add("Aviary " + i);
        }
        Conservatory maxConservatory = new Conservatory(locations);

        // Try to add 100 birds (max is 20 aviaries × 5 birds = 100)
        int rescued = 0;
        for (int i = 1; i <= 100; i++) {
            Bird p = new Parrot("Parrot" + i, "test", false,
                    Set.of(Food.seeds), 2, 10, "Hi");
            String location = maxConservatory.rescue(p);
            if (location != null) {
                rescued++;
            }
        }

        assertEquals("Should rescue exactly 100 birds", 100, rescued);

        // 101st bird should fail
        Bird extraBird = new Parrot("Extra", "test", false,
                Set.of(Food.seeds), 2, 10, "No room");
        assertNull("101st bird should not be rescued", maxConservatory.rescue(extraBird));
    }
}