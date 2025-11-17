package conservatory.subclass;
import java.util.Set;
import conservatory.Food;
import conservatory.Bird;

public class FlightlessBird extends Bird {
    private final String definingChar = "live on the ground and have no (or undeveloped) wings";
    private final String type = "FlightlessBird";
    private final int wings = 0;
    public FlightlessBird(String type, String definingChar, boolean extinct, Set<Food> diet, int wings) {
        super(type, definingChar, extinct, diet, wings);
    }
}