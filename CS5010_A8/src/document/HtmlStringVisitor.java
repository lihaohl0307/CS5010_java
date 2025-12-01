package document;

import document.element.BasicText;
import document.element.BoldText;
import document.element.Heading;
import document.element.HyperText;
import document.element.ItalicText;
import document.element.Paragraph;

import java.util.List;

/**
 * Visitor that builds an HTML representation of a document.
 * Each element appears on its own line, separated by '\n'.
 */
public class HtmlStringVisitor implements DocumentVisitor<String> {

    private final StringBuilder builder = new StringBuilder();
    private boolean firstLine = true;

    private void newLine() {
        if (!firstLine) {
            builder.append("\n");
        }
        firstLine = false;
    }

    @Override
    public String visitBasicText(BasicText current) {
        newLine();
        builder.append(current.getText());
        return builder.toString();
    }

    @Override
    public String visitBoldText(BoldText current) {
        newLine();
        builder.append("<b>").append(current.getText()).append("</b>");
        return builder.toString();
    }

    @Override
    public String visitHeading(Heading current) {
        newLine();
        int level = current.getLevel();
        if (level < 1) level = 1;
        if (level > 6) level = 6;
        builder.append("<h").append(level).append(">")
                .append(current.getText())
                .append("</h").append(level).append(">");
        return builder.toString();
    }

    @Override
    public String visitHyperText(HyperText current) {
        newLine();
        builder.append("<a href=\"")
                .append(current.getUrl())
                .append("\">")
                .append(current.getText())
                .append("</a>");
        return builder.toString();
    }

    @Override
    public String visitItalicText(ItalicText current) {
        newLine();
        builder.append("<i>").append(current.getText()).append("</i>");
        return builder.toString();
    }

    @Override
    public String visitParagraph(Paragraph current) {
        newLine();

        // Build inner HTML, preserving bold/italic/hypertext inside the paragraph
        StringBuilder inner = new StringBuilder();
        boolean first = true;

        List<BasicText> content = current.getContent();
        for (BasicText bt : content) {
            if (!first) {
                inner.append(" ");
            }
            if (bt instanceof BoldText) {
                inner.append("<b>").append(bt.getText()).append("</b>");
            } else if (bt instanceof ItalicText) {
                inner.append("<i>").append(bt.getText()).append("</i>");
            } else if (bt instanceof HyperText) {
                HyperText ht = (HyperText) bt;
                inner.append("<a href=\"")
                        .append(ht.getUrl())
                        .append("\">")
                        .append(ht.getText())
                        .append("</a>");
            } else {
                inner.append(bt.getText());
            }
            first = false;
        }

        builder.append("<p>").append(inner).append("</p>");
        return builder.toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
