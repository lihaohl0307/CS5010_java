package questions;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class OrderingTest {
    @Test
    public void testGlobalOrdering() throws Exception {
        Question tf1 = new TrueFalse("Aardvarks are mammals", "True");
        Question tf2 = new TrueFalse("Zebras are birds", "False");

        Question mc1 = new MultipleChoice("Pick fruits", Arrays.asList("Apple","Broccoli", "Carrot"), 1);
        Question mc2 = new MultipleChoice("Pick green items", Arrays.asList("Sky", "Stop Sign", "Grass"), 3);

        java.lang.reflect.Constructor<MultipleSelect> c =
                MultipleSelect.class.getDeclaredConstructor(String.class, String.class, java.util.List.class);
        c.setAccessible(true);
        Question ms1 = c.newInstance("Pick fruits", "1 3", Arrays.asList("Apple", "Broccoli", "Cherry"));
        Question ms2 = c.newInstance("Pick red items", "2", Arrays.asList("Sky", "Stop Sign", "Grass"));

        Question lk1 = new Likert("I enjoy coding.");
        Question lk2 = new Likert("Teamwork is valuable.");

        List<Question> list = new ArrayList<>(Arrays.asList(lk2, mc2, tf2, ms2, lk1, mc1, tf1, ms1));
        Collections.sort(list);

        assertTrue(list.get(0) instanceof TrueFalse);
        assertEquals("Aardvarks are mammals", list.get(0).getText());
        assertTrue(list.get(1) instanceof TrueFalse);
        assertEquals("Zebras are birds", list.get(1).getText());

        assertTrue(list.get(2) instanceof MultipleChoice);
        assertEquals("Pick fruits", list.get(2).getText());
        assertTrue(list.get(3) instanceof MultipleChoice);
        assertEquals("Pick green items", list.get(3).getText());

        assertTrue(list.get(4) instanceof MultipleSelect);
        assertEquals("Pick fruits", list.get(4).getText());
        assertTrue(list.get(5) instanceof MultipleSelect);
        assertEquals("Pick red items", list.get(5).getText());

        assertTrue(list.get(6) instanceof Likert);
        assertEquals("I enjoy coding.", list.get(6).getText());
        assertTrue(list.get(7) instanceof Likert);
        assertEquals("Teamwork is valuable.", list.get(7).getText());
    }
}
