package md2html.markup;

import java.util.*;

public abstract class AbstractParagraph implements Mark {

    public boolean isClosed = false;

    List<AbstractParagraph> elements;

    public AbstractParagraph(List<AbstractParagraph> list) {
        this.elements = list;
    }

    public StringBuilder toHtml(StringBuilder sb, String open, String close){
        sb.append(open);
        for(AbstractParagraph element : elements){
            sb.append(element.toHtml(new StringBuilder()).toString());
        }
        sb.append(close);
        return sb;
    }



    public void addElements(List<AbstractParagraph> elements){
        this.elements.addAll(elements);
    }
}