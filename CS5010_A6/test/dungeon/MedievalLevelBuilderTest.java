package dungeon;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for MedievalLevelBuilder (JUnit 4 version).
 *
 * Verifies:
 *  - Room, monster, and treasure limits
 *  - Proper exception handling
 *  - Correct values and build behavior
 */
public class MedievalLevelBuilderTest {

    @Test
    public void buildSucceedsWhenTargetsExactlyMet() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(3, 2, 4, 3);
        b.addRoom("Damp stone antechamber");
        b.addRoom("Torch-lit hall");

        b.addGoblins(0, 2);
        b.addOrc(1);
        b.addOgre(1);

        b.addPotion(0);
        b.addGold(1, 5);
        b.addWeapon(1, "rusty sword");

        Level lvl = b.build();

        assertNotNull(lvl);
        String s = lvl.toString();
        assertTrue(s.contains("Level 3"));
        assertTrue(s.contains("goblin (hitpoints = 7)"));
        assertTrue(s.contains("orc (hitpoints = 20)"));
        assertTrue(s.contains("ogre (hitpoints = 50)"));
        assertTrue(s.contains("a healing potion (value = 1)"));
        assertTrue(s.contains("pieces of gold (value = 5)"));
        assertTrue(s.contains("rusty sword (value = 10)"));

        // Cannot build twice
        try {
            b.build();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void addingRoomPastTargetThrows() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 0, 0);
        b.addRoom("Only room");
        try {
            b.addRoom("Excess room");
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void addingMonsterToNonexistentRoomThrows() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 1, 0);
        try {
            b.addOrc(0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}

        b.addRoom("A room");
        b.addOrc(0); // should succeed
    }

    @Test
    public void addingTreasureToNonexistentRoomThrows() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 0, 1);
        try {
            b.addPotion(0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}

        b.addRoom("A room");
        b.addPotion(0); // should succeed
    }

    @Test
    public void monsterTargetStrictlyEnforced_singleAdds() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 1, 0);
        b.addRoom("R");
        b.addOrc(0);
        try {
            b.addOgre(0);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void monsterTargetStrictlyEnforced_bulkGoblins() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 3, 0);
        b.addRoom("R");
        b.addGoblins(0, 2);
        try {
            b.addGoblins(0, 2);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
        b.addGoblins(0, 1); // should succeed (3 total)
    }

    @Test
    public void treasureTargetStrictlyEnforced() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 0, 2);
        b.addRoom("R");
        b.addGold(0, 50);
        b.addWeapon(0, "warhammer");
        try {
            b.addPotion(0);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void weaponAlwaysValueTen_potionIsOne_goldIsProvidedValue() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 0, 3);
        b.addRoom("R");
        b.addWeapon(0, "axe");
        b.addPotion(0);
        b.addGold(0, 42);

        Level lvl = b.build();
        String s = lvl.toString();
        assertTrue(s.contains("axe (value = 10)"));
        assertTrue(s.contains("a healing potion (value = 1)"));
        assertTrue(s.contains("pieces of gold (value = 42)"));
    }

    @Test
    public void humanAddedWithProvidedStats() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(2, 1, 1, 0);
        b.addRoom("R");
        b.addHuman(0, "Sir Lancelot",
                "a valiant knight of the Round Table", 33);
        Level lvl = b.build();
        String s = lvl.toString();
        assertTrue(s.contains("Sir Lancelot (hitpoints = 33)"));
        assertTrue(s.contains("a valiant knight of the Round Table"));
    }

    @Test
    public void buildThrowsWhenIncomplete_roomsMissing() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 0, 0);
        try {
            b.build();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void buildThrowsWhenIncomplete_monstersMissing() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 1, 0);
        b.addRoom("R");
        try {
            b.build();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void buildThrowsWhenIncomplete_treasuresMissing() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 0, 1);
        b.addRoom("R");
        try {
            b.build();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void cannotUseBuilderAfterBuild() {
        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 0, 0);
        b.addRoom("R");
        Level lvl = b.build();
        assertNotNull(lvl);
        try {
            b.addRoom("Another");
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
        try {
            b.build();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void invalidInputsThrow() {
        // Negative targets
        try {
            new MedievalLevelBuilder(1, -1, 0, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}

        MedievalLevelBuilder b = new MedievalLevelBuilder(1, 1, 2, 2);
        b.addRoom("R");

        try {
            b.addGoblins(0, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}

        try {
            b.addHuman(0, "H", "desc", 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}

        try {
            b.addGold(0, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}

        try {
            b.addSpecial(0, "relic", 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}
    }
}
