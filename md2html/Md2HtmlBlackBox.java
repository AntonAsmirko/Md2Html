package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import md2html.markup.*;

class Md2HtmlBlackBox {
    private BufferedReader br;
    private BufferedWriter bw;
    private TextTree textTree = new TextTree();
    private LinkedList<AbstractParagraph> markQueue = new LinkedList<>();
    private SuperParagraph superParagraph = new SuperParagraph(new LinkedList<>());
    private StringBuilder buffer = new StringBuilder();
    private Stack<String> markStack = new Stack<>();
    private ArrayList<StringBuilder> paragraphs = new ArrayList<>();
    private Character prev;
    private boolean tag = false;

    Md2HtmlBlackBox(String input, String output) throws Exception {
        readAllParagraphs(input);
        writeOutput(output, execute());
    }

    private String execute() throws Exception {
        for (StringBuilder textBlock : paragraphs) {
            TextTree.Pair prgType = textTree.headerOrParagraph(textBlock);
            markQueue.add(prgType.prg);
            if (prgType.i != null) {
                textBlock = new StringBuilder(textBlock.substring(prgType.i + 1));
            }
            char[] textBlockChars = textBlock.toString().toCharArray();
            for (int i = 0; i < textBlockChars.length; i++) {
                if (textTree.isNext(textBlockChars[i])) {
                    if (!tag) {
                        tag = true;
                    }
                    textTree.goToNext(textBlockChars[i]);
                } else {
                    if (tag) {
                        tag = false;
                        if (textTree.existsInTextSet(textTree.getCurValue())) {
                            if (!processTag(textTree.getCurValue(),
                                    i != textBlockChars.length - 1 ? textBlockChars[i] : null)) {
                                if (prev == '\\') {
                                    buffer = buffer.replace(buffer.length() - 1, buffer.length(), "");
                                }
                                buffer.append(textTree.getCurValue());
                            }
                            if (textTree.existsInGoodSymbolsSet(String.valueOf(textBlockChars[i]))) {
                                textTree.goToRoot();
                                i--;
                                continue;
                            }
                        } else {
                            buffer.append(textTree.getCurValue());
                        }
                        textTree.goToRoot();
                    }
                    buffer.append(textTree.containsInSpecialSymbols(textBlockChars[i]) ?
                            textTree.getSpecialSymbol(textBlockChars[i]) : textBlockChars[i]);
                    prev = textBlockChars[i];
                }
            }
            dumpBuffer();
            processTag(textTree.getCurValue(), null);
            superParagraph.addElements(new LinkedList<>(List.of(markQueue.getLast())));
            markQueue.clear();
            if (markStack.size() != 0) {
                throw new Exception();
            }
        }
        return superParagraph.toHtml(new StringBuilder()).toString();
    }

    //Проверка на одиночность,
    //Закрывающий/открывающий, если открывающий, то добавит соответствующий класс в markQueue,
    //Если закрывающий, то закрыть последний элт. разметки
    //Возвращает true, если тег не одиночный, иначе - false
    private boolean processTag(String textTag, Character nextChar) {
        if (textTree.existsInTextSet(textTag)) {
            if (prev != null && (prev == '\\' || prev == ' ' && (nextChar == ' ' || nextChar == '\n'))) {
                return false;
            } else {
                dumpBuffer();
                if (isClotheTag(textTag)) {
                    processCloseTag();
                } else {
                    processOpenTag(textTag);
                }
                textTree.goToRoot();
            }
            return true;
        } else {
            return false;
        }
    }

    private void dumpBuffer() {
        if (buffer.length() != 0) {
            markQueue.getLast().addElements(new LinkedList<>(List.of(new Text(buffer.toString()))));
            buffer = new StringBuilder();
        }
    }

    private void processCloseTag() {
        markStack.pop();
        AbstractParagraph tempVar = markQueue.removeLast();
        markQueue.getLast().addElements(new LinkedList<>(List.of(tempVar)));
    }

    private void processOpenTag(String textTag) {
        markStack.push(textTag);
        markQueue.add(textTree.getConstructorByKey(textTag, new LinkedList<>()));
    }

    private boolean isClotheTag(String textTag) {
        return markStack.size() != 0 && markStack.peek().equals(textTag);
    }

    private void writeOutput(String output, String result) {
        try {
            bw = new BufferedWriter(new FileWriter(output, StandardCharsets.UTF_8));
            bw.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readAllParagraphs(String input) {
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(input, StandardCharsets.UTF_8));
            String nextLine = br.readLine();
            while (nextLine != null) {
                if (nextLine.equals("")) {
                    if (sb.toString().equals("")) {
                        nextLine = br.readLine();
                        continue;
                    }
                    sb.replace(sb.length() - 1, sb.length(), "");
                    paragraphs.add(sb);
                    sb = new StringBuilder();
                } else {
                    sb.append(nextLine);
                    sb.append("\n");
                }
                nextLine = br.readLine();
            }
            sb.replace(sb.length() - 1, sb.length(), "");
            paragraphs.add(sb);

        } catch (Exception e) {
            System.out.println("Проблемы со считыванием из файла:");
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                System.out.println("Не удалось закрыть BufferedReader");
                e.printStackTrace();
            }
        }
    }
} 