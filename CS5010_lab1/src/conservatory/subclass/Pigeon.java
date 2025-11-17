package conservatory.subclass;

import conservatory.Bird;
import conservatory.Food;

import java.util.Set;

class Pigeon extends Bird {
    private final String definingChar = "feed young 'bird milk' similar to mammals";
    public Pigeon(String type, String definingChar, boolean extinct, Set<Food> diet, int wings) {
        super(type,definingChar, extinct, diet, wings);
    }
}
