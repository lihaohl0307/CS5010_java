import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import lookandsay.LookAndSayIterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LookAndSayIteratorTest {

    // tiny helper
    private static BigInteger bi(String s) { return new BigInteger(s); }

    @Nested
    @DisplayName("Constructor validation")
    class ConstructorValidation {

        @Test
        void seedMustBePositive() {
            assertThrows(IllegalArgumentException.class,
                    () -> new LookAndSayIterator(bi("0"), bi("9")));
            assertThrows(IllegalArgumentException.class,
                    () -> new LookAndSayIterator(bi("-5"), bi("9")));
        }

        @Test
        void seedMustBeLessThanEnd() {
            assertThrows(IllegalArgumentException.class,
                    () -> new LookAndSayIterator(bi("10"), bi("10")));
            assertThrows(IllegalArgumentException.class,
                    () -> new LookAndSayIterator(bi("11"), bi("10")));
        }

        @Test
        void seedMustNotContainZeroDigit() {
            assertThrows(IllegalArgumentException.class,
                    () -> new LookAndSayIterator(bi("101"), bi("9999")));
            assertThrows(IllegalArgumentException.class,
                    () -> new LookAndSayIterator(bi("1203"))); // 1-arg ctor also validates
        }

        @Test
        void noArgCtorStartsAtOneAndDefaultEndAllowsNext() {
            LookAndSayIterator it = new LookAndSayIterator();
            assertTrue(it.hasNext());
            assertEquals(bi("1"), it.next()); // returns seed first
        }
    }

    @Nested
    @DisplayName("Forward iteration (next)")
    class ForwardIteration {

        @Test
        void classicFirstTerms() {
            LookAndSayIterator it = new LookAndSayIterator(bi("1"), bi("999999999999"));
            assertEquals(bi("1"), it.next());       // 1
            assertEquals(bi("11"), it.next());      // 11
            assertEquals(bi("21"), it.next());      // 21
            assertEquals(bi("1211"), it.next());    // 1211
            assertEquals(bi("111221"), it.next());  // 111221
            assertEquals(bi("312211"), it.next());  // 312211
        }

        @Test
        void hasNextUsesEndBoundOnCurrentTerm() {
            // end = 21, seed = 21 → should still return 21, then stop
            LookAndSayIterator it = new LookAndSayIterator(bi("21"), bi("22"));
            assertTrue(it.hasNext());
            assertEquals(bi("21"), it.next());
            // now current advanced internally to 1211; but we gate on *current <= end*
            assertFalse(it.hasNext());
            assertThrows(java.util.NoSuchElementException.class, it::next);
        }

        @Test
        void hasNextCurrentEqualsEndBound() {
            // end = 21, seed = 21 → should still return 21, then stop
            LookAndSayIterator it = new LookAndSayIterator(bi("21"), bi("21"));
            assertFalse(it.hasNext());
            assertThrows(java.util.NoSuchElementException.class, it::next);
        }
    }

    @Nested
    @DisplayName("Backward one-step (prev / hasPrevious)")
    class BackwardIteration {

        @Test
        void canGoBackFrom11To1ThenStop() {
            LookAndSayIterator it = new LookAndSayIterator(bi("11"));
            assertTrue(it.hasPrevious());          // "11" → (1,1)
            assertEquals(bi("11"), it.prev());     // returns 11, moves to 1
            assertFalse(it.hasPrevious());         // "1" cannot reverse (odd length)
            assertThrows(java.util.NoSuchElementException.class, it::prev);
        }

        @Test
        void reversePairsExampleFromPrompt() {
            // 2112131211 → (2,1)(1,2)(1,3)(1,2)(1,1) → 112321
            LookAndSayIterator it = new LookAndSayIterator(bi("2112131211"));
            assertTrue(it.hasPrevious());
            assertEquals(bi("2112131211"), it.prev());   // return current
            // the internal current should now be 112321; verify by moving forward once
            assertEquals(bi("112321"), it.next());
        }

        @Test
        void cannotReverseWhenPairsInvalid() {
            // contains '0' in what would be a pair → invalid for reverse
            LookAndSayIterator it = new LookAndSayIterator(bi("111"));
            assertFalse(it.hasPrevious());
            assertThrows(java.util.NoSuchElementException.class, it::prev);
        }
    }

    @Nested
    @DisplayName("Mixed forward/backward behavior")
    class Mixed {

        @Test
        void forwardThenBackwardBoundary() {
            LookAndSayIterator it = new LookAndSayIterator(bi("1"), bi("999999999"));
            // next() returns 1 then 11; from 11 we can step back once
            assertEquals(bi("1"), it.next());   // current becomes 11
            assertEquals(bi("11"), it.next());  // current becomes 21
            // at 21, the string is "21" → pairs (2,1) valid; can reverse
            assertTrue(it.hasPrevious());
            assertEquals(bi("21"), it.prev());  // back to 11
            assertTrue(it.hasPrevious());
            assertEquals(bi("11"), it.prev());  // back to 1
            assertFalse(it.hasPrevious());
        }
    }
}
