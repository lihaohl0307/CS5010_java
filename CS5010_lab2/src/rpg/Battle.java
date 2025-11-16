package rpg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/** Orchestrates the pick-dress sequence and declares the winner. */
public final class Battle {
    private final Character p1;
    private final Character p2;
    private final List<Gear> pool;   // remaining items
    private final Random rng;

    public Battle(Character p1, Character p2, List<Gear> items, Random rng) {
        this.p1 = Objects.requireNonNull(p1);
        this.p2 = Objects.requireNonNull(p2);
        this.pool = new ArrayList<>(Objects.requireNonNull(items));
        if (pool.size() < 10) throw new IllegalArgumentException("Need at least 10 items in the pool");
        this.rng = (rng == null) ? new Random() : rng;
    }

    /** Run the 10-turn dress-up battle, printing each turn and the result. */
    public void run() {
        for (int turn = 0; turn < 10; turn++) {
            Character current = (turn % 2 == 0) ? p1 : p2;
            Gear choice = chooseFor(current);
            current.outfit().add(choice, rng);
            pool.remove(choice);

            System.out.printf("Turn %d: P%d picked %s%n", turn + 1, (turn % 2 == 0) ? 1 : 2, choice);
            System.out.println("  P1: " + p1);
            System.out.println("  P2: " + p2);
            System.out.println();
        }
        printWinner();
    }

    private Gear chooseFor(Character c) {
        // Rule 1: prefer types with available slots
        List<ItemType> preferredTypes = new ArrayList<>();
        for (ItemType t : ItemType.values()) {
            if (!c.outfit().isFull(t)) preferredTypes.add(t);
        }
        List<Gear> candidates = new ArrayList<>();
        for (Gear g : pool) {
            if (preferredTypes.contains(g.getType())) candidates.add(g);
        }
        if (candidates.isEmpty()) candidates.addAll(pool); // if all types are full, pick from any

        // Rule 2: highest attack
        // Rule 3: then highest defense
        candidates.sort(
                Comparator.comparingInt(Gear::getAttack).reversed()
                        .thenComparingInt(Gear::getDefense).reversed());

        // find all with same top atk/def to allow Rule 4 (random among ties)
        Gear top = candidates.get(0);
        List<Gear> topTies = new ArrayList<>();
        for (Gear g : candidates) {
            if (g.getAttack() == top.getAttack() && g.getDefense() == top.getDefense()) {
                topTies.add(g);
            } else break;
        }
        return topTies.get(rng.nextInt(topTies.size()));
    }

    private void printWinner() {
        int p1Dmg = p2.totalAttack() - p1.totalDefense();
        int p2Dmg = p1.totalAttack() - p2.totalDefense();
        System.out.printf("Final Damage: P1=%d, P2=%d%n", p1Dmg, p2Dmg);
        if (p1Dmg < p2Dmg) {
            System.out.println("Winner: Player 1");
        } else if (p2Dmg < p1Dmg) {
            System.out.println("Winner: Player 2");
        } else {
            System.out.println("Result: Tie");
        }
    }
}

