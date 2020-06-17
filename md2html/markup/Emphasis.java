package md2html.markup;

import java.util.*;

public class Emphasis extends AbstractParagraph {

    public Emphasis(List<AbstractParagraph> list) {
        super(list);
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        return super.toHtml(sb, "<em>", "</em>");
    }


}