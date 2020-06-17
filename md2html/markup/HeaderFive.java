package md2html.markup;

import java.util.LinkedList;
import java.util.List;

public class HeaderFive extends AbstractParagraph {

    public HeaderFive(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<h5>", "</h5>\n");
    }
}
