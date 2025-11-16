package rpg;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CharacterTest {

    @Test
    public void testTotalAttackAndDefenseIncludesBaseAndOutfit() {
        Outfit outfit = new Outfit();
        outfit.add(new HeadGear(new Name("Sturdy", "Helmet"), 2), new Random(1));
        outfit.add(new HandGear(new Name("Savage", "Sword"), 5), new Random(1));
        outfit.add(new Footwear(new Name("Reinforced", "Boots"), 0, 3), new Random(1));

        Character c = new Character(3, 4, outfit);

        assertEquals(3 + 5, c.totalAttack());    // baseAtk + sword
        assertEquals(4 + 2 + 3, c.totalDefense()); // baseDef + helmet + boots
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeBaseStatsThrows() {
        new Character(-1, 3, new Outfit());
    }
}
