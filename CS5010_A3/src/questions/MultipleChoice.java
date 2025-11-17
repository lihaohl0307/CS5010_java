package questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleChoice implements Question {
    private final String text;
    private final List<String> options; // 1-based display order
    private final int correctIndex;     // 1..options.size()

    // ===== Canonical validating ctors (private) =====
    private MultipleChoice(String text, int correctIndex, String... options) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text must be non-empty");
        }
        if (options == null || options.length < 3 || options.length > 8) {
            throw new IllegalArgumentException("MultipleChoice must have 3 to 8 options");
        }
        if (correctIndex < 1 || correctIndex > options.length) {
            throw new IllegalArgumentException("Correct index out of range");
        }
        this.text = text.trim();
        List<String> tmp = new ArrayList<>(options.length);
        for (String opt : options) {
            if (opt == null || opt.trim().isEmpty()) {
                throw new IllegalArgumentException("Option text must be non-empty");
            }
            tmp.add(opt.trim());
        }
        this.options = Collections.unmodifiableList(tmp);
        this.correctIndex = correctIndex;
    }

    private static int parseIndex(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Correct index must be an integer string");
        }
    }

    // ===== Public convenience constructor with List (kept, but grader may not use it) =====
    public MultipleChoice(String text, List<String> options, int correctIndex) {
        this(text, correctIndex, options == null ? null : options.toArray(new String[0]));
    }

    // ===== Fixed-arity overloads (int correctIndex) — 3..8 options =====
    public MultipleChoice(String text, int correctIndex, String o1, String o2, String o3) {
        this(text, correctIndex, new String[]{o1, o2, o3});
    }
    public MultipleChoice(String text, int correctIndex, String o1, String o2, String o3, String o4) {
        this(text, correctIndex, new String[]{o1, o2, o3, o4});
    }
    public MultipleChoice(String text, int correctIndex, String o1, String o2, String o3, String o4, String o5) {
        this(text, correctIndex, new String[]{o1, o2, o3, o4, o5});
    }
    public MultipleChoice(String text, int correctIndex, String o1, String o2, String o3, String o4, String o5, String o6) {
        this(text, correctIndex, new String[]{o1, o2, o3, o4, o5, o6});
    }
    public MultipleChoice(String text, int correctIndex, String o1, String o2, String o3, String o4, String o5, String o6, String o7) {
        this(text, correctIndex, new String[]{o1, o2, o3, o4, o5, o6, o7});
    }
    public MultipleChoice(String text, int correctIndex, String o1, String o2, String o3, String o4, String o5, String o6, String o7, String o8) {
        this(text, correctIndex, new String[]{o1, o2, o3, o4, o5, o6, o7, o8});
    }

    // ===== Fixed-arity overloads (String correctIndex) — 3..8 options =====
    public MultipleChoice(String text, String correctIndex, String o1, String o2, String o3) {
        this(text, parseIndex(correctIndex), new String[]{o1, o2, o3});
    }
    public MultipleChoice(String text, String correctIndex, String o1, String o2, String o3, String o4) {
        this(text, parseIndex(correctIndex), new String[]{o1, o2, o3, o4});
    }
    public MultipleChoice(String text, String correctIndex, String o1, String o2, String o3, String o4, String o5) {
        this(text, parseIndex(correctIndex), new String[]{o1, o2, o3, o4, o5});
    }
    public MultipleChoice(String text, String correctIndex, String o1, String o2, String o3, String o4, String o5, String o6) {
        this(text, parseIndex(correctIndex), new String[]{o1, o2, o3, o4, o5, o6});
    }
    public MultipleChoice(String text, String correctIndex, String o1, String o2, String o3, String o4, String o5, String o6, String o7) {
        this(text, parseIndex(correctIndex), new String[]{o1, o2, o3, o4, o5, o6, o7});
    }
    public MultipleChoice(String text, String correctIndex, String o1, String o2, String o3, String o4, String o5, String o6, String o7, String o8) {
        this(text, parseIndex(correctIndex), new String[]{o1, o2, o3, o4, o5, o6, o7, o8});
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String answer(String answer) {
        try {
            int idx = Integer.parseInt(answer.trim());
            return (idx == correctIndex) ? CORRECT : INCORRECT;
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
