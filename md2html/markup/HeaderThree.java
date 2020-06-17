package md2html.markup;

import java.util.List;

public class HeaderThree extends AbstractParagraph {

    public HeaderThree(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<h3>", "</h3>\n");
    }

}