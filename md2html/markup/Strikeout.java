package md2html.markup;

import java.util.*;

public class Strikeout extends AbstractParagraph {

    public Strikeout(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<s>", "</s>");
    }

}