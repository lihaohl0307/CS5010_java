package rpg;

import org.junit.Test;

import static org.junit.Assert.*;

public class NameTest {

    @Test
    public void testFullName() {
        Name n = new Name("Happy", "HoverBoard");
        assertEquals("Happy", n.getAdjective());
        assertEquals("HoverBoard", n.getNoun());
        assertEquals("Happy HoverBoard", n.full());
    }

    @Test
    public void testCombineNameFormat() {
        Name weaker = new Name("Scurrying", "Sandals");
        Name stronger = new Name("Happy", "HoverBoard");

        Name combined = Name.combine(weaker, stronger);
        assertEquals("Scurrying, Happy HoverBoard", combined.full());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAdjectiveThrows() {
        new Name("", "Boots");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyNounThrows() {
        new Name("Swift", "");
    }
}
