package priority;

import org.junit.Test;

import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Unit tests for TreeMapToMMPQAdaptor.
 */
public class TreeMapToMMPQAdaptorTest {

    private MinMaxPriorityQueue<String> newQueue() {
        TreeMap<Integer, List<String>> map = new TreeMap<>();
        return new TreeMapToMMPQAdaptor<>(map);
    }

    @Test
    public void testMinOnEmptyReturnsNull() {
        MinMaxPriorityQueue<String> q = newQueue();
        assertNull(q.minPriorityItem());
    }

    @Test
    public void testMaxOnEmptyReturnsNull() {
        MinMaxPriorityQueue<String> q = newQueue();
        assertNull(q.maxPriorityItem());
    }

    @Test
    public void testSingleElementMinAndMax() {
        MinMaxPriorityQueue<String> q = newQueue();
        q.add("only", 5);

        // both min and max should be the same element
        assertEquals("only", q.minPriorityItem());

        // now the queue is empty, max should be null
        assertNull(q.maxPriorityItem());
    }

    @Test
    public void testMinRemovesInIncreasingPriorityOrder() {
        MinMaxPriorityQueue<String> q = newQueue();
        q.add("p10", 10);
        q.add("p1", 1);
        q.add("p5", 5);

        // min should follow 1,5,10
        assertEquals("p1", q.minPriorityItem());
        assertEquals("p5", q.minPriorityItem());
        assertEquals("p10", q.minPriorityItem());
        assertNull(q.minPriorityItem());
    }

    @Test
    public void testMaxRemovesInDecreasingPriorityOrder() {
        MinMaxPriorityQueue<String> q = newQueue();
        q.add("p10", 10);
        q.add("p1", 1);
        q.add("p5", 5);

        // max should follow 10,5,1
        assertEquals("p10", q.maxPriorityItem());
        assertEquals("p5", q.maxPriorityItem());
        assertEquals("p1", q.maxPriorityItem());
        assertNull(q.maxPriorityItem());
    }

    @Test
    public void testFifoWithinSamePriorityForMin() {
        MinMaxPriorityQueue<String> q = newQueue();
        q.add("a1", 3);
        q.add("a2", 3);
        q.add("b1", 5);

        // same priority 3 → should be FIFO
        assertEquals("a1", q.minPriorityItem());
        assertEquals("a2", q.minPriorityItem());
        // then next higher priority
        assertEquals("b1", q.minPriorityItem());
        assertNull(q.minPriorityItem());
    }

    @Test
    public void testFifoWithinSamePriorityForMax() {
        MinMaxPriorityQueue<String> q = newQueue();
        q.add("low", 1);
        q.add("h1", 10);
        q.add("h2", 10);

        // priority 10 → FIFO inside same bucket
        assertEquals("h1", q.maxPriorityItem());
        assertEquals("h2", q.maxPriorityItem());
        // then lower priority
        assertEquals("low", q.maxPriorityItem());
        assertNull(q.maxPriorityItem());
    }

    @Test
    public void testInterleavedMinAndMax() {
        MinMaxPriorityQueue<String> q = newQueue();
        q.add("low1", 1);   // min
        q.add("mid", 5);    // middle
        q.add("high1", 10); // max
        q.add("low2", 1);   // same min priority
        q.add("high2", 10); // same max priority

        // max: high1, high2
        assertEquals("high1", q.maxPriorityItem());
        assertEquals("high2", q.maxPriorityItem());

        // min: low1, low2
        assertEquals("low1", q.minPriorityItem());
        assertEquals("low2", q.minPriorityItem());

        // only "mid" remains
        assertEquals("mid", q.minPriorityItem());
        assertNull(q.minPriorityItem());
        assertNull(q.maxPriorityItem());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullBackingMapThrows() {
        new TreeMapToMMPQAdaptor<String>(null);
    }
}
