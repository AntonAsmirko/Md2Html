package md2html.markup;

import java.util.*;

public class Paragraph extends AbstractParagraph {

    public Paragraph() {
        super(new LinkedList<>());
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<p>", "</p>\n");
    }

}