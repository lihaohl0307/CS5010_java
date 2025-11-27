package priority;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapToMMPQAdaptor<T> implements MinMaxPriorityQueue<T> {

    // Adaptee: a TreeMap from priority -> list of items with that priority.
    // We rely on TreeMap's sorted keys and List's ability to keep insertion order.
    private final TreeMap<Integer, List<T>> backingMap;

    /**
     * Construct an adaptor on top of an existing TreeMap.
     * The map is assumed to be empty initially (as per assignment pattern),
     * but this class will also behave reasonably if it is not.
     */
    public TreeMapToMMPQAdaptor(TreeMap<Integer, List<T>> treeMap) {
        if (treeMap == null) {
            throw new IllegalArgumentException("Backing TreeMap cannot be null");
        }
        this.backingMap = treeMap;
    }

    @Override
    public void add(T item, int priority) {
        // Get the list of items for this priority or create a new one.
        List<T> list = backingMap.get(priority);
        if (list == null) {
            list = new LinkedList<>(); // linked list support O(1) removal(FIFO), array list -> O(n)
            backingMap.put(priority, list);  // priority -> list of items (key value pair)
        }
        list.add(item);
    }

    @Override
    public T minPriorityItem() {
        if (backingMap.isEmpty()) {
            return null;
        }

        // Get the entry with the smallest key (minimum priority)
        Map.Entry<Integer, List<T>> entry = backingMap.firstEntry();
        return removeFromEntry(entry);
    }

    @Override
    public T maxPriorityItem() {
        if (backingMap.isEmpty()) {
            return null;
        }

        // Get the entry with the largest key (maximum priority)
        Map.Entry<Integer, List<T>> entry = backingMap.lastEntry();
        return removeFromEntry(entry);
    }

    /**
     * Helper to remove the earliest-inserted item from a given entry's list.
     * If the list becomes empty, remove the key from the map.
     */
    private T removeFromEntry(Map.Entry<Integer, List<T>> entry) {
        if (entry == null) {
            return null;
        }

        List<T> list = entry.getValue();
        if (list == null || list.isEmpty()) {
            // Defensive: if someone externally put an empty list on the map,
            // clean it up and indicate no item.
            backingMap.remove(entry.getKey());
            return null;
        }

        // FIFO within the same priority: remove from the front.
        T item;

        // Because we created lists as LinkedLists, this cast is safe
        // *for lists created by this class*. If you want to be extra-safe,
        // you can just use list.remove(0) instead (O(n) for ArrayList).
        if (list instanceof LinkedList) {
            item = ((LinkedList<T>) list).removeFirst();
        } else {
            // Fallback for any other List implementation.
            item = list.remove(0);
        }

        // If that was the last item at this priority, remove the priority key.
        if (list.isEmpty()) {
            backingMap.remove(entry.getKey());
        }

        return item;
    }
}
