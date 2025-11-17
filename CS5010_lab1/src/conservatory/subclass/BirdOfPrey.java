package conservatory.subclass;
import java.util.Set;
import conservatory.Food;
import conservatory.Bird;

public class BirdOfPrey extends Bird {
    private final String definingChar = "sharp, hooked beaks with visible nostrils";
    private final String type = "BirdOfPrey";
    public BirdOfPrey(String type, String definingChar, boolean extinct, Set<Food> diet, int wings) {
        super(type, definingChar, extinct, diet, wings);
    }
}
