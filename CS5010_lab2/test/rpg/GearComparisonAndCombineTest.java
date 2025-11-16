package rpg;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class GearComparisonAndCombineTest {

    @Test
    public void testStrongerThanByAttack() {
        Gear a = new HandGear(new Name("Savage", "Sword"), 5);
        Gear b = new HandGear(new Name("Swift", "Dagger"), 3);

        assertTrue(a.strongerThan(b, new Random(1)));
        assertFalse(b.strongerThan(a, new Random(1)));
    }

    @Test
    public void testStrongerThanByDefenseWhenAttackEqual() {
        Gear a = new Footwear(new Name("Reinforced", "Boots"), 1, 5);
        Gear b = new Footwear(new Name("Light", "Boots"), 1, 3);

        assertTrue(a.strongerThan(b, new Random(1)));
        assertFalse(b.strongerThan(a, new Random(1)));
    }

    @Test
    public void testCombineFootwearNameAndStats() {
        Gear s = new Footwear(new Name("Scurrying", "Sandals"), 0, 1);
        Gear h = new Footwear(new Name("Happy", "HoverBoard"), 1, 3);

        // Happy HoverBoard is stronger: higher attack
        Gear combined = s.combineWith(h, new Random(1));
        assertTrue(combined instanceof Footwear);
        assertEquals(1, combined.getAttack());  // 0 + 1
        assertEquals(4, combined.getDefense()); // 1 + 3

        // weaker adjective, stronger full name
        assertEquals("Scurrying, Happy HoverBoard", combined.getName().full());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombineDifferentTypesThrows() {
        Gear head = new HeadGear(new Name("Sturdy", "Helmet"), 3);
        Gear hand = new HandGear(new Name("Savage", "Sword"), 4);

        head.combineWith(hand, new Random(1));
    }
}
