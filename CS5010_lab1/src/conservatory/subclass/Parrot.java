package conservatory.subclass;

import conservatory.Bird;
import conservatory.Food;

import java.util.Set;

public class Parrot extends Bird {
    private final String definingChar = "short, curved beak; noted for intelligence & mimicry";
    private final String type = "Parrot";
    private final int vocabSize;
    private final String favoriteSaying;
    public Parrot(String type, String definingChar, boolean extinct, Set<Food> diet, int wings, int vocabSize, String favoriteSaying) {
        super(type, definingChar, extinct, diet, wings);
        this.vocabSize = vocabSize;
        this.favoriteSaying = favoriteSaying;
    }

    public String getFavoriteSaying() {
        return favoriteSaying;
    }
    public String getVocabSize() {
        return String.valueOf(vocabSize);
    }
}