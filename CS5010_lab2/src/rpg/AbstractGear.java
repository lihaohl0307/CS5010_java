package rpg;

import java.util.Objects;
import java.util.Random;

/** Base class implementing common behavior and invariants. */
public abstract class AbstractGear implements Gear {
    protected final Name name;
    protected final int attack;
    protected final int defense;
    protected final ItemType type;

    protected AbstractGear(Name name, int attack, int defense, ItemType type) {
        this.name = Objects.requireNonNull(name, "name");
        this.type = Objects.requireNonNull(type, "type");
        this.attack = attack;
        this.defense = defense;
        validateInvariants();
    }

    protected abstract void validateInvariants();

    @Override public Name getName() { return name; }
    @Override public int getAttack() { return attack; }
    @Override public int getDefense() { return defense; }
    @Override public ItemType getType() { return type; }

    @Override
    public boolean strongerThan(Gear other, Random rng) {
        if (other == null) throw new IllegalArgumentException("other cannot be null");
        if (this.attack != other.getAttack()) return this.attack > other.getAttack();
        if (this.defense != other.getDefense()) return this.defense > other.getDefense();
        return (rng == null ? new Random() : rng).nextBoolean();
    }

    @Override
    public Gear combineWith(Gear other, Random rng) {
        if (other == null) throw new IllegalArgumentException("other cannot be null");
        if (this.getType() != other.getType()) {
            throw new IllegalArgumentException("Cannot combine different item types");
        }
        Random r = (rng == null) ? new Random() : rng;
        boolean thisIsStronger = this.strongerThan(other, r);
        Gear stronger = thisIsStronger ? this : other;
        Gear weaker   = thisIsStronger ? other : this;

        Name combinedName = Name.combine(weaker.getName(), stronger.getName());
        int atk = stronger.getAttack() + weaker.getAttack();
        int def = stronger.getDefense() + weaker.getDefense();

        switch (stronger.getType()) {
            case HEAD: return new HeadGear(combinedName, def);        // attack forced to 0 by ctor
            case HAND: return new HandGear(combinedName, atk);        // defense forced to 0 by ctor
            case FOOT: return new Footwear(combinedName, atk, def);
            default: throw new IllegalStateException("Unknown type");
        }
    }

    @Override
    public String toString() {
        return String.format("%s{%s, atk=%d, def=%d}", type, name.full(), attack, defense);
    }
}
