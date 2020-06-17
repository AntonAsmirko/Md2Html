package md2html.markup;

import java.util.List;

public class HeaderSix extends AbstractParagraph {

    public HeaderSix(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<h6>", "</h6>\n");
    }
}
