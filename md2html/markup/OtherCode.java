package md2html.markup;

import java.util.HashSet;
import java.util.List;

public class OtherCode extends AbstractParagraph {

    public OtherCode(List<AbstractParagraph> list) {
        super(list);
    }

    private HashSet<String> specialSymbols = new HashSet<>(List.of(">", "<", "&"));

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        String str = elements.get(0).toHtml(new StringBuilder()).toString().trim();
        if (elements.size() == 1 && specialSymbols.contains(str)) {
            elements.clear();
            if (str.equals("<")) {
                elements.add(0, new Text("&lt;"));
            } else if (str.equals(">")) {
                elements.add(0, new Text("&gt;"));
            } else if (str.equals("&")) {
                elements.add(0, new Text("&amp;"));
            }
        }
        return super.toHtml(sb, "<code>", "</code>");
    }

}