package md2html.markup;

import java.util.List;

public class SuperParagraph extends AbstractParagraph {

    public SuperParagraph(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "", "");
    }

}