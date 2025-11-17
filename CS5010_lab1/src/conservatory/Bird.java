package conservatory;

import conservatory.subclass.BirdOfPrey;
import conservatory.subclass.FlightlessBird;
import conservatory.subclass.Waterfowl;

import java.util.Set;

public abstract class Bird {
    private final String type;
    private final String definingChar;
    private final boolean extinct;
    private final int wings;
    private final Set<Food> diet;

    protected Bird(String type, String definingChar, boolean extinct, Set<Food> diet, int wings) {
        this.type = type;
        this.definingChar = definingChar;
        this.extinct = extinct;
        this.wings = wings;
        this.diet = diet;
    }

    public String getType() {
        return type;
    }

    public String getDefiningChar() {
        return definingChar;
    }

    public boolean isExtinct() {
        return extinct;
    }

    public int getWings() {
        return wings;
    }

    public Set<Food> getDiet() {
        return diet;
    }

    // bird type check for aviary mainly
    public boolean isFlightless() {
        return this instanceof FlightlessBird;
    }

    public boolean isBirdOfPrey() {
        return this instanceof BirdOfPrey;
    }

    public boolean isWaterfowl(){
        return this instanceof Waterfowl;
    }
}
