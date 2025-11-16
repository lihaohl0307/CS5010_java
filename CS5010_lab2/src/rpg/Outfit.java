package rpg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/** Holds worn items and enforces slot capacities. */
public final class Outfit {
    private static final int MAX_HEAD = 1;
    private static final int MAX_HAND = 2;
    private static final int MAX_FOOT = 2;

    private final List<HeadGear> head = new ArrayList<>();
    private final List<HandGear> hands = new ArrayList<>();
    private final List<Footwear> feet = new ArrayList<>();

    public Outfit() {}

    /** Return an immutable view of items of a given type. */
    public List<Gear> itemsOf(ItemType type) {
        switch (Objects.requireNonNull(type)) {
            case HEAD: return Collections.unmodifiableList(new ArrayList<>(head));
            case HAND: return Collections.unmodifiableList(new ArrayList<>(hands));
            case FOOT: return Collections.unmodifiableList(new ArrayList<>(feet));
            default: throw new IllegalStateException("Unknown type");
        }
    }

    public boolean isFull(ItemType type) {
        switch (Objects.requireNonNull(type)) {
            case HEAD: return head.size() >= MAX_HEAD;
            case HAND: return hands.size() >= MAX_HAND;
            case FOOT: return feet.size() >= MAX_FOOT;
            default: throw new IllegalStateException("Unknown type");
        }
    }

    /** Add item; if full for that type, combine with any existing one (first). */
    public void add(Gear item, Random rng) {
        Objects.requireNonNull(item, "item");
        Random r = (rng == null) ? new Random() : rng;
        switch (item.getType()) {
            case HEAD:
                if (!isFull(ItemType.HEAD)) {
                    head.add((HeadGear) item);
                } else {
                    Gear combined = head.get(0).combineWith(item, r);
                    head.set(0, (HeadGear) combined);
                }
                break;

            case HAND:
                if (!isFull(ItemType.HAND)) {
                    hands.add((HandGear) item);
                } else {
                    Gear combined = hands.get(0).combineWith(item, r);
                    hands.set(0, (HandGear) combined);
                }
                break;

            case FOOT:
                if (!isFull(ItemType.FOOT)) {
                    feet.add((Footwear) item);
                } else {
                    Gear combined = feet.get(0).combineWith(item, r);
                    feet.set(0, (Footwear) combined);
                }
                break;

            default: throw new IllegalStateException("Unknown type");
        }
    }

    /** Sum attack/defense across all slots. */
    public TupleInt totals() {
        int atk = 0, def = 0;
        for (HeadGear g : head) { atk += g.getAttack(); def += g.getDefense(); }
        for (HandGear g : hands) { atk += g.getAttack(); def += g.getDefense(); }
        for (Footwear g : feet) { atk += g.getAttack(); def += g.getDefense(); }
        return new TupleInt(atk, def);
    }

    @Override
    public String toString() {
        return "Head=" + head + ", Hands=" + hands + ", Feet=" + feet;
    }
}
