package questions;

public interface Question extends Comparable<Question> {
    String CORRECT = "Correct";
    String INCORRECT = "Incorrect";// return question text

    public String getText();

    // evaluate answer: return correct or incorrect
    public String answer(String answer);
}
