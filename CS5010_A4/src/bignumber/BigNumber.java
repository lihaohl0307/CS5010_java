package bignumber;

public interface BigNumber extends  Comparable<BigNumber>{
    // number of digits
    int length();

    // left shift by k
    BigNumber shiftLeft(int k);

    // right shift by k
    BigNumber shiftRight(int k);

    /**
     * add single digit 0 to 9
     * @throws IllegalArgumentException if the digit is not in range 0-9
     */
    BigNumber addDigit(int digit);

    /**
     * return the digit at position pos, where pos=0 is the rightmost (least-significant) digit.
     * @throws IllegalArgumentException if pos is invalid
     */
    int getDigitAt(int pos);

    // provide a copy of this BigNumber
    BigNumber copy();

    // return the sum of big numbers
    BigNumber add(BigNumber other);

    // basic calculations
    @Override
    int compareTo(BigNumber other);

    @Override
    String toString();
}