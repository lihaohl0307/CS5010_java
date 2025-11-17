package questions;

import org.junit.Test;
import static org.junit.Assert.*;

public class TrueFalseTest {
    @Test
    public void testCorrectTrue() {
        Question q = new TrueFalse("Sky is blue", "True");
        assertEquals("Correct", q.answer("True"));
    }

    @Test
    public void testCorrectFalse() {
        Question q = new TrueFalse("Pigs can fly", "False");
        assertEquals("Correct", q.answer("false"));
    }

    @Test
    public void testIncorrectAnswer() {
        Question q = new TrueFalse("Sky is blue", "True");
        assertEquals("Incorrect", q.answer("False"));
    }

    @Test
    public void testInvalidInputIsIncorrect() {
        Question q = new TrueFalse("Sky is blue", "True");
        assertEquals("Incorrect", q.answer("maybe"));
        assertEquals("Incorrect", q.answer(null));
        assertEquals("Incorrect", q.answer(""));
        assertEquals("Incorrect", q.answer("  "));
    }
}
