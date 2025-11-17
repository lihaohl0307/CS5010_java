package conservatory.subclass;

import conservatory.Bird;
import conservatory.Food;
import conservatory.NearWater;

import java.util.Set;

public class Waterfowl extends Bird implements NearWater {
    private final String definingChar = "live near water sources (fresh or salt)";
    private final String type = "Waterfowl";
    private final String bodyOfWater;
    public Waterfowl(String type, String definingChar, boolean extinct, Set<Food> diet, int wings, String bodyOfWater) {
        super(type, definingChar, extinct, diet, wings);
        this.bodyOfWater = bodyOfWater;
    }
    public String getBodyOfWater() {return bodyOfWater;}
}