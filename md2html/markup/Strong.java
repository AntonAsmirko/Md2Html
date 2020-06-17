package md2html.markup;

import java.util.List;

public class Strong extends AbstractParagraph {

    public Strong(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<strong>", "</strong>");
    }

}