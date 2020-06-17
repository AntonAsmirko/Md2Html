package md2html.markup;

import java.util.List;

public class Underline extends AbstractParagraph {

    public Underline(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<u>", "</u>");
    }
}
