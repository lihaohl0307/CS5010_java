 package priority;

public interface MinMaxPriorityQueue<T> {

    /**
     * Add an item with the given priority into the queue.
     *
     * @param item     item to add
     * @param priority smaller means lower priority, larger means higher priority
     */
    void add(T item, int priority);

    /**
     * Remove and return the item with the minimum priority.
     * If no such item exists, return null.
     *
     * If several items have the same (minimum) priority, return them
     * in the order in which they were added.
     */
    T minPriorityItem();

    /**
     * Remove and return the item with the maximum priority.
     * If no such item exists, return null.
     *
     * If several items have the same (maximum) priority, return them
     * in the order in which they were added.
     */
    T maxPriorityItem();
}
