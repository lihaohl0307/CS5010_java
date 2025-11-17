package conservatory;

import conservatory.subclass.BirdOfPrey;
import conservatory.subclass.FlightlessBird;
import conservatory.subclass.ShoreBird;
import conservatory.subclass.Waterfowl;
import conservatory.subclass.Parrot;


import java.util.ArrayList;
import java.util.List;

public class Aviary {
    public static final int MAX_CAP = 5;
    private final String location;
    private final List<Bird> birds =  new ArrayList<Bird>(MAX_CAP);

    public Aviary(String location) {
        this.location = location;
    }

    public String getLocation() {return this.location;}
    public List<Bird> getBirds() {return this.birds;}
    public boolean isEmpty() {return this.birds.isEmpty();}
    public boolean isFull() {return this.birds.size() == MAX_CAP;}

    public boolean hasFlightlessBird() {
        for (Bird bird : this.birds) {
            if (bird instanceof FlightlessBird) {
                return true;
            }
        }
        return false;
    }

    public boolean hasBirdOfPrey() {
        for (Bird bird : this.birds) {
            if (bird instanceof BirdOfPrey) {
                return true;
            }
        }
        return false;
    }

    public boolean hasWaterfowl() {
        for (Bird bird : this.birds) {
            if (bird instanceof Waterfowl) {
                return true;
            }
        }
        return false;
    }

    public boolean acceptBird(Bird bird) {
        if (isFull() || bird.isExtinct()) {
            return false;
        }
        if (isEmpty()) {return true;}

        // restricted birds stay with their own type
        if (hasBirdOfPrey()) {return bird.isBirdOfPrey();}
        if (hasWaterfowl()) {return bird.isWaterfowl();}
        if (hasFlightlessBird()) {return bird.isFlightless();}

        // unrestricted birds are kept with themselves
        return !bird.isBirdOfPrey() && !bird.isWaterfowl() && !bird.isFlightless();
    }

    public boolean addBird(Bird bird) {
        if (acceptBird(bird)) {
            birds.add(bird);
            return true;
        } else {return false;}
    }

    // a sign for any given aviary that gives a description of the birds it houses and
    // any interesting information that it may have about that animal.
    public String sign() {
        StringBuilder sign = new StringBuilder();
        sign.append("Aviary ").append(location).append("\n");
        if (birds.isEmpty()) {
            sign.append("No birds\n");
            return sign.toString();
        }
        for (Bird bird : birds) {
            sign.append(bird.getType())
                .append(": ").append(bird.getDefiningChar())
                .append("| wings: ").append(bird.getWings())
                .append("| diet: ").append(bird.getDiet());

            // Special birds: parrot, shorebirds, waterfowl
            if (bird instanceof Parrot parrot) {
                sign.append("| vocab: ").append(parrot.getVocabSize());
                sign.append("| favorite saying: ").append(parrot.getFavoriteSaying());
            } else if (bird instanceof ShoreBird shoreBird) {
                sign.append("| near water: ").append(shoreBird.getBodyOfWater());
            } else if (bird instanceof Waterfowl waterfowl) {
                sign.append("| near water: ").append(waterfowl.getBodyOfWater());
            }
            sign.append("\n");
        }
        return sign.toString();
    }
}
