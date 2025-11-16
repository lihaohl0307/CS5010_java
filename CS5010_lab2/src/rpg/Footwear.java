package rpg;

/** Footwear: can contribute to both attack and defense. */
public final class Footwear extends AbstractGear {
    public Footwear(Name name, int attack, int defense) {
        super(name, attack, defense, ItemType.FOOT);
    }
    @Override
    protected void validateInvariants() {
        if (attack < 0 || defense < 0) {
            throw new IllegalArgumentException("Footwear stats must be >= 0");
        }
    }
}
