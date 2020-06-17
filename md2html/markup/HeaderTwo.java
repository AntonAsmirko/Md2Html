package md2html.markup;

import java.util.List;

public class HeaderTwo extends AbstractParagraph {

    public HeaderTwo(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<h2>", "</h2>\n");
    }

}