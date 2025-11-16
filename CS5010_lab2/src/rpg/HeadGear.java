package rpg;

/** Head gear: defense-only; attack must be 0. */
public final class HeadGear extends AbstractGear {
    public HeadGear(Name name, int defense) {
        super(name, 0, defense, ItemType.HEAD);
    }
    @Override
    protected void validateInvariants() {
        if (attack != 0) throw new IllegalArgumentException("HeadGear attack must be 0");
        if (defense < 0) throw new IllegalArgumentException("HeadGear defense must be >= 0");
    }
}
