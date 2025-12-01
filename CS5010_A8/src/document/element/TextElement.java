package document.element;

import document.DocumentVisitor;

/**
 * Interface that represents an element in our document.
 */
public interface TextElement {

    /**
     * Returns the text contained within the element.
     *
     * @return the text
     */
    String getText();

    /**
     * Accept a visitor.
     *
     * @param visitor the visitor
     * @param <R>     the result type of the visitor
     * @return the value returned by the visitor for this element
     */
    <R> R accept(document.DocumentVisitor<R> visitor);
}
