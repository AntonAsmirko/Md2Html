package md2html.markup;

import java.util.LinkedList;
import java.util.List;

public class HeaderOne extends AbstractParagraph {

    public HeaderOne(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<h1>", "</h1>\n");
    }

}