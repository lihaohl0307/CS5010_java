package rpg;

/** Tiny immutable pair of ints (attack, defense). */
public final class TupleInt {
    public final int attack;
    public final int defense;
    public TupleInt(int attack, int defense) {
        this.attack = attack; this.defense = defense;
    }
    @Override public String toString() { return "(" + attack + ", " + defense + ")"; }
}
