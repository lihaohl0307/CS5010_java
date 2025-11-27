package document;

import document.element.BasicText;
import document.element.BoldText;
import document.element.Heading;
import document.element.HyperText;
import document.element.ItalicText;
import document.element.Paragraph;

/**
 * A visitor that counts the number of words in a document.
 * It only looks at the text of TextElement objects.
 */
public class WordCountVisitor implements DocumentVisitor<Integer> {

    private int count = 0;

    private void addWords(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        String[] parts = text.trim().split("\\s+");
        for (String p : parts) {
            if (!p.isEmpty()) {
                count++;
            }
        }
    }

    /**
     * Get the total number of words seen so far.
     *
     * @return word count
     */
    public int getCount() {
        return count;
    }

    @Override
    public Integer visitBasicText(BasicText current) {
        addWords(current.getText());
        return count;
    }

    @Override
    public Integer visitBoldText(BoldText current) {
        addWords(current.getText());
        return count;
    }

    @Override
    public Integer visitHeading(Heading current) {
        addWords(current.getText());
        return count;
    }

    @Override
    public Integer visitHyperText(HyperText current) {
        addWords(current.getText());
        return count;
    }

    @Override
    public Integer visitItalicText(ItalicText current) {
        addWords(current.getText());
        return count;
    }

    @Override
    public Integer visitParagraph(Paragraph current) {
        // Paragraph#getText() returns the concatenated text of its contents
        addWords(current.getText());
        return count;
    }
}
