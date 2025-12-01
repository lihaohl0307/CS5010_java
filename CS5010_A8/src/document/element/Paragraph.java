package document.element;

import java.util.ArrayList;
import java.util.List;

import document.DocumentVisitor;

/**
 * A representation of a paragraph in our document.
 */
public class Paragraph implements TextElement {

    /** The content of the paragraph. */
    private List<BasicText> content;

    /** Default constructor. */
    public Paragraph() {
        content = new ArrayList<>();
    }

    /**
     * Add a piece of basic text to this paragraph.
     *
     * @param text the text element to add
     */
    public void add(BasicText text) {
        content.add(text);
    }

    /**
     * Get the content of this paragraph as a list of {@link BasicText}
     * elements.
     *
     * @return the paragraph content
     */
    public List<BasicText> getContent() {
        return content;
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder("");
        for (BasicText element : content) {
            result.append(element.getText()).append(" ");
        }
        return result.toString().trim();
    }

    @Override
    public <R> R accept(DocumentVisitor<R> visitor) {
        return visitor.visitParagraph(this);
    }
}
