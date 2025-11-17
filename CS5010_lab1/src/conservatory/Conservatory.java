package conservatory;

import java.util.*;

public class Conservatory {
    public static final int MAX_AVIARIES = 20;
    private final List<Aviary> aviaries =  new ArrayList<Aviary>(MAX_AVIARIES);

    public Conservatory(List<String> aviaryLocations) {
        // construct aviary list
        for (String location : aviaryLocations) {
            aviaries.add(new Aviary(location));
        }
    }

    // return aviary location if successfully rescue and assign to an available aviary
    public String rescue(Bird bird) {
        if (bird.isExtinct()) {
            return null;
        }
        for (Aviary aviary : aviaries) {
            if (aviary.addBird(bird)) {
                return aviary.getLocation();
            }
        }
        return null;
    }

    // look up a specific type of bird
    public String lookup(String birdType) {
        for  (Aviary aviary : aviaries) {
            for (Bird bird : aviary.getBirds()) {
                if (bird.getType().equals(birdType)) {
                    return aviary.getLocation();
                }
            }
        }
        return null;
    }

    // print info about an aviary
    public String printSign(String aviaryLocation) {
        for (Aviary aviary : aviaries) {
            if (aviary.getLocation().equals(aviaryLocation)) {
                return aviary.sign();
            }
        }
        return null; //no such aviary
    }

    // a “map” that lists all the aviaries by location and the birds they house
    public Map<String, List<Bird>> getMap() {
        Map<String, List<Bird>> map = new HashMap<>();
        for (Aviary aviary : aviaries) {
            List<Bird> birds = aviary.getBirds();
            map.put(aviary.getLocation(), birds);
        }
        return map;
    }

    // alphabetical index of all birds with their location
    public List<Map.Entry<String, String>> getIndex() {
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        for (Aviary aviary : aviaries) {
            for  (Bird bird : aviary.getBirds()) {
                entries.add(Map.entry(bird.getType(), aviary.getLocation()));
            }
        }
        // Sort alphabetically by bird type (the key)
        entries.sort(Map.Entry.comparingByKey());
        return entries;
    }

    // calculate food needs for birds in each conservatory (add up all aviaries)
    public Map<Food, Integer> calculateFoodNeeds() {
        Map<Food, Integer> totals = new EnumMap<>(Food.class);
        for (Aviary aviary : aviaries) {
            for (Bird bird : aviary.getBirds()) {
                for (Food food : bird.getDiet()) {
                    totals.put(food, totals.getOrDefault(food, 0) + 1);
                }
            }
        }
        return totals;
    }
}
