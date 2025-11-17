package questions;

public class Likert implements Question {
    private final String text;

    public Likert(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text must be non-empty");
        }
        this.text = text.trim();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String answer(String answer) {
        try {
            int v = Integer.parseInt(answer.trim());
            return (v >= 1 && v <= 5) ? CORRECT : INCORRECT;
        } catch (Exception e) {
            return INCORRECT;
        }
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
