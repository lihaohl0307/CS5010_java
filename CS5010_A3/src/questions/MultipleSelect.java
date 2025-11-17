package questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultipleSelect implements Question {
    private final String text;
    private final List<String> options;    // 1-based display order
    private final Set<Integer> correctSet; // indices in 1.N

    // ===== Canonical validating ctor with List (public) =====
    public MultipleSelect(String text, String correct, List<String> options) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text must be non-empty");
        }
        if (options == null || options.size() < 3 || options.size() > 8) {
            throw new IllegalArgumentException("MultipleSelect must have 3 to 8 options");
        }
        this.text = text.trim();

        List<String> tmp = new ArrayList<>(options.size());
        for (String opt : options) {
            if (opt == null || opt.trim().isEmpty()) {
                throw new IllegalArgumentException("Option text must be non-empty");
            }
            tmp.add(opt.trim());
        }
        this.options = Collections.unmodifiableList(tmp);

        this.correctSet = parseIndicesAsSet(correct, this.options.size());
        if (this.correctSet.isEmpty()) {
            throw new IllegalArgumentException("At least one correct option must be specified");
        }
    }

    // ===== Private canonical varargs to service overloads =====
    private MultipleSelect(String text, String correct, String... opts) {
        this(text, correct, Arrays.asList(opts));
    }

    // ===== Fixed-arity overloads (3..8 options) =====
    public MultipleSelect(String text, String correct, String o1, String o2, String o3) {
        this(text, correct, new String[]{o1, o2, o3});
    }
    public MultipleSelect(String text, String correct, String o1, String o2, String o3, String o4) {
        this(text, correct, new String[]{o1, o2, o3, o4});
    }
    public MultipleSelect(String text, String correct, String o1, String o2, String o3, String o4, String o5) {
        this(text, correct, new String[]{o1, o2, o3, o4, o5});
    }
    public MultipleSelect(String text, String correct, String o1, String o2, String o3, String o4, String o5, String o6) {
        this(text, correct, new String[]{o1, o2, o3, o4, o5, o6});
    }
    public MultipleSelect(String text, String correct, String o1, String o2, String o3, String o4, String o5, String o6, String o7) {
        this(text, correct, new String[]{o1, o2, o3, o4, o5, o6, o7});
    }
    public MultipleSelect(String text, String correct, String o1, String o2, String o3, String o4, String o5, String o6, String o7, String o8) {
        this(text, correct, new String[]{o1, o2, o3, o4, o5, o6, o7, o8});
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String answer(String answer) {
        try {
            Set<Integer> user = parseIndicesAsSet(answer, options.size());
            return user.equals(correctSet) ? CORRECT : INCORRECT;
        } catch (Exception e) {
            return INCORRECT; // invalid inputs are incorrect
        }
    }

    private static Set<Integer> parseIndicesAsSet(String s, int max) {
        if (s == null) return Collections.emptySet();
        String trimmed = s.trim();
        if (trimmed.isEmpty()) return Collections.emptySet();
        String[] parts = trimmed.split("\\s+");
        Set<Integer> set = new HashSet<>();
        for (String p : parts) {
            int idx = Integer.parseInt(p);
            if (idx < 1 || idx > max) throw new IllegalArgumentException("index out of range: " + idx);
            set.add(idx); // duplicates collapse automatically
        }
        return set;
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
