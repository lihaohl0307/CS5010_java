package questions;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class MultipleChoiceTest {

    @Test
    public void testCorrectAnswer() {
        MultipleChoice q = new MultipleChoice(
                "2 + 2 = ?",
                Arrays.asList("3", "4", "5"),
                2
        );
        assertEquals("Correct", q.answer("2"));
    }

    @Test
    public void testIncorrectAnswer() {
        MultipleChoice q = new MultipleChoice(
                "2 + 2 = ?",
                Arrays.asList("3", "4", "5"),
                2
        );
        assertEquals("Incorrect", q.answer("1"));
        assertEquals("Incorrect", q.answer("3"));
    }

    @Test
    public void testInvalidInputIsIncorrect() {
        MultipleChoice q = new MultipleChoice(
                "2 + 2 = ?",
                Arrays.asList("3", "4", "5"),
                2
        );
        assertEquals("Incorrect", q.answer("0"));
        assertEquals("Incorrect", q.answer("10"));
        assertEquals("Incorrect", q.answer("foo"));
        assertEquals("Incorrect", q.answer(null));
    }
}
