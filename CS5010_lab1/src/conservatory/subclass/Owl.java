package conservatory.subclass;

import conservatory.Bird;
import conservatory.Food;

import java.util.Set;

public class Owl extends Bird {
    private final String definingChar = "facial disks framing the eyes and bill";
    private final String type = "Owl";
    public Owl(String type, String definingChar, boolean extinct, Set<Food> diet, int wings) {
        super(type, definingChar, extinct, diet, wings);
    }
}
