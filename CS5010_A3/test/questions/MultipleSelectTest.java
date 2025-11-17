package questions;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

public class MultipleSelectTest {

    private MultipleSelect make(String text, String correct, List<String> options) throws Exception {
        Constructor<MultipleSelect> c = MultipleSelect.class.getDeclaredConstructor(String.class, String.class, List.class);
        c.setAccessible(true);
        return c.newInstance(text, correct, options);
    }

    @Test
    public void testOrderInsensitiveAndNoDuplicates() throws Exception {
        MultipleSelect q = make(
                "Select primes",
                "1 3 4",
                Arrays.asList("2", "3", "5", "7")
        );
        assertEquals("Correct", q.answer("4 3 1"));
        assertEquals("Correct", q.answer("1 3 4 4 3 1"));
        assertEquals("Incorrect", q.answer("1 3"));
        assertEquals("Incorrect", q.answer("1 2 3 4"));
    }

    @Test
    public void testInvalidUserInputIsIncorrect() throws Exception {
        MultipleSelect q = make(
                "Select even numbers",
                "2 4",
                Arrays.asList("1", "2", "3", "4", "5")
        );
        assertEquals("Incorrect", q.answer("0"));
        assertEquals("Incorrect", q.answer("6"));
        assertEquals("Incorrect", q.answer("foo"));
        assertEquals("Incorrect", q.answer(""));
        assertEquals("Incorrect", q.answer(null));
    }
}
