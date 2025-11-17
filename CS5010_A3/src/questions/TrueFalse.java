package questions;

/** True/False question. */
public class TrueFalse implements Question {
    private final boolean correct;
    private final String text;

    public TrueFalse(String text, String correctAnswer) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text must be non-empty");
        }
        if (correctAnswer == null) {
            throw new IllegalArgumentException("correctAnswer must be provided");
        }
        String norm = correctAnswer.trim();
        if (!norm.equalsIgnoreCase("true") && !norm.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Valid correct answers: 'True' or 'False'");
        }
        this.text = text.trim();
        this.correct = Boolean.parseBoolean(norm);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String answer(String answer) {
        if (answer == null) return INCORRECT;
        String norm = answer.trim();
        if (!norm.equalsIgnoreCase("true") && !norm.equalsIgnoreCase("false")) {
            return INCORRECT; // invalid input is incorrect
        }
        boolean user = Boolean.parseBoolean(norm);
        return (user == correct) ? CORRECT : INCORRECT;
    }

    private static int rankOf(Question q) {
        if (q instanceof TrueFalse)      return 0;
        if (q instanceof MultipleChoice) return 1;
        if (q instanceof MultipleSelect) return 2;
        if (q instanceof Likert)         return 3;
        return Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(Question other) {
        int r1 = rankOf(this);
        int r2 = rankOf(other);
        if (r1 != r2) return Integer.compare(r1, r2);
        return this.getText().compareToIgnoreCase(other.getText());
    }


}