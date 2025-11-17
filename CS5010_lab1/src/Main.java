import conservatory.*;
import conservatory.subclass.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     Welcome to the Conservatory System Demo         ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");

        // Create a conservatory with aviaries
        List<String> locations = Arrays.asList(
                "North Wing", "South Wing", "East Wing",
                "West Wing", "Central Dome"
        );
        Conservatory conservatory = new Conservatory(locations);

        // Create various birds
        System.out.println("📋 Creating birds for the conservatory...\n");

        Bird parrot1 = new Parrot("African Grey", "intelligent parrot", false,
                Set.of(Food.seeds, Food.fruit), 2, 150, "Hello, friend!");

        Bird parrot2 = new Parrot("Macaw", "colorful parrot", false,
                Set.of(Food.nuts, Food.fruit), 2, 100, "Pretty bird!");

        Bird eagle = new BirdOfPrey("Bald Eagle", "majestic predator", false,
                Set.of(Food.fish, Food.small_mammals), 2);

        Bird hawk = new BirdOfPrey("Red-tailed Hawk", "skilled hunter", false,
                Set.of(Food.small_mammals, Food.insects), 2);

        Bird duck = new Waterfowl("Mallard Duck", "common waterfowl", false,
                Set.of(Food.insects, Food.vegetation), 2, "Lake Michigan");

        Bird penguin = new FlightlessBird("Emperor Penguin", "Antarctic bird", false,
                Set.of(Food.fish), 0);

        Bird owl = new Owl("Barn Owl", "nocturnal hunter", false,
                Set.of(Food.small_mammals), 2);

        Bird shoreBird = new ShoreBird("Sandpiper", "coastal bird", false,
                Set.of(Food.insects, Food.fish), 2, "Pacific Ocean");

        // Rescue birds (add them to conservatory)
        System.out.println("🏥 Rescuing birds and assigning to aviaries...\n");

        Map<Bird, String> rescuedBirds = new LinkedHashMap<>();
        rescuedBirds.put(parrot1, conservatory.rescue(parrot1));
        rescuedBirds.put(parrot2, conservatory.rescue(parrot2));
        rescuedBirds.put(eagle, conservatory.rescue(eagle));
        rescuedBirds.put(hawk, conservatory.rescue(hawk));
        rescuedBirds.put(duck, conservatory.rescue(duck));
        rescuedBirds.put(penguin, conservatory.rescue(penguin));
        rescuedBirds.put(owl, conservatory.rescue(owl));
        rescuedBirds.put(shoreBird, conservatory.rescue(shoreBird));

        for (Map.Entry<Bird, String> entry : rescuedBirds.entrySet()) {
            System.out.printf("  ✓ %s assigned to: %s%n",
                    entry.getKey().getType(), entry.getValue());
        }

        // Print conservatory map
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🗺️  CONSERVATORY MAP - Birds by Location");
        System.out.println("═".repeat(60));

        Map<String, List<Bird>> map = conservatory.getMap();
        for (Map.Entry<String, List<Bird>> entry : map.entrySet()) {
            System.out.println("\n📍 " + entry.getKey() + ":");
            if (entry.getValue().isEmpty()) {
                System.out.println("   (empty)");
            } else {
                for (Bird bird : entry.getValue()) {
                    System.out.println("   • " + bird.getType() + " - " + bird.getDefiningChar());
                }
            }
        }

        // Print alphabetical index
        System.out.println("\n" + "═".repeat(60));
        System.out.println("📖 ALPHABETICAL INDEX - All Birds");
        System.out.println("═".repeat(60) + "\n");

        List<Map.Entry<String, String>> index = conservatory.getIndex();
        index.sort(Map.Entry.comparingByKey());
        for (Map.Entry<String, String> entry : index) {
            System.out.printf("%-25s → %s%n", entry.getKey(), entry.getValue());
        }

        // Lookup specific bird
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🔍 BIRD LOOKUP DEMO");
        System.out.println("═".repeat(60) + "\n");

        String[] birdsToLookup = {"African Grey", "Bald Eagle", "Emperor Penguin"};
        for (String birdType : birdsToLookup) {
            String location = conservatory.lookup(birdType);
            System.out.printf("Looking up '%s': Found in %s%n", birdType, location);
        }

        // Print sign for a specific aviary
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🪧  AVIARY SIGN EXAMPLE");
        System.out.println("═".repeat(60) + "\n");
        System.out.println(conservatory.printSign("North Wing"));

        // Calculate food needs
        System.out.println("═".repeat(60));
        System.out.println("🍽️  FOOD REQUIREMENTS FOR ALL BIRDS");
        System.out.println("═".repeat(60) + "\n");

        Map<Food, Integer> foodNeeds = conservatory.calculateFoodNeeds();
        int totalServings = 0;
        for (Map.Entry<Food, Integer> entry : foodNeeds.entrySet()) {
            System.out.printf("%-20s: %d serving(s)%n", entry.getKey(), entry.getValue());
            totalServings += entry.getValue();
        }
        System.out.println("\nTotal food servings needed: " + totalServings);

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║           Demo completed successfully!               ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}