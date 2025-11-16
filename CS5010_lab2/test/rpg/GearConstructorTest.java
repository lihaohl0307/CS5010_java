package rpg;

import org.junit.Test;

import static org.junit.Assert.*;

public class GearConstructorTest {

    @Test
    public void testValidHeadGear() {
        Name n = new Name("Sturdy", "Helmet");
        HeadGear h = new HeadGear(n, 3);
        assertEquals(0, h.getAttack());
        assertEquals(3, h.getDefense());
        assertEquals(ItemType.HEAD, h.getType());
        assertEquals("Sturdy Helmet", h.getName().full());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHeadGearNegativeDefense() {
        new HeadGear(new Name("Sturdy", "Helmet"), -1);
    }

    @Test
    public void testValidHandGear() {
        Name n = new Name("Savage", "Sword");
        HandGear g = new HandGear(n, 5);
        assertEquals(5, g.getAttack());
        assertEquals(0, g.getDefense());
        assertEquals(ItemType.HAND, g.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHandGearNegativeAttack() {
        new HandGear(new Name("Savage", "Sword"), -2);
    }

    @Test
    public void testValidFootwear() {
        Name n = new Name("Scurrying", "Sandals");
        Footwear f = new Footwear(n, 1, 3);
        assertEquals(1, f.getAttack());
        assertEquals(3, f.getDefense());
        assertEquals(ItemType.FOOT, f.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFootwearNegativeAttack() {
        new Footwear(new Name("Scurrying", "Sandals"), -1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFootwearNegativeDefense() {
        new Footwear(new Name("Scurrying", "Sandals"), 1, -3);
    }
}
