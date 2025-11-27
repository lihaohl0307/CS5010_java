package document;

import java.util.ArrayList;
import java.util.List;

import document.element.TextElement;

/**
 * A representation of a rich-text document composed of a sequence of
 * {@link TextElement}s.
 */
public class Document {

    private List<TextElement> content;

    public Document() {
        content = new ArrayList<>();
    }

    /**
     * Add a new element to the end of this document.
     *
     * @param e the element to add
     */
    public void add(TextElement e) {
        content.add(e);
    }

    /**
     * Accept the given visitor and apply it to every element in this document
     * in order. The visitor is responsible for accumulating any results.
     *
     * @param visitor the visitor to accept
     * @param <R>     the return type of the visitor's methods
     * @return the last value returned from the visitor (many visitors will
     *         simply ignore this and store their result in fields)
     */
    public <R> R accept(DocumentVisitor<R> visitor) {
        R result = null;
        for (TextElement e : content) {
            result = e.accept(visitor);
        }
        return result;
    }

    /**
     * Count the number of words in this document.
     *
     * @return the total word count
     */
    public int countWords() {
        WordCountVisitor visitor = new WordCountVisitor();
        for (TextElement e : content) {
            e.accept(visitor);
        }
        return visitor.getCount();
    }

    /**
     * Produce a textual representation of this document using the provided
     * string-building visitor. The visitor is applied to every element and
     * is expected to accumulate its result internally and expose it via
     * {@link Object#toString()}.
     *
     * @param visitor a visitor that builds up a string representation
     * @return the string produced by the visitor
     */
    public String toText(DocumentVisitor<String> visitor) {
        for (TextElement e : content) {
            e.accept(visitor);
        }
        return visitor.toString();
    }
}
