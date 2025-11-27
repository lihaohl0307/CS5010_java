package document;

import document.element.BasicText;
import document.element.BoldText;
import document.element.Heading;
import document.element.HyperText;
import document.element.ItalicText;
import document.element.Paragraph;

/**
 * Visitor that builds a simple, space-separated plain-text representation
 * of a document. All formatting information is ignored.
 */
public class BasicStringVisitor implements DocumentVisitor<String> {

    private final StringBuilder builder = new StringBuilder();
    private boolean first = true;

    private void append(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        if (!first) {
            builder.append(" ");
        }
        builder.append(text);
        first = false;
    }

    @Override
    public String visitBasicText(BasicText current) {
        append(current.getText());
        return builder.toString();
    }

    @Override
    public String visitBoldText(BoldText current) {
        append(current.getText());
        return builder.toString();
    }

    @Override
    public String visitHeading(Heading current) {
        append(current.getText());
        return builder.toString();
    }

    @Override
    public String visitHyperText(HyperText current) {
        append(current.getText());
        return builder.toString();
    }

    @Override
    public String visitItalicText(ItalicText current) {
        append(current.getText());
        return builder.toString();
    }

    @Override
    public String visitParagraph(Paragraph current) {
        append(current.getText());
        return builder.toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
