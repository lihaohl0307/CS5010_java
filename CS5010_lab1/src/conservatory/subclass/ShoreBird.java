package conservatory.subclass;

import conservatory.Bird;
import conservatory.Food;
import conservatory.NearWater;

import java.util.Set;

public class ShoreBird extends Bird implements NearWater {
    private final String definingChar = "live near water sources including wetlands, freshwater and saltwater shorelands, even the ocean";
    private final String type = "ShoreBird";
    private final String bodyOfWater;
    public ShoreBird(String type, String definingChar, boolean extinct, Set<Food> diet, int wings, String bodyOfWater) {
        super(type, definingChar, extinct, diet, wings);
        this.bodyOfWater = bodyOfWater;
    }
    public String getBodyOfWater() {return bodyOfWater;}
}
