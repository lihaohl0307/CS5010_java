package document;

import java.util.ArrayList;
import java.util.List;

import document.element.TextElement;

public class Document {

    private List<TextElement> content;

    public Document() {
        content = new ArrayList<>();
    }

    public void add(TextElement e) {
        content.add(e);
    }

    /**
     * Accept the given visitor, applying it to every element in order.
     *
     * @param visitor the visitor
     * @param <R>     the result type of the visitor
     * @return the last value returned by the visitor (many visitors just ignore it)
     */
    public <R> R accept(DocumentVisitor<R> visitor) {
        R result = null;
        for (TextElement e : content) {
            result = e.accept(visitor);
        }
        return result;
    }

    /**
     * Count the number of words in this document using a WordCountVisitor.
     *
     * @return the total number of words
     */
    public int countWords() {
        WordCountVisitor wc = new WordCountVisitor();
        for (TextElement e : content) {
            e.accept(wc);
        }
        return wc.getCount();
    }

    /**
     * Produce a textual representation using the given "string visitor".
     * The visitor accumulates the result internally and exposes it via toString().
     *
     * @param visitor a visitor that builds up a string
     * @return the string produced by the visitor
     */
    public String toText(DocumentVisitor<String> visitor) {
        for (TextElement e : content) {
            e.accept(visitor);
        }
        return visitor.toString();
    }
}
