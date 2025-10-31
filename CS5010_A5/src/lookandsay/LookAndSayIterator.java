package lookandsay;

import java.math.BigInteger;
import java.util.NoSuchElementException;

/**
 * Look-and-say iterator that can move forward and (sometimes) backward.
 * The iterator returns BigInteger values but internally builds from their decimal strings.
 *
 * Semantics:
 * - next(): return current term, then advance to the next term.
 * - prev(): return current term, then retreat to the previous term (only when
 *           the current term is a valid sequence of (count,digit) pairs with single-digit counts).
 * - hasNext(): true iff the *next number to be returned* (i.e., current) is <= end.
 * - hasPrevious(): true iff the current term can be parsed into valid (count,digit) pairs.
 *
 * Notes on reverse:
 * - We only allow reversal when the current term is composed of pairs "cd" where:
 *     c ∈ {'1',...,'9'} (no zero counts), d ∈ {'1',...,'9'} (digits 1–9), and the length is even.
 *   This corresponds exactly to “take digits two at a time” described in the prompt.
 */
public class LookAndSayIterator implements RIterator<BigInteger> {
    private BigInteger current;
    private final BigInteger end;

    /** Two-arg constructor. */
    public LookAndSayIterator(BigInteger seed, BigInteger end) {
        validate(seed, end);
        this.current = seed;
        this.end = end;
    }

    /** One-arg constructor: the end is the largest 100-digit number (100 nines). */
    public LookAndSayIterator(BigInteger seed) {
        this(seed, maxHundredNines());
    }

    /** No-arg constructor: seed = 1, end = largest 100-digit number. */
    public LookAndSayIterator() {
        this(BigInteger.ONE, maxHundredNines());
    }

    @Override
    public boolean hasNext() {
        // We are allowed to return current if it is not greater than the end.
        return current.compareTo(end) <= 0;
    }

    @Override
    public BigInteger next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No next element (current exceeds end).");
        }
        BigInteger toReturn = current;
        current = nextOf(current);
        return toReturn;
    }

    @Override
    public boolean hasPrevious() {
        return canReverse(current);
    }

    @Override
    public BigInteger prev() {
        if (!hasPrevious()) {
            throw new NoSuchElementException("No previous element for current term.");
        }
        BigInteger toReturn = current;
        current = previousOf(current);
        return toReturn;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() not supported.");
    }

    /* ------------------ helpers ------------------ */

    private static BigInteger maxHundredNines() {
        // largest 100-digit number
        return new BigInteger("9".repeat(100));
    }

    private static void validate(BigInteger seed, BigInteger end) {
        if (seed == null || end == null) {
            throw new IllegalArgumentException("Seed and end must be non-null.");
        }
        if (seed.signum() <= 0) {
            throw new IllegalArgumentException("Seed must be positive.");
        }
        if (seed.compareTo(end) >= 0) {
            throw new IllegalArgumentException("Seed must be less than end.");
        }
        String s = seed.toString();
        if (s.indexOf('0') >= 0) {
            throw new IllegalArgumentException("Seed must not contain any zeros.");
        }
    }

    /** Compute the forward-look-and-say term. */
    private static BigInteger nextOf(BigInteger n) {
        String s = n.toString();
        StringBuilder out = new StringBuilder(s.length() * 2);
        int i = 0;
        while (i < s.length()) {
            char digit = s.charAt(i);
            int count = 1;
            int j = i + 1;
            while (j < s.length() && s.charAt(j) == digit) {
                count++;
                j++;
            }
            out.append(count);     // NOTE: count may be multiple digits (e.g., 11)
            out.append(digit);
            i = j;
        }
        return new BigInteger(out.toString());
    }

    /**
     * True if we can reverse the current term by reading it as pairs (count,digit),
     * with single-digit count (1..9) and digit (1..9). No zeros and even length.
     */
    private static boolean canReverse(BigInteger n) {
        String s = n.toString();
        if ((s.length() & 1) == 1) return false; // must be even length
        for (int i = 0; i < s.length(); i += 2) {
            char cCount = s.charAt(i);
            char cDigit = s.charAt(i + 1);
            if (cCount < '1' || cCount > '9') return false; // single-digit positive count only
            if (cDigit < '1' || cDigit > '9') return false; // digits 1..9 only (no zeros)
        }
        return true;
    }

    /** Compute the previous term by expanding pairs (count,digit). */
    private static BigInteger previousOf(BigInteger n) {
        String s = n.toString();
        if (!canReverse(n)) {
            throw new IllegalArgumentException("Current term cannot be reversed by (count,digit) pairs.");
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i += 2) {
            int count = s.charAt(i) - '0';
            char digit = s.charAt(i + 1);
            for (int k = 0; k < count; k++) out.append(digit);
        }
        return new BigInteger(out.toString());
    }
}
