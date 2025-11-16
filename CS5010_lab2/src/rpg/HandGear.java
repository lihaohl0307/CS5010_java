package rpg;

/** Hand gear: attack-only; defense must be 0. */
public final class HandGear extends AbstractGear {
    public HandGear(Name name, int attack) {
        super(name, attack, 0, ItemType.HAND);
    }
    @Override
    protected void validateInvariants() {
        if (defense != 0) throw new IllegalArgumentException("HandGear defense must be 0");
        if (attack < 0) throw new IllegalArgumentException("HandGear attack must be >= 0");
    }
}
