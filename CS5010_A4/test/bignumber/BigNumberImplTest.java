package bignumber;

import org.testng.annotations.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

import java.math.BigInteger;


public class BigNumberImplTest {

    private static BigNumber N(String s) { return new BigNumberImpl(s); }

    @Test
    @DisplayName("no-arg constructor makes canonical zero")
    public void ctorNoArgIsZero() {
        BigNumber z = new BigNumberImpl();
        assertEquals(1, z.length());
        assertEquals("0", z.toString());
        // shifting zero keeps zero
        assertEquals("0", z.copy().shiftLeft(10).toString());
        assertEquals("0", z.copy().shiftRight(10).toString());
    }

    @Test
    @DisplayName("string constructor: trims leading zeros, validates digits")
    public void ctorStringValidation() {
        assertEquals("0", N("0").toString());
        assertEquals("0", N("000000").toString());
        assertEquals(1, N("000000").length());

        assertEquals("12345", N("00012345").toString());
        assertEquals(5, N("00012345").length());

        assertThrows(IllegalArgumentException.class, () -> new BigNumberImpl(null));
        assertThrows(IllegalArgumentException.class, () -> N(""));
        assertThrows(IllegalArgumentException.class, () -> N("12a34"));
        assertThrows(IllegalArgumentException.class, () -> N("+123"));
        assertThrows(IllegalArgumentException.class, () -> N("-123"));
        assertThrows(IllegalArgumentException.class, () -> N(" 123"));
    }


    @Test
    @DisplayName("getDigitAt: happy path and bounds")
    public void getDigitAtBasics() {
        BigNumber x = N("32411"); // LSD-first inside; API pos0 is rightmost
        assertEquals(1, x.getDigitAt(0));
        assertEquals(1, x.getDigitAt(1));
        assertEquals(4, x.getDigitAt(2));
        assertEquals(2, x.getDigitAt(3));
        assertEquals(3, x.getDigitAt(4));

        assertThrows(IllegalArgumentException.class, () -> x.getDigitAt(-1));
        assertThrows(IllegalArgumentException.class, () -> x.getDigitAt(5));
    }

    @Test
    @DisplayName("shiftLeft / shiftRight including negative k")
    public void shiftsWork() {
        BigNumber x = N("32411");
        assertEquals("324110", x.copy().shiftLeft(1).toString());
        assertEquals("3241100", x.copy().shiftLeft(2).toString());

        assertEquals("3241", x.copy().shiftRight(1).toString());
        assertEquals("324", x.copy().shiftRight(2).toString());
        assertEquals("0", x.copy().shiftRight(99).toString()); // too many → 0

        // negative shifts = opposite direction
        assertEquals("3241", x.copy().shiftLeft(-1).toString());
        assertEquals("324110", x.copy().shiftRight(-1).toString());

        // zero: no-op
        assertEquals(x.toString(), x.copy().shiftLeft(0).toString());
        assertEquals(x.toString(), x.copy().shiftRight(0).toString());
    }

    @Test
    @DisplayName("addDigit without carry (typical pattern shiftLeft then addDigit)")
    public void addDigitNoCarry() {
        BigNumber b = new BigNumberImpl();     // 0
        b.shiftLeft(1).addDigit(3);            // 3
        b.shiftLeft(1).addDigit(2);            // 32
        b.shiftLeft(1).addDigit(4);            // 324
        b.shiftLeft(1).addDigit(1);            // 3241
        b.shiftLeft(1).addDigit(1);            // 32411
        assertEquals("32411", b.toString());
    }

    @Test
    @DisplayName("addDigit with carry (e.g., 99 + 7 = 106)")
    public void addDigitWithCarry() {
        BigNumber n = N("99");
        n.addDigit(7);
        assertEquals("106", n.toString());

        BigNumber m = N("999");
        m.addDigit(7);
        assertEquals("1006", m.toString());

        assertThrows(IllegalArgumentException.class, () -> N("5").addDigit(-1));
        assertThrows(IllegalArgumentException.class, () -> N("5").addDigit(10));
    }

    @Test
    @DisplayName("add(BigNumber) sums correctly and does not mutate operands")
    public void addTwoNumbers() {
        BigNumber a = N("32411");
        BigNumber b = N("589");
        BigNumber sum = a.add(b);
        assertEquals("33000", sum.toString());
        // operands unchanged
        assertEquals("32411", a.toString());
        assertEquals("589", b.toString());

        // different lengths + big carry chain
        assertEquals("100000000000000000000",
                N("99999999999999999999").add(N("1")).toString());
    }

    @Test
    @DisplayName("copy() is deep copy (independent)")
    public void copyIsDeep() {
        BigNumber a = N("12345");
        BigNumber c = a.copy();
        assertEquals("12345", c.toString());

        // mutate original; copy must remain unchanged
        a.shiftLeft(1).addDigit(9); // 123459
        assertEquals("123459", a.toString());
        assertEquals("12345", c.toString());
    }

    @Test
    @DisplayName("compareTo uses numeric order")
    public void compareToOrder() {
        assertEquals(0, N("0").compareTo(N("0")));
        assertTrue(N("0").compareTo(N("1")) < 0);
        assertTrue(N("10").compareTo(N("2")) > 0);
        assertTrue(N("1000").compareTo(N("999")) > 0);
        assertTrue(N("12345").compareTo(N("12344")) > 0);
        assertTrue(N("12345").compareTo(N("12346")) < 0);

        // same length, digit-wise compare from MSD
        assertTrue(N("200").compareTo(N("199")) > 0);
    }

    @Test
    @DisplayName("toString is canonical (no leading zeros)")
    public void toStringCanonical() {
        assertEquals("0", N("0000").toString());
        assertEquals("1200", N("0001200").toString());
        assertEquals("5", N("0005").toString());
    }

    @Test
    @DisplayName("random big addition cross-check with BigInteger")
    public void bigAdditionCrossCheck() {
        String a = "9876543210987654321098765432109876543210";
        String b = "1234567890123456789012345678901234567890";
        BigInteger ai = new BigInteger(a);
        BigInteger bi = new BigInteger(b);
        BigInteger si = ai.add(bi);

        BigNumber an = N(a);
        BigNumber bn = N(b);
        BigNumber sn = an.add(bn);

        assertEquals(si.toString(), sn.toString());
        // operands unchanged
        assertEquals(a, an.toString());
        assertEquals(b, bn.toString());
    }
}
