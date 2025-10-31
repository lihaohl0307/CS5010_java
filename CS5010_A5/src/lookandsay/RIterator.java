package lookandsay;

import java.util.Iterator;

/**
 * A reversible Iterator.
 * @param <T> element type
 */
public interface RIterator<T> extends Iterator<T> {
    /**
     * @return the current element, then move the cursor one step backward.
     */
    T prev();

    /**
     * @return true if a previous() call would succeed
     */
    boolean hasPrevious();
}
