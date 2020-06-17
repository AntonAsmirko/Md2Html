package md2html.markup;

import java.util.LinkedList;
import java.util.List;

public class HeaderFour extends AbstractParagraph {

    public HeaderFour(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<h4>", "</h4>\n");
    }

}