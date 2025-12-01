package document;

import document.element.*;

public class WordCountVisitor implements DocumentVisitor<Integer> {

    private int count = 0;

    private void addWords(String text) {
        if (text == null) {
            return;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return;
        }
        String[] parts = trimmed.split("\\s+");
        count += parts.length;
    }

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
        // Paragraph#getText() already concatenates inner texts
        addWords(current.getText());
        return count;
    }
}
