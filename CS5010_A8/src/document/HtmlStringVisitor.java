package document;

import document.element.BasicText;
import document.element.BoldText;
import document.element.Heading;
import document.element.HyperText;
import document.element.ItalicText;
import document.element.Paragraph;

/**
 * Visitor that builds an HTML representation of a document.
 * Each element is rendered on its own line (separated by '\n').
 */
public class HtmlStringVisitor implements DocumentVisitor<String> {

    private final StringBuilder builder = new StringBuilder();
    private boolean first = true;

    private void newline() {
        if (!first) {
            builder.append("\n");
        }
        first = false;
    }

    @Override
    public String visitBasicText(BasicText current) {
        newline();
        builder.append(current.getText());
        return builder.toString();
    }

    @Override
    public String visitBoldText(BoldText current) {
        newline();
        builder.append("<b>").append(current.getText()).append("</b>");
        return builder.toString();
    }

    @Override
    public String visitHeading(Heading current) {
        newline();
        int level = current.getLevel();
        if (level < 1) {
            level = 1;
        } else if (level > 6) {
            level = 6;
        }
        builder.append("<h").append(level).append(">")
                .append(current.getText())
                .append("</h").append(level).append(">");
        return builder.toString();
    }

    @Override
    public String visitHyperText(HyperText current) {
        newline();
        builder.append("<a href=\"")
                .append(current.getUrl())
                .append("\">")
                .append(current.getText())
                .append("</a>");
        return builder.toString();
    }

    @Override
    public String visitItalicText(ItalicText current) {
        newline();
        builder.append("<i>").append(current.getText()).append("</i>");
        return builder.toString();
    }

    @Override
    public String visitParagraph(Paragraph current) {
        newline();
        builder.append("<p>")
                .append(current.getText())
                .append("</p>");
        return builder.toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
