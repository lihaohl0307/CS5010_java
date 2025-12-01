package document;

import document.element.BasicText;
import document.element.BoldText;
import document.element.Heading;
import document.element.HyperText;
import document.element.ItalicText;
import document.element.Paragraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the visitor-based rich text document.
 */
public class DocumentVisitorTest {

    @Test
    public void testWordCountOnEmptyDocumentIsZero() {
        Document doc = new Document();
        assertEquals(0, doc.countWords());
    }

    @Test
    public void testWordCountWithMixedElements() {
        Document doc = new Document();

        // 2 words
        doc.add(new BasicText("Hello world"));
        // 2 words
        doc.add(new BoldText("bold text"));
        // 1 word
        doc.add(new ItalicText("italic"));
        // 2 words (text), URL ignored by WordCountVisitor
        doc.add(new HyperText("click here", "https://example.com"));
        // 2 words
        doc.add(new Heading("My title", 2));

        // Paragraph: "para one two three" (4 words total)
        Paragraph p = new Paragraph();
        p.add(new BasicText("para one"));
        p.add(new BasicText("two three"));
        doc.add(p);

        // Expected total: 2 + 2 + 1 + 2 + 2 + 4 = 13
        assertEquals(13, doc.countWords());
    }

    @Test
    public void testBasicStringVisitorViaToText() {
        Document doc = new Document();

        doc.add(new Heading("Title", 1));                    // "Title"
        doc.add(new BasicText("Hello world"));               // "Hello world"
        doc.add(new HyperText("Click here", "https://x.y")); // "Click here"
        Paragraph p = new Paragraph();
        p.add(new BasicText("This"));
        p.add(new ItalicText("is"));
        p.add(new BoldText("cool"));
        doc.add(p);                                          // "This is cool"

        BasicStringVisitor visitor = new BasicStringVisitor();
        String result = doc.toText(visitor);

        // Elements are joined with a single space between elements.
        assertEquals("Title Hello world Click here This is cool", result);
    }

    @Test
    public void testHtmlStringVisitorViaToText() {
        Document doc = new Document();

        doc.add(new Heading("Title", 1));
        doc.add(new BasicText("Hello world"));
        doc.add(new HyperText("Click here", "https://example.com"));
        Paragraph p = new Paragraph();
        p.add(new BasicText("This"));
        p.add(new ItalicText("is"));
        p.add(new BoldText("cool"));
        doc.add(p);

        HtmlStringVisitor visitor = new HtmlStringVisitor();
        String html = doc.toText(visitor);

        String expected =
                "<h1>Title</h1>\n" +
                        "Hello world\n" +
                        "<a href=\"https://example.com\">Click here</a>\n" +
                        "<p>This <i>is</i> <b>cool</b></p>";

        assertEquals(expected, html);
    }

    @Test
    public void testDocumentAcceptDelegatesToVisitor() {
        Document doc = new Document();
        doc.add(new BasicText("one two"));
        doc.add(new BasicText("three"));

        WordCountVisitor wc = new WordCountVisitor();
        // Use Document.accept directly instead of countWords()
        doc.accept(wc);

        assertEquals(3, wc.getCount());
    }
}
