package md2html.markup;

import java.util.*;

public class Text extends AbstractParagraph {
    private String text;

    public Text(String text) {
        super(new LinkedList<>());
        this.text = text;
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        sb.append(text);
        return sb;
    }

    

}