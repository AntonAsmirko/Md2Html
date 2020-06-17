package md2html;

import java.util.*;

import md2html.markup.*;

class TextTree {

    private static HashMap<String, FunArr> markMap = new HashMap<>();

    static {
        markMap.put("*", new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new Emphasis(list);
            }
        });
        markMap.put("_", new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new Emphasis(list);
            }
        });
        markMap.put("**", new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new Strong(list);
            }
        });
        markMap.put("__", new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new Strong(list);
            }
        });
        markMap.put("--", new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new Strikeout(list);
            }
        });
        markMap.put("`", new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new OtherCode(list);
            }
        });

        markMap.put("++", new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new md2html.markup.Underline(list);
            }
        });

    }

    private static HashMap<Integer, FunArr> headerMap = new HashMap<>();

    static {
        headerMap.put(1, new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new HeaderOne(list);
            }
        });
        headerMap.put(2, new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new HeaderTwo(list);
            }
        });
        headerMap.put(3, new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new HeaderThree(list);
            }
        });
        headerMap.put(4, new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new HeaderFour(list);
            }
        });
        headerMap.put(5, new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new HeaderFive(list);
            }
        });
        headerMap.put(6, new FunArr() {
            public AbstractParagraph sameConstructor(LinkedList<AbstractParagraph> list) {
                return new HeaderSix(list);
            }
        });
    }

    private static HashMap<Character, String> specialSymbols = new HashMap<>();

    static {
        specialSymbols.put('<', "&lt;");
        specialSymbols.put('>', "&gt;");
        specialSymbols.put('&', "&amp;");
    }

    private HashSet<String> goodSymbolsSet = new HashSet<>(List.of("*", "_", "**", "__", "--", "`", "-", "++"));
    private Root root = new Root();
    private TextTreeNode current = root;


    TextTree() {
        root.setChildren(new LinkedList<TextTreeNode>(List.of(
                new Minus(new LinkedList<TextTreeNode>(List.of(
                        new Minus("--")
                )), "-"),
                new Star(new LinkedList<TextTreeNode>(List.of(
                        new Star("**")
                )), "*"),
                new Code("`"),
                new Underscore(new LinkedList<TextTreeNode>(List.of(
                        new Underscore("__")
                )), "_"),
                new Underline(new LinkedList<TextTreeNode>(List.of(
                        new Underline("++")
                )), "+")
        )));
    }

    String getCurValue() {
        return current.getValue();
    }

    boolean isNext(char symbol) {
        for (TextTreeNode child : current.getChildren()) {
            if (child.getPattern() == symbol) {
                return true;
            }
        }
        return false;
    }

    Pair headerOrParagraph(StringBuilder textBlock) {
        char[] textBlockCharacter = textBlock.toString().toCharArray();
        int i = 0;
        while (i < 7 && textBlockCharacter[i] == '#') {
            i++;
        }
        if (textBlockCharacter[0] != '#') {
            return new Pair(new Paragraph(), null);
        } else if (textBlockCharacter[i] == ' ') {
            return new Pair(headerMap.get(i).sameConstructor(new LinkedList<>()), i);
        } else {
            return new Pair(new Paragraph(), null);
        }
    }

    void goToNext(char symbol) {
        for (TextTreeNode child : current.getChildren()) {
            if (child.getPattern() == symbol) {
                current = child;
                return;
            }
        }
        current = root;
        goToNext(symbol);
    }

    void goToRoot() {
        current = root;
    }

    boolean existsInTextSet(String str) {
        return markMap.containsKey(str);
    }

    AbstractParagraph getConstructorByKey(String key, LinkedList<AbstractParagraph> list) {
        return markMap.get(key).sameConstructor(list);
    }

    boolean existsInGoodSymbolsSet(String symbol) {
        return goodSymbolsSet.contains(symbol);
    }

    boolean containsInSpecialSymbols(Character ch) {
        return specialSymbols.containsKey(ch);
    }

    String getSpecialSymbol(Character ch) {
        return specialSymbols.get(ch);
    }

    class Pair {
        AbstractParagraph prg;
        Integer i;

        Pair(AbstractParagraph prg, Integer i) {
            this.prg = prg;
            this.i = i;
        }
    }

}

abstract class TextTreeNode {
    private char pattern;
    private String value = "";
    private List<TextTreeNode> children;

    char getPattern() {
        return pattern;
    }

    TextTreeNode(char pattern, List<TextTreeNode> children) {
        this.pattern = pattern;
        this.children = children;
    }

    TextTreeNode(char pattern, List<TextTreeNode> children, String value) {
        this(pattern, children);
        this.value = value;
    }

    List<TextTreeNode> getChildren() {
        return this.children;
    }

    String getValue() {
        return value;
    }

    void setChildren(List<TextTreeNode> children) {
        this.children = children;
    }

}

class Star extends TextTreeNode {

    Star(List<TextTreeNode> children, String value) {
        super('*', children, value);
    }

    Star(String value) {
        this(new LinkedList<>(), value);
    }

}

class Minus extends TextTreeNode {

    Minus(List<TextTreeNode> children, String value) {
        super('-', children, value);
    }

    Minus(String value) {
        this(new LinkedList<>(), value);
    }

}

class Underscore extends TextTreeNode {

    Underscore(List<TextTreeNode> children, String value) {
        super('_', children, value);
    }

    Underscore(String value) {
        this(new LinkedList<>(), value);
    }

}

class Code extends TextTreeNode {

    Code(List<TextTreeNode> children, String value) {
        super('`', children, value);
    }

    Code(String value) {
        this(new LinkedList<>(), value);
    }

}

class Root extends TextTreeNode {

    Root() {
        super('0', new LinkedList<>());
    }

}

class Underline extends TextTreeNode {

    Underline(List<TextTreeNode> children, String value) {
        super('+', children, value);
    }

    Underline(String value) {
        super('+', new LinkedList<>(), value);
    }
}
