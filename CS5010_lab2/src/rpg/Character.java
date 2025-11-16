package rpg;
import java.util.Objects;

/** A playable character with base stats and an outfit. */
public final class Character {
    private final int baseAttack;
    private final int baseDefense;
    private final Outfit outfit;

    public Character(int baseAttack, int baseDefense, Outfit outfit) {
        if (baseAttack < 0 || baseDefense < 0) {
            throw new IllegalArgumentException("base stats must be >= 0");
        }
        this.outfit = Objects.requireNonNull(outfit, "outfit");
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    public int totalAttack() { return baseAttack + outfit.totals().attack; }
    public int totalDefense() { return baseDefense + outfit.totals().defense; }
    public Outfit outfit() { return outfit; }

    @Override
    public String toString() {
        return String.format("atk=%d, def=%d, %s", totalAttack(), totalDefense(), outfit);
    }
}
