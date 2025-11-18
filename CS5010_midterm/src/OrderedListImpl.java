import java.util.function.Predicate;

public class OrderedListImpl<E extends Comparable<E>> implements OrderedList<E> {

    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T v) {
            this.value = v;
        }
    }

    private Node<E> head;
    private int size;

    @Override
    public void add(E val) {
        if (val == null) {
            throw new IllegalArgumentException("Null values are not supported");
        }

        Node<E> newNode = new Node<>(val);

        // insert at head if empty or new value <= head
        if (head == null || head.value.compareTo(val) >= 0) {
            newNode.next = head;
            head = newNode;
        } else {
            // find insertion point
            Node<E> curr = head;
            while (curr.next != null && curr.next.value.compareTo(val) <= 0) {
                curr = curr.next;
            }
            newNode.next = curr.next;
            curr.next = newNode;
        }

        size++;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<E> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.value;
    }

    @Override
    public int size() {
        return size;
    }

    // sublist takes in lambda functions
    @Override
    public OrderedList<E> subList(Predicate<E> pred) {
        OrderedListImpl<E> result = new OrderedListImpl<>();
        Node<E> curr = head;
        while (curr != null) {
            if (pred.test(curr.value)) {
                // add() already keeps it ordered
                result.add(curr.value);
            }
            curr = curr.next;
        }
        return result;
    }

    // Problem 1: toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> curr = head;
        boolean first = true;
        while (curr != null) {
            if (!first) {
                sb.append(' ');
            }
            sb.append(curr.value);
            first = false;
            curr = curr.next;
        }
        return sb.toString();
    }
}
