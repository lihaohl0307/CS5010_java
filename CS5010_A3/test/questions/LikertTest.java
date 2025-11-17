package questions;

import org.testng.annotations.Test;
import static org.junit.Assert.*;

public class LikertTest {
    @Test
    public void testValidChoicesAreCorrect() {
        Question q = new Likert("I enjoy programming.");
        for (int i = 1; i <= 5; i++) {
            assertEquals("choice " + i + " should be correct", "Correct", q.answer(Integer.toString(i)));
        }
    }

    @Test
    public void testInvalidChoicesAreIncorrect() {
        Question q = new Likert("I enjoy programming.");
        assertEquals("Incorrect", q.answer("0"));
        assertEquals("Incorrect", q.answer("6"));
        assertEquals("Incorrect", q.answer("-1"));
        assertEquals("Incorrect", q.answer("foo"));
        assertEquals("Incorrect", q.answer(null));
    }
}
