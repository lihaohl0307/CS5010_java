package rpg;

import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class OutfitTest {

    @Test
    public void testEmptyOutfitTotals() {
        Outfit outfit = new Outfit();
        TupleInt totals = outfit.totals();
        assertEquals(0, totals.attack);
        assertEquals(0, totals.defense);
    }

    @Test
    public void testAddHeadGearWhenSpaceAvailable() {
        Outfit outfit = new Outfit();
        HeadGear h = new HeadGear(new Name("Sturdy", "Helmet"), 3);

        outfit.add(h, new Random(1));

        List<Gear> heads = outfit.itemsOf(ItemType.HEAD);
        assertEquals(1, heads.size());
        assertEquals(h, heads.get(0));
    }

    @Test
    public void testAddHandGearAndTotals() {
        Outfit outfit = new Outfit();
        HandGear g1 = new HandGear(new Name("Savage", "Sword"), 4);
        HandGear g2 = new HandGear(new Name("Swift", "Dagger"), 3);

        outfit.add(g1, new Random(1));
        outfit.add(g2, new Random(1));

        TupleInt totals = outfit.totals();
        assertEquals(7, totals.attack);
        assertEquals(0, totals.defense);
        assertTrue(outfit.isFull(ItemType.HAND));
    }

    @Test
    public void testAddThirdHandGearTriggersCombineKeepsSizeTwo() {
        Outfit outfit = new Outfit();
        Random rng = new Random(1);

        HandGear g1 = new HandGear(new Name("Savage", "Sword"), 4);
        HandGear g2 = new HandGear(new Name("Swift", "Dagger"), 3);
        HandGear g3 = new HandGear(new Name("Heavy", "Shield"), 2);

        outfit.add(g1, rng);
        outfit.add(g2, rng);
        assertTrue(outfit.isFull(ItemType.HAND));

        // This should combine g1 and g3 (first hand slot + new item)
        outfit.add(g3, rng);

        List<Gear> hands = outfit.itemsOf(ItemType.HAND);
        assertEquals(2, hands.size());  // still 2 slots

        // Totals should be (4+2) + 3 = 9 attack, 0 defense
        TupleInt totals = outfit.totals();
        assertEquals(9, totals.attack);
        assertEquals(0, totals.defense);
    }
}
