package bignumber;

/**
 * linked list implementation of BigNumber,
 * For example, the number 32411 can be stored as 1 -> 1 -> 4 -> 2 -> 3
 */
public class BigNumberImpl implements BigNumber{
    // singly linked list
    private static final class Node{
        private int data;
        private Node next;
        public Node(int data){
            this.data = data;
        }
    }

    // at the head of the linked list is the LSD (position 0)
    // set global head
    private Node head;
    private int length;

    // construct 0 as required to start with 0
    public BigNumberImpl() {
        this.head = new Node(0);
        this.length = 1;
    }

    // linked list constructor
    public BigNumberImpl(String s) {
        if (s == null || s.isEmpty()) {throw new IllegalArgumentException("null or empty string");}

        // find the first non-zero digit
        int nonZeroIndex = 0;
        while (nonZeroIndex < s.length() && s.charAt(nonZeroIndex) == '0') {nonZeroIndex++;}

        // if all zeros, then construct a 0
        if (nonZeroIndex == s.length()) {
            this.head = new Node(0);
            this.length = 1;
            return;
        }

        // verify all digits are valid
        for (int i = nonZeroIndex; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {throw new IllegalArgumentException("invalid character: " + s.charAt(i));}
        }

        // linked list builder: reverse the string and store the digits in the linked list
        Node head = null; //local
        Node tail = null;
        int count = 0;
        for (int i = s.length() - 1; i >= nonZeroIndex; i--) {
            int digit = s.charAt(i) - '0';
            Node node = new Node(digit);

            if (head == null) {         // first node → both head and tail
                head = tail = node;
            } else {                    // append to tail
                tail.next = node;
                tail = node;
            }
            count++;
        }
        this.head = head;   // LSD-first, global head
        this.length = count;
    }

    /** Private ctor for internal use (assumes canonicalized args). */
    private BigNumberImpl(Node head, int length) {
        this.head = head;
        this.length = length;
    }

    private boolean isZero() {
        return length == 1 && head != null && head.data == 0 && head.next == null;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public BigNumber shiftLeft(int k) {
        if (k == 0) return this;
        if (k < 0) return shiftRight(-k);

        if (isZero()) return this; // 0 * 10^k == 0
        // add leading zeros
        for (int i = 0; i < k; i++) {
            Node zero = new Node(0);
            zero.next = head;
            head = zero;
            length++;
        }
        return this;
    }

    @Override
    public BigNumber shiftRight(int k) {
        if (k == 0) return this;
        if (k < 0) return shiftLeft(-k);

        // drop k least-significant digits (from head).
        if (isZero()) return this;        // digits / 10^k, 0 stays 0.
        for (int i = 0; i < k && head != null; i++) {
            head = head.next;      // drop one node from the head
            length--;
        }
        // if we dropped all digits → reset to canonical 0
        if (head == null) {
            head = new Node(0);
            length = 1;
        }
        return this;
    }

    @Override
    public BigNumber addDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }

        // Add a digit to the least significant position with carry propagation
        Node current = head;
        int sum = current.data + digit;
        current.data = sum % 10;
        int carry = sum / 10;

        // Propagate carry through the list
        while (carry > 0 && current.next != null) {
            current = current.next;
            sum = current.data + carry;
            current.data = sum % 10;
            carry = sum / 10;
        }

        // If there's still a carry, add a new node at the end
        if (carry > 0) {
            current.next = new Node(carry);
            length++;
        }

        return this;
    }


    @Override
    public int getDigitAt(int pos) {
        if (pos < 0 || pos >= length) {
            throw new IllegalArgumentException("Position out of bounds");
        }

        // Traverse to position pos (0 is LSD at head)
        Node current = head;
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }

        return current.data;
    }

    @Override
    public BigNumber copy() {
        // Handle empty/zero case
        if (head == null) {
            return new BigNumberImpl();
        }

        // Create a deep copy of the linked list
        Node newHead = null;
        Node newTail = null;

        Node current = head;
        while (current != null) {
            Node newNode = new Node(current.data);

            if (newHead == null) {
                newHead = newTail = newNode;
            } else {
                newTail.next = newNode;
                newTail = newNode;
            }

            current = current.next;
        }

        return new BigNumberImpl(newHead, this.length);
    }

    @Override
    public BigNumber add(BigNumber other) {
        if (!(other instanceof BigNumberImpl)) {
            throw new IllegalArgumentException("Can only add BigNumberImpl instances");
        }

        BigNumberImpl otherImpl = (BigNumberImpl) other;

        // Create a new BigNumber for the result
        Node resultHead = null;
        Node resultTail = null;
        int resultLength = 0;

        Node curr1 = this.head;
        Node curr2 = otherImpl.head;
        int carry = 0;

        // Add corresponding digits with carry
        while (curr1 != null || curr2 != null || carry > 0) {
            int digit1 = (curr1 != null) ? curr1.data : 0;
            int digit2 = (curr2 != null) ? curr2.data : 0;

            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            int digitResult = sum % 10;

            Node newNode = new Node(digitResult);

            if (resultHead == null) {
                resultHead = resultTail = newNode; // start constructing the result
            } else {
                resultTail.next = newNode;
                resultTail = newNode;
            }

            resultLength++;

            if (curr1 != null) curr1 = curr1.next;
            if (curr2 != null) curr2 = curr2.next;
        }

        return new BigNumberImpl(resultHead, resultLength);
    }

    @Override
    public int compareTo(BigNumber other) {
        if (!(other instanceof BigNumberImpl)) {
            throw new IllegalArgumentException("Can only compare with BigNumberImpl instances");
        }

        BigNumberImpl otherImpl = (BigNumberImpl) other;

        // Compare lengths first
        if (this.length != otherImpl.length) {
            return this.length > otherImpl.length ? 1 : -1;
        }

        // Same length: compare digit by digit from MSD to LSD
        // We need to traverse to the end and compare backwards,
        // or store digits in an array
        int[] thisDigits = new int[this.length];
        int[] otherDigits = new int[otherImpl.length];

        Node current = this.head;
        for (int i = 0; i < this.length; i++) {
            thisDigits[i] = current.data;
            current = current.next;
        }

        current = otherImpl.head;
        for (int i = 0; i < otherImpl.length; i++) {
            otherDigits[i] = current.data;
            current = current.next;
        }

        // Compare from MSD (the highest index) to LSD (index 0)
        for (int i = this.length - 1; i >= 0; i--) {
            if (thisDigits[i] != otherDigits[i]) {
                return thisDigits[i] > otherDigits[i] ? 1 : -1;
            }
        }

        // All digits are equal
        return 0;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "0";
        }

        // Count nodes and store in an array
        int[] digits = new int[length];
        Node current = head;
        int index = 0;

        while (current != null) {
            digits[index++] = current.data;
            current = current.next;
        }

        // Build string in reverse order
        StringBuilder sb = new StringBuilder();
        for (int i = length - 1; i >= 0; i--) {
            sb.append(digits[i]);
        }

        return sb.toString();
    }
}
