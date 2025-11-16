package rpg;
import java.util.Random;

public interface Gear {
    Name getName();
    int getAttack();
    int getDefense();
    ItemType getType();

    /** Stronger means higher attack; if tie, higher defense; if still tie, random. */
    boolean strongerThan(Gear other, Random rng);

    /** Combine with same-type gear, returning a NEW instance (does not mutate either). */
    Gear combineWith(Gear other, Random rng);
}
