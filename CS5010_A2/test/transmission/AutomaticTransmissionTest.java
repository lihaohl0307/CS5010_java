package transmission;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for AutomaticTransmission implementing Transmission.
 * Uses thresholds: 10, 20, 30, 40, 50
 */
public class AutomaticTransmissionTest {

    private Transmission newTx() {
        return new AutomaticTransmission(10, 20, 30, 40, 50);
    }

    /** helper: apply increaseSpeed n times */
    private Transmission inc(Transmission t, int n) {
        for (int i = 0; i < n; i++) t = t.increaseSpeed();
        return t;
    }

    /** helper: apply decreaseSpeed n times */
    private Transmission dec(Transmission t, int n) {
        for (int i = 0; i < n; i++) t = t.decreaseSpeed();
        return t;
    }

    // ---------- constructor validation ----------

    @Test(expected = IllegalArgumentException.class)
    public void ctor_rejectsNonPositiveFirstThreshold() {
        new AutomaticTransmission(0, 10, 20, 30, 40);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctor_rejectsNonIncreasing() {
        new AutomaticTransmission(10, 10, 20, 30, 40);
    }

    @Test
    public void ctor_initialStateIsSpeed0Gear0() {
        Transmission t = newTx();
        assertEquals(0, t.getSpeed());
        assertEquals(0, t.getGear());
        assertEquals("Transmission (speed = 0, gear = 0)", t.toString());
    }

    // ---------- increaseSpeed behavior & immutability ----------

    @Test
    public void increaseSpeed_addsTwoAndReturnsNewInstance() {
        Transmission t1 = newTx();
        Transmission t2 = t1.increaseSpeed();

        // new object returned
        assertNotSame(t1, t2);

        // original unchanged
        assertEquals(0, t1.getSpeed());
        assertEquals(0, t1.getGear());

        // new state correct
        assertEquals(2, t2.getSpeed());
        assertEquals(1, t2.getGear());
    }

    @Test
    public void gearBoundaries_matchSpec() {
        Transmission t = newTx();

        // speed 0 -> gear 0
        assertEquals(0, t.getSpeed());
        assertEquals(0, t.getGear());

        // reach speed 2, 8 (still gear 1)
        t = inc(t, 1); // 2
        assertEquals(1, t.getGear());
        t = inc(t, 3); // 8
        assertEquals(1, t.getGear());

        // reach 10 -> gear 2
        t = inc(t, 1); // 10
        assertEquals(10, t.getSpeed());
        assertEquals(2, t.getGear());

        // reach 20 -> gear 3
        t = inc(t, 5); // 20
        assertEquals(3, t.getGear());

        // reach 30 -> gear 4
        t = inc(t, 5); // 30
        assertEquals(4, t.getGear());

        // reach 40 -> gear 5
        t = inc(t, 5); // 40
        assertEquals(5, t.getGear());

        // reach 50+ -> gear 6
        t = inc(t, 5); // 50
        assertEquals(6, t.getGear());
        t = inc(t, 1); // 52
        assertEquals(6, t.getGear());
    }

    @Test
    public void decreasingAdjustsGearDownAtBoundaries() {
        Transmission t = newTx();
        t = inc(t, 25); // 50 -> gear 6
        assertEquals(50, t.getSpeed());
        assertEquals(6, t.getGear());

        // 48 -> still 6? No, 48 < 50 so gear 5
        t = t.decreaseSpeed(); // 48
        assertEquals(48, t.getSpeed());
        assertEquals(5, t.getGear());

        // 38 -> gear 4 (since < 40)
        t = dec(t, 5); // 38
        assertEquals(38, t.getSpeed());
        assertEquals(4, t.getGear());

        // 28 -> gear 3
        t = dec(t, 5); // 28
        assertEquals(3, t.getGear());

        // 18 -> gear 2
        t = dec(t, 5); // 18
        assertEquals(2, t.getGear());

        // 8 -> gear 1
        t = dec(t, 5); // 8
        assertEquals(1, t.getGear());

        // 0 -> gear 0
        t = dec(t, 4); // from 8 -> 0
        assertEquals(0, t.getSpeed());
        assertEquals(0, t.getGear());
    }

    // ---------- decreaseSpeed exceptions ----------

    @Test(expected = IllegalStateException.class)
    public void decreaseBelowZeroThrows() {
        Transmission t = newTx(); // speed 0
        t.decreaseSpeed();        // 0 - 2 -> throws
    }

    // ---------- toString formatting ----------

    @Test
    public void toString_hasExactFormat() {
        Transmission t = newTx();
        assertEquals("Transmission (speed = 0, gear = 0)", t.toString());
        t = inc(t, 6); // speed 12, gear 2
        assertEquals("Transmission (speed = 12, gear = 2)", t.toString());
    }
}
