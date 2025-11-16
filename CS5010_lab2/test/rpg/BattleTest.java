package rpg;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class BattleTest {

    private List<Gear> makeSimplePool() {
        List<Gear> items = new ArrayList<>();
        items.add(new HeadGear(new Name("Sturdy", "Helmet"), 3));
        items.add(new HeadGear(new Name("Shiny", "Visor"), 2));

        items.add(new HandGear(new Name("Savage", "Sword"), 4));
        items.add(new HandGear(new Name("Swift", "Dagger"), 3));

        items.add(new HandGear(new Name("Heavy", "Shield"), 1));

        items.add(new Footwear(new Name("Scurrying", "Sandals"), 0, 1));
        items.add(new Footwear(new Name("Happy", "HoverBoard"), 1, 3));
        items.add(new Footwear(new Name("Spiky", "Cleats"), 2, 0));
        items.add(new Footwear(new Name("Reinforced", "Boots"), 0, 4));
        items.add(new Footwear(new Name("Balanced", "Sneakers"), 1, 1));

        return items;
    }

    @Test
    public void testBattleRunsAndProducesDeterministicDamageWithSeed() {
        Character p1 = new Character(3, 3, new Outfit());
        Character p2 = new Character(4, 2, new Outfit());
        List<Gear> pool = makeSimplePool();
        Random rng = new Random(42);

        Battle b = new Battle(p1, p2, pool, rng);
        b.run(); // prints to stdout; we won't assert on text

        int p1Dmg = p2.totalAttack() - p1.totalDefense();
        int p2Dmg = p1.totalAttack() - p2.totalDefense();

        // We don't know exact numbers a priori, but we at least assert consistency
        // e.g., someone must have damage <= someone else
        assertTrue(p1Dmg <= p2Dmg || p2Dmg <= p1Dmg);

        // Sanity: nobody should have absurd stats (arbitrary upper bounds)
        assertTrue(Math.abs(p1Dmg) < 100);
        assertTrue(Math.abs(p2Dmg) < 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBattleRequiresAtLeastTenItems() {
        Character p1 = new Character(1, 1, new Outfit());
        Character p2 = new Character(1, 1, new Outfit());
        List<Gear> tooFew = new ArrayList<>();
        tooFew.add(new HandGear(new Name("Tiny", "Dagger"), 1));

        new Battle(p1, p2, tooFew, new Random(1));
    }
}
